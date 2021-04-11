package io.molkars.vesper.event.listeners;

import com.sun.istack.NotNull;
import io.molkars.vesper.Vesper;
import io.molkars.vesper.database.model.VesperUser;
import io.molkars.vesper.src.EventsListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class RealityShardListener implements EventsListener {

  @Override
  public void onBlockBreak(BlockBreakEvent e) {
    final Vesper vesper = Vesper.getInstance();
    if (vesper.random.nextDouble() < 0.002) {
      final Player player = e.getPlayer();
      final String uuid = player.getUniqueId().toString();
      final EntityManager em = vesper.entityManager;

      VesperUser user = em.find(VesperUser.class, uuid);

      final EntityTransaction txn = em.getTransaction();
      txn.begin();
      user = getVesperUser(uuid, em, user);

      int count = user.realityShardCount();
      user.realityShardCount(count + 1);
      txn.commit();

      player.sendMessage("You found a " + ChatColor.BOLD + ChatColor.DARK_PURPLE + "reality shard" + ChatColor.RESET + String.format("! You now have %s reality shards.", count));
    }
  }

  @NotNull
  private VesperUser getVesperUser(String uuid, EntityManager em, VesperUser user) {
    if (user == null) {
      final EntityTransaction txn = em.getTransaction();
      txn.begin();
      user = new VesperUser(uuid);
      user.realityShardCount(0);
      em.persist(user);
      txn.commit();
    }
    return user;
  }
}

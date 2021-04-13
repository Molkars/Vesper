package io.molkars.vesper.command.commands;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.command.CommandBase;
import io.molkars.vesper.database.model.Home;
import io.molkars.vesper.database.model.VesperUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SetHomeCommand extends CommandBase {
  public SetHomeCommand() {
    super("sethome");
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("You must be a player to use this command");
      return true;
    }

    String homeName = args.length == 0 ? "home" : args[0];
    final Vesper vesper = Vesper.getInstance();
    final EntityManager em = vesper.entityManager;
    final Player player = (Player) sender;
    final String uuid = player.getUniqueId().toString();

    final VesperUser user = VesperUser.getOrCreate(uuid);

    final EntityTransaction txn = vesper.entityManager.getTransaction();
    txn.begin();
    final Home home = new Home(user, player.getLocation(), player.getWorld(), homeName);
    em.persist(home);
    txn.commit();

    return true;
  }
}
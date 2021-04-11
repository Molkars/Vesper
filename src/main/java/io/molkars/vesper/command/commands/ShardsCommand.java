package io.molkars.vesper.command.commands;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.database.model.VesperUser;
import io.molkars.vesper.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ShardsCommand extends CommandBase {
  public ShardsCommand() {
    super("shards");
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("You must be a player to use this command");
      return true;
    }

    // If the sender has permission to use /shards and /shards <user>
    final boolean hasAll = sender.hasPermission("vesper.commands.shards.*");
    // If the sender has permission to use /shards
    final boolean hasSelf = sender.hasPermission("vesper.commands.shards.self");


    // The player whose shards should be displayed
    Player player;

    if (args.length > 0) {
      // If the sender has permission to use /shards <user>
      final boolean hasOthers = sender.hasPermission("vesper.commands.shards.others");

      // Return if the user has invalid permissions
      if (!sender.isOp() && !hasAll && !hasOthers) {
        sender.sendMessage("You do not have permission to use this command");
        return true;
      }

      player = Bukkit.getPlayer(args[0]);
      if (player == null) {
        sender.sendMessage(String.format("Player not found: '%s'", args[0]));
        return true;
      }
    } else {
      player = (Player) sender;
    }

    if (!sender.isOp() && !hasAll && !hasSelf) {
      sender.sendMessage("You do not have permission to use this command");
      return true;
    }

    final Vesper vesper = Vesper.getInstance();
    final String uuid = player.getUniqueId().toString();

    final EntityManager em = vesper.entityManager;
    VesperUser user = em.find(VesperUser.class, uuid);

    if (user == null) {
      final EntityTransaction txn = em.getTransaction();
      txn.begin();
      user = new VesperUser(uuid);
      em.persist(user);
      txn.commit();
    }

    long realityShards = user.realityShardCount();
    player.sendMessage(String.format("You have %s reality shards.", realityShards));
    return true;
  }
}

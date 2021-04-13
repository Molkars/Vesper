package io.molkars.vesper.command.commands;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.command.CommandBase;
import io.molkars.vesper.database.model.Home;
import io.molkars.vesper.database.model.VesperUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class DeleteHomeCommand extends CommandBase {

  public DeleteHomeCommand() {
    super("delhome");
  }

  final TabCompleter tabCompleter = new DeleteHomeTabCompleter();

  @Override
  public TabCompleter tabCompleter() {
    return tabCompleter;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only a player can use this command");
      return true;
    }

    if (args.length == 0) {
      sender.sendMessage("You must include the name of the home you want to delete");
      return false;
    }

    final Vesper vesper = Vesper.getInstance();
    final Player player = (Player) sender;
    final String uuid = player.getUniqueId().toString();
    final VesperUser user = VesperUser.getOrCreate(uuid);

    final List<Home> homes = user.homes();
    final EntityTransaction txn = vesper.entityManager.getTransaction();
    txn.begin();
    for (final Home home : homes) {
      if (home.name().equalsIgnoreCase(args[0])) {
        vesper.entityManager.remove(home);
        break;
      }
    }
    txn.commit();

    return true;
  }
}

class DeleteHomeTabCompleter implements TabCompleter {
  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      return new ArrayList<>();
    }

    final List<String> strings = new ArrayList<>();
    final Player player = (Player) sender;
    final String uuid = player.getUniqueId().toString();

    final String filter = args.length == 0 ? "" : args[0];

    final VesperUser user = VesperUser.getOrCreate(uuid);
    for (final Home home : user.homes()) {
      if (home.name().toLowerCase().startsWith(filter.toLowerCase())) {
        strings.add(home.name());
      }
    }

    return strings;
  }
}

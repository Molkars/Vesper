package io.molkars.vesper.command.commands;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.command.CommandBase;
import io.molkars.vesper.database.model.Home;
import io.molkars.vesper.database.model.VesperUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class HomeCommand extends CommandBase {

  public HomeCommand() {
    super("home");
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

    final Vesper vesper = Vesper.getInstance();
    final Player player = (Player) sender;
    final String uuid = player.getUniqueId().toString();

    String homeName = "home";
    if (args.length > 0) {
      homeName = args[0].toLowerCase();
    }

    if (homeName.equalsIgnoreCase("bed")) {
      final Location location = player.getBedSpawnLocation();
      if (location == null) {
        player.sendMessage("You need to sleep in a bed to set your bed home");
      } else {
        player.teleport(player.getBedSpawnLocation());
      }
      return true;
    }

    final EntityManager em = vesper.entityManager;
    final VesperUser user = VesperUser.getOrCreate(uuid);
    final List<Home> homes = user.homes();

    @Nullable Home home = null;
    for (final Home _home : homes) {
      System.out.println("Home: " + _home);
      if (_home.name().equalsIgnoreCase(homeName)) {
        home = _home;
        break;
      }
    }

    if (home == null) {
      if (!homeName.equals("home")) {
        player.sendMessage(String.format("Could not find a home know as '%s'", homeName));
        return true;
      }

      player.sendMessage("You have not set a primary home yet");
      return true;
    }

    final World world = Bukkit.getWorld(home.worldName());
    if (world == null) {
      player.sendMessage("Unable to find the world for this home");
    }
    final Location location = new Location(world, home.positionX(), home.positionY(), home.positionZ());
    player.teleport(location);

    return true;
  }
}

class HomeTabCompleter implements TabCompleter {
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

package io.molkars.vesper.command;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.src.EventsListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class CommandBase implements CommandExecutor {
  public final String name;
  public final String[] aliases;

  protected CommandBase(String name) {
    this.name = name;
    this.aliases = new String[0];
  }

  protected CommandBase(String name, String... aliases) {
    this.name = name;
    this.aliases = aliases;
  }

  public @Nullable String[] getAliases() {
    return null;
  }

  public @Nullable TabCompleter getCompleter() {
    return null;
  }

  public @Nullable EventsListener getEventsListener() {
    return null;
  }

  public boolean register(Vesper vesper) {
    final PluginCommand command = vesper.getCommand(name);
    if (command == null) {
      return false;
    }

    command.setName(name);
    command.setExecutor(this);
    final String[] aliases = getAliases();
    final TabCompleter completer = getCompleter();
    final EventsListener listener = getEventsListener();

    if (aliases != null)
      command.setAliases(Arrays.asList(aliases));
    if (completer != null)
      command.setTabCompleter(completer);
    if (listener != null)
      vesper.eventListener.registerListener(listener);

    return true;
  }
}

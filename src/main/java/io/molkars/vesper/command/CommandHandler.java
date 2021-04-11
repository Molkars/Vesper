package io.molkars.vesper.command;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.command.commands.ShardsCommand;

import java.util.List;

public class CommandHandler {
  public CommandHandler() {
  }

  private final CommandBase shardsCommand = new ShardsCommand();

  private CommandBase[] getCommands() {
    return new CommandBase[]{
        shardsCommand,
    };
  }

  public void registerCommands() {
    Vesper vesper = Vesper.getInstance();
    List<String> commands = vesper.getConfig().getStringList("disabledCommands");

    for (CommandBase command : getCommands()) {
      boolean omitted = false;
      for (String cmd : commands) {
        if (cmd.equalsIgnoreCase(command.name)) {
          omitted = true;
          break;
        }
      }

      if (omitted) {
        continue;
      }

      boolean success = command.register(vesper);
      if (!success) {
        vesper.logger.severe(String.format("[%s] Unable to register command '%s'", vesper.getDescription().getName(), command.name));
      } else {
        vesper.logger.info(String.format("[%s] Registered command '%s'", vesper.getDescription().getName(), command.name));
      }
    }
  }
}

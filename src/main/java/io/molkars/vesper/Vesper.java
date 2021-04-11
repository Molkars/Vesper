package io.molkars.vesper;

import io.molkars.vesper.event.VesperEventListener;
import io.molkars.vesper.command.CommandHandler;
import io.molkars.vesper.database.DatabaseHandler;
import io.molkars.vesper.src.VesperJavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPluginLoader;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Vesper extends VesperJavaPlugin {
  public final Logger logger = getLogger();
  public CommandHandler cmdHandler;
  public VesperEventListener eventListener;
  public EntityManager entityManager;
  public DatabaseHandler dbHandler;

  public Vesper() {
    super();
  }

  protected Vesper(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
    super(loader, description, dataFolder, file);
  }

  @Override
  public void onEnable() {
    PluginManager manager = getServer().getPluginManager();

    // Load config if not exists
    if (!new File(getDataFolder(), "config.yml").exists()) {
      saveDefaultConfig();
    }

    logger.setLevel(Level.FINE);
    dbHandler = new DatabaseHandler();
    entityManager = dbHandler.entityManager();
    cmdHandler = new CommandHandler();
    eventListener = new VesperEventListener();

    cmdHandler.registerCommands();
    manager.registerEvents(eventListener, this);
  }

  @Override
  public void onDisable() {
    entityManager.close();
    saveConfig();
    logger.info(String.format("[%s] Disabled; Version: '%s'", getDescription().getName(), getDescription().getVersion()));
  }

  public static Vesper getInstance() {
    return Vesper.getPlugin(Vesper.class);
  }
}

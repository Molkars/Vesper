package io.molkars.vesper.src;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.logging.Logger;

public class VesperJavaPlugin extends JavaPlugin {
  public static final Logger logger = Logger.getLogger("Minecraft");

  public VesperJavaPlugin() {

  }

  protected VesperJavaPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
    super(loader, description, dataFolder, file);
  }

  @Nullable
  public <T> T getService(@NotNull Class<T> clazz) {
    RegisteredServiceProvider<T> service = getServer().getServicesManager().getRegistration(clazz);
    if (service != null) {
      return service.getProvider();
    }
    logger.severe(String.format("[%s] Unable to get service '%s'", getDescription().getName(), clazz.getName()));
    return null;
  }

  public boolean ensureHasDependency(String name) {
    if (getServer().getPluginManager().getPlugin(name) == null) {
      logger.severe(String.format("[%s] - Disabled due to missing dependency '%s'!", getDescription().getName(), name));
      getServer().getPluginManager().disablePlugin(this);
      return false;
    }
    logger.info(String.format("[%s] Dependency loaded: %s", getDescription().getName(), name));
    return true;
  }
}

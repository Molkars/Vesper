package io.molkars.vesper.database;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.database.model.*;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MariaDBDialect;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

public class DatabaseHandler {
  private final EntityManagerFactory entityManagerFactory;

  public DatabaseHandler() {
    final Vesper vesper = Vesper.getInstance();
    final Class<?>[] entities = new Class[]{
        VesperUser.class,
        VoidStorageInventory.class,
        VoidStorageItem.class,
        RealityShopInfo.class,
        Home.class
    };
    final MariaDbDataSource datasource = new MariaDbDataSource();
    try {
      final String host = vesper.getConfig().getString("database.host");
      final String port = vesper.getConfig().getString("database.port");
      final String database = vesper.getConfig().getString("database.database");
      final String username = vesper.getConfig().getString("database.username");
      final String password = vesper.getConfig().getString("database.password");

      datasource.setUrl(String.format("jdbc:mariadb://%s:%s/%s", host, port, database));
      datasource.setUser(username);
      datasource.setPassword(password);
    } catch (Exception e) {
      e.printStackTrace();
      vesper.logger.severe(String.format("[%s] Unable to connect to database: %s", vesper.getName(), e.getMessage()));
      vesper.getServer().getPluginManager().disablePlugin(vesper);
    }

    final Properties properties = new Properties();
    properties.put(Environment.HBM2DDL_AUTO, "update");
    properties.put(Environment.DIALECT, MariaDBDialect.class);
    properties.put(Environment.DATASOURCE, datasource);

    final VesperPersistenceUnitInfo unitInfo = new VesperPersistenceUnitInfo(
        vesper.getName(),
        Arrays.stream(entities).map(Class::getName).collect(Collectors.toList()),
        properties
    );

    entityManagerFactory = new EntityManagerFactoryBuilderImpl(
        new PersistenceUnitInfoDescriptor(unitInfo),
        new HashMap<>()
    ).build();
  }

  public EntityManager entityManager() {
    return entityManagerFactory.createEntityManager();
  }
}

package io.molkars.vesper.database;

import io.molkars.vesper.Vesper;
import org.hibernate.cfg.ImprovedNamingStrategy;

@SuppressWarnings("unused")
public class PrefixedNamingStrategy extends ImprovedNamingStrategy {
  @Override
  public String classToTableName(String className) {
    return addPrefix(super.classToTableName(className));
  }

  @Override
  public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
    return addPrefix(super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity, associatedEntityTable, propertyName));
  }

  @Override
  public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
    return addPrefix(super.logicalCollectionTableName(tableName, ownerEntityTable, associatedEntityTable, propertyName));
  }

  private String addPrefix(final String composedTableName) {
    final String prefix = Vesper.getInstance().getConfig().getString("database.tablePrefix", "");
    return prefix + composedTableName;
  }
}

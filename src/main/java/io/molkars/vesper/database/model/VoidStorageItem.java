package io.molkars.vesper.database.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@SuppressWarnings("unused")
@Table(name = "void_inventory_items")
public class VoidStorageItem implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, unique = true)
  private int id;

  @Id
  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      optional = false
  )
  @JoinColumn(
      name = "inventory_id",
      referencedColumnName = "id",
      columnDefinition = "bigint",
      nullable = false
  )
  private VoidStorageInventory inventory;
}

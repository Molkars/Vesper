package io.molkars.vesper.database.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@SuppressWarnings("unused")
@Table(name = "void_inventory_items")
@NamedQuery(name = "VoidStorageItem.findAll", query = "SELECT vsi FROM VoidStorageItem vsi ORDER BY vsi.id")
public class VoidStorageItem implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", length = 36, nullable = false, unique = true)
  private int id;

  @ManyToOne
  @JoinColumn(name = "inventory_id", referencedColumnName = "id")
  private VoidStorageInventory inventory;
}

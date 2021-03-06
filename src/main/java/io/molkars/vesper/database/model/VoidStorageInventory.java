package io.molkars.vesper.database.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SuppressWarnings("unused")
@Table(name = "void_inventories")
public class VoidStorageInventory implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(
      name = "id",
      nullable = false,
      unique = true,
      columnDefinition = "bigint"
  )
  private long id;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      optional = false
  )
  @JoinColumn(
      name = "associated_user",
      referencedColumnName = "uuid",
      columnDefinition = "char(36)",
      nullable = false,
      unique = true
  )
  VesperUser associatedUser;

  @OneToMany(
      orphanRemoval = true,
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER
  )
  @JoinColumn(
      name = "inventory_id",
      referencedColumnName = "id",
      columnDefinition = "bigint",
      nullable = false
  )
  private List<VoidStorageItem> items = new ArrayList<>();

  public void items(List<VoidStorageItem> items) throws IllegalStateException {
    if (items.size() > 45) {
      throw new IllegalStateException("An inventory can contain at most 45 items");
    }
    this.items = items;
  }
  public List<VoidStorageItem> items() {
    return items;
  }
  private boolean canAddItem() {
    return items.size() < 45;
  }
  private void addItem(VoidStorageItem item) throws IllegalStateException {
    if (canAddItem()) {
      items.add(item);
    } else {
      throw new IllegalStateException("An inventory can contain at most 45 items");
    }
  }
  private void removeItem(VoidStorageItem item) {
    items.remove(item);
  }

  public long id() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VoidStorageInventory that = (VoidStorageInventory) o;
    return id == that.id && Objects.equals(items, that.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, items);
  }
}
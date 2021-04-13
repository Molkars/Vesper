package io.molkars.vesper.database.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reality_shop_info")
@SuppressWarnings("unused")
public class RealityShopInfo implements Serializable {

  public RealityShopInfo(VesperUser user) {
    associatedUser = user;
    id = user.uuid();
  }

  protected RealityShopInfo() {

  }

  @Id
  @Column(
      name = "id",
      columnDefinition = "char(36)",
      length = 36,
      nullable = false,
      unique = true
  )
  private String id;

  @OneToOne(
      optional = false,
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true
  )
  @JoinColumn(
      name = "id",
      columnDefinition = "char(36)",
      nullable = false,
      unique = true,
      referencedColumnName = "uuid"
  )
  private VesperUser associatedUser;

  @Column(
      name = "void_storage_level",
      nullable = false,
      columnDefinition = "int"
  )
  private int voidStorageLevel = 0;

  public String id() {
    return id;
  }
  public void id(String value) {
    id = value;
  }

  public VesperUser associatedUser() {
    return associatedUser;
  }
  public void associatedUser(VesperUser value) {
    associatedUser = value;
  }

  public int voidStorageLevel() {
    return voidStorageLevel;
  }
  public void voidStorageLevel(int value) {
    voidStorageLevel = value;
  }
}

package io.molkars.vesper.database.model;

import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reality_shop_info")
@SuppressWarnings("unused")
public class RealityShopInfo implements Serializable {
  public RealityShopInfo() {

  }

  public RealityShopInfo(VesperUser user) {
    id = user.uuid();
    this.user = user;
  }

  @Id
  @Column(name = "id", length = 36, unique = true, nullable = false)
  private String id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "id")
  private VesperUser user;

  @Column(name = "void_storage_level")
  private Integer voidStorageLevel = 0;

  public String id() {
    return id;
  }

  public void id(String id) {
    this.id = id;
  }

  public VesperUser user() {
    return user;
  }

  public void user(VesperUser user) {
    this.user = user;
  }

  public @Nullable Integer voidStorageLevel() {
    return voidStorageLevel;
  }

  public void voidStorageLevel(int voidStorageLevel) {
    this.voidStorageLevel = voidStorageLevel;
  }
}

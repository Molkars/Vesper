package io.molkars.vesper.database.model;

import com.google.common.base.Objects;
import io.molkars.vesper.Vesper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuppressWarnings("unused")
@Table(name = "users")
public class VesperUser implements Serializable {
  public VesperUser(String uuid) {
    this.uuid = uuid;
    this.realityShopInfo = new RealityShopInfo(this);
  }

  protected VesperUser() {

  }

  @Id
  @Column(
      name = "uuid",
      length = 36,
      nullable = false,
      unique = true,
      columnDefinition = "char(36)",
      updatable = false
  )
  private String uuid;

  @OneToOne(
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      optional = false
  )
  @JoinColumn(
      name = "uuid",
      referencedColumnName = "id",
      columnDefinition = "char(36)",
      unique = true,
      nullable = false
  )
  private RealityShopInfo realityShopInfo = new RealityShopInfo(this);

  @OneToMany(
      orphanRemoval = true,
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )
  @JoinColumn(name = "associated_user", referencedColumnName = "uuid")
  List<VoidStorageInventory> inventories = new ArrayList<>();

  @OneToMany(
      orphanRemoval = true,
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )
  @JoinColumn(name = "associated_user", referencedColumnName = "uuid")
  List<Home> homes = new ArrayList<>();

  @Column(
      name = "shard_count",
      nullable = false,
      columnDefinition = "int"
  )
  private int realityShardCount = 0;

  public String uuid() {
    return uuid;
  }

  public RealityShopInfo realityShopInfo() {
    return realityShopInfo;
  }

  public int realityShardCount() {
    return realityShardCount;
  }

  public void realityShardCount(int realityShardCount) {
    if (realityShardCount < 0) {
      throw new IllegalArgumentException("A user can not have negative reality shards");
    }
    this.realityShardCount = realityShardCount;
  }

  public List<VoidStorageInventory> inventories() {
    return inventories;
  }

  public List<Home> homes() {
    return homes;
  }

  @Override
  public String toString() {
    return "VesperUser{" +
        "uuid='" + uuid + '\'' +
        ", realityShopInfo=" + realityShopInfo +
        ", realityShardCount=" + realityShardCount +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof VesperUser)) return false;
    VesperUser user = (VesperUser) o;
    return realityShardCount == user.realityShardCount && Objects.equal(uuid, user.uuid) && Objects.equal(realityShopInfo, user.realityShopInfo) && Objects.equal(inventories, user.inventories) && Objects.equal(homes, user.homes);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(uuid, realityShopInfo, inventories, homes, realityShardCount);
  }

  public static VesperUser getOrCreate(String uuid) {
    final EntityManager manager = Vesper.getInstance().entityManager;
    VesperUser user = manager.find(VesperUser.class, uuid);
    if (user == null) {
      user = new VesperUser(uuid);
      manager.persist(user);
    }
    return user;
  }
}

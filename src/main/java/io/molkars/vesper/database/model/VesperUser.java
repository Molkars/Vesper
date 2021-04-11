package io.molkars.vesper.database.model;

import javax.persistence.*;
import java.io.Serializable;

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
  @Column(name = "uuid", length = 36, nullable = false, unique = true)
  private String uuid;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "uuid", referencedColumnName = "id")
  private RealityShopInfo realityShopInfo;

  @Column(name = "shard_count", nullable = false)
  private int realityShardCount = 0;

  public String uuid() {
    return uuid;
  }
  public void uuid(String uuid) {
    this.uuid = uuid;
  }

  public RealityShopInfo realityShopInfo() {
    return realityShopInfo;
  }
  public void realityShopInfo(RealityShopInfo realityShopInfo) {
    this.realityShopInfo = realityShopInfo;
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

  @Override
  public String toString() {
    return "VesperUser{" +
        "uuid='" + uuid + '\'' +
        ", realityShopInfo=" + realityShopInfo +
        ", realityShardCount=" + realityShardCount +
        '}';
  }
}

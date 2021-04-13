package io.molkars.vesper.database.model;

import org.bukkit.Location;
import org.bukkit.World;
import org.hibernate.engine.profile.Association;

import javax.persistence.*;

@Entity
@Table(name = "homes")
@SuppressWarnings("unused")
public class Home {

  protected Home() { }

  public Home(VesperUser user, Location location, World world, String name) {
    this.associatedUser = user;
    this.name = name;
    this.worldName = world.getName();
    this.positionX = location.getBlockX();
    this.positionY = location.getBlockY();
    this.positionZ = location.getBlockZ();
  }

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
      cascade = CascadeType.DETACH,
      fetch = FetchType.LAZY,
      optional = false
  )
  @JoinColumn(
      name = "associated_user",
      referencedColumnName = "uuid",
      nullable = false,
      unique = true,
      columnDefinition = "char(36)"
  )
  private VesperUser associatedUser;

  @Column(name = "position_x", nullable = false, columnDefinition = "bigint")
  private long positionX;

  @Column(name = "position_y", nullable = false, columnDefinition = "bigint")
  private long positionY;

  @Column(name = "position_z", nullable = false, columnDefinition = "bigint")
  private long positionZ;

  @Column(name = "name", nullable = false, columnDefinition = "char(255)")
  private String name;

  @Column(name = "world_id", nullable = false, length = 100, columnDefinition = "char(100")
  private String worldName;

  // Setters and getters

  public long id() {
    return id;
  }

  public VesperUser associatedUser() {
    return associatedUser;
  }

  public long positionX() {
    return positionX;
  }

  public void positionX(long value) {
    positionX = value;
  }

  public long positionY() {
    return positionY;
  }

  public void positionY(long value) {
    positionY = value;
  }

  public long positionZ() {
    return positionZ;
  }

  public void positionZ(long value) {
    positionZ = value;
  }

  public String worldName() {
    return worldName;
  }

  public String name() {
    return name;
  }

  public void name(String value) {
    name = value;
  }

}

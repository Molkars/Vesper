package io.molkars.vesper.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public final class ItemStackUtil {

  public static final ItemStack darkFiller = _getDarkFiller();
  public static final ItemStack genericFiller = _getGenericFiller();
  public static final ItemStack forwardArrow = _getForwardArrow();
  public static final ItemStack backwardArrow = _getBackwardArrow();
  public static final ItemStack close = _getClose();
  public static final ItemStack settings = _getSettings();

  private static ItemStack _getDarkFiller() {
    final ItemStack controlsFiller = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    final ItemMeta controlsMeta = controlsFiller.getItemMeta();
    controlsMeta.displayName(Component.text(String.format("%s", ChatColor.RESET)));
    controlsFiller.setItemMeta(controlsMeta);
    return controlsFiller;
  }

  private static ItemStack _getGenericFiller() {
    final ItemStack filler = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
    final ItemMeta fillerMeta = filler.getItemMeta();
    fillerMeta.displayName(Component.text(String.format("%s", ChatColor.RESET)));
    filler.setItemMeta(fillerMeta);
    return filler;
  }

  private static ItemStack _getForwardArrow() {
    final ItemStack forwardArrow = new ItemStack(Material.SPECTRAL_ARROW);
    final ItemMeta forwardArrowMeta = forwardArrow.getItemMeta();
    forwardArrowMeta.displayName(Component.text(String.format("%sNext Page", ChatColor.RESET)));
    forwardArrow.setItemMeta(forwardArrowMeta);
    return forwardArrow;
  }

  private static ItemStack _getBackwardArrow() {
    final ItemStack backwardArrow = new ItemStack(Material.PLAYER_HEAD);
    final SkullMeta backwardArrowMeta = (SkullMeta) backwardArrow.getItemMeta();
    PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());

    // Set the head texture
    profile.getProperties().add(new ProfileProperty("textures", "8550b7f74e9ed7633aa274ea30cc3d2e87abb36d4d1f4ca608cd44590cce0b"));
    // Give the meta the texture
    backwardArrowMeta.setPlayerProfile(profile);
    // Give the item the metadata
    final ItemMeta backArrowMeta = backwardArrow.getItemMeta();
    // Set the display name
    backArrowMeta.displayName(Component.text(String.format("%sPrevious Page", ChatColor.RESET)));
    return backwardArrow;
  }

  private static ItemStack _getClose() {
    final ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    final ItemMeta meta = item.getItemMeta();
    meta.displayName(Component.text(String.format("%sClose", ChatColor.RESET)));
    item.setItemMeta(meta);
    return item;
  }

  private static ItemStack _getSettings() {
    final ItemStack item = new ItemStack(Material.BOOK);
    final ItemMeta meta = item.getItemMeta();
    meta.displayName(Component.text(String.format("%sSettings", ChatColor.RESET)));
    item.setItemMeta(meta);
    return item;
  }
}

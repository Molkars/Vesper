package io.molkars.vesper.src;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public interface EventsListener {
  default void onInventoryClick(InventoryClickEvent e) {
  }

  default void onBlockBreak(BlockBreakEvent e) {
  }

  default void onInventoryDrag(InventoryDragEvent e) {

  }

  default void onInventoryClose(InventoryCloseEvent e) {
  }

  default void onEntityDeath(EntityDeathEvent e) {

  }
}

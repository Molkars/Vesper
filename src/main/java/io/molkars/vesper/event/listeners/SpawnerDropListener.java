package io.molkars.vesper.event.listeners;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.src.EventsListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnerDropListener implements EventsListener {

  public void onBlockBreak(BlockBreakEvent e) {
    final Vesper vesper = Vesper.getInstance();
    final Block block = e.getBlock();
    final Material blockType = block.getType();

    if (blockType.equals(Material.SPAWNER)) {
      final ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();

      boolean isPickaxe = false;
      switch (hand.getType()) {
        case DIAMOND_PICKAXE:
        case IRON_PICKAXE:
        case GOLDEN_PICKAXE:
        case NETHERITE_PICKAXE:
          isPickaxe = true;
      }

      if (!isPickaxe) {
        return;
      }

      if (hand.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
        final int chance = Vesper.getInstance().getConfig().getInt("silkSpawnerChance", 2);
        boolean shouldDrop = vesper.random.nextDouble() < (1f / chance);

        if (!shouldDrop) {
          return;
        }

        final Player player = e.getPlayer();
        final ItemStack item = block.getState().getData().toItemStack(1);
        if (player.getInventory().firstEmpty() == -1) {
          block.getWorld().dropItemNaturally(block.getLocation().toCenterLocation(), item);
        } else {
          player.getInventory().addItem(item);
        }
      }
    }
  }
}

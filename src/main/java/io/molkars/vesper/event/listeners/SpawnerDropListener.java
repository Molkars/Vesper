package io.molkars.vesper.event.listeners;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.src.EventsListener;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.TileEntityTypes;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        final CreatureSpawner state = (CreatureSpawner) block.getState();
        final String entityType = state.getSpawnedType().name();

        ItemStack item = getItemStack(entityType);

        if (player.getInventory().firstEmpty() == -1) {
          block.getWorld().dropItemNaturally(block.getLocation().toCenterLocation(), item);
        } else {
          player.getInventory().addItem(item);
        }
      }
    }
  }

  private ItemStack getItemStack(String entityId) {
    final ItemStack paperItem = new ItemStack(Material.SPAWNER, 1);
    final ItemMeta meta = paperItem.getItemMeta();
    final Vesper vesper = Vesper.getInstance();

    String prefixedEntity = !entityId.startsWith("minecraft:") ? "minecraft:" + entityId : entityId;
    net.minecraft.server.v1_16_R3.ItemStack mcItem = CraftItemStack.asNMSCopy(CraftItemStack.asCraftCopy(paperItem));
    NBTTagCompound tag = mcItem.getOrCreateTag();
    final String key = vesper.getName();

    if (!tag.hasKey(key)) {
      tag.set(vesper.getName(), new NBTTagCompound());
    }

    tag.getCompound(key).setString("entity", entityId);
    if (!tag.hasKey("BlockEntityTag")) {
      tag.set("BlockEntityTag", new NBTTagCompound());
    }

    tag = tag.getCompound("BlockEntityTag");
    tag.setString("EntityId", entityId);
    tag.setString("id", TileEntityTypes.a(TileEntityTypes.MOB_SPAWNER).getKey());

    if (!tag.hasKey("SpawnData")) {
      tag.set("SpawnData", new NBTTagCompound());
    }
    tag.getCompound("SpawnData").setString("id", prefixedEntity);

    if (!tag.hasKey("SpawnPotentials")) {
      tag.set("SpawnPotentials", new NBTTagCompound());
    }

    tag.getCompound("EntityTag").setString("id", prefixedEntity);

    return CraftItemStack.asCraftMirror(mcItem);
  }
}

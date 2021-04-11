package io.molkars.vesper.event.listeners;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.src.EventsListener;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IronGolemKillListener implements EventsListener {
  @Override
  public void onEntityDeath(EntityDeathEvent e) {
    if (e.getEntityType() != EntityType.IRON_GOLEM) {
      return;
    }

    int poppyCount = 0;
    int ingotCount = 0;

    for (ItemStack s : e.getDrops()) {
      if (s.getType().equals(Material.POPPY)) {
        poppyCount += s.getAmount();
      } else if (s.getType().equals(Material.IRON_INGOT)) {
        ingotCount += s.getAmount();
      }
    }
    e.getDrops().clear();

    final int nuggetCount = 3 * ingotCount;

    final Vesper vesper = Vesper.getInstance();
    List<ItemStack> flowers = new ArrayList<>(poppyCount);
    for (int i = 0; i < poppyCount; i++) {
      ItemStack flower = kFlowers[vesper.random.nextInt(kFlowers.length)];
      final int index = flowers.indexOf(flower);
      if (index < 0) {
        flowers.add(flower);
      } else {
        flower = flowers.get(index);
        flower.setAmount(flower.getAmount() + 1);
      }
    }

    if (nuggetCount > 0) {
      e.getDrops().add(new ItemStack(Material.IRON_NUGGET, nuggetCount));
    }
  }

  static final ItemStack[] kFlowers = new ItemStack[]{
      new ItemStack(Material.DANDELION),
      new ItemStack(Material.POPPY),
      new ItemStack(Material.BLUE_ORCHID),
      new ItemStack(Material.ALLIUM),
      new ItemStack(Material.AZURE_BLUET),
      new ItemStack(Material.RED_TULIP),
      new ItemStack(Material.ORANGE_TULIP),
      new ItemStack(Material.WHITE_TULIP),
      new ItemStack(Material.PINK_TULIP),
      new ItemStack(Material.OXEYE_DAISY),
      new ItemStack(Material.CORNFLOWER),
      new ItemStack(Material.LILY_OF_THE_VALLEY),
      new ItemStack(Material.SUNFLOWER),
      new ItemStack(Material.ROSE_BUSH),
      new ItemStack(Material.LILAC),
      new ItemStack(Material.PEONY),
  };
}

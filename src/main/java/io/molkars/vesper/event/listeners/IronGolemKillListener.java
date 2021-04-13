package io.molkars.vesper.event.listeners;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.src.EventsListener;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

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
    for (int i = 0; i < poppyCount; i++) {
      if (vesper.random.nextDouble() < (1 / 6942f)) {
        e.getDrops().add(new ItemStack(Material.IRON_BLOCK, 1));
      }
    }

    if (nuggetCount > 0) {
      e.getDrops().add(new ItemStack(Material.IRON_NUGGET, nuggetCount));
    }
  }
}

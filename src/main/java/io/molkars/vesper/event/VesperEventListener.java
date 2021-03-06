package io.molkars.vesper.event;

import io.molkars.vesper.Vesper;
import io.molkars.vesper.event.listeners.IronGolemKillListener;
import io.molkars.vesper.event.listeners.RealityShardListener;
import io.molkars.vesper.event.listeners.SpawnerDropListener;
import io.molkars.vesper.src.EventsListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;

public class VesperEventListener implements Listener {
  public VesperEventListener() {
    final EventsListener[] eventsListeners = new EventsListener[]{
        new SpawnerDropListener(),
        new IronGolemKillListener(),
        new RealityShardListener(),
    };

    for (final EventsListener l : eventsListeners) {
      registerListener(l);
    }
  }

  private final ArrayList<EventsListener> listeners = new ArrayList<>();

  public void registerListener(EventsListener listener) {
    Vesper vesper = Vesper.getInstance();
    vesper.logger.info(String.format("[%s] Registering Event: %s", vesper.getDescription().getName(), listener));
    listeners.add(listener);
  }

  @EventHandler
  public void blockBreak(BlockBreakEvent event) {
    for (EventsListener l : listeners) {
      l.onBlockBreak(event);
    }
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent e) {
    for (EventsListener l : listeners) {
      l.onInventoryClick(e);
    }
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent e) {
    for (EventsListener l : listeners) {
      l.onInventoryClose(e);
    }
  }

  @EventHandler
  public void onEntityDeath(EntityDeathEvent e) {
    for (EventsListener l : listeners) {
      l.onEntityDeath(e);
    }
  }
}

package com.oresome.OresomeInfection.Listeners;

import com.oresome.OresomeInfection.Infection;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class NoItemsEvent
  implements Listener
{
  private final Infection plugin;

  public NoItemsEvent(Infection plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onDropItem(PlayerDropItemEvent event) {
    if (this.plugin.roundStarting) {
      event.setCancelled(true);
      return;
    }
    if (this.plugin.roundStarted) {
      event.setCancelled(true);
      return;
    }
  }

  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPickupItem(ItemSpawnEvent event) { if (this.plugin.roundStarting) {
      event.setCancelled(false);
      event.getEntity().remove();
      return;
    }
    if (this.plugin.roundStarted) {
      event.setCancelled(false);
      event.getEntity().remove();
      return;
    }
  }
}
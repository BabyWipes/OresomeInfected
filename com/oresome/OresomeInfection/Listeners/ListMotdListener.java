package com.oresome.OresomeInfection.Listeners;

import com.oresome.OresomeInfection.Infection;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginManager;

public class ListMotdListener
  implements Listener
{
  public Infection plugin;

  public ListMotdListener(Infection plugin)
  {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
  @EventHandler(priority=EventPriority.MONITOR)
  public void onServerListPing(ServerListPingEvent event) {
    event.setMotd(ChatColor.RED + "Oresome Infection!");
  }
}
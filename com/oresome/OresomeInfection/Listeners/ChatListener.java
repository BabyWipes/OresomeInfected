package com.oresome.OresomeInfection.Listeners;

import com.oresome.OresomeInfection.Infection;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener
  implements Listener
{
  private final Infection plugin;

  public ChatListener(Infection plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
    Player player = event.getPlayer();
    String red = "";
    String green = "";
    String cyan = "";
    String r = "";
    String ref = "";
    String mapmaker = "";
    String g = "";
    if (this.plugin.Humans.contains(event.getPlayer().getName())) {
      green = ChatColor.GREEN;
      red = "";
      cyan = "";
      g = ChatColor.GREEN + "[H]";
      r = "";
      ref = "";
    }
    if (this.plugin.Zombies.contains(event.getPlayer().getName())) {
      red = ChatColor.DARK_RED;
      green = "";
      cyan = "";
      r = ChatColor.DARK_RED + "[Z]";
      g = "";
      red = "";
    }

    if (this.plugin.Referees.contains(event.getPlayer().getName())) {
      cyan = ChatColor.AQUA;
      green = "";
      red = "";
      ref = ChatColor.DARK_AQUA + "[R]";
      g = "";
      r = "";
    }

    if (this.plugin.isMapMaker(event.getPlayer().getName())) {
      mapmaker = ChatColor.BLUE + "*";
    }
    event.setFormat(ref + r + g + " <" + mapmaker + red + green + cyan + player.getDisplayName() + ">: " + red + green + cyan + event.getMessage());
  }
}
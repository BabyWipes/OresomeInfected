package com.oresome.OresomeInfection.Listeners;

import com.oresome.OresomeInfection.Infection;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class LastStandListener
  implements Listener
{
  private final Infection plugin;

  public LastStandListener(Infection plugin)
  {
    this.plugin = plugin;
  }

  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onAsyncPlayerChat(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();
    if ((this.plugin.isHuman(player.getName())) && 
      (block.getLocation().getX() == 2080.0D) && 
      (block.getLocation().getY() == 39.0D) && 
      (block.getLocation().getZ() == 904.0D)) {
      event.setCancelled(true);
      player.sendMessage(ChatColor.RED + "You can't break your own monument!");
    }

    if ((this.plugin.isReferee(player.getName())) && 
      (block.getLocation().getX() == 2080.0D) && 
      (block.getLocation().getY() == 39.0D) && 
      (block.getLocation().getZ() == 904.0D)) {
      event.setCancelled(true);
      player.sendMessage(ChatColor.RED + "You can't break the monument as a ref!");
    }

    if ((this.plugin.isZombie(player.getName())) && 
      (block.getLocation().getX() == 2080.0D) && 
      (block.getLocation().getY() == 39.0D) && 
      (block.getLocation().getZ() == 904.0D)) {
      Bukkit.getScheduler().cancelTask(Infection.Round);
      Infection.round = 601;
      this.plugin.roundStarting = false;
      this.plugin.roundStarted = false;

      for (Player p : Bukkit.getOnlinePlayers()) {
        p.setHealth(20);
        this.plugin.makeHuman(p);
        p.setGameMode(GameMode.CREATIVE);
        this.plugin.MapMakers.clear();
      }
      this.plugin.getServer().broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD + "---[" + ChatColor.RESET + ChatColor.RED + "Game Over!" + ChatColor.DARK_RED + ChatColor.BOLD + "]---");
      this.plugin.getServer().broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD + "[" + ChatColor.RED + "Monument broke!" + ChatColor.DARK_RED + ChatColor.BOLD + "]");
      Infection.cycleTimer();
      Bukkit.getScheduler().cancelTask(Infection.Round);
      Bukkit.getScheduler().cancelTask(Infection.Countdown);
      this.plugin.roundStarting = false;
      this.plugin.roundStarted = false;
      this.plugin.roundCycling = true;
      this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.cycleToMap();
        }
      }
      , 700L);
    }
  }
}
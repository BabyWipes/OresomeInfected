package com.oresome.OresomeInfection.Listeners;

import com.oresome.OresomeInfection.Infection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class RefereeListener
  implements Listener
{
  public Infection plugin;

  public RefereeListener(Infection plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    if (this.plugin.isReferee(player.getName())) {
      event.setCancelled(true);
    }
    if (this.plugin.roundCycling)
      event.setCancelled(true);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    if (this.plugin.isReferee(player.getName())) {
      event.setCancelled(true);
    }
    if (this.plugin.roundCycling)
      event.setCancelled(true);
  }

  @EventHandler
  public void onHangingBreak(HangingBreakEvent event) {
    Player player = (Player)event.getEntity();
    if (this.plugin.isReferee(player.getName())) {
      event.setCancelled(true);
    }
    if (this.plugin.roundCycling)
      event.setCancelled(true);
  }

  @EventHandler
  public void onHangingBreak(HangingPlaceEvent event) {
    Player player = event.getPlayer();
    if (this.plugin.isReferee(player.getName())) {
      event.setCancelled(true);
    }
    if (this.plugin.roundCycling)
      event.setCancelled(true);
  }

  @EventHandler
  public void onLeafDecay(PlayerInteractEntityEvent event) {
    Player player = event.getPlayer();
    if (this.plugin.isReferee(player.getName())) {
      event.setCancelled(true);
    }
    if (this.plugin.roundCycling)
      event.setCancelled(true);
  }

  @EventHandler
  public void onLeafDecay(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if (this.plugin.isReferee(player.getName())) {
      event.setCancelled(true);
    }
    if (this.plugin.roundCycling)
      event.setCancelled(true);
  }

  @EventHandler
  public void onNotRef(PlayerCommandPreprocessEvent event) {
    Player player = event.getPlayer();
    if ((!this.plugin.isReferee(player.getName())) && (event.getMessage() == "/tp")) {
      event.setCancelled(true);
    }
    if ((!this.plugin.isReferee(player.getName())) && (event.getMessage() == "/teleport")) {
      event.setCancelled(true);
    }
    if ((!this.plugin.isReferee(player.getName())) && (event.getMessage() == "/j")) {
      event.setCancelled(true);
    }
    if ((!this.plugin.isReferee(player.getName())) && (event.getMessage() == "/jump")) {
      event.setCancelled(true);
    }
    if ((!this.plugin.isReferee(player.getName())) && (event.getMessage() == "/give")) {
      event.setCancelled(true);
    }
    if (this.plugin.roundCycling)
      event.setCancelled(true);
  }
}
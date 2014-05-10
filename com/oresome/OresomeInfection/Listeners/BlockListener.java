package com.oresome.OresomeInfection.Listeners;

import com.oresome.OresomeInfection.Infection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BlockListener
  implements Listener
{
  public Infection plugin;

  public BlockListener(Infection plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    if ((this.plugin.currentMap == null) && (!this.plugin.allowedToEditMaps)) {
      event.setCancelled(true);
      return;
    }
    if (this.plugin.roundCycling) {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Adminiumantics") {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Darkness Of Dusk") {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Funhouse")
      event.setCancelled(true);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if ((this.plugin.currentMap == null) && (!this.plugin.allowedToEditMaps)) {
      event.setCancelled(true);
      return;
    }
    if (event.getBlock().getType() == Material.NETHER_WARTS) {
      event.getPlayer().sendMessage(ChatColor.DARK_RED + "You got 1 Nether Wart!");
      event.getPlayer().getInventory().addItem(new ItemStack[] { new ItemStack(Material.NETHER_STALK, 1) });
      return;
    }
    if (event.getBlock().getType() == Material.RED_ROSE) {
      event.getPlayer().sendMessage(ChatColor.RED + "You got 1 Rose!");
      event.getPlayer().getInventory().addItem(new ItemStack[] { new ItemStack(Material.RED_ROSE, 1) });
      return;
    }
    if (event.getBlock().getType() == Material.EMERALD_ORE) {
      event.getPlayer().sendMessage(ChatColor.GREEN + "You got 1 Emerald!");
      event.getPlayer().getInventory().addItem(new ItemStack[] { new ItemStack(Material.EMERALD, 1) });
      return;
    }
    if (this.plugin.roundCycling) {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Adminiumantics") {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Darkness Of Dusk") {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Funhouse")
      event.setCancelled(true);
  }

  @EventHandler
  public void onHangingBreak(HangingBreakEvent event) {
    if ((this.plugin.currentMap == null) && (!this.plugin.allowedToEditMaps)) {
      event.setCancelled(true);
      return;
    }
    if (this.plugin.roundCycling) {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Adminiumantics") {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Darkness Of Dusk") {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Funhouse")
      event.setCancelled(true);
  }

  @EventHandler
  public void onHangingBreak(HangingPlaceEvent event) {
    if ((this.plugin.currentMap == null) && (!this.plugin.allowedToEditMaps)) {
      event.setCancelled(true);
      return;
    }
    if (this.plugin.roundCycling) {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Adminiumantics") {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Darkness Of Dusk") {
      event.setCancelled(true);
    }
    if (this.plugin.currentMap == "Funhouse")
      event.setCancelled(true);
  }

  @EventHandler
  public void onLeafDecay(LeavesDecayEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onHangingBreak(PlayerInteractEvent event) {
    if (this.plugin.roundCycling)
      event.setCancelled(true);
  }
}
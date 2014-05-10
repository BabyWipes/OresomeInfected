package com.oresome.OresomeInfection.Listeners;

import com.oresome.OresomeInfection.Infection;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn
  implements Listener
{
  private final Infection plugin;

  public PlayerRespawn(Infection plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void onDeath(PlayerRespawnEvent event) {
    if ((!this.plugin.roundStarted) && 
      (!this.plugin.roundStarting) && 
      (!this.plugin.roundCycling)) {
      event.getPlayer().teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
    }

    Player player = event.getPlayer();
    if (this.plugin.currentMap == "Hell's Gorge") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world"), 319.0D, 12.0D, -525.0D));
      this.plugin.makeZombie(player);
    }
    if (this.plugin.currentMap == "Adminiumantics") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world2"), 390.0D, 25.0D, -464.0D));
      this.plugin.makeZombie(player);
    }
    if (this.plugin.currentMap == "Toxic Wasteland") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world3"), 289.0D, 27.0D, -562.0D));
      this.plugin.makeZombie(player);
    }
    if (this.plugin.currentMap == "Suburban Complex") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world6"), 337.0D, 53.0D, -487.0D));
      this.plugin.makeZombie(player);
    }
    if (this.plugin.currentMap == "Hades' Castle") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world5"), 334.0D, 33.0D, -460.0D));
      this.plugin.makeZombie(player);
    }
    if (this.plugin.currentMap == "Satan's Sorrow") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world8"), 239.0D, 61.0D, -424.0D));
      this.plugin.makeZombie(player);
    }
    if (this.plugin.currentMap == "No Way Out") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world9"), 195.0D, 41.0D, -414.0D));
      this.plugin.makeZombie(player);
    }
    if (this.plugin.currentMap == "Darkness Of Dusk") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world10"), 61.0D, 6.0D, 22.0D));
      this.plugin.makeZombie(player);
    }
    if (this.plugin.currentMap == "Funhouse") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world11"), 216.0D, 13.0D, 184.0D));
      this.plugin.makeZombie(player);
    }
    if (this.plugin.currentMap == "Glacier") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world14"), 71.0D, 14.0D, 27.0D));
      this.plugin.makeZombie(player);
    }
    if (this.plugin.currentMap == "Last Stand") {
      event.setRespawnLocation(new Location(this.plugin.getServer().getWorld("world15"), 1970.0D, 16.0D, 907.0D));
      this.plugin.makeFinalStandZombie(player);
    }
  }
}
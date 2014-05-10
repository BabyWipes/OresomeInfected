package com.oresome.OresomeInfection.Listeners;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener
  implements Listener
{
  private final Infection plugin;

  public JoinListener(Infection plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    String prefix = "";
    if (!this.plugin.getPlugin().getConfig().contains(player.getName())) {
      player.sendMessage(ChatColor.GOLD + "This is your first time! Have 10 cookies on the house!");
      this.plugin.getPlugin().getConfig().set(player.getName() + ".economy.money", Integer.valueOf(10));
      this.plugin.getPlugin().saveConfig();
    }
    if (InfectionPermissions.isVIP(player)) {
      this.plugin.addStaff(player.getName());
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (!InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GREEN + "*";
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GREEN + "*" + ChatColor.RED + "*";
    }
    if ((!InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.RED + "*";
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GOLD + "*";
    }
    if ((!InfectionPermissions.isDonor(player)) && 
      (!InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = "";
    }
    String VIP = "";
    if ((InfectionPermissions.isVIP(player)) && (!InfectionPermissions.isModerator(player))) {
      VIP = ChatColor.YELLOW + "*";
    }
    if ((this.plugin.roundStarted) && (!this.plugin.roundStarting)) {
      event.setJoinMessage(VIP + prefix + ChatColor.DARK_RED + player.getName() + " connected.");
      player.sendMessage(ChatColor.DARK_RED + "You joined late, you are a zombie.");
      if (this.plugin.currentMap.equals("Hell's Gorge")) {
        this.plugin.makeZombie(player);
        Infection.teleportToGorge(player);
      }
      if (this.plugin.currentMap.equals("Adminiumantics")) {
        this.plugin.makeZombie(player);
        Infection.teleportToAdminiumantics(player);
      }
      if (this.plugin.currentMap.equals("Suburban Complex")) {
        this.plugin.makeZombie(player);
        Infection.teleportToSuburbanComplex(player);
      }
      if (this.plugin.currentMap.equals("Toxic Wasteland")) {
        this.plugin.makeZombie(player);
        Infection.teleportToToxicWasteland(player);
      }
      if (this.plugin.currentMap.equals("Hades' Castle")) {
        this.plugin.makeZombie(player);
        Infection.teleportToHadesCastle(player);
      }
      if (this.plugin.currentMap.equals("Satan's Sorrow")) {
        this.plugin.makeZombie(player);
        Infection.teleportToSatansSorrow(player);
      }
      if (this.plugin.currentMap == "No Way Out") {
        this.plugin.makeZombie(player);
        Infection.teleportToNoWayOut(player);
      }
      if (this.plugin.currentMap == "Darkness Of Dusk") {
        this.plugin.makeZombie(player);
        Infection.teleportToDarknessOfDusk(player);
      }
      if (this.plugin.currentMap == "Funhouse") {
        this.plugin.makeZombie(player);
        Infection.teleportToFunhouse(player);
      }
      if (this.plugin.currentMap == "Glacier") {
        this.plugin.makeZombie(player);
        Infection.teleportToGlacier(player);
      }
      if (this.plugin.currentMap == "Last Stand") {
        this.plugin.makeFinalStandZombie(player);
        Infection.teleportToLastStandZombieSpawn(player);
      }
    }
    if ((!this.plugin.roundStarted) && (this.plugin.roundStarting)) {
      event.setJoinMessage(VIP + prefix + ChatColor.GREEN + player.getName() + " connected.");
      player.sendMessage(ChatColor.GREEN + "You joined early, you are a human.");
      this.plugin.makeHuman(player);
      if (this.plugin.currentMap.equals("Hell's Gorge")) {
        Infection.teleportToGorge(player);
      }
      if (this.plugin.currentMap.equals("Adminiumantics")) {
        Infection.teleportToAdminiumantics(player);
      }
      if (this.plugin.currentMap.equals("Suburban Complex")) {
        Infection.teleportToSuburbanComplex(player);
      }
      if (this.plugin.currentMap.equals("Toxic Wasteland")) {
        Infection.teleportToToxicWasteland(player);
      }
      if (this.plugin.currentMap.equals("Hades' Castle")) {
        Infection.teleportToHadesCastle(player);
      }
      if (this.plugin.currentMap.equals("Satan's Sorrow")) {
        Infection.teleportToSatansSorrow(player);
      }
      if (this.plugin.currentMap == "No Way Out") {
        Infection.teleportToNoWayOut(player);
      }
      if (this.plugin.currentMap == "Funhouse") {
        Infection.teleportToFunhouse(player);
      }
      if (this.plugin.currentMap == "Glacier") {
        Infection.teleportToGlacier(player);
      }
      if (this.plugin.currentMap == "Darkness Of Dusk") {
        Infection.teleportToDarknessOfDusk(player);
      }
      if (this.plugin.currentMap == "Last Stand") {
        Infection.teleportToLastStand(player);
      }
    }
    if ((!this.plugin.roundStarted) && (!this.plugin.roundStarting) && (!this.plugin.roundCycling)) {
      event.setJoinMessage(VIP + prefix + ChatColor.GREEN + player.getName() + " connected.");
      player.sendMessage(ChatColor.RED + "Hi, there's no round on.");
      event.getPlayer().teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
      this.plugin.makeHuman(player);
    }
    if ((!this.plugin.roundStarted) && (!this.plugin.roundStarting) && (this.plugin.roundCycling)) {
      event.setJoinMessage(VIP + prefix + ChatColor.GREEN + player.getName() + " connected.");
      player.sendMessage(ChatColor.RED + "Hi, there's no round on.");
      event.getPlayer().teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
      this.plugin.makeHuman(player);
    }
  }

  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerQuit(PlayerQuitEvent event) { Player player = event.getPlayer();
    this.plugin.removeZombie(player.getName());
    this.plugin.removeHuman(player.getName());
    if (this.plugin.isStaff(player.getName())) {
      this.plugin.removeStaff(player.getName());
    }
    if (this.plugin.roundStarted) {
      if ((this.plugin.Zombies.size() == 0) && (this.plugin.Humans.size() > 0)) {
        Player p = this.plugin.getServer().getPlayer(this.plugin.randomPlayer());
        this.plugin.makeZombie(p);
        this.plugin.getServer().broadcastMessage(ChatColor.RED + "The disease continues with " + p.getDisplayName() + ChatColor.RED + "!");
        this.plugin.checkStatus();
      }

    }

    event.setQuitMessage(player.getDisplayName() + " disconnected."); }

  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerKick(PlayerKickEvent event) {
    Player player = event.getPlayer();
    String red = "";
    String green = "";
    String prefix = "";
    if (this.plugin.isStaff(player.getName())) {
      this.plugin.removeStaff(player.getName());
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (!InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GREEN + "*";
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GREEN + "*" + ChatColor.RED + "*";
    }
    if ((!InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.RED + "*";
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GOLD + "*";
    }
    if ((!InfectionPermissions.isDonor(player)) && 
      (!InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = "";
    }
    String VIP = "";
    if ((InfectionPermissions.isVIP(player)) && (!InfectionPermissions.isModerator(player))) {
      VIP = ChatColor.YELLOW + "*";
    }
    if (this.plugin.Humans.contains(event.getPlayer().getName())) {
      this.plugin.Humans.remove(player.getName());
      green = ChatColor.GREEN;
    }
    if (this.plugin.Zombies.contains(event.getPlayer().getName())) {
      this.plugin.Zombies.remove(player.getName());
      red = ChatColor.DARK_RED;
    }
    if (this.plugin.roundStarted) {
      if ((this.plugin.Zombies.size() == 0) && (this.plugin.Humans.size() > 0)) {
        Player p = this.plugin.getServer().getPlayer(this.plugin.randomPlayer());
        this.plugin.makeZombie(p);
        this.plugin.getServer().broadcastMessage(ChatColor.RED + "The disease continues with " + p.getDisplayName() + ChatColor.RED + "!");
        this.plugin.checkStatus();
      }
      else if (this.plugin.roundStarting) {
        this.plugin.checkStatus();
      }
    }

    event.setLeaveMessage(VIP + prefix + red + green + player.getName() + " was kicked.");
  }
}
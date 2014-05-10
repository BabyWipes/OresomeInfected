package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandReferee
  implements CommandExecutor
{
  private Infection plugin;

  public CommandReferee(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((sender instanceof ConsoleCommandSender)) return true;
    if (!InfectionPermissions.isVIP((Player)sender)) {
      sender.sendMessage(ChatColor.DARK_RED + "You don't have permission.");
      return true;
    }
    if ((!this.plugin.roundStarted) && (!this.plugin.roundStarting)) {
      sender.sendMessage(ChatColor.RED + "There is no round!");
      return true;
    }
    if (args.length != 0) { sender.sendMessage(ChatColor.DARK_RED + "Invalid Arguments"); return true; }
    if (args.length == 0) {
      Player player = (Player)sender;
      String prefix = "";
      if (!this.plugin.isReferee(player.getName())) {
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
        player.setDisplayName(prefix + ChatColor.DARK_AQUA + player.getName());
        this.plugin.getServer().broadcastMessage(ChatColor.DARK_AQUA + player.getDisplayName() + ChatColor.AQUA + " is now a referee!");
        this.plugin.removeZombie(player.getName());
        this.plugin.removeHuman(player.getName());
        this.plugin.addReferee(player.getName());
        player.getInventory().clear();

        for (Player p : Bukkit.getOnlinePlayers()) {
          p.hidePlayer(player);
        }
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.WOOD_AXE));
        player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.BEDROCK) });
        player.setGameMode(GameMode.CREATIVE);
        this.plugin.checkStatus();
        return true;
      }

      if (this.plugin.isReferee(player.getName())) {
        this.plugin.getServer().broadcastMessage(ChatColor.DARK_AQUA + player.getName() + ChatColor.AQUA + " is no longer a referee!");
        this.plugin.removeReferee(player.getName());
        this.plugin.removeHuman(player.getName());
        this.plugin.removeZombie(player.getName());
        player.getInventory().clear();

        for (Player p : Bukkit.getOnlinePlayers()) {
          p.showPlayer(player);
        }
        if (this.plugin.roundStarted) {
          if (this.plugin.currentMap == "Hell's Gorge") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world"), 319.0D, 12.0D, -525.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Adminiumantics") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world2"), 390.0D, 25.0D, -464.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Toxic Wasteland") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world3"), 289.0D, 27.0D, -562.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Suburban Complex") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world6"), 337.0D, 53.0D, -487.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Hades' Castle") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world5"), 334.0D, 33.0D, -460.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Satan's Sorrow") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world8"), 239.0D, 61.0D, -424.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "No Way Out") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world9"), 195.0D, 41.0D, -414.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Darkness Of Dusk") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world10"), 61.0D, 6.0D, 22.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Funhouse") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world11"), 216.0D, 13.0D, 184.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Glacier") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world14"), 71.0D, 14.0D, 27.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Last Stand") {
            Infection.teleportToLastStandZombieSpawn(player);
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
        }
        if (this.plugin.roundStarting) {
          if (this.plugin.currentMap == "Hell's Gorge") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world"), 319.0D, 12.0D, -525.0D));
            this.plugin.makeHuman(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Adminiumantics") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world2"), 390.0D, 25.0D, -464.0D));
            this.plugin.makeHuman(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Toxic Wasteland") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world3"), 289.0D, 27.0D, -562.0D));
            this.plugin.makeHuman(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Suburban Complex") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world6"), 337.0D, 53.0D, -487.0D));
            this.plugin.makeHuman(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Hades' Castle") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world5"), 334.0D, 33.0D, -460.0D));
            this.plugin.makeHuman(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Satan's Sorrow") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world8"), 239.0D, 61.0D, -424.0D));
            this.plugin.makeHuman(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "No Way Out") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world9"), 195.0D, 41.0D, -414.0D));
            this.plugin.makeHuman(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Darkness Of Dusk") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world10"), 61.0D, 6.0D, 22.0D));
            this.plugin.makeHuman(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Funhouse") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world11"), 216.0D, 13.0D, 184.0D));
            this.plugin.makeHuman(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Glacier") {
            player.teleport(new Location(this.plugin.getServer().getWorld("world14"), 71.0D, 14.0D, 27.0D));
            this.plugin.makeZombie(player);
            this.plugin.checkStatus();
          }
          if (this.plugin.currentMap == "Last Stand") {
            Infection.teleportToLastStand(player);
            this.plugin.makeHuman(player);
          }
        }
        return true;
      }
    }
    return true;
  }
}
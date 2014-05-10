package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandInfect
  implements CommandExecutor
{
  private Infection plugin;

  public CommandInfect(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if ((sender instanceof ConsoleCommandSender)) return true;
    if (!InfectionPermissions.isModerator((Player)sender)) return true;
    if (args.length == 0) { sender.sendMessage(ChatColor.DARK_RED + "Specify a player."); return true; }
    if (!this.plugin.roundStarted) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
    if (this.plugin.roundStarting) { sender.sendMessage(ChatColor.DARK_RED + "The round is starting."); return true; }
    if (args.length > 1) {
      sender.sendMessage(ChatColor.DARK_RED + "Specify only 1 player.");
      return true;
    }
    Player target = this.plugin.getServer().getPlayer(args[0]);
    if (!(target instanceof Player)) {
      sender.sendMessage(ChatColor.DARK_RED + "Specify an online player.");
      return true;
    }
    if (target.isOnline()) {
      if (this.plugin.isZombie(target.getName())) {
        sender.sendMessage(ChatColor.DARK_RED + "That player is a zombie.");
        return true;
      }
      if (!this.plugin.isZombie(target.getName())) {
        target.damage(0);
        Bukkit.getServer().broadcastMessage(target.getDisplayName() + ChatColor.RED + " was forcibly infected by " + ((Player)sender).getDisplayName());
        this.plugin.makeZombie(target);
        this.plugin.checkStatus();
        return true;
      }
    }
    if (this.plugin.roundStarted) {
      this.plugin.checkStatus();
    }
    return true;
  }
}
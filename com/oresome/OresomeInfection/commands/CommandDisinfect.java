package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandDisinfect
  implements CommandExecutor
{
  private Infection plugin;

  public CommandDisinfect(Infection plugin)
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
      if (this.plugin.isHuman(target.getName())) {
        sender.sendMessage(ChatColor.DARK_RED + "That player is a human.");
        return true;
      }
      if (!this.plugin.isHuman(target.getName())) {
        Bukkit.getServer().broadcastMessage(target.getDisplayName() + ChatColor.RED + " was forcibly disinfected by " + ((Player)sender).getDisplayName());
        this.plugin.makeHuman(target);
        if ((this.plugin.roundStarted) && 
          (this.plugin.Zombies.size() == 0)) {
          Player p = this.plugin.getServer().getPlayer(this.plugin.randomPlayer());
          this.plugin.makeZombie(p);
          this.plugin.getServer().broadcastMessage(ChatColor.RED + "The disease continues with " + p.getDisplayName() + ChatColor.RED + "!");
          this.plugin.checkStatus();
        }

        return true;
      }
    }
    return true;
  }
}
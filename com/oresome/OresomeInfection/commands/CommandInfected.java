package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInfected
  implements CommandExecutor
{
  private Infection plugin;

  public CommandInfected(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    String staff = "";
    String separator = ChatColor.DARK_RED + "," + ChatColor.RED + " ";

    for (String name : this.plugin.getPlugin().Zombies) {
      Player player = this.plugin.getPlugin().getServer().getPlayer(name);
      if (player == null) return false;
      staff = staff + ChatColor.RED + player.getName() + separator;
    }
    if (staff.length() == 0) {
      sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD + "[" + ChatColor.RED + ChatColor.ITALIC + "Zombies" + ChatColor.DARK_RED + ChatColor.BOLD + "]");
      sender.sendMessage(ChatColor.GRAY + "(0)");
      return true;
    }
    staff = staff.substring(0, staff.length() - separator.length());
    sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD + "[" + ChatColor.RED + ChatColor.ITALIC + "Zombies" + ChatColor.DARK_RED + ChatColor.BOLD + "]");
    sender.sendMessage(ChatColor.GRAY + "(" + this.plugin.Zombies.size() + "): " + staff);
    return true;
  }
}
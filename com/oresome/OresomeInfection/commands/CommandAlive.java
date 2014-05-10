package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAlive
  implements CommandExecutor
{
  private Infection plugin;

  public CommandAlive(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    String staff = "";
    String separator = ChatColor.DARK_GREEN + "," + ChatColor.GREEN + " ";

    for (String name : this.plugin.getPlugin().Humans) {
      Player player = this.plugin.getPlugin().getServer().getPlayer(name);
      if (player == null) return false;
      staff = staff + ChatColor.GREEN + player.getName() + separator;
    }
    if (staff.length() == 0) {
      sender.sendMessage(ChatColor.DARK_GREEN + ChatColor.BOLD + "[" + ChatColor.GREEN + ChatColor.ITALIC + "Humans" + ChatColor.DARK_GREEN + ChatColor.BOLD + "]");
      sender.sendMessage(ChatColor.GRAY + "(0)");
      return true;
    }
    staff = staff.substring(0, staff.length() - separator.length());
    sender.sendMessage(ChatColor.DARK_GREEN + ChatColor.BOLD + "[" + ChatColor.GREEN + ChatColor.ITALIC + "Humans" + ChatColor.DARK_GREEN + ChatColor.BOLD + "]");
    sender.sendMessage(ChatColor.GRAY + "(" + this.plugin.Humans.size() + "): " + staff);
    return true;
  }
}
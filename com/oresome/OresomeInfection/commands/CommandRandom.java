package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRandom
  implements CommandExecutor
{
  private Infection plugin;

  public CommandRandom(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (this.plugin.Humans.size() < 1) {
      sender.sendMessage(ChatColor.DARK_RED + "You need more than 1 player online for this.");
      return true;
    }
    if (this.plugin.Humans.size() > 1) {
      String player = this.plugin.randomPlayer().toString();
      sender.sendMessage(ChatColor.DARK_AQUA + "Random Player: " + ChatColor.AQUA + player);
      return true;
    }
    return true;
  }
}
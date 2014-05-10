package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRoundsLeft
  implements CommandExecutor
{
  private Infection plugin;

  public CommandRoundsLeft(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    sender.sendMessage(ChatColor.GRAY + "(" + this.plugin.restart + "): " + ChatColor.GREEN + "Rounds left until restart" + ChatColor.GRAY + ".");
    return true;
  }
}
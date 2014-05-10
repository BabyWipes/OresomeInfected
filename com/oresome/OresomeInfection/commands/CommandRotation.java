package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRotation
  implements CommandExecutor
{
  private Infection plugin;

  public CommandRotation(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (args.length == 0) {
      sender.sendMessage(ChatColor.DARK_RED + "Broken. Sorry ;(");
    }
    return true;
  }
}
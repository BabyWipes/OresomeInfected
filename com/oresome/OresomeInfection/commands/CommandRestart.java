package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandRestart
  implements CommandExecutor
{
  private Infection plugin;

  public CommandRestart(Infection instance)
  {
    this.plugin = instance;
  }
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (args.length != 0) { sender.sendMessage(ChatColor.RED + "Invalid args!"); return true; }
    if ((!(sender instanceof ConsoleCommandSender)) && 
      (InfectionPermissions.isAdministrator((Player)sender))) {
      this.plugin.restart = 1;
    }

    if ((sender instanceof ConsoleCommandSender)) {
      this.plugin.restart = 1;
    }
    return true;
  }
}
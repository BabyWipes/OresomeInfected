package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionFunctions;
import com.oresome.OresomeInfection.InfectionPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandSetCookies
  implements CommandExecutor
{
  private Infection plugin;

  public CommandSetCookies(Infection instance)
  {
    this.plugin = instance;
  }
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (args.length == 0) { sender.sendMessage(ChatColor.RED + "Invalid args!"); return true; }
    if (args.length == 1) { sender.sendMessage(ChatColor.RED + "Invalid args!"); return true; }
    if ((!(sender instanceof ConsoleCommandSender)) && 
      (InfectionPermissions.isAdministrator((Player)sender)) && 
      (InfectionFunctions.isParsableToInt(args[1]))) {
      int money = Integer.parseInt(args[1]);
      if (this.plugin.getConfig().contains(args[0] + ".economy.money")) {
        this.plugin.getConfig().set(args[0] + ".economy.money", Integer.valueOf(money));
        this.plugin.saveConfig();
        sender.sendMessage(ChatColor.RED + "You set " + args[0] + ChatColor.RED + "'s cookies to " + money);
        return true;
      }

    }

    if (((sender instanceof ConsoleCommandSender)) && 
      (InfectionFunctions.isParsableToInt(args[1]))) {
      int money = Integer.parseInt(args[1]);
      if (this.plugin.getConfig().contains(args[0] + ".economy.money")) {
        this.plugin.getConfig().set(args[0] + ".economy.money", Integer.valueOf(money));
        this.plugin.saveConfig();
        sender.sendMessage(ChatColor.RED + "You set " + args[0] + ChatColor.RED + "'s cookies to " + money);
        return true;
      }
    }

    return true;
  }
}
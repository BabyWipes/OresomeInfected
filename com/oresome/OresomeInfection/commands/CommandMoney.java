package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandMoney
  implements CommandExecutor
{
  private Infection plugin;

  public CommandMoney(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if ((args.length == 0) && 
      (!(sender instanceof ConsoleCommandSender))) {
      Player player = (Player)sender;
      int money = this.plugin.getConfig().getInt(player.getName() + ".economy.money");
      sender.sendMessage(ChatColor.GOLD + "Cookies for " + player.getDisplayName() + ChatColor.GOLD + ": " + money);
      return true;
    }

    if (args.length > 1) {
      sender.sendMessage(ChatColor.DARK_RED + "Specify only 1 player.");
      return true;
    }
    if (this.plugin.getConfig().contains(args[0])) {
      if (!(this.plugin.getServer().getPlayer(args[0]) instanceof Player)) {
        int money = this.plugin.getConfig().getInt(args[0] + ".economy.money");
        sender.sendMessage(ChatColor.GOLD + "Cookies for " + ChatColor.RED + args[0] + ChatColor.GOLD + ": " + money);
        return true;
      }
      if ((this.plugin.getServer().getPlayer(args[0]) instanceof Player)) {
        Player player = this.plugin.getServer().getPlayer(args[0]);
        int money = this.plugin.getConfig().getInt(args[0] + ".economy.money");
        sender.sendMessage(ChatColor.GOLD + "Cookies for " + ChatColor.RED + player.getDisplayName() + ChatColor.GOLD + ": " + money);
        return true;
      }
    }
    return true;
  }
}
package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionFunctions;
import com.oresome.OresomeInfection.InfectionPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandWarn
  implements CommandExecutor
{
  private Infection plugin;
  private final Server server;
  private String reason;

  public CommandWarn(Infection instance)
  {
    this.plugin = instance;
    this.server = this.plugin.getServer();
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (args.length == 0) { sender.sendMessage(ChatColor.RED + "Invalid args!"); return true; }
    Player target = Bukkit.getPlayer(args[0]);
    if (args.length == 1) { sender.sendMessage(ChatColor.RED + "Invalid args!"); return true; }
    if (!(target instanceof Player)) { sender.sendMessage(ChatColor.DARK_RED + args[0] + ChatColor.RED + " doesn't exist!"); return true; }
    if (args.length > 1) {
      String[] elements = new String[args.length - 1];
      System.arraycopy(args, 1, elements, 0, args.length - 1);
      this.reason = InfectionFunctions.implode(elements, " ");
    }
    if ((sender instanceof ConsoleCommandSender)) {
      if (!(target instanceof Player)) { sender.sendMessage(ChatColor.DARK_RED + args[0] + ChatColor.RED + " doesn't exist!"); return true; }
      if ((target instanceof Player)) {
        InfectionFunctions.messageMods(ChatColor.RED + "Player" + ChatColor.GOLD + " *" + ChatColor.GREEN + "Console " + ChatColor.RED + "warned " + ChatColor.DARK_RED + target.getDisplayName() + ChatColor.RED + " for " + ChatColor.DARK_RED + this.reason);
        target.sendMessage(ChatColor.MAGIC + "~ ~ ~ " + ChatColor.RED + "Warned: " + this.reason);
        return true;
      }
    }
    if (!(sender instanceof ConsoleCommandSender)) {
      Player player = (Player)sender;
      if (InfectionPermissions.isModerator(player)) {
        if (!(target instanceof Player)) { sender.sendMessage(ChatColor.DARK_RED + args[0] + ChatColor.RED + " doesn't exist!"); return true; }
        if ((target instanceof Player)) {
          InfectionFunctions.messageMods(ChatColor.RED + "Player " + player.getDisplayName() + ChatColor.RED + " warned " + ChatColor.DARK_RED + target.getDisplayName() + ChatColor.RED + " for " + ChatColor.DARK_RED + this.reason);
          target.sendMessage(ChatColor.MAGIC + "~ ~ ~ " + ChatColor.RED + "Warned: " + this.reason);
          return true;
        }
      }
    }
    return true;
  }
}
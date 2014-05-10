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

public class CommandBroadcast
  implements CommandExecutor
{
  private Infection plugin;
  private final Server server;
  private String message;

  public CommandBroadcast(Infection instance)
  {
    this.plugin = instance;
    this.server = this.plugin.getServer();
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (args.length == 0) { sender.sendMessage(ChatColor.RED + "Invalid args!"); return true; }
    this.message = InfectionFunctions.implode(args, " ");
    if ((sender instanceof ConsoleCommandSender)) {
      Bukkit.getServer().broadcastMessage(ChatColor.RED + "[Broadcast] " + this.message);
      return true;
    }
    if ((sender instanceof Player)) {
      Player player = (Player)sender;
      if (!InfectionPermissions.isModerator(player)) {
        sender.sendMessage(ChatColor.DARK_RED + "No permission.");
        return true;
      }
      if (InfectionPermissions.isModerator(player)) {
        Bukkit.getServer().broadcastMessage(ChatColor.RED + "[Broadcast] " + this.message);
      }
    }
    return true;
  }
}
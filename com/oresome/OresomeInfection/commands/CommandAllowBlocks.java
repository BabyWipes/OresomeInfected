package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandAllowBlocks
  implements CommandExecutor
{
  private Infection plugin;

  public CommandAllowBlocks(Infection plugin)
  {
    this.plugin = plugin;
  }
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if ((sender instanceof ConsoleCommandSender)) return true;
    if (args.length != 0) return true;
    if (this.plugin.roundStarted) {
      sender.sendMessage(ChatColor.DARK_RED + "There's a round on!");
      return true;
    }
    if (this.plugin.roundStarting) {
      sender.sendMessage(ChatColor.DARK_RED + "There's a round starting!");
      return true;
    }
    Player p = (Player)sender;
    if (!InfectionPermissions.isAdministrator(p)) {
      sender.sendMessage(ChatColor.DARK_RED + "You ain't no admin!");
      return true;
    }
    if (!this.plugin.allowedToEditMaps) {
      this.plugin.allowedToEditMaps = true;
      sender.sendMessage(ChatColor.GREEN + "You allowed the editing of maps.");
      return true;
    }
    if (this.plugin.allowedToEditMaps) {
      this.plugin.allowedToEditMaps = false;
      sender.sendMessage(ChatColor.RED + "You disallowed the editing of maps.");
      return true;
    }
    return true;
  }
}
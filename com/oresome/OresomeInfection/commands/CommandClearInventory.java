package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class CommandClearInventory
  implements CommandExecutor
{
  private Infection plugin;

  public CommandClearInventory(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(ChatColor.DARK_RED + "Console cannot clear its own inventory");
      return true;
    }
    if (args.length != 0) return true;
    Player player = (Player)sender;
    if (!InfectionPermissions.isAdministrator(player)) { sender.sendMessage(ChatColor.DARK_RED + "You don't have permission."); return true; }
    if (args.length == 0) {
      player.getInventory().clear();
      sender.sendMessage(ChatColor.GRAY + "Inventory cleared.");
      return true;
    }
    return true;
  }
}
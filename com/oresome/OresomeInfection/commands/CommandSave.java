package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandSave
  implements CommandExecutor
{
  private Infection plugin;

  public CommandSave(Infection plugin)
  {
    this.plugin = plugin;
  }
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (args.length != 0) return true;
    Player p = (Player)sender;
    if (!InfectionPermissions.isAdministrator(p)) return true;
    if ((!(sender instanceof ConsoleCommandSender)) && (args.length == 0) && (!this.plugin.roundStarted) && (!this.plugin.roundStarting) && (InfectionPermissions.isAdministrator((Player)sender))) {
      Player player = (Player)sender;
      player.getWorld().save();
    }
    return true;
  }
}
package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWorld
  implements CommandExecutor
{
  private Infection plugin;

  public CommandWorld(Infection instance)
  {
    this.plugin = instance;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    Player p = (Player)sender;
    if (args.length == 0) { sender.sendMessage(ChatColor.DARK_RED + "Specify a loaded map."); return true; }
    if ((!this.plugin.roundStarting) && (!this.plugin.roundStarted)) {
      try {
        switch ($SWITCH_TABLE$com$oresome$OresomeInfection$commands$CommandWorld$SubCommands()[SubCommands.valueOf(args[0].toUpperCase()).ordinal()])
        {
        case 1:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToGorge(p);
          }
          break;
        case 2:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToAdminiumantics(p);
          }
          break;
        case 3:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToToxicWasteland(p);
          }
          break;
        case 4:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToJintaExtreme(p);
          }
          break;
        case 5:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToHadesCastle(p);
          }
          break;
        case 6:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToSuburbanComplex(p);
          }
          break;
        case 7:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToFuturisticFantasy(p);
          }
          break;
        case 8:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToSatansSorrow(p);
          }
          break;
        case 9:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToNoWayOut(p);
          }
          break;
        case 10:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToDarknessOfDusk(p);
          }
          break;
        case 11:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToFunhouse(p);
          }
          break;
        case 12:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToDeadTilTheEnd(p);
          }
          break;
        case 13:
          if (InfectionPermissions.isAdministrator(p)) {
            Infection.teleportToBloodsLoft(p);
          }
          break;
        case 14:
          if (InfectionPermissions.isAdministrator(p))
            Infection.teleportToGlacier(p);
          break;
        }
      }
      catch (Exception e) {
        p.sendMessage(ChatColor.DARK_RED + "That world doesn't exist or is not loaded.");
        return true;
      }
      return true;
    }
    return true;
  }
  private static enum SubCommands {
    HELLS_GORGE, 
    ADMINIUMANTICS, 
    TOXIC_WASTELAND, 
    JINTA_EXTREME, 
    HADES_CASTLE, 
    SUBURBAN_COMPLEX, 
    FUTURISTIC_FANTASY, 
    SATANS_SORROW, 
    NO_WAY_OUT, 
    DARKNESS_OF_DUSK, 
    FUNHOUSE, 
    DEAD_TIL_THE_END, 
    BLOODS_LOFT, 
    GLACIER;
  }
}
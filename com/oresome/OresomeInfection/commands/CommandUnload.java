package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import com.oresome.OresomeInfection.WorldHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnload
  implements CommandExecutor
{
  private Infection plugin;
  private WorldHandler handler;

  public CommandUnload(Infection instance)
  {
    this.plugin = instance;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    Player p = (Player)sender;
    if (args.length == 0) { sender.sendMessage(ChatColor.DARK_RED + "Specify a loaded map."); return true; }
    if ((!this.plugin.roundStarting) && (!this.plugin.roundStarted)) {
      try {
        switch ($SWITCH_TABLE$com$oresome$OresomeInfection$commands$CommandUnload$SubCommands()[SubCommands.valueOf(args[0].toUpperCase()).ordinal()])
        {
        case 1:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world");
          }

          break;
        case 2:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world2") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world2").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world2");
          }

          break;
        case 3:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world3") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world3").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world3");
          }

          break;
        case 4:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world4") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world4").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world4");
          }

          break;
        case 5:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world5") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world5").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world5");
          }

          break;
        case 6:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world6") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world6").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world6");
          }

          break;
        case 7:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world7") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world7").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world7");
          }

          break;
        case 8:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world8") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world8").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world8");
          }

          break;
        case 9:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world9") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world9").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world9");
          }

          break;
        case 10:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world10") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world10").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world10");
          }

          break;
        case 11:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world11") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world11").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world11");
          }

          break;
        case 12:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world12") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world12").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world12");
          }

          break;
        case 13:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world13") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world13").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world13");
          }

          break;
        case 14:
          if ((InfectionPermissions.isAdministrator(p)) && 
            ((this.plugin.getServer().getWorld("world14") instanceof World)))
          {
            for (Player player : this.plugin.getServer().getWorld("world14").getPlayers()) {
              player.teleport(new Location(this.plugin.getServer().getWorld("Spawn"), -201.0D, 23.0D, 208.0D));
            }
            WorldHandler.unloadWorld("world14");
          }
          break;
        }
      }
      catch (Exception e) {
        return false;
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
package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLoad
  implements CommandExecutor
{
  private Infection plugin;

  public CommandLoad(Infection instance)
  {
    this.plugin = instance;
  }
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    Player p = (Player)sender;
    if (args.length == 0) { sender.sendMessage(ChatColor.DARK_RED + "Specify an unloaded map."); return true; }
    if ((!this.plugin.roundStarting) && (!this.plugin.roundStarted)) {
      try {
        switch ($SWITCH_TABLE$com$oresome$OresomeInfection$commands$CommandLoad$SubCommands()[SubCommands.valueOf(args[0].toUpperCase()).ordinal()])
        {
        case 1:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world"));
          }

          break;
        case 2:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world2") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world2"));
          }

          break;
        case 3:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world3") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world3"));
          }

          break;
        case 4:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world4") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world4"));
          }

          break;
        case 5:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world5") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world5"));
          }

          break;
        case 6:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world6") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world6"));
          }

          break;
        case 7:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world7") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world7"));
          }

          break;
        case 8:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world8") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world8"));
          }

          break;
        case 9:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world9") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world9").type(WorldType.FLAT));
          }

          break;
        case 10:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world10") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world10").type(WorldType.FLAT));
          }

          break;
        case 11:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world11") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world11").type(WorldType.FLAT));
          }

          break;
        case 12:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world12") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world12").environment(World.Environment.THE_END));
          }

          break;
        case 13:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world13") instanceof World))) {
            this.plugin.getServer().createWorld(new WorldCreator("world13").type(WorldType.FLAT));
          }

          break;
        case 14:
          if ((InfectionPermissions.isAdministrator(p)) && 
            (!(this.plugin.getServer().getWorld("world14") instanceof World)))
            this.plugin.getServer().createWorld(new WorldCreator("world14").type(WorldType.FLAT));
          break;
        }
      }
      catch (Exception e)
      {
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
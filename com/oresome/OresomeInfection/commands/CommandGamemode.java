package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGamemode
  implements CommandExecutor
{
  private Infection plugin;

  public CommandGamemode(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    Player player = (Player)sender;
    if (args.length == 0) return true;
    if (args.length > 1) return true;
    if ((!this.plugin.roundStarted) && (!this.plugin.roundStarting)) {
      if (args[0].equals("1"))
        player.setGameMode(GameMode.CREATIVE);
      if (args[0].equals("0"))
        player.setGameMode(GameMode.SURVIVAL);
      if (args[0].equals("2"))
        player.setGameMode(GameMode.ADVENTURE);
      return true;
    }
    return true;
  }
}
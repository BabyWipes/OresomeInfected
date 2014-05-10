package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionPermissions;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class CommandEnd
  implements CommandExecutor
{
  private Infection plugin;

  public CommandEnd(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((sender instanceof ConsoleCommandSender)) return true;
    if (!InfectionPermissions.isModerator((Player)sender)) { sender.sendMessage(ChatColor.DARK_RED + "Only moderators can do this."); return true; }
    if ((this.plugin.roundStarting) && (!this.plugin.roundStarted)) {
      sender.sendMessage(ChatColor.DARK_RED + "There's no round to end!");
      return true;
    }
    if ((!this.plugin.roundStarting) && (!this.plugin.roundStarted)) {
      sender.sendMessage(ChatColor.DARK_RED + "There's no round to end!");
      return true;
    }
    if (args.length != 0) return true;
    if (args.length == 0) {
      Bukkit.getScheduler().cancelTask(Infection.Round);
      Infection.round = 601;
      this.plugin.roundStarting = false;
      this.plugin.roundStarted = false;

      for (Player p : Bukkit.getOnlinePlayers()) {
        p.setHealth(20);
        this.plugin.makeHuman(p);
        p.setGameMode(GameMode.CREATIVE);
        this.plugin.MapMakers.clear();
      }
      this.plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + ChatColor.BOLD + "---[" + ChatColor.RESET + ChatColor.GREEN + "Game Over!" + ChatColor.DARK_GREEN + ChatColor.BOLD + "]---");
      this.plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + ChatColor.BOLD + "[" + ChatColor.GREEN + "Round was ended!" + ChatColor.DARK_GREEN + ChatColor.BOLD + "]");
      Infection.cycleTimer();
      Bukkit.getScheduler().cancelTask(Infection.Round);
      Bukkit.getScheduler().cancelTask(Infection.Countdown);
      this.plugin.roundStarting = false;
      this.plugin.roundStarted = false;
      this.plugin.roundCycling = true;
      this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.cycleToMap();
        }
      }
      , 700L);
    }
    return true;
  }
}
package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import com.oresome.OresomeInfection.InfectionFunctions;
import com.oresome.OresomeInfection.InfectionPermissions;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandAdminChat
  implements CommandExecutor
{
  private Infection plugin;
  private final Server server;
  private String message;
  private String fullmessage;
  private static final Logger log = Logger.getLogger("Minecraft");

  public CommandAdminChat(Infection instance)
  {
    this.plugin = instance;
    this.server = this.plugin.getServer();
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (args.length == 0) { sender.sendMessage(ChatColor.RED + "Invalid args!"); return true; }
    this.message = InfectionFunctions.implode(args, " ");
    if ((sender instanceof ConsoleCommandSender)) {
      this.fullmessage = (ChatColor.RED + "[A] " + ChatColor.GOLD + "*" + ChatColor.DARK_AQUA + "Console" + ChatColor.DARK_RED + " - " + ChatColor.RED + this.message);
      InfectionFunctions.messageMods(this.fullmessage);
      log.info("[A] *Console - " + this.message);
      return true;
    }
    if ((sender instanceof Player)) {
      Player player = (Player)sender;
      if (!InfectionPermissions.isVIP(player)) {
        sender.sendMessage(ChatColor.DARK_RED + "No permission.");
        return true;
      }
      this.fullmessage = (ChatColor.RED + "[A] " + player.getDisplayName() + ChatColor.DARK_RED + " - " + ChatColor.RED + this.message);
      InfectionFunctions.messageMods(this.fullmessage);
      log.info("[A] " + sender.getName() + " - " + this.message);
      return true;
    }

    return true;
  }
}
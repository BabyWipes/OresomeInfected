package com.oresome.OresomeInfection;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class InfectionFunctions
{
  public static Infection plugin;

  public static String combineString(String[] args)
  {
    List temp_list = new LinkedList();
    temp_list.addAll((Collection)Arrays.asList(args));
    while (temp_list.contains("")) {
      temp_list.remove("");
    }
    args = (String[])temp_list.toArray(new String[0]);

    return implode(args, " ");
  }

  public static String implode(String[] array, String glue) {
    String out = "";

    if (array.length == 0) {
      return out;
    }

    String[] arrayOfString = array; int j = array.length; for (int i = 0; i < j; i++) { String part = arrayOfString[i];
      out = out + part + glue;
    }
    out = out.substring(0, out.length() - glue.length());

    return out;
  }
  static boolean isUserOnline(String username, Server server) { return server.getOfflinePlayer(username).isOnline(); }

  public static boolean isParsableToInt(String i) {
    try {
      Integer.parseInt(i);
      return true; } catch (NumberFormatException nfe) {
    }
    return false;
  }

  public static void messageMods(String message) {
    for (String name : Infection.getIt().Staff) {
      Player player = Infection.getIt().getServer().getPlayer(name);
      if (player == null) return;
      player.sendMessage(message);
    }
  }
}
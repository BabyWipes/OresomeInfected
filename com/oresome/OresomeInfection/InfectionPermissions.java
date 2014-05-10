package com.oresome.OresomeInfection;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class InfectionPermissions
{
  public static boolean isModerator(Player player)
  {
    if (Infection.permission != null) return Infection.permission.playerHas(player, "oresomeinfected.mod");
    return player.hasPermission("oresomeinfected.mod");
  }
  public static boolean isAdministrator(Player player) {
    if (Infection.permission != null) return Infection.permission.playerHas(player, "oresomeinfected.admin");
    return player.hasPermission("oresomeinfected.admin");
  }
  public static boolean isDonor(Player player) {
    if (Infection.permission != null) return Infection.permission.playerHas(player, "oresomeinfected.donor");
    return player.hasPermission("oresomeinfected.donor");
  }
  public static boolean isVIP(Player player) {
    if (Infection.permission != null) return Infection.permission.playerHas(player, "oresomeinfected.vip");
    return player.hasPermission("oresomeinfected.vip");
  }
}
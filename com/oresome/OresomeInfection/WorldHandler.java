package com.oresome.OresomeInfection;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;

public class WorldHandler
{
  public static Infection plugin;

  public static void unloadWorld(String worldname)
  {
    World world = Bukkit.getServer().getWorld(worldname);
    Bukkit.getServer().unloadWorld(world, false);
  }

  public static void unloadWorldWithSave(String worldname)
  {
    if ((Bukkit.getServer().getWorld(worldname) instanceof World)) {
      World world = Bukkit.getServer().getWorld(worldname);
      Bukkit.getServer().unloadWorld(world, false);
    }
  }
}
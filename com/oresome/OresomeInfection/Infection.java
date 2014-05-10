package com.oresome.OresomeInfection;

import com.oresome.OresomeInfection.Listeners.BlockListener;
import com.oresome.OresomeInfection.Listeners.ChatListener;
import com.oresome.OresomeInfection.Listeners.JoinListener;
import com.oresome.OresomeInfection.Listeners.LastStandListener;
import com.oresome.OresomeInfection.Listeners.ListMotdListener;
import com.oresome.OresomeInfection.Listeners.NoItemsEvent;
import com.oresome.OresomeInfection.Listeners.PlayerDeath;
import com.oresome.OresomeInfection.Listeners.PlayerRespawn;
import com.oresome.OresomeInfection.Listeners.TeamDamageEvent;
import com.oresome.OresomeInfection.commands.CommandAdminChat;
import com.oresome.OresomeInfection.commands.CommandAlive;
import com.oresome.OresomeInfection.commands.CommandAllowBlocks;
import com.oresome.OresomeInfection.commands.CommandBroadcast;
import com.oresome.OresomeInfection.commands.CommandBuy;
import com.oresome.OresomeInfection.commands.CommandClearInventory;
import com.oresome.OresomeInfection.commands.CommandDisinfect;
import com.oresome.OresomeInfection.commands.CommandEnd;
import com.oresome.OresomeInfection.commands.CommandGamemode;
import com.oresome.OresomeInfection.commands.CommandInfect;
import com.oresome.OresomeInfection.commands.CommandInfected;
import com.oresome.OresomeInfection.commands.CommandLoad;
import com.oresome.OresomeInfection.commands.CommandMoney;
import com.oresome.OresomeInfection.commands.CommandRandom;
import com.oresome.OresomeInfection.commands.CommandReferee;
import com.oresome.OresomeInfection.commands.CommandRestart;
import com.oresome.OresomeInfection.commands.CommandRotation;
import com.oresome.OresomeInfection.commands.CommandRoundsLeft;
import com.oresome.OresomeInfection.commands.CommandSave;
import com.oresome.OresomeInfection.commands.CommandSetCookies;
import com.oresome.OresomeInfection.commands.CommandUnload;
import com.oresome.OresomeInfection.commands.CommandWarn;
import com.oresome.OresomeInfection.commands.CommandWorld;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Infection extends JavaPlugin
{
  private static Infection plugin;
  private static final Logger log = Logger.getLogger("Minecraft");
  public ArrayList<String> Humans = new ArrayList();
  public ArrayList<String> Zombies = new ArrayList();
  public ArrayList<String> Staff = new ArrayList();
  public ArrayList<String> Referees = new ArrayList();
  public ArrayList<String> MapMakers = new ArrayList();
  public ArrayList<String> loadedMaps = new ArrayList();
  public ArrayList<String> deathReasons = new ArrayList();
  private static EconomyConfigHandler econConfigHandler = new EconomyConfigHandler();
  public boolean chatModeration;
  public boolean roundStarted;
  public boolean roundStarting;
  public boolean roundCycling;
  public boolean announcedFinalPlayer;
  public boolean allowedToEditMaps;
  public int restart;
  public String currentMap;
  public String nextMap;
  public static Permission permission = null;
  public static int Countdown;
  static int count = 35;
  public static int Round;
  public static int round = 601;
  public static int Cycle;
  static int cycle = 35;
  public static int Restart;
  static int res = 35;

  public void onEnable()
  {
    this.restart = 20;
    addHumanCustomRecipe();
    this.loadedMaps.add("Toxic Wasteland");
    this.loadedMaps.add("Adminiumantics");
    this.loadedMaps.add("Hell's Gorge");
    this.loadedMaps.add("Hades' Castle");
    this.loadedMaps.add("Suburban Complex");
    this.loadedMaps.add("Satan's Sorrow");
    this.loadedMaps.add("No Way Out");
    this.loadedMaps.add("Darkness Of Dusk");
    this.loadedMaps.add("Funhouse");
    this.loadedMaps.add("Dead til' the End");
    this.deathReasons.add(" was slain by ");
    this.deathReasons.add(" tried to fight ");
    this.deathReasons.add(" couldn't beat ");
    this.deathReasons.add(" was too weak against ");
    plugin = this;
    setupPermissions();
    log.info("[OresomeInfection] Loading....");
    this.roundStarted = false;
    this.roundStarting = false;
    this.announcedFinalPlayer = false;
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new TeamDamageEvent(plugin), plugin);
    pm.registerEvents(new JoinListener(plugin), plugin);
    pm.registerEvents(new PlayerDeath(plugin), plugin);
    pm.registerEvents(new BlockListener(plugin), plugin);
    pm.registerEvents(new PlayerRespawn(plugin), plugin);
    pm.registerEvents(new LastStandListener(plugin), plugin);
    pm.registerEvents(new NoItemsEvent(plugin), plugin);
    pm.registerEvents(new ListMotdListener(plugin), plugin);
    pm.registerEvents(new ChatListener(plugin), plugin);
    log.info("[OresomeInfection] Events running!");
    try {
      MetricsLite metrics = new MetricsLite(this);
      metrics.start();
    } catch (IOException e) {
      log.info("[OresomeInfection Metrics] Unable to submit stats!");
    }
    getCommand("alive").setExecutor(new CommandAlive(plugin));
    getCommand("adminchat").setExecutor(new CommandAdminChat(plugin));
    getCommand("world").setExecutor(new CommandWorld(plugin));
    getCommand("random").setExecutor(new CommandRandom(plugin));
    getCommand("buy").setExecutor(new CommandBuy(plugin));
    getCommand("broadcast").setExecutor(new CommandBroadcast(plugin));
    getCommand("disinfect").setExecutor(new CommandDisinfect(plugin));
    getCommand("infected").setExecutor(new CommandInfected(plugin));
    getCommand("money").setExecutor(new CommandMoney(plugin));
    getCommand("referee").setExecutor(new CommandReferee(plugin));
    getCommand("save").setExecutor(new CommandSave(plugin));
    getCommand("warn").setExecutor(new CommandWarn(plugin));
    getCommand("load").setExecutor(new CommandLoad(plugin));
    getCommand("unload").setExecutor(new CommandUnload(plugin));
    getCommand("infect").setExecutor(new CommandInfect(plugin));
    getCommand("restart").setExecutor(new CommandRestart(plugin));
    getCommand("gamemode").setExecutor(new CommandGamemode(plugin));
    getCommand("setcookies").setExecutor(new CommandSetCookies(plugin));
    getCommand("rotation").setExecutor(new CommandRotation(plugin));
    getCommand("roundsleft").setExecutor(new CommandRoundsLeft(plugin));
    getCommand("end").setExecutor(new CommandEnd(plugin));
    getCommand("clearinventory").setExecutor(new CommandClearInventory(plugin));
    getCommand("allowblocks").setExecutor(new CommandAllowBlocks(plugin));
    log.info("[OresomeInfection] Commands enabled!");
    log.info("[OresomeInfection] Core Infection enabled!");
    String spawn = "Spawn";
    getServer().createWorld(new WorldCreator(spawn));
    System.out.println("[OresomeInfection] Loaded " + spawn.toString().replace("Spawn", "Spawn World"));
    System.out.println("[OresomeInfection] Total Worlds: " + getServer().getWorlds().size());
    plugin.getServer().createWorld(new WorldCreator("world8"));

    for (Player p : Bukkit.getOnlinePlayers()) {
      teleportToSatansSorrow(p);
      p.setGameMode(GameMode.SURVIVAL);
      plugin.makeHuman(p);
    }
    WorldHandler.unloadWorld("world");
    WorldHandler.unloadWorld("world6");
    WorldHandler.unloadWorld("world2");
    WorldHandler.unloadWorld("world3");
    WorldHandler.unloadWorld("world5");
    WorldHandler.unloadWorld("world9");
    WorldHandler.unloadWorld("world10");
    WorldHandler.unloadWorld("world11");
    WorldHandler.unloadWorld("world14");
    plugin.roundStarting = true;
    plugin.roundCycling = false;
    plugin.currentMap = null;
    plugin.currentMap = "Satan's Sorrow";
    Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
    plugin.MapMakers.clear();
    plugin.addMapMaker("R3creat3");
    plugin.addMapMaker("danielschroeder");
    plugin.addMapMaker("xannallax33");
    beforeRoundTimer();
    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
      public void run() {
        Infection.plugin.roundStarting = false;
        Infection.plugin.roundStarted = true;
        Infection.roundTimer();
        Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
        Infection.count = 35;
      }
    }
    , 700L);
  }

  public void onDisable()
  {
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      p.kickPlayer(ChatColor.RED + "Server restarting, relogin!");
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world4");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world7");
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world11");
      WorldHandler.unloadWorld("world12");
      WorldHandler.unloadWorld("world13");
      WorldHandler.unloadWorld("world14");
      WorldHandler.unloadWorld("world15");
      WorldHandler.unloadWorld("world1");
    }
  }

  public void addZombie(String playername) { if (!this.Zombies.contains(playername))
      this.Zombies.add(playername);
  }

  public void removeZombie(String playername)
  {
    if (this.Zombies.contains(playername))
      this.Zombies.remove(playername);
  }

  public void addHuman(String playername) {
    if (!this.Humans.contains(playername))
      this.Humans.add(playername);
  }

  public void addReferee(String playername) {
    if (!this.Referees.contains(playername))
      this.Referees.add(playername);
  }

  public void addMapMaker(String playername) {
    if (!this.MapMakers.contains(playername))
      this.MapMakers.add(playername);
  }

  public void addStaff(String playername) {
    if (!this.Staff.contains(playername))
      this.Staff.add(playername);
  }

  public void removeHuman(String playername) {
    if (this.Humans.contains(playername))
      this.Humans.remove(playername);
  }

  public void removeReferee(String playername) {
    if (this.Referees.contains(playername))
      this.Referees.remove(playername);
  }

  public void removeMapMaker(String playername) {
    if (this.MapMakers.contains(playername))
      this.MapMakers.remove(playername);
  }

  public void removeStaff(String playername) {
    if (this.Staff.contains(playername))
      this.Staff.remove(playername);
  }

  public ArrayList<String> getZombies() {
    return this.Zombies;
  }
  public ArrayList<String> getHumans() {
    return this.Humans;
  }
  public ArrayList<String> getMapMakers() {
    return this.MapMakers;
  }
  public ArrayList<String> getStaff() {
    return this.Staff;
  }
  public boolean isZombie(String string) {
    if (plugin.Zombies.contains(string)) {
      return true;
    }
    return false;
  }
  public boolean isHuman(String string) {
    if (plugin.Humans.contains(string)) {
      return true;
    }
    return false;
  }
  public boolean isStaff(String string) {
    if (plugin.Staff.contains(string)) {
      return true;
    }
    return false;
  }
  public boolean isReferee(String string) {
    if (plugin.Referees.contains(string)) {
      return true;
    }
    return false;
  }
  public boolean isMapMaker(String string) {
    if (plugin.MapMakers.contains(string)) {
      return true;
    }
    return false;
  }
  public boolean isInstanceZombie(Player string) {
    if (plugin.Zombies.contains(string)) {
      return true;
    }
    return false;
  }
  public boolean isInstanceHuman(Player string) {
    if (plugin.Humans.contains(string)) {
      return true;
    }
    return false;
  }
  public Infection getPlugin() {
    return plugin;
  }
  public static Infection getIt() {
    return plugin;
  }

  private Boolean setupPermissions() {
    RegisteredServiceProvider permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
    if (permissionProvider != null) {
      permission = (Permission)permissionProvider.getProvider();
      log.info("[OresomeInfection] Intergrated into vault!");
    }
    if (permission != null) return Boolean.valueOf(true); return Boolean.valueOf(false);
  }
  public void makeZombie(Player player) {
    String prefix = "";
    if ((InfectionPermissions.isDonor(player)) && 
      (!InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GREEN + "*";
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GREEN + "*" + ChatColor.RED + "*";
    }
    if ((!InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.RED + "*";
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GOLD + "*";
    }
    if ((!InfectionPermissions.isDonor(player)) && 
      (!InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = "";
    }
    String VIP = "";
    if ((InfectionPermissions.isVIP(player)) && (!InfectionPermissions.isModerator(player))) {
      VIP = ChatColor.YELLOW + "*";
    }
    player.setDisplayName(VIP + prefix + ChatColor.DARK_RED + player.getName());
    player.getInventory().setChestplate(null);
    player.getInventory().setLeggings(null);
    player.getInventory().setBoots(null);
    player.getInventory().setHelmet(null);
    player.getInventory().clear();
    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_SWORD, 1) });
    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.WOOD_PICKAXE) });
    player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COOKED_BEEF, 16) });
    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.SAND, 64) });
    plugin.addZombie(player.getName());
    plugin.removeHuman(player.getName());
    plugin.removeReferee(player.getName());
    player.setGameMode(GameMode.SURVIVAL);

    for (Player p : Bukkit.getOnlinePlayers()) {
      player.showPlayer(p);
      player.showPlayer(player);
    }
  }

  public void makeHuman(Player player) { String prefix = "";
    if ((InfectionPermissions.isDonor(player)) && 
      (!InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GREEN + "*";
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GREEN + "*" + ChatColor.RED + "*";
    }
    if ((!InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.RED + "*";
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GOLD + "*";
    }
    if ((!InfectionPermissions.isDonor(player)) && 
      (!InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = "";
    }
    String VIP = "";
    if ((InfectionPermissions.isVIP(player)) && (!InfectionPermissions.isModerator(player))) {
      VIP = ChatColor.YELLOW + "*";
    }
    player.setDisplayName(VIP + prefix + ChatColor.GREEN + player.getName());
    player.getInventory().setChestplate(null);
    player.getInventory().setLeggings(null);
    player.getInventory().setBoots(null);
    player.getInventory().setHelmet(null);
    player.getInventory().clear();
    ItemStack enchantChestPlate = new ItemStack(Material.IRON_CHESTPLATE, 1);
    ItemMeta im = enchantChestPlate.getItemMeta();
    String name = ChatColor.DARK_GREEN + ChatColor.DARK_GREEN + ChatColor.BOLD + ChatColor.ITALIC + "Protective Chestplate";
    im.setDisplayName(name);
    ArrayList lore = new ArrayList();
    lore.add(ChatColor.GREEN + ChatColor.ITALIC + "Consumes your falls!");
    im.setLore(lore);
    enchantChestPlate.setItemMeta(im);
    enchantChestPlate.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 1);
    player.getInventory().setChestplate(enchantChestPlate);
    player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
    ItemStack ss = new ItemStack(Material.STONE_SWORD, 1);
    ItemMeta imss = ss.getItemMeta();
    String namess = ChatColor.DARK_GREEN + ChatColor.DARK_GREEN + ChatColor.BOLD + ChatColor.ITALIC + "Human Sword";
    imss.setDisplayName(namess);
    ArrayList loress = new ArrayList();
    loress.add(ChatColor.GREEN + ChatColor.ITALIC + "Protect yourself from zombies!");
    imss.setLore(loress);
    ss.setItemMeta(imss);
    player.getInventory().setChestplate(enchantChestPlate);
    player.getInventory().addItem(new ItemStack[] { ss });
    plugin.addHuman(player.getName());
    plugin.removeZombie(player.getName());
    plugin.removeReferee(player.getName());
    ItemStack sss = new ItemStack(Material.STONE_PICKAXE, 1);
    ItemMeta imsss = sss.getItemMeta();
    String namesss = ChatColor.DARK_GREEN + ChatColor.DARK_GREEN + ChatColor.BOLD + ChatColor.ITALIC + "Miner's Envy";
    imsss.setDisplayName(namesss);
    ArrayList loresss = new ArrayList();
    loresss.add(ChatColor.GREEN + ChatColor.ITALIC + "Show off those miners!");
    imsss.setLore(loresss);
    sss.setItemMeta(imsss);
    sss.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
    player.getInventory().addItem(new ItemStack[] { sss });
    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COOKED_BEEF, 16) });
    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIRT, 64) });

    for (Player p : Bukkit.getOnlinePlayers()) {
      player.showPlayer(p);
      player.showPlayer(player);
    }
    player.setGameMode(GameMode.SURVIVAL); }

  public static void teleportToGorge(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world"), 319.0D, 12.0D, -525.0D));
  }
  public static void teleportToAdminiumantics(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world2"), 390.0D, 25.0D, -464.0D));
  }
  public static void teleportToToxicWasteland(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world3"), 289.0D, 27.0D, -562.0D));
  }
  public static void teleportToJintaExtreme(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world4"), 319.0D, 12.0D, -525.0D));
  }
  public static void teleportToHadesCastle(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world5"), 334.0D, 33.0D, -460.0D));
  }
  public static void teleportToSuburbanComplex(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world6"), 337.0D, 53.0D, -487.0D));
  }
  public static void teleportToFuturisticFantasy(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world7"), 337.0D, 53.0D, -487.0D));
  }
  public static void teleportToSatansSorrow(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world8"), 239.0D, 61.0D, -424.0D));
  }
  public static void teleportToNoWayOut(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world9"), 195.0D, 41.0D, -414.0D));
  }
  public static void teleportToDarknessOfDusk(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world10"), 61.0D, 6.0D, 22.0D));
  }
  public static void teleportToFunhouse(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world11"), 216.0D, 13.0D, 184.0D));
  }
  public static void teleportToDeadTilTheEnd(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world12"), 216.0D, 13.0D, 184.0D));
  }
  public static void teleportToBloodsLoft(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world13"), 61.0D, 6.0D, 22.0D));
  }
  public static void teleportToGlacier(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world14"), 71.0D, 14.0D, 27.0D));
  }
  public static void teleportToLastStand(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world15"), 2019.0D, 26.0D, 955.0D));
  }
  public static void teleportToLastStandZombieSpawn(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world15"), 1970.0D, 16.0D, 907.0D));
  }
  public static void teleportToLastStandHumanSpawn(Player player) {
    player.teleport(new Location(plugin.getServer().getWorld("world15"), 2108.0D, 30.0D, 916.0D));
  }
  public static String randomMap() {
    Random generator = new Random();
    int index = generator.nextInt(plugin.loadedMaps.size());
    if (index > -1) return (String)plugin.loadedMaps.get(index);
    return (String)plugin.loadedMaps.get(index);
  }
  public String randomDeathReason() {
    Random generator = new Random();
    int index = generator.nextInt(plugin.deathReasons.size());
    if (index > -1) return (String)plugin.deathReasons.get(index);
    return (String)plugin.deathReasons.get(index);
  }
  public String randomPlayer() {
    Random generator = new Random();
    int index = generator.nextInt(plugin.Humans.size());
    if (index > -1) return (String)plugin.Humans.get(index);
    return (String)plugin.Humans.get(index);
  }
  public String currentMap() {
    String name = "";
    name = this.currentMap.toString();
    return name;
  }

  public void checkStatus() {
    if (this.roundStarted) {
      if (plugin.Humans.size() > 1) {
        plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + "Humans Left: " + ChatColor.GREEN + "(" + plugin.Humans.size() + ")");
        this.announcedFinalPlayer = false;
      }
      if ((plugin.Humans.size() == 1) && 
        (!this.announcedFinalPlayer)) {
        plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + "Humans Left: " + ChatColor.GREEN + "(" + plugin.Humans.size() + ")");
        plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + "It's all up to " + ChatColor.GREEN + plugin.randomPlayer() + ChatColor.DARK_GREEN + "! Can he make it??");
        this.announcedFinalPlayer = true;
      }
    }

    if ((this.roundStarted) && (this.Humans.size() == 0) && (this.restart > 1))
    {
      for (Player p : Bukkit.getOnlinePlayers()) {
        p.setHealth(20);
        plugin.makeHuman(p);
        plugin.MapMakers.clear();
        p.setGameMode(GameMode.CREATIVE);
      }
      this.roundStarting = false;
      this.announcedFinalPlayer = false;
      this.roundStarted = false;
      plugin.roundCycling = true;
      plugin.getServer().getScheduler().cancelTask(Round);
      round = 601;
      getServer().broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD + "---[" + ChatColor.RESET + ChatColor.RED + "Game Over!" + ChatColor.DARK_RED + ChatColor.BOLD + "]---");
      getServer().broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD + "[" + ChatColor.RED + "All Humans Perished" + ChatColor.DARK_RED + ChatColor.BOLD + "]");
      cycleTimer();
      plugin.getServer().getScheduler().cancelTask(Countdown);
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.cycleToMap();
        }
      }
      , 700L);
      if (this.restart == 1) {
        restart();
      }
    }
    if ((!this.roundStarted) && (this.Humans.size() == 0) && (this.restart > 1))
    {
      for (Player p : Bukkit.getOnlinePlayers()) {
        p.setHealth(20);
        plugin.makeHuman(p);
        plugin.MapMakers.clear();
        p.setGameMode(GameMode.CREATIVE);
      }
      this.roundStarting = false;
      this.announcedFinalPlayer = false;
      this.roundStarted = false;
      plugin.roundCycling = true;
      plugin.getServer().getScheduler().cancelTask(Round);
      round = 601;
      getServer().broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD + "---[" + ChatColor.RESET + ChatColor.RED + "Game Over!" + ChatColor.DARK_RED + ChatColor.BOLD + "]---");
      getServer().broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD + "[" + ChatColor.RED + "All Humans Perished" + ChatColor.DARK_RED + ChatColor.BOLD + "]"); cycleTimer();
      plugin.getServer().getScheduler().cancelTask(Countdown);
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.cycleToMap();
        }
      }
      , 700L);
      if (this.restart == 1)
        restart();
    }
  }

  public static EconomyConfigHandler getUserHandler()
  {
    return econConfigHandler;
  }

  public void reloadSettings()
  {
    reloadConfig();
    saveConfig();
  }
  public void addHumanCustomRecipe() {
    ItemStack is = new ItemStack(Material.STONE_SWORD, 1);
    ItemMeta im = is.getItemMeta();
    String name = ChatColor.DARK_RED + ChatColor.DARK_RED + ChatColor.BOLD + ChatColor.ITALIC + "Zombie Banisher";
    im.setDisplayName(name);
    ArrayList lore = new ArrayList();
    lore.add(ChatColor.RED + ChatColor.ITALIC + "Super effective on zombies");
    lore.add(ChatColor.RED + ChatColor.ITALIC + "and cooks your pork!");
    im.setLore(lore);
    is.setItemMeta(im);
    is.addEnchantment(Enchantment.FIRE_ASPECT, 1);
    ShapedRecipe recipe = new ShapedRecipe(is);
    recipe.shape(new String[] { "BBB", "BAB", "BBB" });
    recipe.setIngredient('B', Material.DIRT);
    recipe.setIngredient('A', Material.COOKED_BEEF);
    getServer().addRecipe(recipe);
  }

  public static void beforeRoundTimer()
  {
    Countdown = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable()
    {
      public void run() {
        Infection.count -= 1;
        if ((Infection.count == 30) || (Infection.count == 20) || (Infection.count == 15) || (Infection.count < 11)) {
          Bukkit.broadcastMessage(ChatColor.DARK_RED + Infection.count + ChatColor.RED + " seconds until game starts!");
          if ((!Infection.plugin.roundStarted) && (!Infection.plugin.roundStarting) && (!Infection.plugin.roundCycling)) {
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
            Infection.count = 35;
          }
          if (Infection.plugin.roundCycling) {
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
            Infection.count = 35;
          }
        }
        if ((Infection.count == 0) && (Infection.plugin.Humans.size() > 0) && (Infection.plugin.currentMap != "Last Stand") && 
          (Infection.plugin.Humans.size() > 0)) {
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + "The Round has started!");
          Player player = Bukkit.getServer().getPlayer(Infection.plugin.randomPlayer());
          Infection.plugin.makeZombie(player);
          Infection.plugin.addZombie(player.getName());
          Infection.plugin.removeHuman(player.getName());
          player.getWorld().strikeLightningEffect(player.getLocation());
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + player.getDisplayName() + ChatColor.RED + " was infected!");
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundCycling = false;
          Infection.plugin.roundStarted = true;
          Infection.plugin.checkStatus();
          if (Infection.plugin.restart > 1)
            Infection.plugin.restart -= 1;
          return;
        }
        Object localObject;
        Player p;
        if ((Infection.count == 0) && (Infection.plugin.Humans.size() > 3) && (Infection.plugin.currentMap == "Last Stand")) {
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + "The Round has started!");

          Player player = Bukkit.getServer().getPlayer(Infection.plugin.randomPlayer());
          Infection.plugin.makeFinalStandZombie(player);
          Infection.plugin.addZombie(player.getName());
          Infection.plugin.removeHuman(player.getName());
          player.getWorld().strikeLightningEffect(player.getLocation());
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + player.getDisplayName() + ChatColor.RED + " was infected!");
          Infection.teleportToLastStandZombieSpawn(player);

          Player player2 = Bukkit.getServer().getPlayer(Infection.plugin.randomPlayer());
          Infection.plugin.makeFinalStandZombie(player2);
          Infection.plugin.addZombie(player2.getName());
          Infection.plugin.removeHuman(player2.getName());
          player2.getWorld().strikeLightningEffect(player2.getLocation());
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + player2.getDisplayName() + ChatColor.RED + " was infected!");
          Infection.teleportToLastStandZombieSpawn(player2);

          Player player3 = Bukkit.getServer().getPlayer(Infection.plugin.randomPlayer());
          Infection.plugin.makeFinalStandZombie(player3);
          Infection.plugin.addZombie(player3.getName());
          Infection.plugin.removeHuman(player3.getName());
          player3.getWorld().strikeLightningEffect(player3.getLocation());
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + player3.getDisplayName() + ChatColor.RED + " was infected!");
          Infection.teleportToLastStandZombieSpawn(player3);
          Player[] arrayOfPlayer2;
          int i = (arrayOfPlayer2 = Bukkit.getServer().getOnlinePlayers()).length; for (localObject = 0; localObject < i; localObject++) { p = arrayOfPlayer2[localObject];
            if (Infection.plugin.isHuman(p.getName())) {
              Infection.teleportToLastStandHumanSpawn(p);
            }
          }
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundCycling = false;
          Infection.plugin.roundStarted = true;
          Infection.plugin.checkStatus();
          if (Infection.plugin.restart > 1)
            Infection.plugin.restart -= 1;
          return;
        }
        Player p;
        if ((Infection.count == 0) && (Infection.plugin.Humans.size() > 1) && (Infection.plugin.currentMap == "Last Stand")) {
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + "The Round has started!");

          Player player = Bukkit.getServer().getPlayer(Infection.plugin.randomPlayer());
          Infection.plugin.makeFinalStandZombie(player);
          Infection.plugin.addZombie(player.getName());
          Infection.plugin.removeHuman(player.getName());
          player.getWorld().strikeLightningEffect(player.getLocation());
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + player.getDisplayName() + ChatColor.RED + " was infected!");
          Infection.teleportToLastStandZombieSpawn(player);

          Player player2 = Bukkit.getServer().getPlayer(Infection.plugin.randomPlayer());
          Infection.plugin.makeFinalStandZombie(player2);
          Infection.plugin.addZombie(player2.getName());
          Infection.plugin.removeHuman(player2.getName());
          player2.getWorld().strikeLightningEffect(player2.getLocation());
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + player2.getDisplayName() + ChatColor.RED + " was infected!");
          Infection.teleportToLastStandZombieSpawn(player2);
          Player[] arrayOfPlayer1;
          localObject = (arrayOfPlayer1 = Bukkit.getServer().getOnlinePlayers()).length; for (p = 0; p < localObject; p++) { p = arrayOfPlayer1[p];
            if (Infection.plugin.isHuman(p.getName())) {
              Infection.teleportToLastStandHumanSpawn(p);
            }
          }
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundCycling = false;
          Infection.plugin.roundStarted = true;
          Infection.plugin.checkStatus();
          if (Infection.plugin.restart > 1)
            Infection.plugin.restart -= 1;
          return;
        }
        Player p;
        if ((Infection.count == 0) && (Infection.plugin.Humans.size() > 1) && (Infection.plugin.currentMap == "Last Stand")) {
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + "The Round has started!");
          Player player = Bukkit.getServer().getPlayer(Infection.plugin.randomPlayer());
          Infection.plugin.makeFinalStandZombie(player);
          Infection.plugin.addZombie(player.getName());
          Infection.plugin.removeHuman(player.getName());
          player.getWorld().strikeLightningEffect(player.getLocation());
          Infection.plugin.getServer().broadcastMessage(ChatColor.RED + player.getDisplayName() + ChatColor.RED + " was infected!");
          Infection.teleportToLastStandZombieSpawn(player);

          p = (localObject = Bukkit.getServer().getOnlinePlayers()).length; for (p = 0; p < p; p++) { p = localObject[p];
            if (Infection.plugin.isHuman(p.getName())) {
              Infection.teleportToLastStandHumanSpawn(p);
            }
          }
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundCycling = false;
          Infection.plugin.roundStarted = true;
          Infection.plugin.checkStatus();
          if (Infection.plugin.restart > 1) {
            Infection.plugin.restart -= 1;
          }
          return;
        }
        if ((Infection.count == 0) && (Infection.plugin.Humans.size() == 0)) {
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;

          p = (p = Bukkit.getOnlinePlayers()).length; for (p = 0; p < p; p++) { Player p = p[p];
            p.setHealth(20);
            Infection.plugin.makeHuman(p);
            Infection.plugin.MapMakers.clear();
            p.setGameMode(GameMode.CREATIVE);
          }
          Infection.plugin.roundStarting = false;
          Infection.plugin.announcedFinalPlayer = false;
          Infection.plugin.roundStarted = false;
          Infection.plugin.roundCycling = true;
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.round = 601;
          Infection.plugin.getServer().broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD + "---[" + ChatColor.RESET + ChatColor.RED + "Game Over!" + ChatColor.DARK_RED + ChatColor.BOLD + "]---");
          Infection.plugin.getServer().broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD + "[" + ChatColor.RED + "All Humans Perished" + ChatColor.DARK_RED + ChatColor.BOLD + "]"); Infection.cycleTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          if (Infection.plugin.restart > 1) {
            Infection.plugin.restart -= 1;
          }
          Infection.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Infection.plugin.getPlugin(), new Runnable() {
            public void run() {
              Infection.cycleToMap();
            }
          }
          , 700L);
        }
      }
    }
    , 0L, 20L);
  }

  public static void roundTimer()
  {
    Round = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable()
    {
      public void run() {
        if (Infection.plugin.roundCycling) {
          Infection.round = 601;
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          return;
        }
        Infection.round -= 1;
        if (Infection.round == 600) {
          Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
          Bukkit.getServer().broadcastMessage(ChatColor.RED + "10:00 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
        }
        if ((Infection.round == 570) && 
          (Infection.plugin.roundStarted)) {
          Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
          Bukkit.getServer().broadcastMessage(ChatColor.RED + "9:30 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
        }

        if ((Infection.round == 540) && 
          (Infection.plugin.roundStarted)) {
          Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
          Bukkit.getServer().broadcastMessage(ChatColor.RED + "9:00 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
        }

        if (Infection.round == 510) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "8:30 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 480) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "8:00 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 450) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "7:30 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 420) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "7:00 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 390) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "6:30 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 360) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "6:00 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 330) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "5:30 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 300) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "5:00 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 270) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "4:30 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 240) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "4:00 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 210) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "3:30 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 180) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "3:00 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 150) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "2:30 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 120) {
          if ((Infection.plugin.roundStarted) && (!Infection.plugin.roundCycling)) {
            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "2:00 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 90) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "1:30 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 60) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_RED + "Now Playing " + ChatColor.RED + ChatColor.BOLD + Infection.plugin.currentMap);
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "1:00 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 30) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "0:30 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 15) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "0:15 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if (Infection.round == 5) {
          if (Infection.plugin.roundStarted) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "0:05 " + ChatColor.DARK_RED + "-" + ChatColor.DARK_GREEN + " (" + ChatColor.GREEN + Infection.plugin.Humans.size() + ChatColor.DARK_GREEN + ")" + ChatColor.GREEN + " Humans left");
          }
          if (!Infection.plugin.roundStarted) {
            Infection.round = 601;
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
          }
        }
        if ((Infection.round > 0) && (!Infection.plugin.roundStarted) && (!Infection.plugin.roundStarting) && (Infection.plugin.roundCycling)) {
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
        }
        if (Infection.round == 0) {
          if (Infection.plugin.restart > 1) {
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
            Infection.round = 601;
            Infection.plugin.roundStarting = false;
            Infection.plugin.roundStarted = false;
            int killersOldMoney;
            for (String name : Infection.plugin.getPlugin().Humans) {
              player = Infection.getIt().getServer().getPlayer(name);
              player.sendMessage(ChatColor.GOLD + "You earned 10 cookies for surviving a round");
              killersOldMoney = Infection.plugin.getConfig().getInt(player.getName() + ".economy.money");
              int newMoney = killersOldMoney + 10;
              Infection.plugin.getConfig().set(player.getName() + ".economy.money", Integer.valueOf(newMoney));
              Infection.plugin.saveConfig();
            }

            Player player = (killersOldMoney = Bukkit.getOnlinePlayers()).length; for (Player localPlayer1 = 0; localPlayer1 < player; localPlayer1++) { Player p = killersOldMoney[localPlayer1];
              p.setHealth(20);
              Infection.plugin.Zombies.clear();
              Infection.plugin.Humans.clear();
              Infection.plugin.makeHuman(p);
              p.setGameMode(GameMode.CREATIVE);
              Infection.plugin.MapMakers.clear();
            }
            Infection.plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + ChatColor.BOLD + "---[" + ChatColor.RESET + ChatColor.GREEN + "Game Over!" + ChatColor.DARK_GREEN + ChatColor.BOLD + "]---");
            Infection.plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + ChatColor.BOLD + "[" + ChatColor.GREEN + "The Humans survived!" + ChatColor.DARK_GREEN + ChatColor.BOLD + "]");
            Infection.cycleTimer();
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Round);
            Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
            Infection.plugin.roundStarting = false;
            Infection.plugin.roundStarted = false;
            Infection.plugin.roundCycling = true;
            Infection.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Infection.plugin.getPlugin(), new Runnable() {
              public void run() {
                Infection.cycleToMap();
              }
            }
            , 700L);
          }
          if (Infection.plugin.restart == 1)
            Infection.restart();
        }
      }
    }
    , 0L, 20L);
  }

  public static void cycleTimer()
  {
    Cycle = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(getIt(), new Runnable() {
      public void run() {
        Infection.cycle -= 1;
        if (Infection.cycle == 33) {
          if (Infection.plugin.currentMap == "Glacier") {
            Infection.plugin.nextMap = "Satan's Sorrow";
          }
          if (Infection.plugin.currentMap == "Satan's Sorrow") {
            Infection.plugin.nextMap = "Last Stand";
          }
          if (Infection.plugin.currentMap == "Last Stand") {
            Infection.plugin.nextMap = "Funhouse";
          }
          if (Infection.plugin.currentMap == "Funhouse") {
            Infection.plugin.nextMap = "Hell's Gorge";
          }
          if (Infection.plugin.currentMap == "Funhouse") {
            Infection.plugin.nextMap = "Hell's Gorge";
          }
          if (Infection.plugin.currentMap == "Hell's Gorge") {
            Infection.plugin.nextMap = "Toxic Wasteland";
          }
          if (Infection.plugin.currentMap == "Toxic Wasteland") {
            Infection.plugin.nextMap = "Adminiumantics";
          }
          if (Infection.plugin.currentMap == "Adminiumantics") {
            Infection.plugin.nextMap = "Hades' Castle";
          }
          if (Infection.plugin.currentMap == "Hades' Castle") {
            Infection.plugin.nextMap = "Suburban Complex";
          }
          if (Infection.plugin.currentMap == "Suburban Complex") {
            Infection.plugin.nextMap = "No Way Out";
          }
          if (Infection.plugin.currentMap == "No Way Out") {
            Infection.plugin.nextMap = "Darkness Of Dusk";
          }
          if (Infection.plugin.currentMap == "Darkness Of Dusk") {
            Infection.plugin.nextMap = "Glacier";
          }
        }
        if ((Infection.cycle == 30) || (Infection.cycle == 20) || (Infection.cycle == 15) || (Infection.cycle < 11)) {
          Bukkit.broadcastMessage(ChatColor.RED + "Cycling to " + Infection.plugin.nextMap + " in " + ChatColor.DARK_RED + Infection.cycle + ChatColor.RED + " seconds!");
        }
        if (Infection.cycle == 0) {
          Infection.cycle = 35;
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Cycle);
        }
      }
    }
    , 0L, 20L);
  }
  public static void cycleToMap() {
    if (plugin.currentMap == "Glacier") {
      plugin.getServer().createWorld(new WorldCreator("world8"));

      for (Player p : Bukkit.getOnlinePlayers()) {
        teleportToSatansSorrow(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world11");
      WorldHandler.unloadWorld("world14");
      WorldHandler.unloadWorld("world15");
      plugin.roundStarting = true;
      plugin.roundCycling = false;
      plugin.currentMap = null;
      plugin.currentMap = "Satan's Sorrow";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("R3creat3");
      plugin.addMapMaker("danielschroeder");
      plugin.addMapMaker("xannallax33");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundStarted = true;
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
    if (plugin.currentMap == "Satan's Sorrow")
    {
      for (Player p : Bukkit.getOnlinePlayers()) {
        plugin.getServer().createWorld(new WorldCreator("world15"));
        teleportToLastStand(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world14");
      plugin.roundStarting = true;
      plugin.roundCycling = false;
      plugin.currentMap = null;
      plugin.currentMap = "Last Stand";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! 1/3 zombies will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("R3creat3");
      plugin.addMapMaker("JackPownage");
      plugin.addMapMaker("sorroway");
      plugin.addMapMaker("jazz10101010");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundStarted = true;
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
    if (plugin.currentMap == "Last Stand")
    {
      for (Player p : Bukkit.getOnlinePlayers()) {
        plugin.getServer().createWorld(new WorldCreator("world11"));
        teleportToFunhouse(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world14");
      WorldHandler.unloadWorld("world15");
      plugin.roundStarting = true;
      plugin.roundCycling = false;
      plugin.currentMap = null;
      plugin.currentMap = "Funhouse";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("R3creat3");
      plugin.addMapMaker("Poyomon2");
      plugin.addMapMaker("xannallax33");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundStarted = true;
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
    if (plugin.currentMap == "Funhouse") {
      plugin.getServer().createWorld(new WorldCreator("world"));

      for (Player p : Bukkit.getOnlinePlayers()) {
        teleportToGorge(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world11");
      WorldHandler.unloadWorld("world14");
      WorldHandler.unloadWorld("world15");
      plugin.roundStarting = true;
      plugin.roundCycling = false;
      plugin.currentMap = null;
      plugin.currentMap = "Hell's Gorge";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("R3creat3");
      plugin.addMapMaker("_Moist");
      plugin.addMapMaker("xannallax33");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundStarted = true;
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
    if (plugin.currentMap == "Hell's Gorge") {
      plugin.getServer().createWorld(new WorldCreator("world3"));

      for (Player p : Bukkit.getOnlinePlayers()) {
        teleportToToxicWasteland(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world11");
      WorldHandler.unloadWorld("world14");
      WorldHandler.unloadWorld("world15");
      plugin.currentMap = null;
      plugin.roundCycling = false;
      plugin.roundStarting = true;
      plugin.currentMap = "Toxic Wasteland";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("_Moist");
      plugin.addMapMaker("xannallax33");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundStarted = true;
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
    if (plugin.currentMap == "Toxic Wasteland") {
      plugin.getServer().createWorld(new WorldCreator("world2"));

      for (Player p : Bukkit.getOnlinePlayers()) {
        teleportToAdminiumantics(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world11");
      WorldHandler.unloadWorld("world14");
      WorldHandler.unloadWorld("world15");
      plugin.roundStarting = true;
      plugin.roundCycling = false;
      plugin.currentMap = null;
      plugin.currentMap = "Adminiumantics";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("R3creat3");
      plugin.addMapMaker("kalikakitty");
      plugin.addMapMaker("sgarr17");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
    if (plugin.currentMap == "Adminiumantics") {
      plugin.getServer().createWorld(new WorldCreator("world5"));

      for (Player p : Bukkit.getOnlinePlayers()) {
        teleportToHadesCastle(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world11");
      WorldHandler.unloadWorld("world14");
      WorldHandler.unloadWorld("world15");
      plugin.roundStarting = true;
      plugin.currentMap = null;
      plugin.roundCycling = false;
      plugin.currentMap = "Hades' Castle";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("GPWonderful");
      plugin.addMapMaker("xannallax33");
      plugin.addMapMaker("kalikakitty");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundStarted = true;
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
    if (plugin.currentMap == "Hades' Castle") {
      plugin.getServer().createWorld(new WorldCreator("world6"));

      for (Player p : Bukkit.getOnlinePlayers()) {
        teleportToSuburbanComplex(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world11");
      WorldHandler.unloadWorld("world14");
      WorldHandler.unloadWorld("world15");
      plugin.roundStarting = true;
      plugin.roundCycling = false;
      plugin.currentMap = null;
      plugin.currentMap = "Suburban Complex";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("R3creat3");
      plugin.addMapMaker("danielschroeder");
      plugin.addMapMaker("xannallax33");
      plugin.addMapMaker("kalikakitty");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundStarted = true;
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
    if (plugin.currentMap == "Suburban Complex")
    {
      for (Player p : Bukkit.getOnlinePlayers()) {
        plugin.getServer().createWorld(new WorldCreator("world9"));
        teleportToNoWayOut(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world11");
      WorldHandler.unloadWorld("world14");
      WorldHandler.unloadWorld("world15");
      plugin.roundStarting = true;
      plugin.roundCycling = false;
      plugin.currentMap = null;
      plugin.currentMap = "No Way Out";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("R3creat3");
      plugin.addMapMaker("danielschroeder");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundStarted = true;
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
    if (plugin.currentMap == "No Way Out")
    {
      for (Player p : Bukkit.getOnlinePlayers()) {
        plugin.getServer().createWorld(new WorldCreator("world10"));
        teleportToDarknessOfDusk(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world11");
      WorldHandler.unloadWorld("world14");
      WorldHandler.unloadWorld("world15");
      plugin.roundStarting = true;
      plugin.roundCycling = false;
      plugin.currentMap = null;
      plugin.currentMap = "Darkness Of Dusk";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("xannallax33");
      plugin.addMapMaker("dinner1111");
      plugin.addMapMaker("pepsidawg00");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundStarted = true;
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
    if (plugin.currentMap == "Darkness Of Dusk")
    {
      for (Player p : Bukkit.getOnlinePlayers()) {
        plugin.getServer().createWorld(new WorldCreator("world14"));
        teleportToGlacier(p);
        p.setGameMode(GameMode.SURVIVAL);
        plugin.makeHuman(p);
      }
      WorldHandler.unloadWorld("world");
      WorldHandler.unloadWorld("world8");
      WorldHandler.unloadWorld("world6");
      WorldHandler.unloadWorld("world2");
      WorldHandler.unloadWorld("world3");
      WorldHandler.unloadWorld("world5");
      WorldHandler.unloadWorld("world9");
      WorldHandler.unloadWorld("world11");
      WorldHandler.unloadWorld("world10");
      WorldHandler.unloadWorld("world15");
      plugin.roundStarting = true;
      plugin.roundCycling = false;
      plugin.currentMap = null;
      plugin.currentMap = "Glacier";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[Announcement]" + ChatColor.RED + " Round starting in 35 seconds! A zombie will be picked at random!");
      plugin.MapMakers.clear();
      plugin.addMapMaker("R3creat3");
      plugin.addMapMaker("zezo268");
      plugin.addMapMaker("WannaMineMyCraft");
      plugin.addMapMaker("jazz10101010");
      beforeRoundTimer();
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
        public void run() {
          Infection.plugin.roundStarting = false;
          Infection.plugin.roundStarted = true;
          Infection.roundTimer();
          Infection.plugin.getServer().getScheduler().cancelTask(Infection.Countdown);
          Infection.count = 35;
        }
      }
      , 700L);
      return;
    }
  }

  public static void restart()
  {
    Restart = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
      public void run() {
        Infection.res -= 1;
        if ((Infection.res == 30) || (Infection.res == 20) || (Infection.res == 15) || (Infection.res < 11)) {
          Bukkit.broadcastMessage(ChatColor.RED + "Server restarting in " + ChatColor.DARK_RED + Infection.res + ChatColor.RED + " seconds!");
        }
        if (Infection.res == 0) {
          Bukkit.dispatchCommand(Infection.plugin.getServer().getConsoleSender(), "sendall hub");
          Bukkit.shutdown();
        }
      }
    }
    , 0L, 20L);
  }
  public void makeFinalStandZombie(Player player) {
    String prefix = "";
    if ((InfectionPermissions.isDonor(player)) && 
      (!InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GREEN + "*";
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GREEN + "*" + ChatColor.RED + "*";
    }
    if ((!InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.RED + "*";
    }
    if ((InfectionPermissions.isDonor(player)) && 
      (InfectionPermissions.isModerator(player)) && 
      (InfectionPermissions.isAdministrator(player))) {
      prefix = ChatColor.GOLD + "*";
    }
    if ((!InfectionPermissions.isDonor(player)) && 
      (!InfectionPermissions.isModerator(player)) && 
      (!InfectionPermissions.isAdministrator(player))) {
      prefix = "";
    }
    String VIP = "";
    if ((InfectionPermissions.isVIP(player)) && (!InfectionPermissions.isModerator(player))) {
      VIP = ChatColor.YELLOW + "*";
    }
    player.setDisplayName(VIP + prefix + ChatColor.DARK_RED + player.getName());
    player.getInventory().setChestplate(null);
    player.getInventory().setLeggings(null);
    player.getInventory().setBoots(null);
    player.getInventory().setHelmet(null);
    player.getInventory().clear();
    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_SWORD, 1) });
    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.WOOD_PICKAXE) });
    player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COOKED_BEEF, 16) });
    ItemStack is = new ItemStack(Material.DIAMOND_PICKAXE, 1);
    ItemMeta im = is.getItemMeta();
    String name = ChatColor.DARK_RED + ChatColor.DARK_RED + ChatColor.BOLD + ChatColor.ITALIC + "Obsidian Breaker";
    im.setDisplayName(name);
    ArrayList lore = new ArrayList();
    lore.add(ChatColor.RED + ChatColor.ITALIC + "Only 1 use!");
    im.setLore(lore);
    is.setItemMeta(im);
    player.getInventory().addItem(new ItemStack[] { is });
    short dura = 0;
    is.setDurability(dura);
    plugin.addZombie(player.getName());
    plugin.removeHuman(player.getName());
    plugin.removeReferee(player.getName());
    player.setGameMode(GameMode.SURVIVAL);

    for (Player p : Bukkit.getOnlinePlayers()) {
      player.showPlayer(p);
      p.showPlayer(player);
    }
  }
}
package com.oresome.OresomeInfection.commands;

import com.oresome.OresomeInfection.Infection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandBuy
  implements CommandExecutor
{
  private Infection plugin;

  public CommandBuy(Infection plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if ((sender instanceof ConsoleCommandSender)) return true;
    Player player = (Player)sender;
    if (args.length == 0) {
      sender.sendMessage(ChatColor.BOLD + ChatColor.DARK_GREEN + "###" + ChatColor.RESET + ChatColor.GREEN + "SHOP" + ChatColor.BOLD + ChatColor.DARK_GREEN + "###");
      sender.sendMessage(ChatColor.GOLD + "- " + ChatColor.YELLOW + "Revive" + ChatColor.GOLD + " (100 cookies)");
      sender.sendMessage(ChatColor.GOLD + "- " + ChatColor.YELLOW + "Armor" + ChatColor.GOLD + " (45 cookies)");
      sender.sendMessage(ChatColor.GOLD + "- " + ChatColor.YELLOW + "Supplies" + ChatColor.GOLD + " (35 cookies)");
      sender.sendMessage(ChatColor.GOLD + "- " + ChatColor.YELLOW + "Blocks" + ChatColor.GOLD + " (10 cookies)");
      sender.sendMessage(ChatColor.GOLD + "- " + ChatColor.YELLOW + "Coords <player>" + ChatColor.GOLD + " (15 cookies)");
      return true;
    }
    if (args.length > 2) {
      sender.sendMessage(ChatColor.DARK_RED + "Specify only 1 purchase/target.");
      return true;
    }
    if (args[0].equalsIgnoreCase("Revive")) {
      if ((!this.plugin.roundStarted) && (!this.plugin.roundStarting)) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
      if ((!this.plugin.roundStarted) && (this.plugin.roundStarting)) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
      if (this.plugin.getConfig().getInt(player.getName() + ".economy.money") < 100) {
        sender.sendMessage(ChatColor.DARK_RED + "You need 100 cookies for that.");
        return true;
      }
      if ((this.plugin.getConfig().getInt(player.getName() + ".economy.money") > 100) && (this.plugin.isZombie(player.getName()))) {
        int oldMoney = this.plugin.getConfig().getInt(player.getName() + ".economy.money");
        int newMoney = oldMoney - 100;
        this.plugin.getServer().broadcastMessage(player.getDisplayName() + ChatColor.GREEN + " bought a revive for 100 cookies!");
        this.plugin.makeHuman(player);
        this.plugin.getConfig().set(player.getName() + ".economy.money", Integer.valueOf(newMoney));
        this.plugin.saveConfig();
        if (this.plugin.Zombies.size() == 0) {
          Player p = this.plugin.getServer().getPlayer(this.plugin.randomPlayer());
          this.plugin.makeZombie(p);
          this.plugin.getServer().broadcastMessage(ChatColor.RED + "The disease continues with " + p.getDisplayName() + ChatColor.RED + "!");
          this.plugin.checkStatus();
        }
        this.plugin.checkStatus();
        return true;
      }
      if (this.plugin.isHuman(player.getName())) {
        sender.sendMessage(ChatColor.DARK_RED + "You aren't a zombie!");
        return true;
      }
    }
    if (args[0].equalsIgnoreCase("Armor")) {
      if ((!this.plugin.roundStarted) && (!this.plugin.roundStarting)) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
      if ((!this.plugin.roundStarted) && (this.plugin.roundStarting)) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
      if (this.plugin.getConfig().getInt(player.getName() + ".economy.money") < 45) {
        sender.sendMessage(ChatColor.DARK_RED + "You need 45 cookies for that.");
        return true;
      }
      if (this.plugin.getConfig().getInt(player.getName() + ".economy.money") > 45) {
        int oldMoney = this.plugin.getConfig().getInt(player.getName() + ".economy.money");
        int newMoney = oldMoney - 45;
        this.plugin.getServer().broadcastMessage(player.getDisplayName() + ChatColor.GREEN + " bought some armor for 45 cookies!");
        if (this.plugin.isHuman(player.getName())) {
          player.getInventory().setHelmet(new ItemStack(Material.AIR));
          player.getInventory().setChestplate(new ItemStack(Material.AIR));
          player.getInventory().setLeggings(new ItemStack(Material.AIR));
          player.getInventory().setBoots(new ItemStack(Material.AIR));
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
          player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
          player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
          player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        }
        if (this.plugin.isZombie(player.getName())) {
          player.getInventory().setHelmet(new ItemStack(Material.AIR));
          player.getInventory().setChestplate(new ItemStack(Material.AIR));
          player.getInventory().setLeggings(new ItemStack(Material.AIR));
          player.getInventory().setBoots(new ItemStack(Material.AIR));
          ItemStack enchantHelmet = new ItemStack(Material.LEATHER_HELMET, 1);
          enchantHelmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
          player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
          player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
          player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
          player.getInventory().setHelmet(new ItemStack(enchantHelmet));
        }
        this.plugin.getConfig().set(player.getName() + ".economy.money", Integer.valueOf(newMoney));
        this.plugin.saveConfig();
        return true;
      }
    }
    if (args[0].equalsIgnoreCase("Blocks")) {
      if ((!this.plugin.roundStarted) && (!this.plugin.roundStarting)) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
      if ((!this.plugin.roundStarted) && (this.plugin.roundStarting)) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
      if (this.plugin.getConfig().getInt(player.getName() + ".economy.money") < 10) {
        sender.sendMessage(ChatColor.DARK_RED + "You need 10 cookies for that.");
        return true;
      }
      if (this.plugin.getConfig().getInt(player.getName() + ".economy.money") > 9) {
        int oldMoney = this.plugin.getConfig().getInt(player.getName() + ".economy.money");
        int newMoney = oldMoney - 9;
        this.plugin.getServer().broadcastMessage(player.getDisplayName() + ChatColor.GREEN + " bought some more blocks for 10 cookies!");
        if (this.plugin.isHuman(player.getName())) {
          player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIRT, 20) });
        }
        if (this.plugin.isZombie(player.getName())) {
          player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.SAND, 20) });
        }
        this.plugin.getConfig().set(player.getName() + ".economy.money", Integer.valueOf(newMoney));
        this.plugin.saveConfig();
        return true;
      }
    }
    if (args[0].equalsIgnoreCase("Supplies")) {
      if ((!this.plugin.roundStarted) && (!this.plugin.roundStarting)) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
      if ((!this.plugin.roundStarted) && (this.plugin.roundStarting)) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
      if (this.plugin.getConfig().getInt(player.getName() + ".economy.money") < 35) {
        sender.sendMessage(ChatColor.DARK_RED + "You need 35 cookies for that.");
        return true;
      }
      if (this.plugin.getConfig().getInt(player.getName() + ".economy.money") > 35) {
        int oldMoney = this.plugin.getConfig().getInt(player.getName() + ".economy.money");
        int newMoney = oldMoney - 35;
        this.plugin.getServer().broadcastMessage(player.getDisplayName() + ChatColor.GREEN + " bought some supplies for 35 cookies!");
        player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLDEN_APPLE, 5) });
        player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.EXP_BOTTLE, 25) });
        this.plugin.getConfig().set(player.getName() + ".economy.money", Integer.valueOf(newMoney));
        this.plugin.saveConfig();
        return true;
      }
    }
    if ((args.length == 2) && 
      (args[0].equalsIgnoreCase("Coords")) && ((this.plugin.getServer().getPlayer(args[1]) instanceof Player))) {
      if ((!this.plugin.roundStarted) && (!this.plugin.roundStarting)) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
      if ((!this.plugin.roundStarted) && (this.plugin.roundStarting)) { sender.sendMessage(ChatColor.DARK_RED + "There is no round on."); return true; }
      if (this.plugin.getConfig().getInt(player.getName() + ".economy.money") < 15) {
        sender.sendMessage(ChatColor.DARK_RED + "You need 15 cookies for that.");
        return true;
      }
      if (this.plugin.getConfig().getInt(player.getName() + ".economy.money") > 15) {
        Player target = this.plugin.getServer().getPlayer(args[1]);
        int oldMoney = this.plugin.getConfig().getInt(player.getName() + ".economy.money");
        int newMoney = oldMoney - 15;
        this.plugin.getServer().broadcastMessage(player.getDisplayName() + ChatColor.GREEN + " announced " + target.getDisplayName() + ChatColor.GREEN + "'s coordinates for 15 cookies!");
        this.plugin.getServer().broadcastMessage(target.getDisplayName() + "is at " + getX(target) + "x, " + getY(target) + "y, " + getZ(target) + "z" + "!");
        this.plugin.getConfig().set(player.getName() + ".economy.money", Integer.valueOf(newMoney));
        this.plugin.saveConfig();
        return true;
      }
    }

    return true;
  }
  public String getX(Player player) {
    double X = player.getLocation().getX();

    DecimalFormat df = new DecimalFormat("#.#");
    String x = df.format(X);
    return x;
  }
  public String getY(Player player) {
    double Y = player.getLocation().getY();

    DecimalFormat df = new DecimalFormat("#.#");
    String y = df.format(Y);
    return y;
  }
  public String getZ(Player player) {
    double Z = player.getLocation().getZ();

    DecimalFormat df = new DecimalFormat("#.#");
    String z = df.format(Z);
    return z;
  }
}
package com.oresome.OresomeInfection.Listeners;

import com.oresome.OresomeInfection.Infection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath
  implements Listener
{
  private final Infection plugin;

  public PlayerDeath(Infection plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void onDamageSpawnPoint(EntityDamageEvent event) {
    if (!this.plugin.roundStarted) {
      event.setCancelled(true);
    }
    if (this.plugin.roundStarting)
      event.setCancelled(true);
  }

  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void onDeath(PlayerDeathEvent event) {
    Player player = event.getEntity();
    Player killer = event.getEntity().getKiller();
    if (((event.getEntity().getKiller() instanceof Player)) && (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + this.plugin.randomDeathReason() + killer.getDisplayName());
      if (this.plugin.isZombie(player.getName())) {
        killer.sendMessage(ChatColor.GOLD + "You earned 1 cookie for killing a zombie.");
        int killersOldMoney = this.plugin.getConfig().getInt(killer.getName() + ".economy.money");
        int newMoney = killersOldMoney + 1;
        this.plugin.getConfig().set(killer.getName() + ".economy.money", Integer.valueOf(newMoney));
        this.plugin.saveConfig();
      }
      if (this.plugin.isHuman(player.getName())) {
        killer.sendMessage(ChatColor.GOLD + "You earned 3 cookies for killing a human.");
        int killersOldMoney = this.plugin.getConfig().getInt(killer.getName() + ".economy.money");
        int newMoney = killersOldMoney + 3;
        this.plugin.getConfig().set(killer.getName() + ".economy.money", Integer.valueOf(newMoney));
        this.plugin.saveConfig();
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " fell out of the world!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " burned to death!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " burned to death!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " exploded!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " exploded!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if ((player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) && (!(player.getKiller() instanceof Player))) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " was slain by an angry mob!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " tried to swim in lava!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " was shot!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.DROWNING) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " drowned!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      double distance = player.getFallDistance();
      DecimalFormat df = new DecimalFormat("#.#");
      String format = df.format(distance);
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " hit the ground too hard! " + ChatColor.DARK_RED + "(" + format + " blocks)");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.STARVATION) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " starved to death!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " died from unknown causes!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " committed suicide!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " was struck by lightning!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CONTACT) {
      if (this.plugin.isHuman(player.getName())) {
        player.getWorld().strikeLightningEffect(player.getLocation());
      }
      event.setDeathMessage(player.getDisplayName() + ChatColor.RED + " was damaged by contact!");
      this.plugin.makeZombie(player);
      this.plugin.checkStatus();
    }
    if (this.plugin.Humans.size() == 0)
      player.setHealth(20);
  }
}
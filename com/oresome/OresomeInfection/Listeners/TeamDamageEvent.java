package com.oresome.OresomeInfection.Listeners;

import com.oresome.OresomeInfection.Infection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TeamDamageEvent
  implements Listener
{
  private final Infection plugin;

  public TeamDamageEvent(Infection plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onEntityDamage(EntityDamageByEntityEvent event) {
    Entity player = event.getEntity();
    Entity damager = event.getDamager();
    if (((player instanceof Player)) && 
      ((damager instanceof Player))) {
      if ((this.plugin.isHuman(((Player)damager).getName())) && 
        (this.plugin.isHuman(((Player)player).getName()))) {
        event.setCancelled(true);
      }

      if ((this.plugin.isZombie(((Player)damager).getName())) && 
        (this.plugin.isZombie(((Player)player).getName()))) {
        event.setCancelled(true);
      }

    }

    if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Arrow))) {
      Arrow arrow = (Arrow)event.getDamager();
      Player shooter = null;
      Player playerr = (Player)event.getEntity();

      if ((arrow.getShooter() instanceof Player)) {
        shooter = (Player)arrow.getShooter();
      }

      if (shooter != null)
      {
        if ((this.plugin.isHuman(playerr.getName())) && (this.plugin.isHuman(shooter.getName())))
        {
          event.setCancelled(true);
        }

        if ((this.plugin.isZombie(playerr.getName())) && (this.plugin.isZombie(shooter.getName())))
        {
          event.setCancelled(true);
        }
      }

    }

    if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Egg))) {
      Egg egg = (Egg)event.getDamager();
      Player shooter = null;
      Player playerr = (Player)event.getEntity();

      if ((egg.getShooter() instanceof Player)) {
        shooter = (Player)egg.getShooter();
      }

      if (shooter != null)
      {
        if ((this.plugin.isHuman(playerr.getName())) && (this.plugin.isHuman(shooter.getName())))
        {
          event.setCancelled(true);
        }

        if ((this.plugin.isZombie(playerr.getName())) && (this.plugin.isZombie(shooter.getName())))
        {
          event.setCancelled(true);
        }
      }
    }
  }
}
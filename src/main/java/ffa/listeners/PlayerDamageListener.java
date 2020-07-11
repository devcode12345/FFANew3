package me.devcode.ffa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {


    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            if(e.getEntity().getLocation().distance(e.getEntity().getWorld().getSpawnLocation()) < 5) {
                e.setCancelled(true);
            }
        }
    }

}

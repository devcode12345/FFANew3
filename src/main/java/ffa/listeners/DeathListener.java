package me.devcode.ffa.listeners;

import me.devcode.ffa.FFA;
import me.devcode.ffa.mysql.MySQLRepository;
import me.devcode.ffa.player.FFAPlayer;
import me.devcode.ffa.player.PlayerRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setKeepInventory(true);
        Player player = e.getEntity();
        PlayerRepository playerRepository = FFA.getInstance().getPlayerRepository();
        FFAPlayer ffaPlayer = playerRepository.findById(player.getUniqueId());
        ffaPlayer.getPlayerStats().updateDeaths();
        player.sendMessage(ffaPlayer.getPlayerStats().getDeaths() +"");
        e.setDeathMessage("§2" + player.getName() + " §7died.");
        if(player.getKiller() != null) {
            Player killer = player.getKiller();
            FFAPlayer ffaPlayer1 = playerRepository.findById(killer.getUniqueId());
            ffaPlayer1.getPlayerStats().updateKill();
            ffaPlayer1.getHologram().getLines().set(1, "§7Kills: §2" + ffaPlayer1.getPlayerStats().getKills());
            player.sendMessage("§7You have been killed by §2" + killer.getName());
            killer.sendMessage("§7You killed " + player.getName());
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(FFA.getInstance(), 10);
    }

}

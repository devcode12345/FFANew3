package me.devcode.ffa.listeners;

import me.devcode.ffa.FFA;
import me.devcode.ffa.player.FFAPlayer;
import me.devcode.ffa.player.PlayerRepository;
import me.devcode.ffa.utils.Hologram;
import me.devcode.ffa.utils.HologramAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(e.getPlayer().getWorld().getSpawnLocation());
        PlayerRepository playerRepository = FFA.getInstance().getPlayerRepository();
        FFAPlayer ffaPlayer = playerRepository.findById(e.getPlayer().getUniqueId());
        Hologram hologram = ffaPlayer.getHologram();
        hologram.setLines(new ArrayList<>(Arrays.asList("§7Stats", "§7Kills: §2" + ffaPlayer.getPlayerStats().getKills(), "§7Deaths: §2" + ffaPlayer.getPlayerStats().getDeaths(),
                "§7Games: §2" + ffaPlayer.getPlayerStats().getGames(), "§7Rank: §2" + ffaPlayer.getPlayerStats().getRank())));
        ffaPlayer.setHologram(hologram);
        new BukkitRunnable() {
            @Override
            public void run() {

                ffaPlayer.getHologram().

                        spawn(e.getPlayer());
            }
        }.runTaskLater(FFA.getInstance(), 5);
    }

}

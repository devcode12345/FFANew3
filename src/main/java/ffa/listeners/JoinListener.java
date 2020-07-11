package me.devcode.ffa.listeners;

import me.devcode.ffa.FFA;
import me.devcode.ffa.mysql.MySQLRepository;
import me.devcode.ffa.player.FFAPlayer;
import me.devcode.ffa.player.PlayerRepository;
import me.devcode.ffa.utils.Hologram;
import me.devcode.ffa.utils.HologramAPI;
import me.devcode.ffa.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setExp(0);
        player.setLevel(0);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.getInventory().clear();
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.SURVIVAL);
        e.setJoinMessage("§2" + player.getName() +" §7joined.");
        MySQLRepository mySQLRepository = FFA.getInstance().getMySQLRepository();
        mySQLRepository.createUser(player.getUniqueId());
        mySQLRepository.loadUser(player.getUniqueId());
        player.getInventory().setItem(0, new ItemManager(Material.IRON_SWORD).setUnbreakable(true).build());
        player.getInventory().setItem(1, new ItemManager(Material.BOW).setUnbreakable(true).build());
        player.getInventory().setItem(8, new ItemManager(Material.ARROW).setAmount(16).setUnbreakable(true).build());
        player.getInventory().setBoots(new ItemManager(Material.IRON_BOOTS).setUnbreakable(true).build());
        player.getInventory().setLeggings(new ItemManager(Material.IRON_LEGGINGS).setUnbreakable(true).build());
        player.getInventory().setChestplate(new ItemManager(Material.IRON_CHESTPLATE).setUnbreakable(true).build());
        player.getInventory().setHelmet(new ItemManager(Material.IRON_HELMET).setUnbreakable(true).build());

        HologramAPI api = new HologramAPI();
        PlayerRepository playerRepository = FFA.getInstance().getPlayerRepository();
        FFAPlayer ffaPlayer = playerRepository.findById(player.getUniqueId());
        ffaPlayer.getPlayerStats().updateGames();
        Hologram hologram = api.createHologram(player.getName() + "stats", player.getLocation().add(5,2,5),
                new ArrayList<>(Arrays.asList("§7Stats", "§7Kills: §2" + ffaPlayer.getPlayerStats().getKills(), "§7Deaths: §2" + ffaPlayer.getPlayerStats().getDeaths(),
                        "§7Games: §2" + ffaPlayer.getPlayerStats().getGames(), "§7Rank: §2" + ffaPlayer.getPlayerStats().getRank())));
        hologram.spawn(e.getPlayer());
        ffaPlayer.setHologram(hologram);
    }

}

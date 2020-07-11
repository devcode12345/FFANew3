package me.devcode.ffa.commands;

import me.devcode.ffa.FFA;
import me.devcode.ffa.mysql.MySQLRepository;
import me.devcode.ffa.player.FFAPlayer;
import me.devcode.ffa.player.PlayerRepository;
import me.devcode.ffa.player.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;

public class StatsCommand implements CommandExecutor {
    private HashMap<OfflinePlayer, FFAPlayer> offlinePlayerPlayerManagerHashMap = new HashMap<>();

    @Override
    public boolean onCommand(final CommandSender cs, Command cmd, String label,
                             String[] args) {
        if (!(cs instanceof Player)) {
            return true;
        }
        if (args.length == 0) {

            Player p = (Player) cs;

            PlayerRepository playerRepository = FFA.getInstance().getPlayerRepository();
            FFAPlayer ffaPlayer = playerRepository.findById(p.getUniqueId());
            PlayerStats playerStats = ffaPlayer.getPlayerStats();

             sendStats((Player) cs, p.getName(), playerStats);

            return true;
        }
        if (Bukkit.getPlayer(args[0]) != null) {
            Player p = Bukkit.getPlayer(args[0]);

            PlayerRepository playerRepository = FFA.getInstance().getPlayerRepository();
            FFAPlayer ffaPlayer = playerRepository.findById(p.getUniqueId());
            PlayerStats playerStats = ffaPlayer.getPlayerStats();

             sendStats((Player) cs, p.getName(), playerStats);

            return true;
        } else {
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
            MySQLRepository mySQLRepository = FFA.getInstance().getMySQLRepository();

            if(offlinePlayerPlayerManagerHashMap.containsKey(p)) {
                if(offlinePlayerPlayerManagerHashMap.getOrDefault(p, null) == null) {
                    cs.sendMessage("§cPlayer not found.");
                    return true;
                }
                mySQLRepository.loadUser(p.getUniqueId());
                FFAPlayer ffaPlayer = offlinePlayerPlayerManagerHashMap.get(p);
                PlayerStats playerStats = ffaPlayer.getPlayerStats();
                 sendStats((Player) cs, p.getName(), playerStats);
            }else {
                if (!mySQLRepository.exists(p.getUniqueId())) {
                    cs.sendMessage("§cPlayer not found.");
                    offlinePlayerPlayerManagerHashMap.put(p, null);
                    return true;
                }
                PlayerRepository playerRepository = FFA.getInstance().getPlayerRepository();
                FFAPlayer ffaPlayer = playerRepository.findById(p.getUniqueId());
                PlayerStats playerStats = ffaPlayer.getPlayerStats();
                offlinePlayerPlayerManagerHashMap.put(p, ffaPlayer);
                 sendStats((Player) cs, p.getName(), playerStats);
            }
            return true;
        }
    }

    private void sendStats(Player player, String player2, PlayerStats playerStats) {
        int kills = playerStats.getKills();
        if (kills < 0) {
            kills = 0;
        }
        int deaths = playerStats.getDeaths();
        if (deaths < 0) {
            deaths = 0;
        }
        int games = playerStats.getGames();
        double kd = Double.valueOf(kills) / Double.valueOf(deaths);
        if (deaths == 0) {
            kd = kills;
        }

        DecimalFormat f = new DecimalFormat("#0.00");
        double toFormat = ((double) Math.round(kd * 100)) / 100;

        String formatted = f.format(toFormat);

        player.sendMessage("");
        player.sendMessage("§6Stats §8● §7Player §8» §e" + player2);
        player.sendMessage("§6Stats §8● §7Kills §8» §e" + kills);
        player.sendMessage("§6Stats §8● §7Deaths §8» §e" + deaths);
        player.sendMessage("§6Stats §8● §7K/D §8» §e" + formatted.replace("NaN", "0").replace("Infinity", "0"));
        player.sendMessage("§6Stats §8● §7Games §8» §e" + games);
        player.sendMessage("§6Stats §8● §7Rank §8» §e" + playerStats.getRank());
    }

}

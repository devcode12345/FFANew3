package me.devcode.ffa.player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerRepository {

    /*
    Other class are not allowed to communicate directly with the Player (OOP)
    Also look at aggregates.
     */

    private HashMap<UUID, FFAPlayer> ffaPlayerHashMap = new HashMap<>();

    public void save(FFAPlayer FFAPlayer) {
        ffaPlayerHashMap.put(FFAPlayer.getPLAYER_UUID(), FFAPlayer);
    }

    public FFAPlayer load(UUID uuid, PlayerStats playerStats) {
        FFAPlayer ffaPlayer = new FFAPlayer(uuid, playerStats, null);
        ffaPlayerHashMap.put(uuid, ffaPlayer);
        return ffaPlayer;
    }

    public FFAPlayer findById(UUID uuid) {
        return ffaPlayerHashMap.getOrDefault(uuid, null);
    }

    public HashMap<UUID, FFAPlayer> allPlayers() {
        return ffaPlayerHashMap;
    }

}

package me.devcode.ffa.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class PlayerStats {

    private final UUID uuid;
    private int kills, deaths, games, rank;

    public void updateKill() {
        kills++;
    }

    public void updateDeaths() {
        deaths++;
    }

    public void updateGames() {
        games++;
    }

    public List<Integer> getStats() {

        return new ArrayList<>(Arrays.asList(kills, deaths, games));
    }

}

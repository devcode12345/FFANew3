package me.devcode.ffa.player;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.devcode.ffa.utils.Hologram;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FFAPlayer {

    @EqualsAndHashCode.Include
    private final UUID PLAYER_UUID;
    private PlayerStats playerStats;
    @Setter
    private Hologram hologram;

}

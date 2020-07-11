package me.devcode.ffa.utils;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HologramAPI {

    private static me.devcode.ffa.utils.HologramAPI instance;
    private List<me.devcode.ffa.utils.Hologram> holograms = new ArrayList();

    public static me.devcode.ffa.utils.HologramAPI getInstance() {
        if (instance == null) {
            instance = new me.devcode.ffa.utils.HologramAPI();
        }

        return instance;
    }

    public List<me.devcode.ffa.utils.Hologram> getHolograms() {
        return this.holograms;
    }

    public me.devcode.ffa.utils.Hologram getHologram(String name) {
        Iterator iterator = this.holograms.iterator();

        me.devcode.ffa.utils.Hologram hologram;
        do {
            if (!iterator.hasNext()) {
                return null;
            }

            hologram = (me.devcode.ffa.utils.Hologram) iterator.next();
        } while (!hologram.getName().equals(name));

        return hologram;
    }

    public me.devcode.ffa.utils.Hologram createHologram(String name, Location location, List<String> lines) {
        if (this.getHologram(name) == null) {
            me.devcode.ffa.utils.Hologram hologram = new me.devcode.ffa.utils.Hologram(name, location, lines);
            this.holograms.add(hologram);
            return hologram;
        } else {
            return null;
        }
    }

    public void destroyHologram(String name) {
        if (this.getHologram(name) != null) {
            me.devcode.ffa.utils.Hologram hologram = this.getHologram(name);
            this.holograms.remove(hologram);
        }

    }

}

package me.devcode.ffa.listeners;

import me.devcode.ffa.FFA;
import me.devcode.ffa.mysql.MySQLRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.setQuitMessage("§2" + player.getName() +" §7quit.");
        MySQLRepository mySQLRepository = FFA.getInstance().getMySQLRepository();
        mySQLRepository.updateUser(player.getUniqueId());
    }

}

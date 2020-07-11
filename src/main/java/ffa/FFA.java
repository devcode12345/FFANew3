package me.devcode.ffa;

import lombok.Getter;
import me.devcode.ffa.commands.StatsCommand;
import me.devcode.ffa.listeners.*;
import me.devcode.ffa.mysql.AsyncMySQL;
import me.devcode.ffa.mysql.MySQLRepository;
import me.devcode.ffa.mysql.MySQLUtils;
import me.devcode.ffa.player.PlayerRepository;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

@Getter
public class FFA extends JavaPlugin {

    @Getter
    private static FFA instance;

    private AsyncMySQL asyncMySQL;
    private MySQLUtils mySQLUtils;
    private MySQLRepository mySQLRepository;
    private PlayerRepository playerRepository;

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("FFA activated.");
        getConfig().addDefault("MySQL.Host", "localhost");
        getConfig().addDefault("MySQL.User", "user");
        getConfig().addDefault("MySQL.Database", "database");
        getConfig().addDefault("MySQL.Port", 3306);
        getConfig().addDefault("MySQL.Password", "password");
        getConfig().options().copyDefaults(true);
        saveConfig();
        asyncMySQL =  new MySQLRepository().connectDatabase();
        mySQLUtils = new MySQLUtils();
        registerListeners();
        registerCommands();
        mySQLRepository = new MySQLRepository();
        playerRepository = new PlayerRepository();
    }

    private void registerCommands() {
        getCommand("stats").setExecutor(new StatsCommand());
    }

    private void registerListeners() {
        ArrayList<Listener> list = new ArrayList<>();
        list.add(new CancelListeners());
        list.add(new DeathListener());
        list.add(new JoinListener());
        list.add(new PlayerQuitListener());
        list.add(new PlayerDamageListener());
        list.add(new RespawnListener());
        PluginManager pluginManager = getServer().getPluginManager();
        list.forEach(listener ->
                pluginManager.registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        System.out.println("FFA deactivated.");
    }
}

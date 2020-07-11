package me.devcode.ffa.mysql;


import lombok.Getter;
import me.devcode.ffa.FFA;
import me.devcode.ffa.player.PlayerRepository;
import me.devcode.ffa.player.PlayerStats;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class MySQLRepository {

    /*
    Other class are not allowed to communicate directly with the Database (OOP)
    Also look at aggregates.
     */

    private AsyncMySQL asyncMySQL;

    public AsyncMySQL connectDatabase() {
        asyncMySQL = new AsyncMySQL(FFA.getInstance(), FFA.getInstance().getConfig().getString("MySQL.Host"), FFA.getInstance().getConfig().getInt("MySQL.Port"), FFA.getInstance().getConfig().getString("MySQL.User"), FFA.getInstance().getConfig().getString("MySQL.Password"), FFA.getInstance().getConfig().getString("MySQL.Database"));
        asyncMySQL.update("CREATE TABLE IF NOT EXISTS ffa(uuid varchar(36), kills int, deaths int, games int);");
        return asyncMySQL;
    }

    public void createUser(UUID uuid) {
        FFA.getInstance().getMySQLUtils().createUser(uuid.toString());
        
    }

    public boolean exists(UUID uuid) {
        return FFA.getInstance().getMySQLUtils().isUserExisting(uuid.toString(), "ffa");
    }

    public void loadUser(UUID uuid) {
        PlayerStats playerStats = new PlayerStats(uuid, (int) getObject(uuid, "kills"), (int) getObject(uuid, "deaths"), (int) getObject(uuid, "games"), FFA.getInstance().getMySQLUtils().getRank(uuid.toString()));
        PlayerRepository playerRepository = FFA.getInstance().getPlayerRepository();
        playerRepository.load(uuid, playerStats);
    }

    public Object getObject(UUID uuid, String object) {
        return FFA.getInstance().getMySQLUtils().getObject("ffa", "UUID", uuid.toString(), object);
    }

    public Boolean getBoolean(UUID uuid) {
        return FFA.getInstance().getMySQLUtils().getBooleanMethod("ffa", "UUID", uuid.toString());
    }

    public void updateUser(UUID uuid) {
        PlayerStats playerStats = FFA.getInstance().getPlayerRepository().findById(uuid).getPlayerStats();
        FFA.getInstance().getMySQLUtils().updatePLayer(uuid.toString(), playerStats.getKills(), playerStats.getDeaths(), playerStats.getGames());
    }

}

package me.devcode.ffa.mysql;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import me.devcode.ffa.FFA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLUtils {

    public boolean isUserExisting(String uuid, String table) {
        return getBooleanMethod(table, "uuid", uuid);
    }

    @SneakyThrows
    public void createUser(String uuid) {
        if (!isUserExisting(uuid, "ffa")) {
            PreparedStatement preparedStatement = FFA.getInstance().getAsyncMySQL().prepare("INSERT INTO ffa(uuid, kills, deaths, games) VALUES (?,?,?,?);");
            //MySQL starting at 1 not at 0
            preparedStatement.setString(1, uuid);
            preparedStatement.setInt(2,0);
            preparedStatement.setInt(3,0);
            preparedStatement.setInt(4,0);
            FFA.getInstance().getAsyncMySQL().update(preparedStatement);
            
        }
    }

    public int getRank(String uuid) {
        int count = 0;
        if(!isUserExisting(uuid, "ffa")) {
            createUser(uuid);
            return getRank(uuid);
        }
        ResultSet resultSet = null;
        try{
            PreparedStatement preparedStatement = FFA.getInstance().getAsyncMySQL().prepare("SELECT * FROM ffa ORDER BY kills DESC;");
            preparedStatement.executeQuery();
            while (resultSet.next()) {
                count++;
                String namedUUID = resultSet.getString("uuid");
                UUID uuid2 = UUID.fromString(namedUUID);
                if(uuid2.toString().equals(uuid)) {
                    preparedStatement.close();
                    return count;
                }
            }
        }catch(Exception e) {

        }
        return -1;
    }

    public boolean getBooleanMethod(String table, String from, String uuid) {
        boolean contains = false;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = FFA.getInstance().getAsyncMySQL().prepare("SELECT " + from + " FROM " + table + " WHERE " + from + "=?");
            ps.setString(1, uuid);

            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (rs.next()) {
                if (rs != null) {
                    contains = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contains;
    }

    @SneakyThrows
    public Object getObject(String table, String from, String uuid, String get) {
        PreparedStatement statement;
        ResultSet rs;
        statement = FFA.getInstance().getAsyncMySQL()
                .prepare("SELECT " + get +" FROM " + table +" WHERE " + from +"=?;");
        statement.setString(1, uuid);
        rs = statement.executeQuery();

        Object object = null;
        while (rs.next()) {
            if (rs != null) {
                object = rs.getObject(get);
                break;
            }
        }
        statement.close();
        return object;
    }

    @SneakyThrows
    public void updatePLayer(String uuid, Object kills, Object deaths, Object games) {
        PreparedStatement preparedStatement =
                FFA.getInstance().getAsyncMySQL().prepare("UPDATE ffa SET kills = ?, deaths = ?, games = ? WHERE uuid = ?;");
            preparedStatement.setInt(1, (int) kills);
            preparedStatement.setInt(2, (int) deaths);
            preparedStatement.setInt(3, (int) games);
            preparedStatement.setString(4, uuid);
            FFA.getInstance().getAsyncMySQL().update(preparedStatement);

    }





}

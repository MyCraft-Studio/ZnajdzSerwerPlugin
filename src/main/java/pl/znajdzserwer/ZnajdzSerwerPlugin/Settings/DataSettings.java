package pl.znajdzserwer.ZnajdzSerwerPlugin.Settings;

import pl.znajdzserwer.ZnajdzSerwerPlugin.Config;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Utils.GlobalUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataSettings {

    private static HashMap<UUID, Long> users = new HashMap<>();

    public static HashMap<UUID, Long> getUsers() {
        return users;
    }

    public static void loadUsers() {
        if(Config.getConfig("data").getConfigurationSection("users") == null)
            return;
        for(String id : Config.getConfig("data").getConfigurationSection("users").getKeys(false)) {
            UUID uuid = UUID.fromString(id);
            long last = Config.getConfig("data").getLong("users."+id);
            users.put(uuid, last);
        }
        GlobalUtil.setLogs("Wczytano "+users.size()+" użytkowników z data.yml");
    }

    public static void saveUsers() {
        Config.getConfig("data").set("users", null);
        for(Map.Entry<UUID, Long> map : users.entrySet()) {
            String id = String.valueOf(map.getKey());
            long last = map.getValue();
            Config.getConfig("data").set("users."+id, last);
        }
        Config.saveConfig("data");
        GlobalUtil.setLogs("Zapisano "+users.size()+" użytkowników do data.yml");
    }
}

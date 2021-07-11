package pl.znajdzserwer.ZnajdzSerwerPlugin.Settings;

import pl.znajdzserwer.ZnajdzSerwerPlugin.Config;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Utils.GlobalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigSettings {

    private static String address = null;
    private static List<String> rewardCommands = new ArrayList<>();
    private static int cooldown = 30;
    private static int saveData = 600;

    private static HashMap<String, String> messages = new HashMap<>();

    public static String getAddress() {
        return address;
    }

    public static List<String> getRewardCommands() {
        return rewardCommands;
    }

    public static int getCooldown() {
        return cooldown;
    }

    public static int getSaveData() {
        return saveData;
    }

    public static HashMap<String, String> getMessages() {
        return messages;
    }

    public static void loadConfig() {
        address = Config.getConfig("config").getString("settings.address");
        rewardCommands = Config.getConfig("config").getStringList("settings.rewardCommands");
        cooldown = Config.getConfig("config").getInt("settings.cooldown");
        saveData = Config.getConfig("config").getInt("settings.saveData");

        for(String string : Config.getConfig("config").getConfigurationSection("messages").getKeys(false)) {
            messages.put(string, GlobalUtil.getColor(Config.getConfig("config").getString("messages."+string)));
        }
        GlobalUtil.setLogs("Wczytano ustawienia z config.yml");
    }
}

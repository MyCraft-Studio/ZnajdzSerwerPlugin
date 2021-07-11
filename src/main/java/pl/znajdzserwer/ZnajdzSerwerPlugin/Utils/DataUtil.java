package pl.znajdzserwer.ZnajdzSerwerPlugin.Utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Main;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Settings.ConfigSettings;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Settings.DataSettings;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class DataUtil {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static boolean isUsed(final UUID uuid) {
        if(!DataSettings.getUsers().containsKey(uuid))
            return false;
        Long last = DataSettings.getUsers().get(uuid);
        Long now = System.currentTimeMillis();
        int secBetween = (int) ((now-last)/1000);
        return (secBetween < 86400);
    }

    public static String getTimeWhenCanBeUsed(final UUID uuid) {
        Long last = DataSettings.getUsers().get(uuid);
        Long newTime = last+86400000;
        Timestamp timestamp = new Timestamp(newTime);
        LocalDateTime date = timestamp.toLocalDateTime();
        return date.format(formatter);
    }

    public static void addData(final UUID uuid) {
        DataSettings.getUsers().put(uuid, System.currentTimeMillis());
    }

    public static void saveTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                DataSettings.saveUsers();
            }
        }.runTaskTimerAsynchronously(Main.plugin, 20L*ConfigSettings.getSaveData(), 20L*ConfigSettings.getSaveData());
    }
}

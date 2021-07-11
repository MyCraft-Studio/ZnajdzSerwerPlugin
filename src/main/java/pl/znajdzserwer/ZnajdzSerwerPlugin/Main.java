package pl.znajdzserwer.ZnajdzSerwerPlugin;

import org.bukkit.plugin.java.JavaPlugin;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Commands.ZnajdzserwerCommand;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Settings.ConfigSettings;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Settings.DataSettings;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Utils.DataUtil;

public class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Config.setupConfigs("config", "data");

        ConfigSettings.loadConfig();
        DataSettings.loadUsers();

        getCommand("znajdzserwer").setExecutor(new ZnajdzserwerCommand());

        DataUtil.saveTask();
    }

    @Override
    public void onDisable() {
        DataSettings.saveUsers();
    }
}

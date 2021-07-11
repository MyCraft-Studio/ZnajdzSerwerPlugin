package pl.znajdzserwer.ZnajdzSerwerPlugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Config {

    /*
    Source: https://github.com/Ziomkeee/YamlConfigSpigot
     */

    private static final HashMap<String, FileConfiguration> fileConfigurationHashMap = new HashMap<>();
    private static final HashMap<String, File> fileHashMap = new HashMap<>();

    /*
    If your main class is not named "Main", replace "Main" with the name of your main class.
    For example:
    Your main class is MyFirstPlugin, so:
    public static MyFirstPlugin plugin = MyFirstPlugin.getPlugin(MyFirstPlugin.class);
     */
    public static Main plugin = Main.getPlugin(Main.class);

    /*
    Use this function in onEnable, this way you can create or operate on files of your choice.
    When using the function just type the file name without the extension!
    For example:
    for multiple files: setupConfigs("config", "data", "lang");
    for single file: setupConfigs("config");
     */
    public static void setupConfigs(final String... name) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        for (int i = 0; i < name.length; i++) {
            createConfig(name[i]);
        }
    }

    /*
    This is an internal function, if you need to use it anywhere, change it from private to public.
     */
    private static void createConfig(final String name) {
        File file = new File(plugin.getDataFolder(), name + ".yml");
        if (!file.exists()) {
            try {
                if (plugin.getResource(name + ".yml") == null) {
                    file.createNewFile();
                } else {
                    plugin.saveResource(name + ".yml", false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

        fileConfigurationHashMap.put(name, fileConfiguration);
        fileHashMap.put(name, file);
    }

    /*
    Returns the selected file by name
    Use the name without extension, the same as when creating the file (setupConfigs)
     */
    public static FileConfiguration getConfig(final String name) {
        return fileConfigurationHashMap.get(name);
    }

    /*
    After editing the file, save it with this function.
    Use the name without extension, the same as when creating the file (setupConfigs)
     */
    public static void saveConfig(final String name) {
        try {
            fileConfigurationHashMap.get(name).save(fileHashMap.get(name));
        } catch (IOException e) {
            System.out.println("There was a problem saving the " + name + ".yml file");
        }
    }
}
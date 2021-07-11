package pl.znajdzserwer.ZnajdzSerwerPlugin.Utils;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Settings.ConfigSettings;

import java.net.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class LikeUtil {

    private static HashMap<UUID, Long> cooldownCache = new HashMap<>();

    public static void checkLike(Player player) {
        if(!player.hasPermission("znajdzserwer.use")) {
            player.sendMessage(ConfigSettings.getMessages().get("prefix") + ConfigSettings.getMessages().get("noPerm"));
            return;
        }
        if(!player.hasPermission("znajdzserwer.cooldown.bypass")) {
            if(cooldownCache.containsKey(player.getUniqueId())) {
                Long time = cooldownCache.get(player.getUniqueId());
                long now = System.currentTimeMillis();
                int secBetween = (int) ((now-time)/1000);
                if(secBetween < ConfigSettings.getCooldown()) {
                    player.sendMessage(ConfigSettings.getMessages().get("prefix") + ConfigSettings.getMessages().get("cooldownError")
                    .replace("%time%", String.valueOf(ConfigSettings.getCooldown()-secBetween)));
                    return;
                }
            }
        }
        if(DataUtil.isUsed(player.getUniqueId())) {
            player.sendMessage(ConfigSettings.getMessages().get("prefix") + ConfigSettings.getMessages().get("liked")
                    .replace("%date%", DataUtil.getTimeWhenCanBeUsed(player.getUniqueId())));
            return;
        }
        cooldownCache.put(player.getUniqueId(), System.currentTimeMillis());
        try {
            URL url = new URL("https://znajdzserwer.pl/application/api/addLike.php?ip="+player.getAddress().getAddress().getHostAddress()+"&address="+ ConfigSettings.getAddress());
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inline.toString());

            int status = Integer.parseInt(data_obj.get("status").toString());
            if(status == 106) {
                player.sendMessage(ConfigSettings.getMessages().get("prefix") + ConfigSettings.getMessages().get("noLiked"));
                return;
            }
            if(status < 200) {
                player.sendMessage(ConfigSettings.getMessages().get("prefix") + ConfigSettings.getMessages().get("connectError"));
                GlobalUtil.setLogs("Wystąpił błąd "+status+", zobacz docs na github");
                return;
            }
            if(status == 200) {
                player.sendMessage(ConfigSettings.getMessages().get("prefix") + ConfigSettings.getMessages().get("success"));
                for(String cmd : ConfigSettings.getRewardCommands()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getName()));
                }
                GlobalUtil.setLogs(player.getName()+" dał like serwerowi i otrzymał nagrodę!");
                DataUtil.addData(player.getUniqueId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

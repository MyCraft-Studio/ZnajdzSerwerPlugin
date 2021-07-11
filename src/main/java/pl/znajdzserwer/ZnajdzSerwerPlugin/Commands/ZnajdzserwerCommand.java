package pl.znajdzserwer.ZnajdzSerwerPlugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.znajdzserwer.ZnajdzSerwerPlugin.Utils.LikeUtil;

public class ZnajdzserwerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("znajdzserwer")) {
            if(sender instanceof ConsoleCommandSender) {
                sender.sendMessage("Tylko dla gracza");
                return false;
            }
            Player player = (Player) sender;
            LikeUtil.checkLike(player);
        }
        return false;
    }
}

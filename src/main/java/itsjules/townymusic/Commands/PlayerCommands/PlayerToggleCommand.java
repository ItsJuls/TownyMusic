package itsjules.townymusic.Commands.PlayerCommands;

import com.palmergames.bukkit.towny.TownyMessaging;
import itsjules.townymusic.Listeners.TownEnterListener;
import itsjules.townymusic.TownyMusic;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlayerToggleCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0){
            TownyMessaging.sendMsg(sender, "/togglemusic on/off");
            return true;
        }

        if(args[0].equalsIgnoreCase("on") && sender instanceof Player){
            ((Player) sender).getPersistentDataContainer().set(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "true");
            TownyMessaging.sendMsg(sender, "Music inside towns has been turned on.");
            return true;
        }

        if(args[0].equalsIgnoreCase("off") && sender instanceof Player){
            ((Player) sender).getPersistentDataContainer().set(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "false");
            TownyMessaging.sendMsg(sender, "Music inside towns has been turned off.");

            if(TownEnterListener.radioMap.containsKey(((Player) sender).getUniqueId())){
                TownEnterListener.radioMap.get(((Player) sender).getUniqueId()).destroy();
            }

        }


        if(!(sender instanceof Player)){
            TownyMusic.logger.warning("You aren't a player!");
            return false;
        }



        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        return List.of("on", "off");
    }
}

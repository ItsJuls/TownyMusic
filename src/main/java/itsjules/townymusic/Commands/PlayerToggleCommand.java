package itsjules.townymusic.Commands;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
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

import java.util.ArrayList;
import java.util.List;

public class PlayerToggleCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length > 1){
            return false;
        }


        if(args[0].equalsIgnoreCase("off") && sender instanceof Player){
            ((Player) sender).getPersistentDataContainer().set(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "false");
            TownyMessaging.sendMsg(sender, "Music inside towns has been turned off.");
            for(SongPlayer rsp : NoteBlockAPI.getSongPlayersByPlayer(((Player) sender).getPlayer())){
                rsp.destroy();
            }

        }

        if(args[0].equalsIgnoreCase("on") && sender instanceof Player || args.length == 0){
            ((Player) sender).getPersistentDataContainer().set(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "true");
            TownyMessaging.sendMsg(sender, "Music inside towns has been turned on.");
        }

        if(!(sender instanceof Player)){
            TownyMusic.plugin.getLogger().warning("You aren't a player!");
            return false;
        }



        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();

        list.add("on");
        list.add("off");

        return list;
    }
}

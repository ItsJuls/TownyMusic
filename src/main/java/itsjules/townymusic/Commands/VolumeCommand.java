package itsjules.townymusic.Commands;

import com.palmergames.bukkit.towny.TownyMessaging;
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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class VolumeCommand implements CommandExecutor, TabCompleter {
    List<Integer> listOfInts = IntStream.rangeClosed(0, 100).boxed().toList();
    List<String> list = listOfInts.stream().map(Objects::toString).collect(Collectors.toList());
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0){
            TownyMessaging.sendErrorMsg(sender, "You must put a number between 0-100!");
            return true;
        }

        if(!(sender instanceof Player)){
            TownyMusic.logger.warning("must be run by a player!");
            return true;
        }

        if(!list.contains(args[0])){
            TownyMessaging.sendErrorMsg(sender, "You must put a number between 0-100!");
            return true;
        }

        ((Player)sender).getPersistentDataContainer().set(new NamespacedKey(TownyMusic.plugin, "TownyMusicVolume"), PersistentDataType.BYTE, Integer.getInteger(args[0]).byteValue());
        TownyMessaging.sendMsg(sender, "Volume has been set to " + args[0]);




        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return list;
    }
}

package itsjules.townymusic.Commands;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import itsjules.townymusic.TownyMusic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetMusicCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){
        Resident resident = TownyUniverse.getInstance().getResident(((Player) sender).getUniqueId());
        if(sender instanceof Player) {
            if(resident.hasTown()){
                if(resident.isMayor() || ((Player) sender).getPlayer().hasPermission("towny.command.town.set.music")){
                    resident.getTownOrNull().addMetaData(new StringDataField("Music", String.join(" ", args)));
                    TownyMessaging.sendPrefixedTownMessage(resident.getTownOrNull(), "Town Music has been set to: " + String.join(" ", args));
                }
            }else{
                TownyMusic.logger.warning("A player has to run this command!");
            }
        }
        return true;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File file = new File(TownyMusic.plugin.getDataFolder(), "/Songs");
        String[] listOfFiles = file.list();
        if(args.length == 1){
            return Arrays.stream(listOfFiles).toList();
        }
        return Collections.emptyList();
    }
}

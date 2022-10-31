package itsjules.townymusic.Commands;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SetMusicCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){
        Resident resident = TownyUniverse.getInstance().getResident(((Player) sender).getUniqueId());
        if(sender instanceof Player) {
            if(resident.hasTown()){
                if(resident.isMayor() || ((Player) sender).getPlayer().hasPermission("towny.command.town.set.music")){
                    resident.getTownOrNull().addMetaData(new StringDataField("Music", Arrays.toString(args)));
                    TownyMessaging.sendPrefixedTownMessage(resident.getTownOrNull(), "Town Music has been set to: " + Arrays.toString(args)
                            .replaceAll("]", "")
                            .replaceAll("\\[", "")
                            .replaceAll(",", ""));
                }
            }else{
                System.out.println("A player has to run this command!");
            }
        }
        return true;

    }
}

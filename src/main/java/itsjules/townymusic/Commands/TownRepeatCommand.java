package itsjules.townymusic.Commands;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import itsjules.townymusic.TownyMusic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TownRepeatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Resident resident = TownyUniverse.getInstance().getResident(((Player) sender).getUniqueId());
        if (!(sender instanceof Player)) {
            TownyMusic.logger.warning("must be run by a player!");
            return true;
        }

        if(!resident.hasTown()){
            TownyMessaging.sendErrorMsg(sender, "You don't have a town!");
            return true;
        }

        if (!resident.isMayor() && !((Player) sender).getPlayer().hasPermission("towny.command.town.toggle.repeat")) {
            TownyMessaging.sendErrorMsg(sender, "You don't have permission for this or aren't the Mayor!");
            return true;
        }

        if (args[0].equalsIgnoreCase("on")) {
            resident.getTownOrNull().addMetaData(new BooleanDataField("ToggleRepeat", true));
            TownyMessaging.sendPrefixedTownMessage(resident.getTownOrNull(), "Music Repeat has been Enabled");
        } else if (args[0].equalsIgnoreCase("off")) {
            resident.getTownOrNull().addMetaData(new BooleanDataField("ToggleRepeat", false));
            TownyMessaging.sendPrefixedTownMessage(resident.getTownOrNull(), "Music Repeat has been Disabled");
        }
        return false;
    }
}

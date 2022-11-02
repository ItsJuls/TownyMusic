package itsjules.townymusic.Commands;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleMusicCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Resident resident = TownyUniverse.getInstance().getResident(((Player) sender).getUniqueId());
        if (sender instanceof Player) {
            if (resident.hasTown()) {
                if (resident.isMayor() || ((Player) sender).getPlayer().hasPermission("towny.command.town.toggle.music")) {
                    if (args[0].equalsIgnoreCase("on") || args.length == 0) {
                        resident.getTownOrNull().addMetaData(new BooleanDataField("ToggleMusic", true));
                        TownyMessaging.sendPrefixedTownMessage(resident.getTownOrNull(), "Town Music has been Enabled");
                    } else if (args[0].equalsIgnoreCase("off")) {
                        resident.getTownOrNull().addMetaData(new BooleanDataField("ToggleMusic", false));
                        TownyMessaging.sendPrefixedTownMessage(resident.getTownOrNull(), "Town Music has been Disabled");
                    }
                }
            }
            return true;
        }
   return true; }
}

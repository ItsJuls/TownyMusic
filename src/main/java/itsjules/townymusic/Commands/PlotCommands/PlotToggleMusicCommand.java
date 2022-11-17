package itsjules.townymusic.Commands.PlotCommands;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import itsjules.townymusic.TownyMusic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlotToggleMusicCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Resident resident = TownyUniverse.getInstance().getResident(((Player) sender).getUniqueId());
        if (!(sender instanceof Player)) {
            TownyMusic.logger.warning("must be run by a player!");
            return true;
        }
        Player player = ((Player) sender).getPlayer();
        TownBlock tb = TownyUniverse.getInstance().getTownBlockOrNull(WorldCoord.parseWorldCoord(player));

        if (!resident.hasTown()) {
            TownyMessaging.sendErrorMsg(sender, "You don't have a town!");
            return true;
        }

        if (!resident.isMayor() && !((Player) sender).getPlayer().hasPermission("towny.command.plot.toggle.music")) {
            TownyMessaging.sendErrorMsg(sender, "You don't have permission for this or aren't the Plot Owner!");
            return true;
        }

        if (args[0].equalsIgnoreCase("on")) {
            tb.addMetaData(new BooleanDataField("ToggleMusic", true));
            TownyMessaging.sendMsg(player, "Plot Music has been Enabled");
        } else if (args[0].equalsIgnoreCase("off")) {
            tb.addMetaData(new BooleanDataField("ToggleMusic", false));
            TownyMessaging.sendMsg(player, "Plot Music has been Disabled");
        }
        return false;}
}

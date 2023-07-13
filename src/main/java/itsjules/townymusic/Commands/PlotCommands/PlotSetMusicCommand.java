package itsjules.townymusic.Commands.PlotCommands;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.WorldCoord;
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

public class PlotSetMusicCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            TownyMusic.logger.warning("must be run by a player!");
            return true;
        }

        Resident resident = TownyUniverse.getInstance().getResident(((Player) sender).getUniqueId());
        Player player = ((Player) sender).getPlayer();
        if (!resident.hasTown()) {
            TownyMessaging.sendErrorMsg(sender, "You don't have a town!");
            return true;
        }

        if (!resident.isMayor() && !((Player) sender).getPlayer().hasPermission("towny.command.plot.set.music") && TownyUniverse.getInstance().getTownBlockOrNull(WorldCoord.parseWorldCoord(player)).isOwner(resident)) {
            TownyMessaging.sendErrorMsg(sender, "You don't have permission for this or don't own this plot!");
            return true;
        }

        TownyUniverse.getInstance().getTownBlockOrNull(WorldCoord.parseWorldCoord(player)).addMetaData(new StringDataField("PlotMusic", String.join(" ", args)));
        TownyMessaging.sendPrefixedTownMessage(resident.getTownOrNull(), "Plot Music has been set to: " + String.join(" ", args));


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

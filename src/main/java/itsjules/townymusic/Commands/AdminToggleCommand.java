package itsjules.townymusic.Commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminToggleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<Town> towns = TownyAPI.getInstance().getTowns();
        List<String> listOfTownNames = towns.stream().map(t -> t.getName()).toList();
        String enabledMessage = args[0] + "'s Music has been Enabled";
        String disabledMessage = args[0] + "'s Music has been Disabled";
        if (args.length != 0) {
            if (listOfTownNames.stream().anyMatch(list -> list.equalsIgnoreCase(args[0]))) {
                if(args[1].equalsIgnoreCase("on")){
                    TownyAPI.getInstance().getTown(args[0]).addMetaData(new BooleanDataField("ToggleMusic", true));
                    TownyMessaging.sendMsg(sender, enabledMessage);
                }else if(args[1].equalsIgnoreCase("off")){
                    TownyAPI.getInstance().getTown(args[0]).addMetaData(new BooleanDataField("ToggleMusic", false));
                    TownyMessaging.sendMsg(sender , disabledMessage);
                }
            } else {
                TownyMessaging.sendErrorMsg(sender, "This Town does not exist!");
            }
        } else {
            TownyMessaging.sendErrorMsg(sender, "You have to put a Town!");
            return false;
        }
   return false; }
}

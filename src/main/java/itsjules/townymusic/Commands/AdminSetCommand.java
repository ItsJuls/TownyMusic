package itsjules.townymusic.Commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class AdminSetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        List<Town> towns = TownyAPI.getInstance().getTowns();
        List<String> listOfTownNames = towns.stream().map(t -> t.getName()).toList();
        if(args.length != 0){
            if(listOfTownNames.stream().anyMatch(list -> list.equalsIgnoreCase(args[0]))){
                String song = Arrays.toString(args)
                        .replaceFirst(args[0], "")
                        .replaceAll("]", "")
                        .replaceAll("\\[", "")
                        .replaceAll(",", "")
                        .replaceFirst(" ", "");
                String message = args[0] + "'s music has been changed to: " + song;
                TownyAPI.getInstance().getTown(args[0]).addMetaData(new StringDataField("Music", song));
                TownyMessaging.sendMsg(sender,  message);
            }else{
                TownyMessaging.sendErrorMsg(sender, "This Town does not exist!");
            }
        }else{
            TownyMessaging.sendErrorMsg(sender, "You have to put a Town!");
        }
        return true;

    }
}


package itsjules.townymusic.Commands.AdminCommands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import itsjules.townymusic.TownyMusic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdminSetCommand implements CommandExecutor, TabExecutor {
    List<Town> towns = TownyAPI.getInstance().getTowns();
    List<String> listOfTownNames = towns.stream().map(t -> t.getName()).toList();
    File file = new File(TownyMusic.plugin.getDataFolder(), "/Songs");
    List<String> listOfFiles = Arrays.asList(file.list());

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if(args.length != 0){
            if(listOfTownNames.stream().anyMatch(list -> list.equalsIgnoreCase(args[0]))){

                String song = String.join(" ", List.of(args).remove(0));
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(args.length == 1){
            return listOfTownNames;
        }
        if(args.length == 2){
            return listOfFiles;
        }
        return Collections.emptyList();
    }
}


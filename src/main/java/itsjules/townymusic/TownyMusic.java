package itsjules.townymusic;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import com.palmergames.bukkit.towny.object.AddonCommand;
import com.palmergames.bukkit.towny.object.Town;
import itsjules.townymusic.Commands.*;
import itsjules.townymusic.Listeners.TownCreateListener;
import itsjules.townymusic.Listeners.TownEnterListener;
import itsjules.townymusic.Listeners.TownLeaveListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class TownyMusic extends JavaPlugin {
    public static TownyMusic plugin;

    @Override
    public void onEnable(){
        plugin = this;
        createDirectory(TownyMusic.this);
        System.out.println("TownyMusic has been Enabled!");
        registerEvents();
        registerCommands(TownyMusic.this);
    }


    @Override
    public void onDisable() {
        System.out.println("Plugin has been disabled.");
    }

    public void createDirectory(TownyMusic plugin){
        File songs = new File(plugin.getDataFolder(),"Songs");
        if(!songs.exists()){
            System.out.println("No Songs folder has been found, making one.");
            try{
                songs.mkdirs();
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            System.out.println("Songs folder has been found.");
        }
    }
    public void registerEvents(){
        plugin.getServer().getPluginManager().registerEvents(new TownEnterListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TownLeaveListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TownCreateListener(), plugin);
    }

    public void registerCommands(TownyMusic plugin){
        List<Town> towns = TownyAPI.getInstance().getTowns();
        List<String> listOfTownNames = towns.stream().map(t -> t.getName()).collect(Collectors.toList());
        File file = new File(plugin.getDataFolder(), "/Songs");
        String[] listOfFiles = file.list();

        AddonCommand setMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWN_SET, "Music", new SetMusicCommand());
        setMusic.setTabCompletion(0, Arrays.stream(listOfFiles).toList());
        TownyCommandAddonAPI.addSubCommand(setMusic);

        AddonCommand toggleMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWN_TOGGLE, "Music", new ToggleMusicCommand());
        toggleMusic.setTabCompletion(0, Arrays.asList("off, on"));
        TownyCommandAddonAPI.addSubCommand(toggleMusic);

        AddonCommand adminSetMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWNYADMIN_SET, "Music", new AdminSetCommand());
        adminSetMusic.setTabCompletion(0, listOfTownNames);
        adminSetMusic.setTabCompletion(1, Arrays.stream(listOfFiles).toList());
        TownyCommandAddonAPI.addSubCommand(adminSetMusic);

        AddonCommand adminToggleMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWNYADMIN_TOGGLE, "Music", new AdminToggleCommand());
        adminToggleMusic.setTabCompletion(0, listOfTownNames);
        adminToggleMusic.setTabCompletion(1, Arrays.asList("off, on"));
        TownyCommandAddonAPI.addSubCommand(adminToggleMusic);

        plugin.getCommand("togglemusic").setExecutor(new PlayerToggleCommand());

    }
}


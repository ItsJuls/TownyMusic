package itsjules.townymusic;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import com.palmergames.bukkit.towny.object.AddonCommand;
import com.palmergames.bukkit.towny.object.Town;
import itsjules.townymusic.Commands.*;
import itsjules.townymusic.Listeners.TownCreateListener;
import itsjules.townymusic.Listeners.TownEnterListener;
import itsjules.townymusic.Listeners.TownLeaveListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class TownyMusic extends JavaPlugin {
    public static TownyMusic plugin;
    public static Logger logger;
    public static PluginManager pm;

    @Override
    public void onEnable(){
        plugin = this;
        logger = plugin.getLogger();
        pm = plugin.getServer().getPluginManager();
        createDirectory();
        registerEvents();
        checkDependencies();
        registerCommands();
    }


    @Override
    public void onDisable() {
        logger.info("Shutting down plugin.");
    }

    public void checkDependencies(){
        if(pm.isPluginEnabled("Towny") && pm.isPluginEnabled("NoteBlockAPI")){
            logger.info("TownyMusic has been enabled");
        }else{
            logger.severe("Couldn't find dependencies(Towny/NoteBlockAPI), shutting down.");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }


    public void createDirectory(){
        File songs = new File(plugin.getDataFolder(),"Songs");
        if(!songs.exists()){
            logger.warning("No Songs folder has been found, making one.");
            try{
                songs.mkdirs();
            }catch(Exception e){
                logger.severe(e.getMessage());
            }
        }else{
            logger.finer("Songs folder has been found.");
        }
    }
    public void registerEvents(){
        pm.registerEvents(new TownEnterListener(), plugin);
        pm.registerEvents(new TownLeaveListener(), plugin);
        pm.registerEvents(new TownCreateListener(), plugin);
    }

    public void registerCommands(){
        List<Town> towns = TownyAPI.getInstance().getTowns();
        List<String> listOfTownNames = towns.stream().map(t -> t.getName()).collect(Collectors.toList());
        File file = new File(plugin.getDataFolder(), "/Songs");
        List<String> files = Arrays.asList(file.list());

        AddonCommand setMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWN_SET, "Music", new SetMusicCommand());
        TownyCommandAddonAPI.addSubCommand(setMusic);

        AddonCommand toggleMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWN_TOGGLE, "Music", new ToggleMusicCommand());
        TownyCommandAddonAPI.addSubCommand(toggleMusic);

        AddonCommand adminSetMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWNYADMIN_SET, "Music", new AdminSetCommand());
        TownyCommandAddonAPI.addSubCommand(adminSetMusic);

        AddonCommand adminToggleMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWNYADMIN_TOGGLE, "Music", new AdminToggleCommand());
        adminToggleMusic.setTabCompletion(0, listOfTownNames);
        adminToggleMusic.setTabCompletion(1, files);
        TownyCommandAddonAPI.addSubCommand(adminToggleMusic);

        plugin.getCommand("togglemusic").setExecutor(new PlayerToggleCommand());

    }
}


package itsjules.townymusic;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import com.palmergames.bukkit.towny.object.AddonCommand;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import itsjules.townymusic.Commands.AdminCommands.AdminSetCommand;
import itsjules.townymusic.Commands.AdminCommands.AdminToggleCommand;
import itsjules.townymusic.Commands.PlayerCommands.PlayerToggleCommand;
import itsjules.townymusic.Commands.PlayerCommands.ReloadCommand;
import itsjules.townymusic.Commands.PlayerCommands.VolumeCommand;
import itsjules.townymusic.Commands.TownCommands.SetMusicCommand;
import itsjules.townymusic.Commands.TownCommands.ToggleMusicCommand;
import itsjules.townymusic.Commands.TownCommands.TownRepeatCommand;
import itsjules.townymusic.Listeners.TownCreateListener;
import itsjules.townymusic.Listeners.TownEnterListener;
import itsjules.townymusic.Listeners.TownLeaveListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class TownyMusic extends JavaPlugin {
    public static TownyMusic plugin;
    public static Logger logger;
    public static PluginManager pm;

    @Override
    public void onEnable(){
        plugin = this;
        logger = plugin.getLogger();
        pm = plugin.getServer().getPluginManager();
        checkDependencies();
        createDirectory();
        registerEvents();
        registerCommands();
        checkTowns();
        plugin.saveDefaultConfig();

    }


    @Override
    public void onDisable() {
        logger.info("Shutting down plugin.");
    }

    public void checkTowns(){
        logger.info("Applying data for towns...");
        for(Town town: TownyAPI.getInstance().getTowns()){
            if(!town.hasMeta("ToggleMusic")){
                town.addMetaData(new BooleanDataField("ToggleMusic", true));
            }

            if(!town.hasMeta("ToggleRepeat")){
                town.addMetaData(new BooleanDataField("ToggleRepeat", true));
            }
        }
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

        AddonCommand setMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWN_SET, "Music", new SetMusicCommand());
        TownyCommandAddonAPI.addSubCommand(setMusic);

        AddonCommand toggleMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWN_TOGGLE, "Music", new ToggleMusicCommand());
        TownyCommandAddonAPI.addSubCommand(toggleMusic);

        AddonCommand toggleRepeat = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWN_TOGGLE, "Repeat", new TownRepeatCommand());
        TownyCommandAddonAPI.addSubCommand(toggleRepeat);

        AddonCommand adminSetMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWNYADMIN_SET, "Music", new AdminSetCommand());
        TownyCommandAddonAPI.addSubCommand(adminSetMusic);

        AddonCommand adminToggleMusic = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWNYADMIN_TOGGLE, "Music", new AdminToggleCommand());
        TownyCommandAddonAPI.addSubCommand(adminToggleMusic);

        plugin.getCommand("togglemusic").setExecutor(new PlayerToggleCommand());
        plugin.getCommand("volume").setExecutor(new VolumeCommand());
        plugin.getCommand("reloadconfig").setExecutor(new ReloadCommand());

    }
}


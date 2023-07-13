package itsjules.townymusic.Listeners;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownCreateListener implements Listener {
    @EventHandler
    public void onTownCreate(NewTownEvent event){
        event.getTown().addMetaData(new BooleanDataField("ToggleMusic", true));
        TownyMessaging.sendMsg(event.getTown().getMayor().getPlayer(), "Hey, You just made a town! Congrats, why not set some music using /t set music");
        event.getTown().addMetaData(new BooleanDataField("ToggleRepeat", true));
        event.getTown().addMetaData(new StringDataField("Music", ""));
    }
}

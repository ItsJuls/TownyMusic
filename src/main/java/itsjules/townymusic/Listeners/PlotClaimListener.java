package itsjules.townymusic.Listeners;

import com.palmergames.bukkit.towny.event.TownClaimEvent;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlotClaimListener implements Listener {
    @EventHandler
    public void onClaim(TownClaimEvent e){
        TownBlock tb = e.getTownBlock();
        if(!tb.hasMeta("ToggleMusic")){
            tb.addMetaData(new BooleanDataField("ToggleMusic", true));
        }

        if(!tb.hasMeta("ToggleRepeat")){
            tb.addMetaData(new BooleanDataField("ToggleRepeat", true));
        }
    }
}

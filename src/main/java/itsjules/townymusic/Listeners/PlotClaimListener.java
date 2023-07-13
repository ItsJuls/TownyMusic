package itsjules.townymusic.Listeners;

import com.palmergames.bukkit.towny.event.TownClaimEvent;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlotClaimListener implements Listener {
    @EventHandler
    public void onClaim(TownClaimEvent e){
        TownBlock tb = e.getTownBlock();
            tb.addMetaData(new BooleanDataField("ToggleMusic", true));
            tb.addMetaData(new BooleanDataField("ToggleRepeat", true));
            tb.addMetaData(new StringDataField("PlotMusic", ""));
        }
    }

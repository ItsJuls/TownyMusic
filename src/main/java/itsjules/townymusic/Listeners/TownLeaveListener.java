package itsjules.townymusic.Listeners;


import com.palmergames.bukkit.towny.event.PlayerLeaveTownEvent;
import com.palmergames.bukkit.towny.object.Town;
import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.model.FadeType;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.songplayer.Fade;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import itsjules.townymusic.TownyMusic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class TownLeaveListener implements Listener {
    @EventHandler
    public void onTownLeave(PlayerLeaveTownEvent event){
       Player player = event.getPlayer();
        Town town = event.getLefttown();
        endSong(player, town);


    }

    public void endSong(Player player, Town town) {
        if (NoteBlockAPI.isReceivingSong(player)) {
            ArrayList<SongPlayer> songPlayer = NoteBlockAPI.getSongPlayersByPlayer(player);
            for (SongPlayer sp : songPlayer) {
                Fade fadeout = sp.getFadeOut();
                fadeout.setType(FadeType.LINEAR);
                fadeout.setFadeDuration(TownyMusic.plugin.getConfig().getInt("FadeOutSeconds") * 20);
                sp.setRepeatMode(RepeatMode.NO);
                sp.setPlaying(false, fadeout);
            }
        }
    }
}

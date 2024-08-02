package itsjules.townymusic.Listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.palmergames.bukkit.towny.event.player.PlayerExitsFromDistrictEvent;
import com.palmergames.bukkit.towny.event.player.PlayerExitsFromTownBorderEvent;
import com.xxmicloxx.NoteBlockAPI.model.FadeType;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.songplayer.Fade;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;

import itsjules.townymusic.TownyMusic;
import itsjules.townymusic.Commands.Utils.MusicUtils;

public class TownLeaveListener implements Listener {
    @EventHandler
    public void onTownLeave(PlayerExitsFromTownBorderEvent event){
       Player player = event.getPlayer();
        endSong(player);
    }
    
    @EventHandler
    public void onDistrictLeave(PlayerExitsFromDistrictEvent event){
       Player player = event.getPlayer();
        endSong(player);
    }
    
    public void endSong(Player player) {
        if(MusicUtils.radioMap.containsKey(player.getUniqueId())){
            SongPlayer sp = MusicUtils.radioMap.get(player.getUniqueId());
                Fade fadeout = sp.getFadeOut();
                fadeout.setType(FadeType.LINEAR);
                fadeout.setFadeDuration(TownyMusic.plugin.getConfig().getInt("FadeOutSeconds") * 20);
                sp.setRepeatMode(RepeatMode.NO);
                sp.setPlaying(false, fadeout);
            }
        }
    }

package itsjules.townymusic.Listeners;


import com.palmergames.bukkit.towny.event.player.PlayerExitsFromTownBorderEvent;
import com.palmergames.bukkit.towny.object.Town;
import com.xxmicloxx.NoteBlockAPI.model.FadeType;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.songplayer.Fade;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import itsjules.townymusic.Utils.MusicUtils;
import itsjules.townymusic.TownyMusic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class TownLeaveListener implements Listener {
    @EventHandler
    public void onTownLeave(PlayerExitsFromTownBorderEvent event){
       Player player = event.getPlayer();
        Town town = event.getLeftTown();
        endSong(player);
    }

    @EventHandler
    public void onPlayerLeaveServer(PlayerQuitEvent event){
        Player player = event.getPlayer();
        endSong(player);
    }

    public void endSong(Player player) {
        if(MusicUtils.radioMap.containsKey(player.getUniqueId())){
            SongPlayer sp = MusicUtils.radioMap.get(player.getUniqueId());
                Fade fadeout = sp.getFadeOut();
                fadeout.setType(FadeType.LINEAR);
                fadeout.setFadeDuration(TownyMusic.plugin.getConfig().getInt("FadeOutSeconds"));
                sp.setRepeatMode(RepeatMode.NO);
                sp.setPlaying(false, fadeout);
            }
        if(MusicUtils.soundMap.containsKey(player.getUniqueId())){
            player.stopSound(MusicUtils.soundMap.get(player.getUniqueId()));
            MusicUtils.soundMap.remove(player.getUniqueId());
        }
        if(MusicUtils.taskMap.containsKey(player.getUniqueId())){
            MusicUtils.taskMap.get(player.getUniqueId()).cancel();
            MusicUtils.taskMap.remove(player.getUniqueId());
        }
      }
    }

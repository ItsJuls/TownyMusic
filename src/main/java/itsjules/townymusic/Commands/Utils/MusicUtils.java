package itsjules.townymusic.Commands.Utils;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import itsjules.townymusic.TownyMusic;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

public class MusicUtils {
    public static HashMap<UUID, SongPlayer> radioMap = new HashMap<>();
    public static void playMusic(Player player, Town town, RadioSongPlayer rsp) {
        if (town.getMetadata("ToggleMusic").getValue().equals(true)) {
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            rsp.addPlayer(player);
            rsp.setPlaying(true);
            RepeatMode rpm;
            if (town.getMetadata("ToggleRepeat").getValue().equals(true)) {
                rpm = RepeatMode.ONE;
            } else {
                rpm = RepeatMode.NO;
            }

            rsp.setRepeatMode(rpm);
            rsp.setVolume(pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusicVolume"), PersistentDataType.BYTE, (byte) 100));
            if (TownyMusic.plugin.getConfig().getBoolean("ExtraOctaves", false)) {
                rsp.setEnable10Octave(true);
            }
            TownyMessaging.sendMsg(player, "Playing now: " + rsp.getSong().getTitle());
        }
    }

    public static void playMusic(Player player, TownBlock townblock, RadioSongPlayer rsp){
        if(townblock.getMetadata("ToggleMusic").getValue().equals(true)){
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        rsp.addPlayer(player);
        rsp.setPlaying(true);
        RepeatMode rpm;
        if(townblock.getMetadata("ToggleRepeat").getValue().equals(true)){
            rpm = RepeatMode.ONE;
        }else{
            rpm = RepeatMode.NO;
        }

        rsp.setRepeatMode(rpm);
        rsp.setVolume(pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusicVolume"), PersistentDataType.BYTE, (byte) 100));
        if(TownyMusic.plugin.getConfig().getBoolean("ExtraOctaves", false)){
            rsp.setEnable10Octave(true);
        }
        TownyMessaging.sendMsg(player, "Playing now: " + rsp.getSong().getTitle());
    }
}
}

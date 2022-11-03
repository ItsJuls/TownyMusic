package itsjules.townymusic.Listeners;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.event.PlayerEnterTownEvent;
import com.palmergames.bukkit.towny.object.Town;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import itsjules.townymusic.TownyMusic;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class TownEnterListener implements Listener {

    public static HashMap<UUID, SongPlayer> radioMap = new HashMap<>();

    @EventHandler
    public void onTownEnter(PlayerEnterTownEvent event) {
        if(event.getEnteredtown().hasMeta("Music") && event.getEnteredtown().hasMeta("ToggleMusic")) {

            if (event.getEnteredtown().getMetadata("ToggleMusic").getValue().equals(true)) {

                Player player = event.getPlayer();

                PersistentDataContainer pdc = player.getPersistentDataContainer();

                Town town = event.getEnteredtown();

                String songName  = String.join(" ", (CharSequence) town.getMetadata("Music").getValue());

                File file = new File(TownyMusic.plugin.getDataFolder(), "/Songs/" + songName);


                if (pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "true").equalsIgnoreCase("true")) {

                    if (!file.isDirectory()) {
                        if (file.exists()) {

                            Song song = NBSDecoder.parse(file);
                            RadioSongPlayer rsp = new RadioSongPlayer(song);

                            if (radioMap.containsValue(player.getUniqueId())) {
                                radioMap.get(player.getUniqueId()).destroy();
                            }

                            playMusic(player, rsp);
                            radioMap.put(player.getUniqueId(), rsp);
                        }

                    } else if (!file.exists() && town.isMayor(TownyUniverse.getInstance().getResident(player.getUniqueId()))) {
                        TownyMessaging.sendMsg(player, "Could not find this song/song is invalid.");
                    }
                }
            }
        }
    }

        public void playMusic (Player player, RadioSongPlayer rsp){
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            rsp.addPlayer(player);
            rsp.setPlaying(true);
            rsp.setRepeatMode(RepeatMode.ONE);
            rsp.setVolume(pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusicVolume"), PersistentDataType.BYTE, (byte) 100));
            if(TownyMusic.plugin.getConfig().getBoolean("ExtraOctaves", false)){
                rsp.setEnable10Octave(true);
            }
            TownyMessaging.sendMsg(player, "Playing now: " + rsp.getSong().getTitle());
        }

    }


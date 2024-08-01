package itsjules.townymusic.Listeners;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.event.player.PlayerEntersIntoDistrictEvent;
import com.palmergames.bukkit.towny.event.player.PlayerEntersIntoTownBorderEvent;
import com.palmergames.bukkit.towny.object.Town;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

import itsjules.townymusic.TownyMusic;

public class TownEnterListener implements Listener {

    public static HashMap<UUID, SongPlayer> radioMap = new HashMap<>();

    @EventHandler
    public void onTownEnter(PlayerEntersIntoTownBorderEvent event) {
        if(event.getEnteredTown().hasMeta("Music") && event.getEnteredTown().hasMeta("ToggleMusic")) {

            if (event.getEnteredTown().getMetadata("ToggleMusic").getValue().equals(true)) {

                Player player = event.getPlayer();

                PersistentDataContainer pdc = player.getPersistentDataContainer();

                Town town = event.getEnteredTown();

                String songName  = String.join(" ", (CharSequence) town.getMetadata("Music").getValue());

                File file = new File(TownyMusic.plugin.getDataFolder(), "/Songs/" + songName);


                if (pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "true").equalsIgnoreCase("true")) {

                    if (!file.isDirectory()) {
                        if (file.exists()) {

                            Song song = NBSDecoder.parse(file);
                            RadioSongPlayer rsp = new RadioSongPlayer(song);

                            if (radioMap.containsKey(player.getUniqueId())) {
                                radioMap.get(player.getUniqueId()).destroy();
                            }

                            playMusic(player, town,  rsp);
                            radioMap.put(player.getUniqueId(), rsp);
                        }

                    } else if (!file.exists() && town.isMayor(TownyUniverse.getInstance().getResident(player.getUniqueId()))) {
                        TownyMessaging.sendMsg(player, "Could not find this song/song is invalid.");
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onDistrictEnter(PlayerEntersIntoDistrictEvent event) {
        if(event.getEnteredDistrict().getTown().hasMeta("Music") && event.getEnteredDistrict().getTown().hasMeta("ToggleMusic")) {

            if (event.getEnteredDistrict().getTown().getMetadata("ToggleMusic").getValue().equals(true)) {

                Player player = event.getPlayer();

                PersistentDataContainer pdc = player.getPersistentDataContainer();

                Town town = event.getEnteredDistrict().getTown();

                String songName  = String.join(" ", (CharSequence) town.getMetadata("Music").getValue());

                File file = new File(TownyMusic.plugin.getDataFolder(), "/Songs/" + songName);


                if (pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "true").equalsIgnoreCase("true")) {

                    if (!file.isDirectory()) {
                        if (file.exists()) {

                            Song song = NBSDecoder.parse(file);
                            RadioSongPlayer rsp = new RadioSongPlayer(song);

                            if (radioMap.containsKey(player.getUniqueId())) {
                                radioMap.get(player.getUniqueId()).destroy();
                            }

                            playMusic(player, town,  rsp);
                            radioMap.put(player.getUniqueId(), rsp);
                        }

                    } else if (!file.exists() && town.isMayor(TownyUniverse.getInstance().getResident(player.getUniqueId()))) {
                        TownyMessaging.sendMsg(player, "Could not find this song/song is invalid.");
                    }
                }
            }
        }
    }
    
    public void playMusic (Player player, Town town, RadioSongPlayer rsp){
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        rsp.addPlayer(player);
        rsp.setPlaying(true);
        RepeatMode rpm;
        if(town.getMetadata("ToggleRepeat").getValue().equals(true)){
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
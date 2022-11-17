package itsjules.townymusic.Listeners;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import itsjules.townymusic.Commands.Utils.MusicUtils;
import itsjules.townymusic.TownyMusic;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;

public class PlotEnterListener implements Listener {
    @EventHandler
    public void onPlotEnter(PlayerChangePlotEvent e) {
        Player player = e.getPlayer();
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        Resident res = TownyAPI.getInstance().getResident(player);

        if (e.getTo().getTownBlockOrNull() != null) {

            TownBlock tb = e.getTo().getTownBlockOrNull();

            if (e.getTo().getTownBlockOrNull().hasMeta("PlotMusic")) {

                String songName = String.join(" ", (CharSequence) tb.getMetadata("PlotMusic").getValue());

                File file = new File(TownyMusic.plugin.getDataFolder(), "/Songs/" + songName);

                if (pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "true").equalsIgnoreCase("true")) {

                    if (!file.isDirectory()) {
                        if (file.exists()) {

                            Song song = NBSDecoder.parse(file);
                            RadioSongPlayer rsp = new RadioSongPlayer(song);

                            if (MusicUtils.radioMap.containsKey(player.getUniqueId())) {
                                if (!MusicUtils.radioMap.get(player.getUniqueId()).getSong().getPath().equals(rsp.getSong().getPath())) {
                                    MusicUtils.radioMap.get(player.getUniqueId()).destroy();
                                    MusicUtils.playMusic(player, tb, rsp);
                                    MusicUtils.radioMap.put(player.getUniqueId(), rsp);
                                }
                                if(!MusicUtils.radioMap.get(player.getUniqueId()).isPlaying()){
                                    MusicUtils.radioMap.get(player.getUniqueId()).destroy();
                                    MusicUtils.playMusic(player, tb, rsp);
                                    MusicUtils.radioMap.put(player.getUniqueId(), rsp);
                                }
                            } else {
                                MusicUtils.playMusic(player, tb, rsp);
                                MusicUtils.radioMap.put(player.getUniqueId(), rsp);
                            }
                        }

                    } else if (!file.exists() && tb.isOwner(res)) {
                        TownyMessaging.sendMsg(player, "Could not find this song/song is invalid.");
                    }
                }
            } else {
                if (pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "true").equalsIgnoreCase("true")) {
                    Town town = e.getTo().getTownOrNull();

                    String songName = String.join(" ", (CharSequence) town.getMetadata("Music").getValue());

                    File file = new File(TownyMusic.plugin.getDataFolder(), "/Songs/" + songName);

                    if (!file.isDirectory()) {
                        if (file.exists()) {
                            Song song = NBSDecoder.parse(file);
                            RadioSongPlayer rsp = new RadioSongPlayer(song);

                            if (MusicUtils.radioMap.containsKey(player.getUniqueId())) {
                                if (!MusicUtils.radioMap.get(player.getUniqueId()).getSong().getPath().equals(rsp.getSong().getPath())) {
                                    MusicUtils.radioMap.get(player.getUniqueId()).destroy();
                                    MusicUtils.playMusic(player, town, rsp);
                                    MusicUtils.radioMap.put(player.getUniqueId(), rsp);
                                }
                                if(!MusicUtils.radioMap.get(player.getUniqueId()).isPlaying()){
                                    MusicUtils.radioMap.get(player.getUniqueId()).destroy();
                                    MusicUtils.playMusic(player, town, rsp);
                                    MusicUtils.radioMap.put(player.getUniqueId(), rsp);
                                }
                            }else {
                                MusicUtils.playMusic(player, town, rsp);
                                MusicUtils.radioMap.put(player.getUniqueId(), rsp);
                            }



                            } else if (!file.exists() && tb.isOwner(res)) {
                                TownyMessaging.sendMsg(player, "Could not find this song/song is invalid.");
                            }
                        }
                    }
                }
            }
        }
    }




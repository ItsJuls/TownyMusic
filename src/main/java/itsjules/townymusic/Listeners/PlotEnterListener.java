package itsjules.townymusic.Listeners;

import com.google.common.io.Files;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import itsjules.townymusic.TownyMusic;
import itsjules.townymusic.Utils.MusicUtils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class PlotEnterListener implements Listener {
    @EventHandler
    public void onPlotEnter(PlayerChangePlotEvent e) {
        Player player = e.getPlayer();
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        Resident res = TownyAPI.getInstance().getResident(player);
        if (e.getTo().getTownBlockOrNull() == null) {
            return;
        }

        TownBlock tb = e.getTo().getTownBlockOrNull();

        if (tb.getMetadata("PlotMusic").getValue() == null) {
            return;
        }

        if (!tb.getMetadata("PlotMusic").getValue().equals("")) {

            String songName = String.join(" ", (CharSequence) tb.getMetadata("PlotMusic").getValue());

            File file = new File(TownyMusic.plugin.getDataFolder(), "/Songs/" + songName);

            if (isMusicForPlayerEnabled(player)) {

                if (!file.isDirectory() && file.exists()) {


                    if(Files.getFileExtension(file.getName()).equals("nbs")){
                        playNBS(file, player, tb);
                    }else if(Files.getFileExtension(file.getName()).equals("ogg")){
                       String fileWithoutExtension = Files.getNameWithoutExtension(file.getName());
                            playOGG(player, fileWithoutExtension, tb);
                    }



                } else if (!file.exists() && tb.isOwner(res)) {
                    TownyMessaging.sendMsg(player, "Could not find this song/song is invalid.");
                }
            }
        } else {
            if (isMusicForPlayerEnabled(player)) {
                Town town = e.getTo().getTownOrNull();

                String songName = String.join(" ", (CharSequence) town.getMetadata("Music").getValue());

                File file = new File(TownyMusic.plugin.getDataFolder(), "/Songs/" + songName);

                if (!file.isDirectory() && file.exists()) {

                    if(Files.getFileExtension(file.getName()).equals("nbs")){
                        playNBS(file, player, town);
                    }else if(Files.getFileExtension(file.getName()).equals("ogg")){
                        String fileWithoutExtension = Files.getNameWithoutExtension(file.getName());
                        playOGG(player, fileWithoutExtension, town);
                    }

                    } else if (!file.exists() && tb.isOwner(res)) {
                        TownyMessaging.sendMsg(player, "Could not find this song/song is invalid.");
                    }
                }
            }
        }

        private void playNBS(File file, Player player, TownBlock tb){
            Song song = NBSDecoder.parse(file);
            RadioSongPlayer rsp = new RadioSongPlayer(song);

            if (MusicUtils.radioMap.containsKey(player.getUniqueId())) {
                if (!MusicUtils.radioMap.get(player.getUniqueId()).getSong().getPath().equals(rsp.getSong().getPath())) {
                    MusicUtils.radioMap.get(player.getUniqueId()).destroy();
                    MusicUtils.playMusic(player, tb, rsp);
                    MusicUtils.radioMap.put(player.getUniqueId(), rsp);
                }
                if (!MusicUtils.radioMap.get(player.getUniqueId()).isPlaying()) {
                    MusicUtils.radioMap.get(player.getUniqueId()).destroy();
                    MusicUtils.playMusic(player, tb, rsp);
                    MusicUtils.radioMap.put(player.getUniqueId(), rsp);
                }
            } else {
                MusicUtils.playMusic(player, tb, rsp);
                MusicUtils.radioMap.put(player.getUniqueId(), rsp);
            }
        }

        private void playNBS(File file, Player player, Town town){
            Song song = NBSDecoder.parse(file);
            RadioSongPlayer rsp = new RadioSongPlayer(song);
            UUID id = player.getUniqueId();

            if (MusicUtils.radioMap.containsKey(id)) {
                if (!MusicUtils.radioMap.get(id).getSong().getPath().equals(rsp.getSong().getPath())) {
                    MusicUtils.radioMap.get(id).destroy();
                    MusicUtils.playMusic(player, town, rsp);
                    MusicUtils.radioMap.put(player.getUniqueId(), rsp);
                }
                if (!MusicUtils.radioMap.get(id).isPlaying()) {
                    MusicUtils.radioMap.get(id).destroy();
                    MusicUtils.playMusic(player, town, rsp);
                    MusicUtils.radioMap.put(id, rsp);
                }
            } else {
                MusicUtils.playMusic(player, town, rsp);
                MusicUtils.radioMap.put(id, rsp);
            }
        }

        private void playOGG(Player player, String string, Town town) {
        UUID id = player.getUniqueId();
        Sound music = Sound.sound(Key.key(string.toLowerCase()), Sound.Source.MASTER, 1F, 1F);
        HashMap<UUID,Sound> map = MusicUtils.soundMap;


            if(map.isEmpty()){
                MusicUtils.playMusic(player, music, town);
            } else if (!map.containsKey(id)) {
                MusicUtils.playMusic(player, music, town);
            } else if(map.containsKey(id) && !map.get(id).equals(music)) {
                player.stopSound(map.get(id));
                MusicUtils.playMusic(player, music, town);
            }
        }

    private void playOGG(Player player, String string, TownBlock tb) {
        UUID id = player.getUniqueId();
        Sound music = Sound.sound(Key.key(string.toLowerCase()), Sound.Source.MASTER, 1F, 1F);
        HashMap<UUID,Sound> map = MusicUtils.soundMap;


        if(map.isEmpty()){
            MusicUtils.playMusic(player, music, tb);
        } else if (!map.containsKey(id)) {
            MusicUtils.playMusic(player, music, tb);
        } else if(map.containsKey(id) && !map.get(id).equals(music)) {
            player.stopSound(map.get(id));
            MusicUtils.playMusic(player, music, tb);
        }
    }

        private boolean isMusicForPlayerEnabled(Player player){
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            if(pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "true").equalsIgnoreCase("true")){
                return true;
            }else{
                return false;
            }
        }
    }




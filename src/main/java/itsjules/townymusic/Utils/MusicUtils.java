package itsjules.townymusic.Utils;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import itsjules.townymusic.TownyMusic;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class MusicUtils {
    public static HashMap<UUID, SongPlayer> radioMap = new HashMap<>();
    public static HashMap<UUID, Sound> soundMap = new HashMap<>();
    public static HashMap<UUID, BukkitTask> taskMap = new HashMap<>();
    public static void playMusic(Player player, Town town, RadioSongPlayer rsp) {
        if (isToggled(town)) {
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            rsp.addPlayer(player);
            rsp.setPlaying(true);
            RepeatMode rpm;
            if (isRepeating(town)) {
                rpm = RepeatMode.ONE;
            } else {
                rpm = RepeatMode.NO;
            }

            rsp.setRepeatMode(rpm);
            rsp.setVolume(pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusicVolume"), PersistentDataType.BYTE, (byte) 100));
            if (TownyMusic.plugin.getConfig().getBoolean("ExtraOctaves", false)) {
                rsp.setEnable10Octave(true);
            }
            sendTownyMessaging(player, rsp);
        }
    }

    public static void playMusic(Player player, TownBlock townblock, RadioSongPlayer rsp){
        if(isToggled(townblock)){
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        rsp.addPlayer(player);
        rsp.setPlaying(true);
        RepeatMode rpm;
        if(isRepeating(townblock)){
            rpm = RepeatMode.ONE;
        }else{
            rpm = RepeatMode.NO;
        }
        rsp.setRepeatMode(rpm);
        rsp.setVolume(pdc.getOrDefault(new NamespacedKey(TownyMusic.plugin, "TownyMusicVolume"), PersistentDataType.BYTE, (byte) 100));
        if(TownyMusic.plugin.getConfig().getBoolean("ExtraOctaves", false)){
            rsp.setEnable10Octave(true);
        }
        sendTownyMessaging(player, rsp);
    }
  }

  public static void playMusic(Player player, Sound sound, Town town){
        if(isToggled(town)){
            if(!isRepeating(town)){
                player.playSound(sound, Sound.Emitter.self());
                MusicUtils.soundMap.put(player.getUniqueId(), sound);
            }else{
                playLoopingMusic(player, sound);
            }
        }
  }

    public static void playMusic(Player player, Sound sound, TownBlock townBlock){
        if(isToggled(townBlock)){
            if(!isRepeating(townBlock)){
                player.playSound(sound, Sound.Emitter.self());
                MusicUtils.soundMap.put(player.getUniqueId(), sound);
            }else{
                playLoopingMusic(player, sound);
            }
        }

    }

  public static void playLoopingMusic(Player player, Sound sound) {
          @NotNull BukkitTask runnable = new BukkitRunnable() {
              @Override
              public void run() {
                  player.playSound(sound, Sound.Emitter.self());
                  player.sendMessage("Bukkit Runnable test ya");
              }
          }.runTaskTimerAsynchronously(TownyMusic.plugin, 1, 3700);
      MusicUtils.soundMap.put(player.getUniqueId(), sound);
      MusicUtils.taskMap.put(player.getUniqueId(), runnable);
      }


  private static void sendTownyMessaging(Player player, RadioSongPlayer rsp){
      TownyMessaging.sendMsg(player, "Playing now: " + rsp.getSong().getTitle());
  }

  private static boolean isRepeating(Town town){
        if(town.getMetadata("ToggleRepeat").getValue().equals(true)){
            return true;
        }else{
            return false;
        }
  }

  private static boolean isToggled(Town town){
      if(town.getMetadata("ToggleMusic").getValue().equals(true)){
          return true;
      }else{
          return false;
      }
  }

    private static boolean isRepeating(TownBlock tb){
        if(tb.getMetadata("ToggleRepeat").getValue().equals(true)){
            return true;
        }else{
            return false;
        }
    }

    private static boolean isToggled(TownBlock tb){
        if(tb.getMetadata("ToggleMusic").getValue().equals(true)){
            return true;
        }else{
            return false;
        }
    }
}

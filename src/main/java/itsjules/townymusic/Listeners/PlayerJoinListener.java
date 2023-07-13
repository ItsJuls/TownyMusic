package itsjules.townymusic.Listeners;

import itsjules.townymusic.TownyMusic;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        PersistentDataContainer pdc = e.getPlayer().getPersistentDataContainer();
        if(!pdc.has(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING)){
            e.getPlayer().getPersistentDataContainer().set(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "true");
        }
        if(!pdc.has(new NamespacedKey(TownyMusic.plugin, "TownyMusicVolume"), PersistentDataType.BYTE)) {
            e.getPlayer().getPersistentDataContainer().set(new NamespacedKey(TownyMusic.plugin, "TownyMusicVolume"), PersistentDataType.BYTE, (byte) 100);
        }
    }
}

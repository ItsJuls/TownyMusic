package itsjules.townymusic.Listeners;

import itsjules.townymusic.TownyMusic;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().getPersistentDataContainer().set(new NamespacedKey(TownyMusic.plugin, "TownyMusic"), PersistentDataType.STRING, "true");
    }
}

package itsjules.townymusic.Commands.AdminCommands;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import itsjules.townymusic.TownyMusic;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PurgeMusicCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.isOp()){
            TownyMessaging.sendErrorMsg(sender, "You need permission for this!");
            return true;
        }

        if(args.length == 0){
            TownyMessaging.sendErrorMsg(sender, "You must have a player to purge their music!");
            return true;
        }

        if(!(sender instanceof Player)){
            TownyMusic.logger.info("Purged all music for " + args[0] + "!");
                for(SongPlayer sp : NoteBlockAPI.getSongPlayersByPlayer(Bukkit.getPlayer(args[0]))){
                    sp.destroy();
                }
            }

        TownyMessaging.sendMsg(sender, "Purged all music for " + args[0] + "!");
                for(SongPlayer sp : NoteBlockAPI.getSongPlayersByPlayer(Bukkit.getPlayer(args[0]))){
                    sp.destroy();
                }
        return true;
    }
}

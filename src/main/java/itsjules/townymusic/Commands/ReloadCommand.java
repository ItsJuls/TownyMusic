package itsjules.townymusic.Commands;

import com.palmergames.bukkit.towny.TownyMessaging;
import itsjules.townymusic.TownyMusic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.isOp()){
            TownyMessaging.sendErrorMsg(sender, "You need permission for this!");
            return true;
        }

        if(!(sender instanceof Player)){
            TownyMusic.logger.info("Reloaded Config!");
            return true;
        }
        TownyMessaging.sendMsg(sender, "Reloaded Config!");
        TownyMusic.plugin.reloadConfig();
        return true;
    }
}

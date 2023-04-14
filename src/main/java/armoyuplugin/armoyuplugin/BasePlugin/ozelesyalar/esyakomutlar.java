package armoyuplugin.armoyuplugin.BasePlugin.ozelesyalar;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class esyakomutlar  implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Only players can use that command");
            return true;
        }else{
            Player player = (Player) sender;
            if (player.isOp()){
                if (cmd.getName().equalsIgnoreCase("poseidonunmizragi")){
                    player.getWorld().dropItemNaturally(player.getLocation(),esyayonetim.Poseidonunmizragi);


                }
            }else{
                sender.sendMessage("op değilsin gardaş");
            }
            return true;
        }
    }
}

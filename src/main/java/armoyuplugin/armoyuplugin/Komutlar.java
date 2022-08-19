package armoyuplugin.armoyuplugin;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Komutlar  implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label , String[] args){
            if (!(sender instanceof Player)){return false;}
            Player oyuncu = (Player) sender;


            if (cmd.getName().equalsIgnoreCase("heal")){
                double maxHealth = oyuncu.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
                oyuncu.setHealth(maxHealth);
            }

        if (cmd.getName().equalsIgnoreCase("feed")){
            oyuncu.setFoodLevel(20);
        }

            return true;
    }
}

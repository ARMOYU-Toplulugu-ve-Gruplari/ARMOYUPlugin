package ARMOYU.Komutlar.TabTamamlama;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class ClaimTabTamamlama implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        Player oyuncu = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("claim")) {





            if (args.length == 1) {

                List<String> arguments = new ArrayList<>();

                    arguments.add("al");
                    arguments.add("sil");
                    arguments.add("hissedar ekle");
                    arguments.add("hissedar ekle heryer");
                    arguments.add("hissedar sil");
                    arguments.add("hissedar sil heryer");
                    arguments.add("aciklama");
                    arguments.add("aciklamaHeryer");
                    arguments.add("aciklama");
                    arguments.add("rehin");
                    arguments.add("devret");
                    arguments.add("liste");

                return arguments;

            }

        }
        return null;
    }
}


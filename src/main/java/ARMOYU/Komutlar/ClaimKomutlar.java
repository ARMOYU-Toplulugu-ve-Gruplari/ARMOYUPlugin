package ARMOYU.Komutlar;

import ARMOYU.Menuler.Claim.AnaMenu;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import static ARMOYU.ARMOYUPlugin.*;

public class ClaimKomutlar implements CommandExecutor {

    String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player p = (Player) sender;


        if (cmd.getName().equalsIgnoreCase("claim")) {
            String[] oyuncuAdiVeParola = jsonService.getOyuncuAdiVeParola(p);

            if (args.length == 0) {
                try {
                    MenuManager.openMenu(AnaMenu.class, p);
                } catch (MenuManagerException | MenuManagerNotSetupException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equals("al")) {

                if (args.length == 1) {
                    claimCommandsService.claimAl(p, oyuncuAdiVeParola);
                } else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Doğru kullanım şekli /claim al");
            } else if (args[0].equals("hissedar")) {
                if (args[1].equals("ekle")) {
                    if (args.length == 3) {
                        claimCommandsService.hissedarEkle(p, oyuncuAdiVeParola, args[2]);
                        return true;
                    }
                    if (args[2].equals("heryer")) {
                        claimCommandsService.hissedarEkleHeryer(p, oyuncuAdiVeParola, args[3]);
                        return true;
                    }

                } else if (args[1].equals("sil")) {
                    if (args.length == 3) {
                        claimCommandsService.hissedarSil(p, oyuncuAdiVeParola, args[2]);
                        return true;
                    }
                    if (args[2].equals("heryer")) {
                        claimCommandsService.hissedarSilHeryer(p, oyuncuAdiVeParola, args[3]);
                        return true;
                    }
                }

            } else if (args[0].equals("sil")) {
                if (args.length == 1){
                    claimCommandsService.claimSil(p,oyuncuAdiVeParola);
                    return true;
                }
                if (args[1].equals("heryer")){
                    claimCommandsService.claimSilHeryer(p,oyuncuAdiVeParola);
                    return true;
                }
            } else if (args[0].equals("aciklama")){
                claimCommandsService.claimAciklama(p,oyuncuAdiVeParola,args);
                return true;
            } else if (args[0].equals("aciklamaHeryer")) {
                claimCommandsService.claimAciklamaHeryer(p,oyuncuAdiVeParola,args);
                return true;
            } else if (args[0].equals("rehin")) {
                claimCommandsService.claimRehin(p,oyuncuAdiVeParola);
                return true;
            } else if (args[0].equals("devret")) {
                claimCommandsService.claimDevret(p,oyuncuAdiVeParola,args[1]);
                return true;
            }

        }return true;
    }
}



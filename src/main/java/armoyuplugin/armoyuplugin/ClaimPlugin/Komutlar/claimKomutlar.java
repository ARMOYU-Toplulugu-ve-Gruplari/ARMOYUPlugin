package armoyuplugin.armoyuplugin.ClaimPlugin.Komutlar;

import armoyuplugin.armoyuplugin.ClaimPlugin.ClaimListesi.ArsaBilgiLink;

import armoyuplugin.armoyuplugin.ClaimPlugin.menuler.AnaMenu;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.*;

public class claimKomutlar implements CommandExecutor {
    String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {  return true;   }

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

                int claimkontrol = 0;


                if (args.length == 1) {

                    ArsaBilgiLink temp = claimListesi.listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString());
                    if (temp!=null){
                        claimkontrol++;
                    }
                    if (claimkontrol != 0) {
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Bu arsa sahipli");
                    } else {
                            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsaal","0|0","0|0","2|2",p.getWorld().toString(),p.getLocation().getChunk().toString()};
                            String[] durumVeAciklama = apiService.getDurumVeAciklama(p,linkElemanlar);
                            if (!durumVeAciklama[0].equals("null")) {
                                if (durumVeAciklama[0].equals("0")) {
                                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + durumVeAciklama[1]);
                                } else {
                                    p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + durumVeAciklama[1]);
                                    claimListesi.arsaAl(p.getLocation().getChunk().toString(), p.getName(), p.getWorld().toString());
                                }
                            }else
                                p.sendMessage("claimal null hatası yetkiliye danışın");
                        return true;
                    }
                } else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Doğru kullanım şekli /claim al");
            } else if (args[0].equals("trust")) {
                if (args.length == 2) {

                        String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"hissedar","ekle",p.getWorld().toString(),p.getLocation().getChunk().toString(),args[1]};
                        String[] durumVeAciklama = apiService.getDurumVeAciklama(p,linkElemanlar);
                        if (!durumVeAciklama[0].equals("null")) {
                            if (durumVeAciklama[0].equals("1")) {
                                p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + durumVeAciklama[1]);
                                claimListesi.hissedarlaraEkleBir(p.getLocation().getChunk().toString(), p.getName(), args[1], p.getWorld().toString());
                            } else
                                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + durumVeAciklama[1]);
                        }

                } else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim trust oyuncuismi");
            } else if (args[0].equals("untrust")) {
                if (args.length == 2) {
                        String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"hissedar","sil-heryer",p.getWorld().toString(),p.getLocation().getChunk().toString(),args[1]};
                        String[] durumVeAciklama = apiService.getDurumVeAciklama(p,linkElemanlar);
                        if (!durumVeAciklama[0].equals("null")) {
                            if (durumVeAciklama[0].equals("1")) {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + durumVeAciklama[1]);
                                claimListesi.hissedarlardanCikar(p.getLocation().getChunk().toString(), p.getName(), args[1], p.getWorld().toString());
                        } else{
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + durumVeAciklama[1]);}
                        }

                } else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim untrust oyuncuismi");
            } else if (args[0].equals("sil")) {
                if (args.length == 1) {

                        String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsasil","0|0","0|0","2|2",p.getWorld().toString(),p.getLocation().getChunk().toString()};
                        String[] durumVeAciklama = apiService.getDurumVeAciklama(p,linkElemanlar);
                        if (!durumVeAciklama[0].equals("null")) {
                            if (durumVeAciklama[0].equals("0")) {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + durumVeAciklama[1]);
                        } else {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + durumVeAciklama[1]);
                                claimListesi.arsaKaldirBir(p, p.getWorld().toString());
                        }
                        }



                } else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim sil");
            }
            else if (args[0].equals("heryeri")){

                if (args.length == 2){

                    if (args[1].equals("sil")){

                        String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsasil-heryer","0|0","0|0","2|2",p.getWorld().toString(),p.getLocation().getChunk().toString()};
                        String[] durumVeAciklama = apiService.getDurumVeAciklama(p,linkElemanlar);
                        if (!durumVeAciklama[0].equals("null")) {
                            if (durumVeAciklama[0].equals("1")) {
                                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + durumVeAciklama[1]);
                                    }else{
                                        p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + durumVeAciklama[1]);
                                claimListesi.arsaKaldirHepsi(p.getName());
                                    }
                }
                    }
                    else
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryeri sil");

                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryeri sil");
            }


            else if (args[0].equals("heryere")){

                if (args.length == 3){
                    if (args[1].equals("ekle")) {
                        String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"hissedar","ekle-heryer",p.getWorld().toString(),p.getLocation().getChunk().toString(),args[2]};
                        String[] durumVeAciklama = apiService.getDurumVeAciklama(p,linkElemanlar);
                        if (!durumVeAciklama[0].equals("null")) {
                            if (durumVeAciklama[0].equals("1")) {
                                p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + durumVeAciklama[1]);
                                claimListesi.hissedarlaraEkleHepsi(p.getName(), args[2]);
                            } else
                                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + durumVeAciklama[1]);
                        }

                    }else
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryere ekle <oyuncuismi>");
                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryere ekle <oyuncuismi>");
            }


            else if(args[0].equals("heryerden")){
                if (args.length == 3){
                    if (args[1].equals("cikar")){
                            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"hissedar","sil-heryer",p.getWorld().toString(),p.getLocation().getChunk().toString(),args[2]};
                            String[] durumVeAciklama = apiService.getDurumVeAciklama(p,linkElemanlar);
                            if (!durumVeAciklama[0].equals("null")) {
                                if (durumVeAciklama[0].equals("1")) {
                                    p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + durumVeAciklama[1]);
                                    claimListesi.hissedarlardanCikarHepsi(p.getName(), args[2]);
                                } else
                                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + durumVeAciklama[1]);
                            }

                    }else
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryerden cikar <oyuncuismi>");
                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryerden cikar <oyuncuismi>");

            }


            else if (args[0].equals("aciklama")) {
                if (args[1].equals("hepsi")){
                    if (args.length!=2){
                        claimListesi.arsaAciklamaDegisHepsi(p,args);
                    }else
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim aciklama hepsi <aciklama>");
                }
                else {
                if (args.length == 1){
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim aciklama <aciklama>");
                }else {
                    claimListesi.arsaAciklamaDegisBir(p,args);
                }
                }
            } else if (args[0].equals("rehin")) {
                if (args.length==1){
                    claimListesi.arsaRehin(p.getLocation().getChunk().toString(),p.getName(),p.getWorld().toString());
                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim rehin");
            } else if (args[0].equals("devret")) {
                if (args.length==2){
                    String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsalar","deviret",args[1],p.getLocation().getChunk().toString(),p.getWorld().toString()};
                    String[] durumVeAciklama = apiService.getDurumVeAciklama(p,linkElemanlar);
                    if (!durumVeAciklama[0].equals("null")) {
                        if (durumVeAciklama[0].equals("1")) {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + durumVeAciklama[1]);
                            claimListesi.arsaDevret(p.getLocation().getChunk().toString(),p.getName(),args[1],p.getWorld().toString());
                        } else
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + durumVeAciklama[1]);
                    }
                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim devret oyuncuismi");
            }

        }


        return true;
    }
}



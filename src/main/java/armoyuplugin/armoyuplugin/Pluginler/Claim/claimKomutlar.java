package armoyuplugin.armoyuplugin.Pluginler.Claim;

import armoyuplugin.armoyuplugin.Pluginler.Claim.ClaimListesi.ArsaBilgiLink;

import armoyuplugin.armoyuplugin.Pluginler.Claim.menuler.AnaMenu;

import armoyuplugin.armoyuplugin.Servisler.CommandService.ClaimCommandsService;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.*;

public class claimKomutlar implements CommandExecutor {

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
                        System.out.println("1");
                    } else {
                        if (args[2].equals("heryer")) {
                            claimCommandsService.hissedarEkleHeryer(p, oyuncuAdiVeParola, args[3]);
                            System.out.println("2");
                        }
                    }
                } else if (args[1].equals("sil")) {
                    if (args.length == 3) {
                        claimCommandsService.hissedarSil(p, oyuncuAdiVeParola, args[2]);
                        System.out.println("3");
                    } else {
                        if (args[2].equals("heryer")) {
                            claimCommandsService.hissedarSilHeryer(p, oyuncuAdiVeParola, args[3]);
                            System.out.println("4");
                        }
                    }

                }


//            } else if (args[0].equals("untrust")) {
//
//            } else if (args[0].equals("sil")) {
//                if (args.length == 1) {
//
//                        String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsasil","0|0","0|0","2|2",p.getWorld().toString(),p.getLocation().getChunk().toString()};
//                        try{JSONObject json = apiService.readJsonFromUrl(apiService.linkOlustur(linkElemanlar));
//                            if (json.get("durum").toString().equals("0")) {
//                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
//                        } else {
//                            p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
//                            claimListesi.arsaKaldirBir(p, p.getWorld().toString());
//                        }
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//
//
//                } else
//                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim sil");
//            }
//            else if (args[0].equals("heryeri")){
//
//                if (args.length == 2){
//
//                    if (args[1].equals("sil")){
//
//                        String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsasil-heryer","0|0","0|0","2|2",p.getWorld().toString(),p.getLocation().getChunk().toString()};
//                        try{JSONObject json = apiService.readJsonFromUrl(apiService.linkOlustur(linkElemanlar));
//                            if (json.get("durum").toString().equals("0")) {
//                                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
//                            } else {
//                                p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
//                                claimListesi.arsaKaldirHepsi(p.getName());
//                            }
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//
//
//
//                    }
//                    else
//                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryeri sil");
//
//                }else
//                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryeri sil");
//            }
//
//
//
//
//            else if (args[0].equals("aciklama")) {
//                if (args[1].equals("hepsi")){
//                    if (args.length!=2){
//                        String aciklama= "";
//                        for (int i = 1; i < args.length; i++) {
//                            aciklama = aciklama + args[i] + " ";
//                        }
//                        if (aciklama.length()<25){
//                            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsalar","hepsi-aciklama",aciklama,"",""};
//                            try{JSONObject json = apiService.readJsonFromUrl(apiService.linkOlustur(linkElemanlar));
//                                if (json.get("durum").toString().equals("0")) {
//                                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
//                                } else {
//                                    p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
//                                    claimListesi.arsaAciklamaDegisHepsi(p,aciklama);
//                                }
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//
//                        }
//                        else
//                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsa açıklaması 25 karakterden uzun olamaz.");
//                    }else
//                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim aciklama hepsi <aciklama>");
//                }
//                else {
//                    String aciklama= "";
//                    for (int i = 1; i < args.length; i++) {
//                        aciklama = aciklama + args[i] + " ";
//                    }
//                    if (aciklama.length()<25){
//                    String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsalar","aciklama",aciklama,p.getLocation().getChunk().toString(),p.getWorld().toString()};
//                        try{JSONObject json = apiService.readJsonFromUrl(apiService.linkOlustur(linkElemanlar));
//                            if (json.get("durum").toString().equals("0")) {
//                                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
//                            } else {
//                                p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
//                                claimListesi.arsaAciklamaDegisBir(p,aciklama,p.getWorld().toString(),p.getLocation().getChunk().toString());
//                            }
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }}
//                    else
//                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsa açıklaması 25 karakterden uzun olamaz.");
//
//                }
//
//            } else if (args[0].equals("liste")) {
//                //tüm arsalarının konumlarını gösterir.
//            } else if (args[0].equals("rehin")) {
//                if (args.length==1){
//                    claimListesi.arsaRehin(p.getLocation().getChunk().toString(),p.getName(),p.getWorld().toString());
//                }else
//                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim rehin");
//            } else if (args[0].equals("devret")) {
//                if (args.length==2){
//                    String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsalar","deviret",args[1],p.getLocation().getChunk().toString(),p.getWorld().toString()};
//                    try{JSONObject json = apiService.readJsonFromUrl(apiService.linkOlustur(linkElemanlar));
//                        if (json.get("durum").toString().equals("0")) {
//                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
//                        } else {
//                            p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
//                            claimListesi.arsaDevret(p.getLocation().getChunk().toString(),p.getName(),args[1],p.getWorld().toString());
//                        }
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                }else
//                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim devret oyuncuismi");
//            }



            }

        }return true;
    }
}



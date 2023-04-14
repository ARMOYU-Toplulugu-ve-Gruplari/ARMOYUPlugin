package armoyuplugin.armoyuplugin.ClaimPlugin.Komutlar;

import armoyuplugin.armoyuplugin.ClaimPlugin.ClaimListesi.Link;

import armoyuplugin.armoyuplugin.ClaimPlugin.menuler.AnaMenu;
import armoyuplugin.armoyuplugin.Services.JsonServices.JsonService;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.yeniListe;
import static armoyuplugin.armoyuplugin.ARMOYUPlugin.jsonService;

public class claimKomutlar implements CommandExecutor {



    String APIKEY = "771df488714111d39138eb60df756e6b";
    String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";





    ////////////////////////////JSON ÇEKME FONKSİYON BAŞLANGIÇ////////////////////////////
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
////////////////////////////JSON ÇEKME FONKSİYON BİTTİ////////////////////////////

    ////////////////////////////MD5 HASH KOD BAŞLANGIÇ////////////////////////////
    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }















    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {  return true;   }

        Player p = (Player) sender;







        if (cmd.getName().equalsIgnoreCase("claim")) {
            String oyuncuAdiVeParola[] = jsonService.getOyuncuAdiVeParola(p);
            System.out.println(oyuncuAdiVeParola[0]+" "+oyuncuAdiVeParola[1]);

            if (args.length == 0) {
                try {
                    MenuManager.openMenu(AnaMenu.class, p);
                } catch (MenuManagerException | MenuManagerNotSetupException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equals("al")) {

                int claimkontrol = 0;


                if (args.length == 1) {

                    Link temp = yeniListe.head;
                    while (temp != null) {
                        for (int i = 0; i < temp.trustlar.size(); i++) {
                            if (temp.trustlar.get(i).arsaKonum.equals(p.getPlayer().getLocation().getChunk().toString())) {
                                claimkontrol++;
                            }

                        }

                        temp = temp.next;
                    }
                    if (claimkontrol != 0) {
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Bu arsa sahipli");
                        return true;
                    } else {

                        try {


                            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncuAdiVeParola[0] + "/" + oyuncuAdiVeParola[1] + "/arsaal" + "/" + 0 + "|" + 0 + "/" + 0 + "|" + 0 + "/2|2/" + p.getLocation().getWorld() + "/" + p.getLocation().getChunk());
                            if (json.get("durum").equals("0")) {
                                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                            } else {
                                p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
                                yeniListe.claimAl(p.getPlayer().getLocation().getChunk().toString(), p.getName(), p.getWorld().toString());
                            }
                        } catch (IOException e) {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Sunucu ile bağlantı sorunu!");
                        }


                        return true;
                    }
                } else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Doğru kullanım şekli /claim al");
            } else if (args[0].equals("trust")) {
                if (args.length == 2) {
                    try {

                        JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncuAdiVeParola[0] + "/" + oyuncuAdiVeParola[1] + "/hissedar/ekle/" + p.getWorld() + "/" + p.getLocation().getChunk() + "/" + args[1]);
                        if (json.get("durum").toString().equals("1")) {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
                            yeniListe.birTrustVer(p.getPlayer().getLocation().getChunk().toString(), p.getName(), args[1], p.getWorld().toString());
                        } else
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim trust oyuncuismi");
            } else if (args[0].equals("untrust")) {
                if (args.length == 2) {

                    try {

                        JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncuAdiVeParola[0] + "/" + oyuncuAdiVeParola[1] + "/hissedar/sil-heryer/" + p.getWorld() + "/" + p.getLocation().getChunk() + "/" + args[2]);
                        if (json.get("durum").toString().equals("1")) {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
                            yeniListe.removeTrust(p.getPlayer().getLocation().getChunk(), p, args[1], p.getWorld().toString());
                        } else
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claim untrust oyuncuismi");
            } else if (args[0].equals("sil")) {
                if (args.length == 1) {
                    try {

                        JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncuAdiVeParola[0] + "/" + oyuncuAdiVeParola[1] + "/arsasil" + "/" + 0 + "|" + 0 + "/" + 0 + "|" + 0 + "/2|2/" + p.getLocation().getWorld() + "/" + p.getLocation().getChunk());
                        if (json.get("durum").toString().equals("0")) {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                        } else {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
                            yeniListe.deleteOneChunk(p, p.getWorld().toString());
                        }
                    } catch (IOException e) {
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Sunucu bağlantı kurulammadı");
                    }


                } else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim sil");
            }
            else if (args[0].equals("heryeri")){

                if (args.length == 2){

                    if (args[1].equals("sil")){

                                try {

                                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/"+oyuncuAdiVeParola[0]+"/"+oyuncuAdiVeParola[1]+"/arsasil-heryer"+"/"+0+"|"+0+"/"+0+"|"+0+"/2|2/"+p.getLocation().getWorld()+"/"+p.getLocation().getChunk());
                                    if (json.get("durum").toString().equals("0")){
                                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                                    }else{
                                        p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
                                        yeniListe.claimSilHepsi(p);
                                    }
                                } catch (IOException e) {
                                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Sunucu Bağlantı hatası!");
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

                        try {

                                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/"+oyuncuAdiVeParola[0]+"/"+oyuncuAdiVeParola[1]+"/hissedar/ekle-heryer/"+p.getWorld()+"/"+p.getLocation().getChunk()+"/"+args[2]);
                                    if (json.get("durum").toString().equals("1")){
                                        p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
                                        yeniListe.trustVerHepsi(p.getName(), args[2]);
                                    }else
                                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());



                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }else
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryere ekle <oyuncuismi>");
                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryere ekle <oyuncuismi>");
            }


            else if(args[0].equals("heryerden")){
                if (args.length == 3){
                    if (args[1].equals("cikar")){
                        try {

                                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/"+oyuncuAdiVeParola[0]+"/"+oyuncuAdiVeParola[1]+"/hissedar/sil-heryer/"+p.getWorld()+"/"+p.getLocation().getChunk()+"/"+args[2]);
                                    if (json.get("durum").toString().equals("1")){
                                        p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString());
                                        yeniListe.trustSilHepsi(p.getName(),args[2]);
                                    }else
                                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());



                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }else
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryerden cikar <oyuncuismi>");
                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanim /claim heryerden cikar <oyuncuismi>");

            }


            else if (args[0].equals("aciklama")) {
                if (args.length == 1 || args.length ==0){
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Örnek kullanım /claimaciklama <aciklama>");
                }else {
                    yeniListe.arsaAciklamaDegisme(p,args);
                }
            }

        }


        return true;
    }
}



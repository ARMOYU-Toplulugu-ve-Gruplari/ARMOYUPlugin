package armoyuplugin.armoyuplugin;

import armoyuplugin.armoyuplugin.models.Players;
import armoyuplugin.armoyuplugin.utils.JsonUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class Komutlar  implements CommandExecutor {
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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player oyuncu = (Player) sender;


        if (cmd.getName().equalsIgnoreCase("heal")) {
            double maxHealth = oyuncu.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
            oyuncu.setHealth(maxHealth);
        }

        if (cmd.getName().equalsIgnoreCase("feed")) {
            oyuncu.setFoodLevel(20);
        }

        if (cmd.getName().equalsIgnoreCase("giris")) {

            if (args.length != 1) {
                oyuncu.sendMessage(ChatColor.RED +"[ARMOYU] " +ChatColor.YELLOW + "Hatalı Kullanım Yaptınız");
            }else{

                try {
                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/c99e178d83cdfea3c167bc1d15f9b47ff8f80145/"+oyuncu.getDisplayName()+"/"+args[0]+"/0/0/0/");
                    System.out.println(json.get("kontrol"));

                    if (json.get("kontrol").equals("1")){

                        JsonUtility.updateNote(oyuncu.getDisplayName() ,json.get("varsaygrupkisa").toString(),true);

                        try {
                            JsonUtility.saveNotes();

                            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }
                            List<Players> findAllNotes = JsonUtility.findAllNotes();
                            for (int i = 0; i < findAllNotes.size(); i++) {
                                Players oyuncucek = findAllNotes.get(i);
                                if(oyuncucek.getOyuncuadi().equals(oyuncu.getDisplayName())){

                                    if (oyuncucek.getX() == 0.0 && oyuncucek.getY() == 0.0 && oyuncucek.getZ() == 0.0){
                                        oyuncu.teleport(new Location(Bukkit.getWorld(oyuncucek.getLocation()),-8,76,-8));
                                    }else{
                                        oyuncu.teleport(new Location(Bukkit.getWorld(oyuncucek.getLocation()),oyuncucek.getX(),oyuncucek.getY(),oyuncucek.getZ()));
                                    }

                                    int aclik = (int) oyuncucek.getAclik();
                                    oyuncu.setFoodLevel(aclik);
                                    oyuncu.setHealth(oyuncucek.getSaglik());
                                }
                            }
                        } catch (IOException ERR) {
                            System.out.println("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
                            ERR.printStackTrace();
                        }
                        oyuncu.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "Giriş Başarılı");

                    }else {
                        oyuncu.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "Hatalı GİRİŞ");

                    }

                }catch (Exception aa){
                    System.out.println(ChatColor.RED +"[ARMOYU] " +"Sunucu Bağlanısı Kurulamadı");
                    oyuncu.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "Sunucu ile Bağlantı Kurulamadı");
                }

            }
            return true;
        }
        return true;

    }
}
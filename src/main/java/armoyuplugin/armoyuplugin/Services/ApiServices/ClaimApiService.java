package armoyuplugin.armoyuplugin.Services.ApiServices;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.yeniListe;

public class ClaimApiService {
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
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        } finally {
            is.close();
        }
    }

    public String linkOlustur(String[] linkDizi){
        String APIKEY = "771df488714111d39138eb60df756e6b/";
        StringBuilder link = new StringBuilder("https://aramizdakioyuncu.com/botlar/" + APIKEY);
        for (String s : linkDizi) {
            link.append(s).append("/");
        }
           return link.toString();
    }


    public String[] getDurumVeAciklama(Player p,String[] linkElemanlar){
        //Dönüş olarak sadece durum ve açiklama aldığımız linkler için kullanılır
            String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";
            String link = linkOlustur(linkElemanlar);
            try {
                JSONObject json = readJsonFromUrl(link);
                return new String[]{json.get("durum").toString(),json.get("aciklama").toString()};
            } catch (IOException e) {
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Sunucu ile bağlantı sorunu!");
        }
            return new String[]{"null","null"};
    }





    public void listeyiDoldur(){
        //Server çalıştırıldığına Claim LinkListini siteden gelen verilerle doldurur.

        try {
            String[] linkDizi = {"deneme","deneme","arsalar","0","0"};
            String link = linkOlustur(linkDizi);

            JSONObject json = readJsonFromUrl(link);

            if (!json.get("arsalar").toString().equals("null")) {
                JSONArray recs = json.getJSONArray("arsalar");
                for (int i = 0; i < recs.length(); ++i) {
                    JSONObject rec = recs.getJSONObject(i);
                    yeniListe.insertToHead(rec.get("oyuncuadi").toString());

                    if (!rec.get("arsalarim").equals("null")) {
                        JSONArray recsTwo = rec.getJSONArray("arsalarim");
                        for (int j = 0; j < recsTwo.length(); j++) {
                            JSONObject recTwo = recsTwo.getJSONObject(j);
                            yeniListe.buyClaim(recTwo.get("arsachank").toString(), rec.get("oyuncuadi").toString(), recTwo.get("arsadunya").toString());

                            JSONArray recsThree = recTwo.getJSONArray("hissedarlar");
                            for (int k = 0; k < recsThree.length(); k++) {
                                JSONObject recThree = recsThree.getJSONObject(k);
                                yeniListe.giveTrustForOneChunk(recTwo.get("arsachank").toString(), rec.get("oyuncuadi").toString(), recThree.get("oyuncuadi").toString(), recTwo.get("arsadunya").toString());
                            }

                        }
                    }

                }
            }
        } catch (IOException e) {
            Bukkit.getLogger().info("Catch claimOnEnable");
        }
    }
}

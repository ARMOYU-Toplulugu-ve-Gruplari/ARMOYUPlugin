package armoyuplugin.armoyuplugin.Services.ApiServices;

import org.bukkit.Bukkit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

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
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public String linkOlustur(String[] linkDizi){
        String APIKEY = "771df488714111d39138eb60df756e6b/";
        String link = "https://aramizdakioyuncu.com/botlar/"+ APIKEY;
        for (int i = 0; i < linkDizi.length; i++) {
            link+=linkDizi[i]+"/";
        }
           return link;
    }






    public void listeyiDoldur(){

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
                            yeniListe.claimAl(recTwo.get("arsachank").toString(), rec.get("oyuncuadi").toString(), recTwo.get("arsadunya").toString());

                            JSONArray recsThree = recTwo.getJSONArray("hissedarlar");
                            for (int k = 0; k < recsThree.length(); k++) {
                                JSONObject recThree = recsThree.getJSONObject(k);
                                yeniListe.birTrustVer(recTwo.get("arsachank").toString(), rec.get("oyuncuadi").toString(), recThree.get("oyuncuadi").toString(), recTwo.get("arsadunya").toString());
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

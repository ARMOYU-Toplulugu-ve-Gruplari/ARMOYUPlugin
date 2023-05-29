package armoyuplugin.armoyuplugin.Servisler.ApiServices;

import armoyuplugin.armoyuplugin.Pluginler.Claim.ClaimListesi.ArsaBilgiLink;
import armoyuplugin.armoyuplugin.Pluginler.Klan.KlanListesi.KlanBilgiLink;
import armoyuplugin.armoyuplugin.Pluginler.Klan.KlanListesi.KlanRutbeleri;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.claimListesi;
import static armoyuplugin.armoyuplugin.ARMOYUPlugin.klanListesi;

public class ApiService {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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


    public String[] getDurumVeAciklama(Player p, String[] linkElemanlar){
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

    public void claimListesiniDoldur(){
        //Server çalıştırıldığına Claim LinkListini siteden gelen verilerle doldurur.
        try {
            String[] linkDizi = {"deneme","deneme","arsalar","0","0"};
            String link = linkOlustur(linkDizi);
            String arsaOyuncuAdi = "";
            String arsaKlanAdi = "";
            String arsaAciklamasi = "Arsa açıklaması";
            JSONObject json = readJsonFromUrl(link);

            if (!json.get("icerik").toString().equals("null")) {
                JSONArray recs = json.getJSONArray("icerik");
                for (int i = 0; i < recs.length(); ++i) {
                    JSONObject rec = recs.getJSONObject(i);
                    if (!rec.isNull("arsaoyuncuadi")){
                        arsaOyuncuAdi = rec.get("arsaoyuncuadi").toString();
                    }
                    if (!rec.isNull("arsaklanadi")){
                        arsaKlanAdi = rec.get("arsaklanadi").toString();
                    }
                    if (!rec.isNull("arsaaciklama")){
                        arsaAciklamasi = rec.get("arsaaciklama").toString();
                    }

                    claimListesi.arsaAlSite(rec.get("arsachunk").toString(),arsaOyuncuAdi,rec.get("arsadunya").toString(),arsaKlanAdi,arsaAciklamasi);

                    JSONArray recsTwo = rec.getJSONArray("hissedarlar");

                    for (int k = 1; k < recsTwo.length(); k++) {
                        JSONObject recThree = recsTwo.getJSONObject(k);
                        claimListesi.hissedarlaraEkleBir(rec.get("arsachunk").toString(), rec.get("arsaoyuncuadi").toString(), recThree.get("oyuncuadi").toString(), rec.get("arsadunya").toString());
                    }
                }
            }


//            ArsaBilgiLink temp = claimListesi.head;
//            while (temp != null){
//                System.out.println(temp.arsaChunk);
//                for (int i = 0; i < temp.hissedarlar.size(); i++) {
//                    System.out.println(temp.hissedarlar.get(i));
//                }
//                temp = temp.next;
//            }
//            System.out.println("------------------------------------------------------------------------------------");

        } catch (IOException e) {
            Bukkit.getLogger().info("Catch claimOnEnable");
        }
    }
    public void klanListesiniDoldur() {

        try {
            String[] linkDizi = {"deneme", "deneme", "klanlar", "0", "0"};
            String link = linkOlustur(linkDizi);

            JSONObject json = readJsonFromUrl(link);

            if (!json.get("klanlar").toString().equals("null")) {

                JSONArray recs = json.getJSONArray("klanlar");
                for (int i = 0; i < recs.length(); ++i) {
                    JSONObject rec = recs.getJSONObject(i);
                    KlanBilgiLink klan = klanListesi.apiKlanOlustur(rec.get("klankurucu").toString(),rec.get("klanadi").toString());


                    JSONArray recsTwo = rec.getJSONArray("klanrutbeler");
                    for (int j = 0; j < recsTwo.length(); j++) {
                        JSONObject recTwo = recsTwo.getJSONObject(j);
                        KlanRutbeleri rutbe = klanListesi.rutbeOlustur(recTwo.get("rutbeadi").toString(),(int)recTwo.get("rutbesira"),(int)recTwo.get("davet"),(int)recTwo.get("kurucu"),1,(int)recTwo.get("uyeduzenle"));
                        klan.klanRutbeleri.add(rutbe);
                    }

                    if (!rec.get("klanoyuncular").toString().equals("null")){
                    JSONArray recsThree = rec.getJSONArray("klanoyuncular");
                    for (int j = 0; j < recsThree.length(); j++) {
                        JSONObject recTwo = recsThree.getJSONObject(j);
                        klanListesi.klanaOyuncuEkle(recTwo.get("mcuyeadi").toString(),klanListesi.rutbeBul(recTwo.get("mcuyerolu").toString(),klan),klan);
                    }
                    }
                }
            }

//            KlanBilgiLink temp = klanListesi.head;
//            while (temp !=null){
//                System.out.println(temp.klanAdi);
//                System.out.println();
//                for (int i = 0; i < temp.klanRutbeleri.size(); i++) {
//                    System.out.println(temp.klanRutbeleri.get(i).rutbeAdi);
//                }
//                System.out.println();
//                for (int i = 0; i < temp.klanUyeleri.size(); i++) {
//                    System.out.println(temp.klanUyeleri.get(i).oyuncuAdi);
//                    System.out.println(temp.klanUyeleri.get(i).rutbe.rutbeAdi);
//                }
//                temp = temp.next;
//            }
        } catch(IOException e){
            Bukkit.getLogger().info("Catch claimOnEnable");
        }

    }
}
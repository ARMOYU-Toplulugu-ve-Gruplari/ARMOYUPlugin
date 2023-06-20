package armoyuplugin.armoyuplugin.Servisler.ApiServices;

import armoyuplugin.armoyuplugin.Listeler.KlanListesi.KlanBilgiLink;
import armoyuplugin.armoyuplugin.Listeler.KlanListesi.KlanRutbeleri;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.*;

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
    public JSONObject postYolla(String Link,JSONObject yollancaklar){

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(Link);
            final List<NameValuePair> nameValuePairs = new ArrayList<>();
            for (int i = 0; i < yollancaklar.names().length(); i++) {
                nameValuePairs.add(new BasicNameValuePair(yollancaklar.names().get(i).toString(),yollancaklar.get(yollancaklar.names().get(i).toString()).toString()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            try (final CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

                return new JSONObject(responseBody);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
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
                        claimListesi.hissedarEkleSite(rec.get("arsachunk").toString(), rec.get("arsaoyuncuadi").toString(), recThree.get("oyuncuadi").toString(), rec.get("arsadunya").toString());
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

            if (!json.isNull("icerik")) {

                JSONArray recs = json.getJSONArray("icerik");
                for (int i = 0; i < recs.length(); ++i) {
                    JSONObject rec = recs.getJSONObject(i);
                    KlanBilgiLink klan = klanListesi.apiKlanOlustur(rec.get("klankurucu").toString(),rec.get("klanadi").toString(),rec.get("klanaciklama").toString());


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
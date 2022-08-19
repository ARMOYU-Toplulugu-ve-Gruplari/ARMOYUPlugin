package armoyuplugin.armoyuplugin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.ChatColor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class jsontest {

    public static void main(String[] args) {


        try {
            String url = "https://aramizdakioyuncu.com/botlar/c99e178d83cdfea3c167bc1d15f9b47ff8f80145/antikor/antikor/0/0/0/";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : "+ url);
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();
            // System.out.println(response);

            int kontrol = response.charAt(12);
            if (kontrol == 49){
//                oyuncu.sendMessage(ChatColor.RED +"[ARMOYU]" + ChatColor.GREEN + "Giriş Başarılı");
                System.out.println("Başarılı");
            }else {
//                oyuncu.sendMessage(ChatColor.RED +"[ARMOYU]" + ChatColor.YELLOW + "Hatalı GİRİŞ");
                System.out.println("Hatalı");


            }
        }catch (Exception e){System.out.println(ChatColor.RED +"[ARMOYU]" +"Sunucu Bağlanısı Kurulamadı");}



//        try {
//
//           File input = new File("C:/Users/berka/IdeaProjects/ARMOYUPlugin/src/main/java/armoyuplugin/armoyuplugin/user.json");
//           JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
//           JsonObject fileObject = fileElement.getAsJsonObject();
//
//           String name = fileObject.get("name").getAsString();
//           String email = fileObject.get("email").getAsString();
//
//
//           System.out.println(name);
//           System.out.println(email);
//
//        } catch (FileNotFoundException e) {
////            e.printStackTrace();
//        }
    }
}


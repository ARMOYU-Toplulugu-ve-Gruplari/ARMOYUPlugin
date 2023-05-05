package armoyuplugin.armoyuplugin.Servisler.TxtServices.utils;


import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.Servisler.TxtServices.models.Players;
import com.google.gson.Gson;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtility {

    private static ArrayList<Players> notes = new ArrayList<Players>();

    public static Players createNote(Player p, boolean hareket, int aclik, int saglik) {
        String k = "world";
        if (p.getLocation().toString().contains("world_nether")) {
            k = "world_nether";
        } else if (p.getLocation().toString().contains("world_end")) {
            k = "world_end";
        }
        Players note = new Players(p.getDisplayName(), "", 0, "", "", "" ,"" ,0,hareket, aclik, saglik, -8, 76, -8, k);
        notes.add(note);
        return note;
    }


    public static List<Players> findAllNotes() {
        return notes;
    }

    public static void loadNotes() throws IOException {

        Gson gson = new Gson();
        File file = new File(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/oyuncular.json");
        if (file.exists()) {
            Reader reader = new FileReader(file);
            Players[] n = gson.fromJson(reader, Players[].class);
            notes = new ArrayList<>(Arrays.asList(n));
        }

    }

//    public static void loadNotesxyz() throws IOException {
//
//        Gson gson = new Gson();
//        File file = new File(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/oyuncular.json");
//        if (file.exists()) {
//            Reader reader = new FileReader(file);
//            Playerxyz[] n = gson.fromJson(reader, Playerxyz[].class);
//            notes1 = new ArrayList<>(Arrays.asList(n));
//        }
//
//    }

    public static void saveNotes() throws IOException {


        try{
            Gson gson = new Gson();
            File file = new File(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/oyuncular.json");
            file.getParentFile().mkdir();
            file.createNewFile();
            Writer writer = new FileWriter(file, false);
            gson.toJson(notes, writer);
            writer.flush();
            writer.close();
        }catch(Exception e){
            System.out.println(ChatColor.DARK_RED + "[ARMOYU] Veri Güncelleme Hatası.:"+ e);

        }


    }

    public static Players updateNote(String oyuncuadi, String oyuncuparola, String klan, String klanrutbe,String klanrenk,int leslerim, boolean hareket, int para) {
        for (Players note : notes) {
            if (note.getOyuncuadi().equals(oyuncuadi)) {
                note.setOyuncuparola(oyuncuparola);
                note.setKlan(klan);
                note.setKlanrutbe(klanrutbe);
                note.setKlanrenk(klanrenk);
                note.setLeslerim(leslerim);
                note.setHareket(hareket);
                note.setPara(para);
            }
        }
        return null;
    }

    public static Players updateNotexyz(String oyuncuadi, int para, boolean hareket, double aclik, double saglik, double x, double y, double z, String location) {

        for (Players note : notes) {
            if (note.getOyuncuadi().equals(oyuncuadi)) {
                note.setHareket(hareket);
                note.setPara(para);
                note.setAclik(aclik);
                note.setSaglik(saglik);
                note.setX(x);
                note.setY(y);
                note.setZ(z);
                note.setLocation(location);
            }
        }
        return null;
    }

    public static Players updatereload() {

        try { JsonUtility.loadNotes(); } catch (IOException e) {  System.out.println("[ARMOYU] Oyuncu hareketleri okunamadı!");   }

        for (Players note : notes) {
            note.setHareket(false);

        }
        return null;
    }

    public static Players updateevayarla(String oyuncuadi,String evkordinat) {
        for (Players oyuncubul : notes) {
            if (oyuncubul.getOyuncuadi().equals(oyuncuadi)) {
                oyuncubul.setEv(evkordinat);
            }
        }
        return null;
    }

    public static Players updatepara(String oyuncuadi,int para) {
        for (Players oyuncubul : notes) {
            if (oyuncubul.getOyuncuadi().equals(oyuncuadi)) {
                oyuncubul.setPara(para);
            }
        }
        return null;
    }

    public static Players updateles(String oyuncuadi,int les) {
        for (Players oyuncubul : notes) {
            if (oyuncubul.getOyuncuadi().equals(oyuncuadi)) {
                oyuncubul.setLeslerim(les);
            }
        }
        return null;
    }

    public static Players updateklan(String oyuncuadi, String klan,String klanrutbe) {
        for (Players oyuncubul : notes) {
            if (oyuncubul.getOyuncuadi().equals(oyuncuadi)) {
                oyuncubul.setKlan(klan);
                oyuncubul.setKlanrutbe(klanrutbe);
            }
        }
        return null;
    }
}
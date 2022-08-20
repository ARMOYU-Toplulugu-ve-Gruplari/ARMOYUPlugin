package armoyuplugin.armoyuplugin.utils;


import armoyuplugin.armoyuplugin.ARMOYUPlugin;

import armoyuplugin.armoyuplugin.models.Players;

import armoyuplugin.armoyuplugin.models.Playerxyz;
import com.google.gson.Gson;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtility {

    private static ArrayList<Players> notes = new ArrayList<Players>();
    private static ArrayList<Playerxyz> notes1 = new ArrayList<Playerxyz>();

    public static Players createNote(Player p, String mesaj,boolean hareket){
        String k = "world";
        if (p.getLocation().toString().contains("world_nether")){
            k = "world_nether";
        }
        else if (p.getLocation().toString().contains("world_end")){
            k = "world_end";
        }
        Players note = new Players(p.getDisplayName(), mesaj,hareket,00,00,00,k);
        notes.add(note);
        return note;
    }


    public static List<Players> findAllNotes(){
        return notes;
    }

    public static void loadNotes() throws IOException {

        Gson gson = new Gson();
        File file = new File(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/oyuncular.json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            Players[] n = gson.fromJson(reader, Players[].class);
            notes = new ArrayList<>(Arrays.asList(n));
        }

    }
    public static void loadNotesxyz() throws IOException {

        Gson gson = new Gson();
        File file = new File(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/oyuncular.json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            Playerxyz[] n = gson.fromJson(reader, Playerxyz[].class);
            notes1 = new ArrayList<>(Arrays.asList(n));
        }

    }
    public static void saveNotes() throws IOException {

        Gson gson = new Gson();
        System.out.println(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath());
        File file = new File(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/oyuncular.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(notes, writer);
        writer.flush();
        writer.close();
        System.out.println("[ARMOYU] Veri Güncellendi.");

    }
    public static void saveNotesxyz() throws IOException {

        Gson gson = new Gson();
        System.out.println(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath());
        File file = new File(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/oyuncular.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(notes1, writer);
        writer.flush();
        writer.close();
        System.out.println("[ARMOYU] XYZ Veri Güncellendi.");

    }
    public static Players updateNote(String oyuncuadi,boolean hareket){
        for (Players note : notes) {
            if (note.getOyuncuadi().equalsIgnoreCase(oyuncuadi)) {
                note.setHareket(hareket);
            }
        }
        return null;
    }

    public static Playerxyz updateNotexyz(String oyuncuadi,boolean hareket, double x, double y , double z, String location){
        for (Playerxyz note1 : notes1) {
            System.out.println("----------------------");

            if (note1.getOyuncuadi().equalsIgnoreCase(oyuncuadi)) {
                note1.setHareket(hareket);
                note1.setX(x);
                note1.setY(y);
                note1.setZ(z);
                note1.setLocation(location);

            }
        }
        return null;
    }
}
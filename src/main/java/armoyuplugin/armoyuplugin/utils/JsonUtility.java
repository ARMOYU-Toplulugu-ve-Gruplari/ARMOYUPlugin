package armoyuplugin.armoyuplugin.utils;


import armoyuplugin.armoyuplugin.ARMOYUPlugin;

import armoyuplugin.armoyuplugin.models.Note;
import armoyuplugin.armoyuplugin.models.Players;
import com.google.gson.Gson;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtility {

    private static ArrayList<Players> notes = new ArrayList<Players>();

    public static Players createNote(Player p, String mesaj,boolean hareket){

        Players note = new Players(p.getDisplayName(), mesaj,hareket);
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
            System.out.println("Notes loaded.");
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
        System.out.println("Notes saved.");

    }

    public static Players updateNote(String oyuncuadi,boolean hareket){
        for (Players note : notes) {
            if (note.getOyuncuadi().equalsIgnoreCase(oyuncuadi)) {
                note.setHareket(hareket);
            }
        }
        return null;
    }

}
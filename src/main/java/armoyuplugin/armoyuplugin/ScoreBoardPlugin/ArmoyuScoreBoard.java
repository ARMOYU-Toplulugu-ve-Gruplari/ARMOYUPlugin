package armoyuplugin.armoyuplugin.ScoreBoardPlugin;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.Services.JsonServices.models.Players;
import armoyuplugin.armoyuplugin.Services.JsonServices.utils.JsonUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.yeniListe;

public class ArmoyuScoreBoard {
    public void setScoreBoard(ARMOYUPlugin plugin){

        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
        String oyuncuadi = "";
        String oyuncuklanadi = "";
        String oyuncuklanrutbe = "";
        int oyuncupara = 0;
        int oyunculeslerim=0;




        for (Player player : plugin.getServer().getOnlinePlayers()){

            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

            List<Players> findAllNotes = JsonUtility.findAllNotes();
            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);
                if(oyuncucek.getOyuncuadi().equals(player.getName())){

                    oyuncuadi=oyuncucek.getOyuncuadi();
                    oyuncuklanadi=oyuncucek.getKlan();
                    oyuncuklanrutbe=oyuncucek.getKlanrutbe();
                    oyuncupara=oyuncucek.getPara();
                    oyunculeslerim=oyuncucek.getLeslerim();

                    break;
                }
            }

            ScoreboardManager m = Bukkit.getScoreboardManager();
            Scoreboard b = m.getNewScoreboard();

            Objective o = b.registerNewObjective("ARMOYU TEST SERVER", "ANA","");


            String claim = yeniListe.claimSahipKim(player.getLocation().getChunk().toString(),player.getWorld().toString());


            o.setDisplaySlot(DisplaySlot.SIDEBAR);

            o.setDisplayName(ChatColor.DARK_AQUA + o.getName());

            Score slotoyuncuadi = o.getScore(ChatColor.YELLOW + "Oyuncu Adı: " + ChatColor.WHITE + oyuncuadi );

            Score slotpara = o.getScore(ChatColor.YELLOW + "Para: " + ChatColor.WHITE + oyuncupara );

            Score slotles = o.getScore(ChatColor.YELLOW + "Leş: "+ ChatColor.WHITE+oyunculeslerim);

            Score slotClaim = o.getScore(ChatColor.WHITE + "Arazi ->" + ChatColor.WHITE + claim);

            Score slotbosluk1 = o.getScore(" ");

            Score slotbosluk2 = o.getScore("  ");

            Score slotbosluk3 = o.getScore("   ");

            Score slotbosluk4 = o.getScore("    ");

            Score slotbosluk5 = o.getScore("     ");

            Score slotreklam = o.getScore(ChatColor.YELLOW + "§laramizdakioyuncu.com");

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();

            Score slotzaman = o.getScore(ChatColor.YELLOW + formatter.format(date));




            slotClaim.setScore(11);
            slotbosluk1.setScore(10);
            slotoyuncuadi.setScore(9);
            slotbosluk2.setScore(8);
            slotpara.setScore(7);
            slotles.setScore(6);
            slotbosluk3.setScore(5);

            if (!oyuncuklanadi.equals("")){

                Score slotklanadi = o.getScore(ChatColor.DARK_AQUA + "Klan: " + ChatColor.GOLD+ oyuncuklanadi );
                Score slotklanrutbe = o.getScore(ChatColor.DARK_AQUA  + "Rütbe: " + ChatColor.DARK_GREEN+ oyuncuklanrutbe );

                slotklanadi.setScore(4);
                slotklanrutbe.setScore(3);
            }

            slotbosluk4.setScore(2);
            slotzaman.setScore(1);
            slotbosluk5.setScore(0);
            slotreklam.setScore(-1);





            player.setScoreboard(b);




            Team team = scoreboard.getTeam(player.getName());


            if (team == null) {
                team = scoreboard.registerNewTeam(player.getName());
            }



            String klanad = "";

            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);

                if(oyuncucek.getOyuncuadi().equals(player.getName())){

                    if(oyuncucek.getHareket() == false){
                        player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "giriş yapmak için /giris <sifre>");
                        return;
                    }

                    if (oyuncucek.getKlan().equals("")){
                        klanad = "";

                    }else {

                        klanad = ChatColor.GOLD + "[" + oyuncucek.getKlan() + "] ";

                    }

                    team.setSuffix(ChatColor.GREEN + " " + oyuncucek.getKlanrutbe());
                    team.setPrefix(klanad);

                    player.setDisplayName(klanad + ChatColor.WHITE + player.getName() + " " + ChatColor.DARK_GREEN + oyuncucek.getKlanrutbe());
                    player.setPlayerListName(klanad + ChatColor.WHITE + player.getName() + " " + ChatColor.DARK_GREEN + oyuncucek.getKlanrutbe());

                    team.addEntry(player.getName());
                }
            }
        }


    }
    }


package armoyuplugin.armoyuplugin.ClaimPlugin.ClaimListesi;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.time.Duration;

public class LinkList {
    public Link head;
    public Link tail;

    public LinkList() {
        head = null;
        tail = null;
    }

    public void insertToHead(String pName) {
        Link current = head;
        int varmi = 0;
        while (current != null) {
            if (current.oyuncu.equals(pName)){
                varmi = 1;
                break;
            }
            current = current.next;
        }
        if (varmi == 0){
        Link newLink = new Link();
        newLink.oyuncu = pName;

        newLink.next = head; //mevcut head yeni kaydın nexti
        head = newLink; //yeni head newLinki gösterir
               }
    }
    public void buyClaim(String claim, String pName,String dunya) {
        Link current = listedeOyuncuBul(pName);
        if (current != null){
                TrustLink newLink = new TrustLink();
                newLink.arsaKonum = claim;
                newLink.arsaDunya = dunya;
                newLink.arsaBahset=0;
                newLink.trustVerilenler.add(pName);
                current.trustlar.add(newLink);
            }

    }

    public void removeTrust(String chunk,String silen,String silinen,String dunya){
        int trustDurum;
        Link current = listedeOyuncuBul(silen);
        if (current != null) {
            if (!silen.equals(silinen)) {
                    int i = oyuncuArsaBul(current,chunk,dunya);
                    if (i != -1) {
                        trustDurum = trustuVarmi(silinen,silen,dunya,chunk);
                        if (trustDurum != 0) {
                            current.trustlar.get(i).trustVerilenler.remove(silinen);
                        }
                    }
                }
            }
        }

    public void giveTrustForOneChunk(String chunk,String ekleyen, String eklenen,String dunya){
        int trustDurum;
        int i;
        Link current = listedeOyuncuBul(ekleyen);
        if (current != null) {
            i = oyuncuArsaBul(current,chunk,dunya);
            if (i !=-1) {
                trustDurum = trustuVarmi(eklenen,ekleyen,dunya,chunk);
                if (trustDurum == 0) {
                    current.trustlar.get(i).trustVerilenler.add(eklenen);
                }
            }
        }
    }

    public void giveTrustForAll(String ekleyen,String eklenen){
        int trustDurum;
        Link current = listedeOyuncuBul(ekleyen);
        if (current != null) {
            for (int i = 0; i < current.trustlar.size(); i++) {
                trustDurum = trustuVarmi(eklenen,ekleyen,current.trustlar.get(i).arsaDunya,current.trustlar.get(i).arsaKonum);
                if (trustDurum == 0) {
                    current.trustlar.get(i).trustVerilenler.add(eklenen);
                }
            }
        }
    }

    public void removeTrustForAll(String ekleyen, String silinen){
        int trustDurum;
        Link current = listedeOyuncuBul(ekleyen);
        if (current != null) {
            for (int i = 0; i < current.trustlar.size(); i++) {
                trustDurum = trustuVarmi(silinen,ekleyen,current.trustlar.get(i).arsaDunya,current.trustlar.get(i).arsaKonum);
                if (trustDurum != 0) {
                    current.trustlar.get(i).trustVerilenler.remove(silinen);
                }
            }
        }
    }


    public void removeClaimForOneChunk(Player silen,String dunya) {
        Link temp = listedeOyuncuBul(silen.getName());
        if (temp != null){
            int i = oyuncuArsaBul(temp,silen.getLocation().getChunk().toString(),dunya);
            if (i != -1){
                temp.trustlar.remove(i);
            }
        }
    }

    public void removeClaimForAll(String silen) {

        Link temp = listedeOyuncuBul(silen);
        if (temp != null){
            temp.trustlar.clear();
        }

    }

    public void dusmanBolgeGiris(Audience target,String arsaSahibi,String arsaAciklamasi) {
        final Component mainTitle = Component.text(arsaSahibi, NamedTextColor.RED);
        final Component subTitle = Component.text(arsaAciklamasi, NamedTextColor.WHITE);

        final Title title = Title.title(mainTitle,subTitle,Title.Times.times(Duration.ofMillis(750),Duration.ofMillis(1500),Duration.ofMillis(750)));

        // Send the title, you can also use Audience#clearTitle() to remove the title at any time
        target.showTitle(title);

    }
    public void dostBolgeGiris(Audience target,String arsaSahibi,String arsaAciklamasi) {
        final Component mainTitle = Component.text(arsaSahibi, NamedTextColor.GREEN);
        final Component subTitle = Component.text(arsaAciklamasi, NamedTextColor.WHITE);

        final Title title = Title.title(mainTitle,subTitle,Title.Times.times(Duration.ofMillis(750),Duration.ofMillis(1500),Duration.ofMillis(750)));

        // Send the title, you can also use Audience#clearTitle() to remove the title at any time
        target.showTitle(title);

    }

    public void bosAraziGiris(Audience target) {
        final Component mainTitle = Component.text("Sahipsiz", NamedTextColor.RED);
        final Component subTitle = Component.text("arazi", NamedTextColor.WHITE);

        final Title title = Title.title(mainTitle,subTitle,Title.Times.times(Duration.ofMillis(750),Duration.ofMillis(1500),Duration.ofMillis(750)));

        // Send the title, you can also use Audience#clearTitle() to remove the title at any time
        target.showTitle(title);

    }
    public void chunkControlOnScreen(Player p, Chunk chunk,String dunya,ARMOYUPlugin plugin){
        int arsadolu = 0;
        int trustvarmi = 0;
        int durmadanYazmasiniEngelleme = 0;
        String arsaSahibi = "";
        String aciklama="default arsa aciklamasi";

        Link temp = head;
        while (temp != null){
            for (int i = 0; i < temp.trustlar.size(); i++) {
                if (temp.trustlar.get(i).arsaDunya.equals(dunya)) {
                    if (temp.trustlar.get(i).arsaKonum.equals(chunk.toString())) {
                        arsaSahibi = temp.oyuncu;
                        break;
                    }
                }
            }
            temp = temp.next;
        }

        temp = head;
        while (temp != null) {
            if (temp.oyuncu.equals(p.getName())){

                    if (temp.oncekiGecilenAraziSahibi.equals(arsaSahibi)) {
                        durmadanYazmasiniEngelleme = 1;
                    } else {
                        durmadanYazmasiniEngelleme = 0;
                        temp.oncekiGecilenAraziSahibi = arsaSahibi;
                    }
            }

            temp = temp.next;
        }
        if (durmadanYazmasiniEngelleme==0) {
            temp = head;
            while (temp != null) {
                for (int i = 0; i < temp.trustlar.size(); i++) {
                    if (temp.trustlar.get(i).arsaDunya.equals(dunya)){
                    if (temp.trustlar.get(i).arsaKonum.equals(chunk.toString())) {
                        for (int j = 0; j < temp.trustlar.get(i).trustVerilenler.size(); j++) {
                            if (temp.trustlar.get(i).trustVerilenler.get(j).equals(p.getName())) {
                                trustvarmi++;
                                break;
                            }
                        }
                        arsaSahibi = temp.trustlar.get(i).trustVerilenler.get(0);
                        aciklama = temp.arsaAciklamasi;
                        arsadolu++;
                        break;
                    }
                    }
                }

                temp = temp.next;
            }
            if (trustvarmi!=0){
                dostBolgeGiris(plugin.getAdventure().player(p),arsaSahibi,aciklama);
            }else if(arsadolu != 0){
                dusmanBolgeGiris(plugin.getAdventure().player(p),arsaSahibi,aciklama);
            }else
                bosAraziGiris(plugin.getAdventure().player(p));
        }

    }

    public void claimNotificationChange(String oyuncuIsmi,String[] diziAciklama){
        String aciklama= "";
        for (int i = 1; i < diziAciklama.length; i++) {
            aciklama = aciklama + diziAciklama[i] + " ";
        }
        Link temp = listedeOyuncuBul(oyuncuIsmi);
        if (temp != null) {
            if (aciklama.length() < 25) {
                temp.arsaAciklamasi = aciklama;
            }
        }


    }

    public int chunkControl(String playerName,String chunk,String dunya){
        int arsadolu = 0;
        int trustDurum = 0;
        int oyuncuArsa;
            Link temp = head;
            while (temp != null) {
                oyuncuArsa = oyuncuArsaBul(temp,chunk,dunya);
                if (oyuncuArsa != -1) {
                    trustDurum = trustuVarmi(playerName, temp.oyuncu, dunya, chunk);
                    arsadolu++;
                    break;
                }
                temp = temp.next;
            }

        if (trustDurum!=0){
            return 1;
        } else if (arsadolu !=0) {
            return 2;
        }
       return 0;
    }
    public String claimWhoOwner(String chunk,String dunya){
        Link temp = head;
        while (temp != null) {
            for (int i = 0; i < temp.trustlar.size(); i++) {
                if (temp.trustlar.get(i).arsaDunya.equals(dunya)) {
                    if (temp.trustlar.get(i).arsaKonum.equals(chunk)) {
                        return temp.trustlar.get(i).trustVerilenler.get(0);
                    }
                }
            }
            temp = temp.next;
        }
        return "Claimsiz";
    }

    public void arsaBahset(String chunk, String oyuncuIsmi,String dunya){
        Link temp = listedeOyuncuBul(oyuncuIsmi);
        if (temp != null){
            int oyuncuArsa = oyuncuArsaBul(temp,chunk,dunya);
            if (oyuncuArsa != -1){
                temp.trustlar.get(oyuncuArsa).arsaBahset=1;
            }
        }
    }

    public void claimTransfer(String chunk, String bagislayan,String bagislanan,String dunya){
                Link temp = listedeOyuncuBul(bagislayan);
                if (temp!=null) {
                    int oyuncuArsa = oyuncuArsaBul(temp, chunk, dunya);
                    if (oyuncuArsa != -1) {
                        temp.trustlar.remove(oyuncuArsa);
                        buyClaim(chunk, bagislanan, dunya);
                    }
                }
    }







    //Genel


    private int trustuVarmi(String kimin,String araziSahibi,String dunya,String chunk){
        Link temp = listedeOyuncuBul(araziSahibi);
        if (temp != null) {
            int i = oyuncuArsaBul(temp, chunk, dunya);
            if (i != -1){
                for (int j = 0; j < temp.trustlar.get(i).trustVerilenler.size(); j++) {
                    if (temp.trustlar.get(i).trustVerilenler.get(j).equals(kimin)){
                        return 1;
                    }
                }
            }
        }
        return 0;
    }




    private int oyuncuArsaBul(Link temp,String chunk,String dunya){
        for (int i = 0; i < temp.trustlar.size(); i++) {
            if (temp.trustlar.get(i).arsaKonum.equals(chunk)){
                if (temp.trustlar.get(i).arsaDunya.equals(dunya)){
                    if (temp.trustlar.get(i).trustVerilenler.get(0).equals(temp.oyuncu)){
                        return i;
                    }
                }
            }
        }
        return -1;
    }



    private Link listedeOyuncuBul(String oyuncuIsmi){
        Link temp = head;
        while (temp != null) {
            if (temp.oyuncu.equals(oyuncuIsmi)){
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }





}
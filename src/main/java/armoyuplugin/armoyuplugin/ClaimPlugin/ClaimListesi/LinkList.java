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


    public LinkList() {
        head = null;

    }

    public void buyClaim(String chunk, String pName,String dunya) {
        Link current = listedeAraziBul(dunya,chunk);
        Link temp = head;
        if (current == null){
            Link arazi = new Link();
            arazi.arsaoyuncuadi=pName;
            arazi.arsaChunk=chunk;
            arazi.arsaDunya=dunya;
            arazi.arsaKlanaBaglanmaDurumu=0;
            arazi.hissedarlar.add(pName);
            arazi.next=head;
            head=arazi;
            }
    }

    public void removeTrust(String chunk,String silen,String silinen,String dunya){

        Link temp = listedeAraziBul(dunya,chunk);
        if (temp!=null){
            if (temp.arsaoyuncuadi.equals(silen)){

                for (int i = 0; i < temp.hissedarlar.size(); i++) {
                    if (temp.hissedarlar.get(i).equals(silinen)){
                        temp.hissedarlar.remove(i);
                        break;
                    }
                }

            }
        }
        }

    public void giveTrustForOneChunk(String chunk,String ekleyen, String eklenen,String dunya){
        Link temp = listedeAraziBul(dunya,chunk);
        if (temp!=null){
            if (temp.arsaoyuncuadi.equals(ekleyen)) {
                int trustuVarmi = trustuVarmi(eklenen, dunya, chunk);
                if (trustuVarmi == 0) {
                    temp.hissedarlar.add(eklenen);
                }
            }
        }
    }

    public void giveTrustForAll(String ekleyen,String eklenen){
        int trustdurum;
        Link temp=head;
        while (temp!=null){
            if (temp.arsaoyuncuadi.equals(ekleyen)){
                trustdurum=0;
                for (int i = 0; i < temp.hissedarlar.size(); i++) {
                    if (temp.hissedarlar.get(i).equals(eklenen)){
                        trustdurum=1;
                        break;
                    }
                }
                if (trustdurum==0){
                    temp.hissedarlar.add(eklenen);
                }
            }
            temp=temp.next;
        }
    }

    public void removeTrustForAll(String ekleyen, String silinen){
        Link temp=head;
        while (temp!=null){
            if (temp.arsaoyuncuadi.equals(ekleyen)){
                for (int i = 0; i < temp.hissedarlar.size(); i++) {
                    if (temp.hissedarlar.get(i).equals(silinen)){
                        temp.hissedarlar.remove(i);
                    }
                }
            }
            temp=temp.next;
        }
    }


    public void removeClaimForOneChunk(Player silen,String dunya) {
        nesneyiSil(silen.getName(),dunya,silen.getLocation().getChunk().toString());
    }

    public void removeClaimForAll(String silen) {

        Link temp = head;
        while (temp!=null){
            if (temp.next.arsaoyuncuadi.equals(silen)){
                temp.next=temp.next.next;
            }
            temp=temp.next;
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

        Link temp = listedeAraziBul(dunya,chunk.toString());
        arsaSahibi = temp.arsaoyuncuadi;

//        temp = head;
//        while (temp != null) {
//            if (temp.oyuncu.equals(p.getName())){
//
//                    if (temp.oncekiGecilenAraziSahibi.equals(arsaSahibi)) {
//                        durmadanYazmasiniEngelleme = 1;
//                    } else {
//                        durmadanYazmasiniEngelleme = 0;
//                        temp.oncekiGecilenAraziSahibi = arsaSahibi;
//                    }
//            }
//
//            temp = temp.next;
//        }
//        if (durmadanYazmasiniEngelleme==0) {
//            temp = head;
//            while (temp != null) {
//                for (int i = 0; i < temp.trustlar.size(); i++) {
//                    if (temp.trustlar.get(i).arsaDunya.equals(dunya)){
//                    if (temp.trustlar.get(i).arsaKonum.equals(chunk.toString())) {
//                        for (int j = 0; j < temp.trustlar.get(i).trustVerilenler.size(); j++) {
//                            if (temp.trustlar.get(i).trustVerilenler.get(j).equals(p.getName())) {
//                                trustvarmi++;
//                                break;
//                            }
//                        }
//                        arsaSahibi = temp.trustlar.get(i).trustVerilenler.get(0);
//                        aciklama = temp.arsaAciklamasi;
//                        arsadolu++;
//                        break;
//                    }
//                    }
//                }
//
//                temp = temp.next;
//            }
//            if (trustvarmi!=0){
//                dostBolgeGiris(plugin.getAdventure().player(p),arsaSahibi,aciklama);
//            }else if(arsadolu != 0){
//                dusmanBolgeGiris(plugin.getAdventure().player(p),arsaSahibi,aciklama);
//            }else
//                bosAraziGiris(plugin.getAdventure().player(p));
//        }

    }

    public void claimNotificationChange(Player oyuncu,String[] diziAciklama){
        String aciklama= "";
        for (int i = 1; i < diziAciklama.length; i++) {
            aciklama = aciklama + diziAciklama[i] + " ";
        }
        Link temp = listedeAraziBul(oyuncu.getWorld().toString(),oyuncu.getLocation().getChunk().toString());
        if (temp != null) {
            if (oyuncu.getName().equals(temp.arsaoyuncuadi)){
            if (aciklama.length() < 25) {
                temp.arsaAciklamasi = aciklama;
                }
            }
        }


    }

    public int chunkControl(String playerName,String chunk,String dunya){
        int arsadolu = 0;
        int trustDurum = 0;
        Link temp = listedeAraziBul(dunya,chunk);
        if (temp!=null){
            for (int i = 0; i < temp.hissedarlar.size(); i++) {
                if (temp.hissedarlar.get(i).equals(playerName)){
                    trustDurum++;
                    break;
                }
            }
            arsadolu++;
        }

        if (trustDurum!=0){
            return 1;
        } else if (arsadolu !=0) {
            return 2;
        }
       return 0;
    }
    public String claimWhoOwner(String chunk,String dunya){
        Link temp = listedeAraziBul(dunya,chunk);
        if (temp!=null){
            return temp.arsaoyuncuadi;
        }
        return "Claimsiz";
    }

    public void arsaBahset(String chunk, String arsayiKlanaBaglayan,String dunya){
        Link temp = listedeAraziBul(dunya,chunk);
        if (temp != null){
            if (temp.arsaoyuncuadi.equals(arsayiKlanaBaglayan)){
                temp.arsaKlanaBaglanmaDurumu = 1;
            }
        }
    }

    public void claimTransfer(String chunk, String bagislayan,String bagislanan,String dunya){
                Link temp = listedeAraziBul(dunya,chunk);
                if (temp!=null) {
                    if (temp.arsaoyuncuadi.equals(bagislayan)){
                        temp.arsaoyuncuadi = bagislanan;
                        temp.hissedarlar.clear();
                        temp.hissedarlar.add(bagislanan);
                    }
                }
    }







    //Genel


    private int trustuVarmi(String kimin,String dunya,String chunk){
        Link temp = listedeAraziBul(dunya,chunk);
        if (temp != null) {
            for (int i = 0; i < temp.hissedarlar.size(); i++) {
                if (temp.hissedarlar.get(i).equals(kimin)){
                    return 1;
                }
            }
        }
        return 0;
    }

    public Link listedeAraziBul(String dunya,String chunk){
        Link temp = head;
        while (temp!=null){
            if (temp.arsaDunya.equals(dunya)){
                if (temp.arsaChunk.equals(chunk)){
                    return temp;
                }
            }
            temp=temp.next;
        }
        return null;
    }


    private void nesneyiSil(String oyuncuAdi,String dunya, String chunk){
        Link temp = head;
        while (temp!=null){
            if (temp.next.arsaChunk.equals(chunk)){
                if(temp.next.arsaDunya.equals(dunya)){
                    if (temp.next.arsaoyuncuadi.equals(oyuncuAdi)){
                        temp.next=temp.next.next;
                    }
                }
            }
        }
    }











}
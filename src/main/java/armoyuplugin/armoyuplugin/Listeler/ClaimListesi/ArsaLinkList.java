package armoyuplugin.armoyuplugin.Listeler.ClaimListesi;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.Listeler.OyuncuBilgiListesi.OyuncuBilgiLink;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.time.Duration;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.klanListesi;
import static armoyuplugin.armoyuplugin.ARMOYUPlugin.oyuncuListesi;

public class ArsaLinkList {
    public ArsaBilgiLink head;

    public ArsaLinkList() {
        head = null;
    }

    public void arsaAl(String chunk, String pName,String dunya) {
            ArsaBilgiLink arazi = new ArsaBilgiLink();
            arazi.arsaoyuncuadi=pName;
            arazi.arsaChunk=chunk;
            arazi.arsaDunya=dunya;
            arazi.arsaKlan= "";
            arazi.hissedarlar.add(pName);
            arazi.arsaAciklamasi = "Arsa açıklaması.";

            arazi.next=head;
            head=arazi;
    }
    public void arsaAlSite(String chunk, String pName,String dunya,String arsaKlanAdi,String arsaAciklaması) {
        ArsaBilgiLink current = listedeAraziBul(dunya,chunk);
        if (current == null){
            ArsaBilgiLink arazi = new ArsaBilgiLink();
            arazi.arsaoyuncuadi=pName;
            arazi.arsaChunk=chunk;
            arazi.arsaDunya=dunya;
            arazi.arsaKlan=arsaKlanAdi;
            arazi.hissedarlar.add(pName);
            arazi.arsaAciklamasi = arsaAciklaması;

            arazi.next=head;
            head=arazi;
        }
    }
    public void hissedarSil(String chunk,String silen,String silinen,String dunya){

        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
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

    public void hissedarEkle(String chunk,String ekleyen, String eklenen,String dunya){
        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
        if (temp!=null){
            if (temp.arsaoyuncuadi.equals(ekleyen)) {
                    temp.hissedarlar.add(eklenen);
            }
        }
    }

    public void hissedarEkleHeryer(String ekleyen,String eklenen){
        int trustdurum;
        ArsaBilgiLink temp=head;
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

    public void hissedarSilHeryer(String ekleyen, String silinen){
        ArsaBilgiLink temp=head;
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


    public void claimSil(String oyuncuIsmi,String chunk,String dunya) {
        ArsaBilgiLink temp = head;
        while (temp != null) {
            if (temp.arsaoyuncuadi.equals(oyuncuIsmi)){
                if (temp.arsaChunk.equals(chunk)){
                    if (temp.arsaDunya.equals(dunya)){
                        if (temp.arsaKlan.isEmpty()){
                            temp.arsaDunya ="";
                            temp.hissedarlar.clear();
                            temp.arsaoyuncuadi = "";
                            temp.arsaKlan = "";
                            temp.arsaChunk = "";
                        }
                    }
                }
            }
            temp = temp.next;
        }
    }

    public void claimSilHeryer(String oyuncuIsmi) {

        ArsaBilgiLink temp = head;
        while (temp != null) {
            if (temp.arsaoyuncuadi.equals(oyuncuIsmi)){
                temp.arsaDunya ="";
                temp.hissedarlar.clear();
                temp.arsaoyuncuadi = "";
                temp.arsaKlan = "";
                temp.arsaChunk = "";
            }
            temp = temp.next;
        }
    }

    public void claimAciklama(String oyuncuIsmi,String aciklama,String dunya,String chunk){

        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
        if (temp != null) {
            if (oyuncuIsmi.equals(temp.arsaoyuncuadi)){
                temp.arsaAciklamasi = aciklama;
            }
        }
    }
    public void claimAciklamaHeryer(String oyuncuIsmi,String aciklama){

        ArsaBilgiLink temp = head;
        while (temp != null){
            if (temp.arsaoyuncuadi.equals(oyuncuIsmi)){
                temp.arsaAciklamasi = aciklama;
            }
            temp=temp.next;
        }

    }

    public void claimRehin(String rehinVeren,String dunya,String chunk,String klanAdi){
        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
        if (temp != null){
            if (temp.arsaoyuncuadi.equals(rehinVeren)){
                temp.arsaKlan = klanListesi.hangiKlanaUye(rehinVeren);
            }
        }
    }

    public void claimDevret(String chunk, String bagislayan,String bagislanan,String dunya){
        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
        if (temp!=null) {
            if (temp.arsaoyuncuadi.equals(bagislayan)){
                temp.arsaoyuncuadi = bagislanan;
                temp.hissedarlar.clear();
                temp.hissedarlar.add(bagislanan);
            }
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
        String arsaSahibi;
        String aciklama = arsaAciklamaBul(dunya,chunk.toString());

        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk.toString());
        if (temp != null){
            arsaSahibi = temp.arsaoyuncuadi;
        }else {
            arsaSahibi = "";
        }


        OyuncuBilgiLink oyuncu = oyuncuListesi.head;
        while (oyuncu != null) {
            if (oyuncu.oyuncuAdi.equals(p.getName())){

                    if (oyuncu.oncekiGecilenAraziSahibi.equals(arsaSahibi)) {
                        durmadanYazmasiniEngelleme = 1;
                    } else {
                        durmadanYazmasiniEngelleme = 0;
                        oyuncu.oncekiGecilenAraziSahibi = arsaSahibi;
                    }
            }

            oyuncu = oyuncu.next;
        }
        if (durmadanYazmasiniEngelleme==0) {
            if (temp != null) {
                for (int i = 0; i < temp.hissedarlar.size(); i++) {
                    if(temp.hissedarlar.get(i).equals(p.getName())){
                        trustvarmi = 1;
                        break;
                }
                    arsadolu = 1;
            }
            }
            if (trustvarmi!=0){
                dostBolgeGiris(plugin.getAdventure().player(p),arsaSahibi,aciklama);
            }else if(arsadolu != 0){
                dusmanBolgeGiris(plugin.getAdventure().player(p),arsaSahibi,aciklama);
            }else
                bosAraziGiris(plugin.getAdventure().player(p));
        }

    }



    public int arsaKontrol(String playerName,String chunk,String dunya){
        int arsadolu = 0;
        int trustDurum = 0;
        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
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
    public String arsaninSahibiKim(String chunk,String dunya){
        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
        if (temp!=null){
            return temp.arsaoyuncuadi;
        }
        return "Claimsiz";
    }









    //Genel


    public boolean kontrolTrustuVarmi(String kimin,String dunya,String chunk){
        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
        if (temp != null) {
            for (int i = 0; i < temp.hissedarlar.size(); i++) {
                if (temp.hissedarlar.get(i).equals(kimin)){
                    return true;
                }
            }
        }
        return false;
    }

    public ArsaBilgiLink listedeAraziBul(String dunya, String chunk){
        ArsaBilgiLink temp = head;
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

    private String arsaAciklamaBul(String arsaDunya,String arsaChunk){
        ArsaBilgiLink temp = head;

        while (temp != null){
            if (temp.arsaDunya.equals(arsaDunya)){
                if (temp.arsaChunk.equals(arsaChunk)){
                    if (temp.arsaKlan.isEmpty()){
                        return temp.arsaAciklamasi;
                    }else{
                        return klanListesi.arsaAciklamaBul(temp.arsaKlan);}
                }
            }
            temp = temp.next;
        }
    return "";
    }







    // klanla ilgili işlemler

    public void klandanAyrilClaim(String ayrilan){
        ArsaBilgiLink temp = head;
        while (temp != null){
            if (temp.arsaoyuncuadi.equals(ayrilan))
                if (temp.arsaKlan != null){
                    temp.arsaoyuncuadi = "";
                    temp.hissedarlar.clear();
                    temp.arsaAciklamasi = "";
                }
            temp = temp.next;
        }
    }


}
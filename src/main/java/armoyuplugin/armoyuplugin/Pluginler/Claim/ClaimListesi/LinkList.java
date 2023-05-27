package armoyuplugin.armoyuplugin.Pluginler.Claim.ClaimListesi;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.OyuncuBilgiListesi.OyuncuBilgiLink;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.time.Duration;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.klanListesi;
import static armoyuplugin.armoyuplugin.ARMOYUPlugin.oyuncuListesi;

public class LinkList {
    public ArsaBilgiLink head;

    public LinkList() {
        head = null;
    }

    public void arsaAl(String chunk, String pName,String dunya) {
        ArsaBilgiLink current = listedeAraziBul(dunya,chunk);
        if (current == null){
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
    public void hissedarlardanCikar(String chunk,String silen,String silinen,String dunya){

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

    public void hissedarlaraEkleBir(String chunk,String ekleyen, String eklenen,String dunya){
        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
        if (temp!=null){
            if (temp.arsaoyuncuadi.equals(ekleyen)) {
                int trustuVarmi = trustuVarmi(eklenen, dunya, chunk);
                if (trustuVarmi == 0) {
                    temp.hissedarlar.add(eklenen);
                }
            }
        }
    }

    public void hissedarlaraEkleHepsi(String ekleyen,String eklenen){
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

    public void hissedarlardanCikarHepsi(String ekleyen, String silinen){
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


    public void arsaKaldirBir(Player silen,String dunya) {
        nesneyiSil(silen.getName(),dunya,silen.getLocation().getChunk().toString());
    }

    public void arsaKaldirHepsi(String silen) {

        ArsaBilgiLink temp = head;
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

    public void arsaAciklamaDegisBir(Player oyuncu,String aciklama,String dunya,String chunk){

        ArsaBilgiLink temp = listedeAraziBul(oyuncu.getWorld().toString(),oyuncu.getLocation().getChunk().toString());
        if (temp != null) {
            if (oyuncu.getName().equals(temp.arsaoyuncuadi)){
                if (dunya.equals(oyuncu.getWorld().toString())){
                    if (chunk.equals(oyuncu.getLocation().getChunk().toString())) {
                        temp.arsaAciklamasi = aciklama;
                    }
                }

            }

        }
    }
    public void arsaAciklamaDegisHepsi(Player oyuncu,String aciklama){

        ArsaBilgiLink temp = head;
        while (temp != null){
            if (temp.arsaoyuncuadi.equals(oyuncu.getName())){
                temp.arsaAciklamasi = aciklama;
            }
            temp=temp.next;
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

    public void arsaRehin(String chunk, String arsayiKlanaBaglayan,String dunya){
        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
        if (temp != null){
            if (temp.arsaoyuncuadi.equals(arsayiKlanaBaglayan)){
                temp.arsaKlan = klanListesi.hangiKlanaUye(arsayiKlanaBaglayan);
            }
        }
    }

    public void arsaDevret(String chunk, String bagislayan,String bagislanan,String dunya){
                ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
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
        ArsaBilgiLink temp = listedeAraziBul(dunya,chunk);
        if (temp != null) {
            for (int i = 0; i < temp.hissedarlar.size(); i++) {
                if (temp.hissedarlar.get(i).equals(kimin)){
                    return 1;
                }
            }
        }
        return 0;
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


    private void nesneyiSil(String oyuncuAdi,String dunya, String chunk){
        ArsaBilgiLink temp = head;
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
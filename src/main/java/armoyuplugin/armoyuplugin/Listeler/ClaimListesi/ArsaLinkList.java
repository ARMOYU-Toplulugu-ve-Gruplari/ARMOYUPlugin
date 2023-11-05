package armoyuplugin.armoyuplugin.Listeler.ClaimListesi;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.Listeler.KlanListesi.Yetkiler;
import armoyuplugin.armoyuplugin.Listeler.OyuncuBilgiListesi.OyuncuBilgiLink;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.time.Duration;
import java.util.List;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.*;
import static armoyuplugin.armoyuplugin.ARMOYUPlugin.apiService;

public class ArsaLinkList {
    public ArsaBilgiLink head;
    private String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";

    public ArsaLinkList() {
        head = null;
    }

    public void arsaAl(Player p, JSONObject yollanacaklar,String link) {
        if (listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString()) == null){
            JSONObject json = apiService.postYolla(link,yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                ArsaBilgiLink arazi = new ArsaBilgiLink();
                arazi.arsaoyuncuadi=p.getName();
                arazi.arsaChunk=p.getLocation().getChunk().toString();
                arazi.arsaDunya=p.getWorld().toString();
                arazi.arsaKlan= "";
                arazi.hissedarlar.add(p.getName());
                arazi.arsaAciklamasi = "Arsa açıklaması.";

                arazi.next=head;
                head=arazi;
            }
        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arazi zaten alınmış.");

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

    public void hissedarSil(Player p,JSONObject yollanacaklar,String link,String cikarilan){

        ArsaBilgiLink temp = listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString());
        if (temp!=null){
            if (temp.arsaoyuncuadi.equals(p.getName())){

//                if (arsaKlanaBagliMi(p.getWorld().toString(),p.getLocation().getChunk().toString()))

                for (int i = 0; i < temp.hissedarlar.size(); i++) {
                    if (temp.hissedarlar.get(i).equals(cikarilan)){
                        JSONObject json = apiService.postYolla(link, yollanacaklar);
                        if (json.get("durum").toString().equals("0")) {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                        } else {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                            temp.hissedarlar.remove(i);
                        }
                        return;
                    }
                }
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Çıkarmak istediğin oyuncu hissedarlarına dahil değil.");



            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arazi sana ait değil.");
        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arazi kimseye ait değil.");
    }

    public void hissedarEkle(Player p,String eklenilen,JSONObject yollanacaklar,String link){
        ArsaBilgiLink temp = listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString());
        if (temp!=null){
            if (temp.arsaoyuncuadi.equals(p.getName())) {

                if (ayniKlandaMi(p.getName(),eklenilen)){

                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("0")) {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                    temp.hissedarlar.add(eklenilen);
                }

                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arazi klana bağlandığı için sadece klandakilere trust verebilirsin.");

            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arazi sana ait değil.");
        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arazi kimseye ait değil.");
    }
    public void hissedarEkleSite(String arsaChunk,String arsaOyuncuAdi,String eklenilenOyuncuAdi,String arsaDunya){
        ArsaBilgiLink temp = listedeAraziBul(arsaDunya,arsaChunk);
        if (temp != null){
            if (temp.arsaoyuncuadi.equals(arsaOyuncuAdi)){
                temp.hissedarlar.add(eklenilenOyuncuAdi);
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

    public void claimSil(Player p,JSONObject yollanacaklar,String link) {
        ArsaBilgiLink temp = head;
        if (head.arsaoyuncuadi.equals(p.getName())){
            if (head.arsaChunk.equals(p.getLocation().getChunk().toString())){
                if (head.arsaDunya.equals(p.getWorld().toString())){
                    if (head.arsaKlan.isEmpty()){
                        JSONObject json = apiService.postYolla(link, yollanacaklar);
                        if (json.get("durum").toString().equals("0")) {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                            return;
                        } else {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                            head = head.next;
                            return;
                        }
                    }
                }
            }
        }
        while (temp != null) {
            if (temp.next.arsaoyuncuadi.equals(p.getName())){
                if (temp.next.arsaChunk.equals(p.getLocation().getChunk().toString())){
                    if (temp.next.arsaDunya.equals(p.getWorld().toString())){
                        if (temp.next.arsaKlan.isEmpty()){
                            JSONObject json = apiService.postYolla(link, yollanacaklar);
                            if (json.get("durum").toString().equals("0")) {
                                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                            } else {
                                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                                temp.next = temp.next.next;
                            }
                        }else
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsan klana bağlı olduğu için silemezsin.");
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
                if(temp.arsaKlan.isEmpty()){
                    temp.arsaDunya ="";
                    temp.hissedarlar.clear();
                    temp.arsaoyuncuadi = "";
                    temp.arsaKlan = "";
                    temp.arsaChunk = "";
                }
            }
            temp = temp.next;
        }
    }

    public void claimAciklama(Player p,JSONObject yollanacaklar,String link,String aciklama){

        ArsaBilgiLink temp = listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString());
        if (temp != null) {
            if (p.getName().equals(temp.arsaoyuncuadi)){

                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("0")) {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                    temp.arsaAciklamasi = aciklama;
                }
            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsanın açıklamasını sadece arsanın sahibi değiştirebilir.");
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

    public void claimRehin(Player p,JSONObject yollanacaklar,String link,String klanAdi){
        ArsaBilgiLink temp = listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString());
        if (temp != null){
            if (temp.arsaoyuncuadi.equals(p.getName())){
                if (temp.arsaKlan.isEmpty()){
                    JSONObject json = apiService.postYolla(link, yollanacaklar);
                    if (json.get("durum").toString().equals("0")) {
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                    } else {
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                        temp.arsaKlan = klanAdi;
                        temp.hissedarlar.clear();
                        temp.hissedarlar.add(p.getName());
                    }
                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsa zaten rehin verilmiş.");
            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsa sana ait değil.");
        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsa kimseye ait değil.");
    }

    public void claimDevret(Player p,JSONObject yollanacaklar,String link,String devredilen){
        ArsaBilgiLink temp = listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString());
        if (temp!=null) {
            if (temp.arsaoyuncuadi.equals(p.getName())){

                if (arsaKlanaBagliMi(temp.arsaDunya,temp.arsaChunk)){

                    JSONObject json = apiService.postYolla(link, yollanacaklar);
                    if (json.get("durum").toString().equals("0")) {
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                    } else {
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                        temp.arsaoyuncuadi = devredilen;
                        temp.hissedarlar.clear();
                        temp.hissedarlar.add(devredilen);
                    }

                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsa klana bağlandığı için devredilemez.");
            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsa sana ait değil.");
        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsa kimseye ait değil.");

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
    public void EkranaAraziBilgiYazdirma(Player p, Chunk chunk,String dunya,ARMOYUPlugin plugin){
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
                System.out.println(1);
                dostBolgeGiris(plugin.getAdventure().player(p),arsaSahibi,aciklama);
            }else if(arsadolu != 0){
                System.out.println(2);
                dusmanBolgeGiris(plugin.getAdventure().player(p),arsaSahibi,aciklama);
            }else
                bosAraziGiris(plugin.getAdventure().player(p));
            System.out.println(3);
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
                        return klanListesi.arsaAciklamaBul(temp.arsaKlan);
                    }
                }
            }
            temp = temp.next;
        }
    return "";
    }







    // klanla ilgili işlemler

    public boolean yetkiKontrol(String oyuncuAdi, String yetki){
        return klanListesi.yetkiKontrol(oyuncuAdi,yetki);
    }

    public boolean ayniKlandaMi(String oyuncuBir,String oyuncuIki){
        String bir = klanListesi.hangiKlanaUye(oyuncuBir);
        String iki = klanListesi.hangiKlanaUye(oyuncuIki);
        boolean sonDurum = true;
        if (!bir.isEmpty()){
            if (!iki.isEmpty()){
                sonDurum = bir.equals(iki);
            }
        }
        return sonDurum;
    }

    public boolean arsaKlanaBagliMi(String dunya, String chunk){
        return !(listedeAraziBul(dunya,chunk).arsaKlan.isEmpty());
    }

    public void klandanAyrilClaim(String ayrilan){
        ArsaBilgiLink temp = head;
        while (temp != null){
            if (temp.arsaoyuncuadi.equals(ayrilan))
                if (!temp.arsaKlan.isEmpty()){
                    temp.arsaoyuncuadi = "";
                    temp.hissedarlar.clear();
                    temp.arsaAciklamasi = "";
                }
            temp = temp.next;
        }
    }

    public void klanDagitClaim(String klanAdi){
        ArsaBilgiLink temp = head;
        if (head.arsaKlan.equals(klanAdi)){
                if (head.arsaoyuncuadi.isEmpty()){
                    head = head.next;
                }else {
                    temp.arsaKlan = "";
                }
            return;
        }
        while (temp != null){
            if (temp.next.arsaKlan.equals(klanAdi)){
                if (temp.next.arsaoyuncuadi.isEmpty()){
                    temp.next = temp.next.next;
                }else {
                    temp.next.arsaKlan = "";
                }
            }
            temp = temp.next;
        }
    }

    public void klanArsaSil(String name){
        ArsaBilgiLink temp = head;
        if (head.arsaoyuncuadi.equals(name)){
            head = head.next;
            return;
        }
        while (temp != null){
            if (temp.next.arsaoyuncuadi.equals(name)){
                temp.next = temp.next.next;
            }
            temp = temp.next;
        }
    }

}
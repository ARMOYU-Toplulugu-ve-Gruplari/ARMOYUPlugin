package ARMOYU.Listeler.KlanListesi;

import ARMOYU.Listeler.ClaimListesi.ArsaBilgiLink;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import static ARMOYU.ARMOYUPlugin.*;
import static org.bukkit.Bukkit.getServer;

public class KlanLinkList {
    private String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Klan] ";
    public KlanBilgiLink head;
    public KlanLinkList() {
        head = null;
    }

    public void klanKatil(Player p, JSONObject yollanacaklar,String link,String katilacagiKlanAdi){
        String uyeOlabilirmi = hangiKlanaUye(p.getName());
        if (uyeOlabilirmi.isEmpty()){
            KlanBilgiLink temp = klanBulKlanAdi(katilacagiKlanAdi);
            if (temp != null){
                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("0")) {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                    KlanRutbeleri rutbe = new KlanRutbeleri();
                    rutbe.rutbeSira = 0;
                    rutbe.rutbeAdi = "Çapulcu";

                    KlanOyuncuBilgi eklenilen = new KlanOyuncuBilgi();
                    eklenilen.oyuncuAdi = p.getName();
                    eklenilen.rutbe = rutbe;

                    temp.klanUyeleri.add(eklenilen);
                }
            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Klan bulunamadı.");
        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Önce klandan ayrılmalısın.");
    }


    public void klanOlustur(Player p,JSONObject yollanacaklar,String link,String klanAdi){

            if (klanBulKlanAdi(klanAdi)==null){
                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("0")) {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                    KlanBilgiLink klan = new KlanBilgiLink();
                    klan.klanAdi = klanAdi;
                    klan.klanKurucu = p.getName();
                    klan.arsaAciklamasi = "Klan Arsa Açıklaması.";
                    KlanRutbeleri kurucuRutbesi = new KlanRutbeleri();

                    kurucuRutbesi.rutbeAdi = "Lider";
                    kurucuRutbesi.rutbeSira = 1;

                    klan.klanRutbeleri.add(kurucuRutbesi);

                    KlanOyuncuBilgi kurucu = new KlanOyuncuBilgi();
                    kurucu.rutbe=kurucuRutbesi;
                    kurucu.rutbe.yetkiler.add(Yetkiler.kurucu);
                    kurucu.oyuncuAdi=p.getName();
                    klan.klanUyeleri.add(kurucu);

                    klan.next = head;
                    head = klan;

                    KlanBilgiLink temp = head;
                    while (temp != null){
                        System.out.println(temp.klanUyeleri.get(0));
                        temp = temp.next;
                    }
                }

            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Klan adi kullanılıyor.");

    }

    public void klanAyril(Player p,JSONObject yollanacaklar,String link){
        if (!hangiKlanaUye(p.getName()).isEmpty()){
            JSONObject json = apiService.postYolla(link, yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                claimListesi.klandanAyrilClaim(p.getName());
                klandanCikar(p.getName());
            }

        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Önce bir klana üye olmalısın.");

    }

    public void klanDagit(Player p,JSONObject yollanacaklar,String link){
        String klanAdi = hangiKlanaUye(p.getName());
        if (!klanAdi.isEmpty()){

            if (yetkiKontrol(p.getName(),Yetkiler.kurucu)) {
                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("1")) {
//                KlanBilgiLink klan = klanBulKlanAdi(klanAdi);
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                    KlanBilgiLink temp = head;
                    if (head.klanAdi.equals(klanAdi)) {
                        claimListesi.klanDagitClaim(klanAdi);
                        head = head.next;
                        return;
                    }
                    while (temp != null) {
                        if (temp.next.klanAdi.equals(klanAdi)) {
                            claimListesi.klanDagitClaim(klanAdi);
                            temp.next = temp.next.next;
                            break;
                        }
                        temp = temp.next;
                    }

//                if (klan.klanKurucu.equals(p.getName())) {
//                    klan.klanKurucu = "";
//                    klan.klanAdi = "";
//                    klan.klanUyeleri.clear();
//                    klan.klanRutbeleri.clear();
//                    klan.arsaAciklamasi = "";
//                }

                }else
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Yetkiniz bulunmamaktadır.");

        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Önce bir klana üye olmalısın.");
    }

    public void klanGit(Player p,JSONObject yollanacaklar,String link){
            JSONObject json = apiService.postYolla(link, yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                JSONObject rec = json.getJSONObject("icerik");
                String klandunya = rec.get("klandunya").toString();
                String kordinat = rec.get("klankordinat").toString();
                String[] result = kordinat.split(",");
                double x = Double.parseDouble(result[0]);
                double y = Double.parseDouble(result[1]);
                double z = Double.parseDouble(result[2]);
                p.teleport(new Location(Bukkit.getWorld(klandunya),x,y,z));
            }
    }

    public void klanBaslangicNoktasi(Player p,JSONObject yollanacaklar,String link){
        String klanAdi = hangiKlanaUye(p.getName());
        if (!klanAdi.isEmpty()){

            if (yetkiKontrol(p.getName(),Yetkiler.klanBaslangicAyarla)) {

                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("0")) {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                }

            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Yetkiniz bulunmamaktadır.");

        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Önce bir klana üye olmalısın.");
    }

    public void klanDavet(Player p, JSONObject yollanacaklar, String link, Server server){
        String klanAdi = hangiKlanaUye(p.getName());
        if (!klanAdi.isEmpty()) {

            yollanacaklar.put("klanadi", klanAdi);

            if (yetkiKontrol(p.getName(), Yetkiler.davet)) {


                int oyuncubul = 0;
                Player oyuncuarkadas = null;
                for (Player player : getServer().getOnlinePlayers()) {
                    if (player.getName().equals(yollanacaklar.get("kullaniciadi"))) {
                        oyuncubul = 1;
                        oyuncuarkadas = player;
                        break;
                    }
                }
                if (oyuncubul == 1) {
                    oyuncuarkadas.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + p.getName() + " adlı oyuncu seni " + klanAdi + " adlı klana davet ediyor.");
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + p.getName() + " davet etmek istediğin oyuncu aktif değil.");
                    return;
                }
                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("0")) {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                }
            } else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Yetkiniz bulunmamaktadır.");


        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Önce bir klana üye olmalısın.");
    }
    public void klanAciklama(Player p,JSONObject yollanacaklar,String link){
        String klanAdi = hangiKlanaUye(p.getName());
        if (!klanAdi.isEmpty()){
            yollanacaklar.put("grupadi",klanAdi);
            if (yetkiKontrol(p.getName(),Yetkiler.kurucu)){
                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("0")) {

                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    KlanBilgiLink klan = klanBulKlanAdi(klanAdi);
                    klan.arsaAciklamasi = yollanacaklar.get("aciklama").toString();
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                }
            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Yetkiniz bulunmamaktadır.");
        }
    }
    public void klanAt(Player p,JSONObject yollanacaklar,String link){
        String klanAdi = hangiKlanaUye(p.getName());
        if (!klanAdi.isEmpty()){

            if (yetkiKontrol(p.getName(),Yetkiler.uyeSil)) {
                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("1")) {
                                klandanCikar(yollanacaklar.get("atilacakoyuncuadi").toString());
                                claimListesi.klandanAyrilClaim(yollanacaklar.get("atilacakoyuncuadi").toString());
                                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                }
            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Yetkiniz bulunmamaktadır.");

    }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Önce bir klana üye olmalısın.");
    }

    public void klanArsaDevret(Player p,JSONObject yollanacaklar,String link,String devreden,String devredilen){
        String klanAdi = hangiKlanaUye(devreden);
        if (!klanAdi.isEmpty()){

            if (yetkiKontrol(p.getName(),Yetkiler.klanArazi)) {

                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("1")) {

                    ArsaBilgiLink arsaBilgiLink = claimListesi.listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString());
                    arsaBilgiLink.hissedarlar.clear();
                    arsaBilgiLink.hissedarlar.add(devredilen);
                    arsaBilgiLink.arsaoyuncuadi = devredilen;

                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                }

            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Yetkiniz bulunmamaktadır.");

        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Önce bir klana üye olmalısın.");
    }

    public void klanArsaSil(Player p,JSONObject yollanacaklar,String link){
        String klanAdi = hangiKlanaUye(p.getName());
        if (!klanAdi.isEmpty()){
            if (yetkiKontrol(p.getName(),Yetkiler.klanArazi)) {
                JSONObject json = apiService.postYolla(link, yollanacaklar);
                if (json.get("durum").toString().equals("1")) {
                    claimListesi.klanArsaSil(p.getName());
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                }

            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Yetkiniz bulunmamaktadır.");

        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Önce bir klana üye olmalısın.");
    }

    public KlanBilgiLink apiKlanOlustur(String olusturan,String klanAdi,String klanAciklama){

        if (klanBulKlanAdi(klanAdi)==null){
            KlanBilgiLink klan = new KlanBilgiLink();
            klan.klanAdi = klanAdi;
            klan.klanKurucu = olusturan;
            klan.arsaAciklamasi = klanAciklama;
            klan.next = head;
            head = klan;
            return klan;
        }

        return null;

    }




    public void yetkiVer(String yetkiyiVeren,String kime,String yetkiIsmi){

    }
    public String klanLideri(String oyuncuIsmi){
        return "";
    }

    public boolean yetkiKontrol(String oyuncuAdi,String yetki){
            KlanOyuncuBilgi klanOyuncuBilgi = oyuncuBilgi(oyuncuAdi);
            if (klanOyuncuBilgi != null){
                for (int i = 0; i < klanOyuncuBilgi.rutbe.yetkiler.size(); i++) {
                    if (klanOyuncuBilgi.rutbe.yetkiler.get(i).equals(yetki)){
                        return true;
                    } else if (klanOyuncuBilgi.rutbe.yetkiler.get(i).equals(Yetkiler.kurucu)) {
                        return true;
                    }
                }
                return false;
            }
        return false;
    }


    private KlanBilgiLink klanBulKlanAdi(String klanAdi){
        KlanBilgiLink temp = head;
        while (temp != null){
            if (temp.klanAdi.equals(klanAdi)){
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    private KlanBilgiLink hangiKlandaKurucu(String oyuncuIsmi){
        KlanBilgiLink temp = head;
        while (temp!= null){
            for (int i = 0; i < temp.klanUyeleri.size(); i++) {
                if (temp.klanUyeleri.get(i).oyuncuAdi.equals(oyuncuIsmi)){
                    for (int j = 0; j < temp.klanUyeleri.get(i).rutbe.yetkiler.size(); j++) {
                        if (temp.klanUyeleri.get(i).rutbe.yetkiler.get(j).equals(Yetkiler.kurucu));{
                            return temp;
                        }
                    }
                }
            }
            temp = temp.next;
        }
        return null;
    }


    private void klandanCikar(String oyuncuAdi){
        KlanBilgiLink temp = head;
        int k = 0;
        while (temp!= null){
            for (int i = 0; i < temp.klanUyeleri.size(); i++) {
                if (temp.klanUyeleri.get(i).oyuncuAdi.equals(oyuncuAdi)){
                    temp.klanUyeleri.remove(i);
                    k++;
                    break;
                }
            }
            if (k!=0){
                break;
            }
            temp = temp.next;
        }
    }
    

    public String hangiKlanaUye(String oyuncuAdi){
        KlanBilgiLink temp = head;
        while (temp != null){
            for (int i = 0; i < temp.klanUyeleri.size(); i++) {
                if (temp.klanUyeleri.get(i).oyuncuAdi.equals(oyuncuAdi)){
                    return temp.klanAdi;
                }
            }
            temp = temp.next;
        }
        return "";
    }

    public KlanOyuncuBilgi oyuncuBilgi(String oyuncuAdi){
        KlanBilgiLink temp = head;
        while (temp != null){
            for (int i = 0; i < temp.klanUyeleri.size(); i++) {
                if (temp.klanUyeleri.get(i).oyuncuAdi.equals(oyuncuAdi)){
                    return temp.klanUyeleri.get(i);
                }
            }
            temp = temp.next;
        }
        return null;
    }

    public KlanRutbeleri rutbeOlustur(int rutbeId, String rutbeAdi,int rutbeSirasi,int davet,int kurucu,int klanBaslangic,int uyeSil,
                                      int klanArazi,int klanHazine){
        KlanRutbeleri rutbe = new KlanRutbeleri();
        rutbe.rutbeSira = rutbeSirasi;
        rutbe.rutbeAdi = rutbeAdi;
        rutbe.rutbeId = rutbeId;

        if (davet == 1){
            rutbe.yetkiler.add(Yetkiler.davet);
        }
        if (kurucu == 1){
            rutbe.yetkiler.add(Yetkiler.kurucu);
        }
        if (klanBaslangic == 1){
            rutbe.yetkiler.add(Yetkiler.klanBaslangicAyarla);
        }
        if (uyeSil == 1){
            rutbe.yetkiler.add(Yetkiler.uyeSil);
        }
        if (klanArazi == 1){
            rutbe.yetkiler.add(Yetkiler.klanArazi);
        }
        if (klanHazine == 1){
            rutbe.yetkiler.add(Yetkiler.klanHazine);
        }
        return rutbe;
    }

    public KlanOyuncuBilgi oyuncuBilgiLinkOlustur(String oyuncuAdi,KlanRutbeleri rutbesi){
        KlanOyuncuBilgi oyuncu = new KlanOyuncuBilgi();
        oyuncu.oyuncuAdi = oyuncuAdi;
        oyuncu.rutbe = rutbesi;
        return oyuncu;
    }

    public KlanRutbeleri rutbeBul(String rutbeAdi,KlanBilgiLink klan){
        for (int i = 0; i < klan.klanRutbeleri.size(); i++) {
            if (klan.klanRutbeleri.get(i).rutbeAdi.equals(rutbeAdi)){
                return klan.klanRutbeleri.get(i);
            }
        }
    return null;
    }

    public void klanaOyuncuEkle(String oyuncuAdi,KlanRutbeleri rutbe,KlanBilgiLink klan){
        KlanOyuncuBilgi oyuncu = new KlanOyuncuBilgi();
        oyuncu.oyuncuAdi = oyuncuAdi;
        oyuncu.rutbe = rutbe;

        klan.klanUyeleri.add(oyuncu);
    }


    //claim işlemleri

    public String arsaAciklamaBul(String klanAdi){
        KlanBilgiLink klan = klanBulKlanAdi(klanAdi);
        if (klan != null){
            return klan.arsaAciklamasi;
        }
        return "";
    }

}

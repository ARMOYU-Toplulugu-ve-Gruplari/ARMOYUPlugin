package armoyuplugin.armoyuplugin.Listeler.KlanListesi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.apiService;
import static armoyuplugin.armoyuplugin.ARMOYUPlugin.claimListesi;

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
                    rutbe.davet = 0;
                    rutbe.kurucu = 0;
                    rutbe.uyeDuzenle = 0;
                    rutbe.klanBaslangicAyarla = 0;

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
                    kurucuRutbesi.kurucu = 1;
                    kurucuRutbesi.davet = 1;
                    kurucuRutbesi.rutbeAdi = "Lider";
                    kurucuRutbesi.rutbeSira = 1;
                    kurucuRutbesi.uyeDuzenle = 1;
                    kurucuRutbesi.klanBaslangicAyarla = 1;

                    klan.klanRutbeleri.add(kurucuRutbesi);

                    KlanOyuncuBilgi kurucu = new KlanOyuncuBilgi();
                    kurucu.oyuncuAdi=p.getName();
                    kurucu.rutbe=kurucuRutbesi;
                    klan.klanUyeleri.add(kurucu);

                    klan.next = head;
                    head = klan;
                }

            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Klan adi kullanılıyor.");

    }

    public void klanAyril(Player p,JSONObject yollanacaklar,String link){
        if (hangiKlanaUye(p.getName()).isEmpty()){
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
            KlanBilgiLink klan = klanBulKlanAdi(klanAdi);
            if (klan.klanKurucu.equals(p.getName())){
                klan.klanKurucu = "";
                klan.klanAdi = "";
                klan.klanUyeleri.clear();
                klan.klanRutbeleri.clear();
                klan.arsaAciklamasi = "";
            }
        }
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
            JSONObject json = apiService.postYolla(link, yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            }
        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Önce bir klana üye olmalısın.");
    }

    public void klanDavet(Player p,JSONObject yollanacaklar,String link){
        String klanAdi = hangiKlanaUye(p.getName());
        if (!klanAdi.isEmpty()){
            yollanacaklar.put("grupadi",klanAdi);

            JSONObject json = apiService.postYolla(link, yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            }

        }
    }
    public void klanAciklama(Player p,JSONObject yollanacaklar,String link){
        String klanAdi = hangiKlanaUye(p.getName());
        if (!klanAdi.isEmpty()){
            yollanacaklar.put("grupadi",klanAdi);

            JSONObject json = apiService.postYolla(link, yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            }

        }
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



    public void klandanAt(String atan,String atilan){
        KlanBilgiLink temp = head;
        int atanSira = -1;
        int atilanSira = -1;
        while (temp != null){
            for (int i = 0; i < temp.klanUyeleri.size(); i++) {
                if (temp.klanUyeleri.get(i).oyuncuAdi.equals(atan)){
                    if (temp.klanUyeleri.get(i).rutbe.uyeDuzenle == 1){
                    atanSira = i;
                        }
                    }
                if (temp.klanUyeleri.get(i).oyuncuAdi.equals(atilan)){
                    atilanSira = i;
                }
                if (atanSira != -1 && atilanSira != -1){
                    klandanCikar(atilan);
                    claimListesi.klandanAyrilClaim(atilan);
                }
            }
            temp = temp.next;
        }
    }
    public void yetkiVer(String yetkiyiVeren,String kime,String yetkiIsmi){

    }
    public String klanLideri(String oyuncuIsmi){
        return "";
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
                    if (temp.klanUyeleri.get(i).rutbe.kurucu == 1){
                        return temp;
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

    public KlanRutbeleri rutbeOlustur(String rutbeAdi,int rutbeSirasi,int davet,int kurucu,int baslangicAyarla,int uyeYonetim){
        KlanRutbeleri rutbe = new KlanRutbeleri();
        rutbe.rutbeSira = rutbeSirasi;
        rutbe.rutbeAdi = rutbeAdi;
        rutbe.klanBaslangicAyarla = baslangicAyarla;
        rutbe.kurucu = kurucu;
        rutbe.uyeDuzenle = uyeYonetim;
        rutbe.davet = davet;

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

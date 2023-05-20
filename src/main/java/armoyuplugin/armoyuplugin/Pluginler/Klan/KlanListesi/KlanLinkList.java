package armoyuplugin.armoyuplugin.Pluginler.Klan.KlanListesi;

import armoyuplugin.armoyuplugin.OyuncuBilgiListesi.OyuncuBilgiLink;
import org.bukkit.entity.Player;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.claimListesi;

public class KlanLinkList {
    public KlanBilgiLink head;
    public KlanLinkList() {
        head = null;
    }
    public KlanBilgiLink klanOlustur(String olusturan,String klanAdi){

            if (klanBulKlanAdi(klanAdi)==null){
                KlanBilgiLink klan = new KlanBilgiLink();
                klan.klanAdi = klanAdi;
                klan.klanKurucu = olusturan;

                KlanRutbeleri kurucuRutbesi = new KlanRutbeleri();
                kurucuRutbesi.kurucu = 1;
                kurucuRutbesi.davet = 1;
                kurucuRutbesi.rutbeAdi = "kurucu";
                kurucuRutbesi.rutbeSira = 1;
                kurucuRutbesi.uyeDuzenle = 1;
                kurucuRutbesi.klanBaslangicAyarla = 1;

                klan.klanRutbeleri.add(kurucuRutbesi);

                KlanOyuncuBilgi kurucu = new KlanOyuncuBilgi();
                kurucu.oyuncuAdi=olusturan;
                kurucu.rutbe=kurucuRutbesi;
                klan.klanUyeleri.add(kurucu);

                klan.next = head;
                head = klan;
                return klan;
            }

            return null;

    }
    public KlanBilgiLink apiKlanOlustur(String olusturan,String klanAdi){

        if (klanBulKlanAdi(klanAdi)==null){
            KlanBilgiLink klan = new KlanBilgiLink();
            klan.klanAdi = klanAdi;
            klan.klanKurucu = olusturan;

            klan.next = head;
            head = klan;
            return klan;
        }

        return null;

    }
    public void klanaKatil(String katilan,String katan,String klanAdi){
        String uyeOlabilirmi = hangiKlanaUye(katilan);
        KlanBilgiLink temp = head;
        if (uyeOlabilirmi != null){
            while (temp != null){
                if (temp.klanAdi.equals(klanAdi)){
                    for (int i = 0; i < temp.klanUyeleri.size(); i++) {
                        if (temp.klanUyeleri.get(i).oyuncuAdi.equals(katan)){
                            if (temp.klanUyeleri.get(i).rutbe.uyeDuzenle == 1){
                                KlanRutbeleri rutbe = new KlanRutbeleri();
                                rutbe.rutbeSira = 0;
                                rutbe.rutbeAdi = "Çapulcu";
                                rutbe.davet = 0;
                                rutbe.kurucu = 0;
                                rutbe.uyeDuzenle = 0;
                                rutbe.klanBaslangicAyarla = 0;

                                KlanOyuncuBilgi eklenilen = new KlanOyuncuBilgi();
                                eklenilen.oyuncuAdi = katilan;
                                eklenilen.rutbe = rutbe;

                                temp.klanUyeleri.add(eklenilen);
                            }
                            break;
                        }
                    }
                    break;
                }
                temp = temp.next;
            }
        }
    }
    public void klandanAyril(String ayrilan){
        claimListesi.klandanAyrilClaim(ayrilan);
        klandanCikar(ayrilan);
    }
    public void klanaDavetEt(String davetiYollayan,String katilacakOlan){

    }
    public void klanBaslangicAyarla(String baslangicAyarlayan,int x,int y,int z){
        String klanAdi = hangiKlanaUye(baslangicAyarlayan);
        if (klanAdi != null){
            KlanBilgiLink temp = klanBulKlanAdi(klanAdi);
            if (temp != null){
                if (oyuncuBilgi(baslangicAyarlayan).rutbe.klanBaslangicAyarla == 1){
                    temp.klanBaslangicNoktasi = new int[]{x,y,z};
                }
            }
        }

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
        return null;
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
    private boolean kurucuMu(String oyuncuIsmi){
        KlanBilgiLink temp = head;
        int k = 0;
        while (temp!= null){
            for (int i = 0; i < temp.klanUyeleri.size(); i++) {
                if (temp.klanUyeleri.get(i).oyuncuAdi.equals(oyuncuIsmi)){
                    k++;
                    if (temp.klanUyeleri.get(i).rutbe.kurucu == 1){
                        return true;
                    }
                }
            }
            if (k!=0){
                return false;
            }
            temp = temp.next;
        }
        return false;
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
        return null;
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



}

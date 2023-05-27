package armoyuplugin.armoyuplugin.OyuncuBilgiListesi;

public class OyuncuLinkList {
    public OyuncuBilgiLink head;

    public OyuncuLinkList() {
        head = null;
    }
    public boolean oyuncuEkle(String kullaniciAdi){
        OyuncuBilgiLink temp = head;
        while (temp != null){
            if (temp.oyuncuAdi.equals(kullaniciAdi)){
                return false;
            }
            temp = temp.next;
        }
        OyuncuBilgiLink oyuncu = new OyuncuBilgiLink();
        oyuncu.oyuncuAdi = kullaniciAdi;
        oyuncu.oncekiGecilenAraziSahibi = "null";
        if (head == null){
            head = oyuncu;
            return true;
        }

        oyuncu.next=head;
        head=oyuncu;

        return true;
    }

    public OyuncuBilgiLink oyuncuBul(String oyuncuAdi){
        OyuncuBilgiLink temp = head;
        while (temp != null){
            if (temp.oyuncuAdi.equals(oyuncuAdi)){
                return temp;
            }
            temp=temp.next;
        }
        return null;
    }
}

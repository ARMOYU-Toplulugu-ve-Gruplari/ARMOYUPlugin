package armoyuplugin.armoyuplugin.OyuncuBilgiListesi;

public class LinkList {
    public OyuncuBilgiLink head;


    public LinkList() {
        head = null;
    }
    public void oyuncuEkle(String kullaniciAdi,String klanAdi){

    }
    public String klanAdiBul(String oyuncuAdi){
        OyuncuBilgiLink temp = oyuncuBul(oyuncuAdi);
        if (temp != null){
            return temp.oyuncuKlani;
        }
        return "";
    }


    private OyuncuBilgiLink oyuncuBul(String oyuncuAdi){
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

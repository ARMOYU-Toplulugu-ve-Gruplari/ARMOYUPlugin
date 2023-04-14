package armoyuplugin.armoyuplugin.ClaimPlugin.ClaimListesi;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.time.Duration;

public class LinkList {

    String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";

    public Link head;
    public Link tail;

    private final ARMOYUPlugin plugin = ARMOYUPlugin.getPlugin();

    public LinkList() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return head == null;
        //head == null ise true gönder
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
    public void claimAl(String claim, String pName,String dunya) {
        Link current = head;
        while (current != null) {
            if (current.oyuncu.equals(pName)){
                TrustLink newLink = new TrustLink();
                newLink.arsaKonum = claim;
                newLink.arsaDunya = dunya;
                newLink.trustVerilenler.add(pName);
                current.trustlar.add(newLink);

                break;
            }
            current = current.next;
        }
    }

    public void removeTrust(Chunk chunk,Player silen,String silinen,String dunya){
        int trustuVarmi =0;
        Link current = head;
        while (current != null) {
            if (current.oyuncu.equals(silen.getName())) {
                if (silen.getName().equals(silinen)) {
                    silen.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Kendi arsandan kendini silemezsin");
                } else {
                    for (int i = 0; i < current.trustlar.size(); i++) {
                        if (current.trustlar.get(i).arsaDunya.equals(dunya)){
                        if (current.trustlar.get(i).arsaKonum.equals(silen.getPlayer().getLocation().getChunk().toString())) {
                            for (int j = 0; j < current.trustlar.get(i).trustVerilenler.size(); j++) {
                                if (current.trustlar.get(i).trustVerilenler.get(j).equals(silinen)) {
                                    trustuVarmi++;
                                    break;
                                }
                            }

                            if (trustuVarmi == 0) {


                            } else {

                                current.trustlar.get(i).trustVerilenler.remove(silinen);
                            }

                            break;
                        }
                        }
                    }

                }
            }
            current = current.next;
        }
    }
    public void birTrustVer(String chunk,String ekleyen, String eklenen,String dunya){
        int trustuVarmi =0;
        Link current = head;
        while (current != null) {
            if (current.oyuncu.equals(ekleyen)) {

                    for (int i = 0; i < current.trustlar.size(); i++) {
                        if (current.trustlar.get(i).arsaDunya.equals(dunya)) {
                            if (current.trustlar.get(i).arsaKonum.equals(chunk)) {
                                for (int j = 0; j < current.trustlar.get(i).trustVerilenler.size(); j++) {
                                    if (current.trustlar.get(i).trustVerilenler.get(j).equals(eklenen)) {
                                        trustuVarmi++;
                                        break;
                                    }
                                }


                                if (trustuVarmi == 0) {
                                    current.trustlar.get(i).trustVerilenler.add(eklenen);
                                }
                                break;
                            }

                        }

                    }

                break;
            }
            current = current.next;
        }
    }

    public void arsaDevret(){

    }

    public void trustVerHepsi(String ekleyen,String eklenen){
        int trustuVarmi =0;
        Link current = head;
        while (current != null) {
            if (current.oyuncu.equals(ekleyen)) {
                    for (int i = 0; i < current.trustlar.size(); i++) {
                        trustuVarmi=0;
                            for (int j = 0; j < current.trustlar.get(i).trustVerilenler.size(); j++) {
                                if (current.trustlar.get(i).trustVerilenler.get(j).equals(eklenen)) {
                                    trustuVarmi++;
                                    break;
                                }
                            }

                            if (trustuVarmi == 0) {
                                current.trustlar.get(i).trustVerilenler.add(eklenen);
                            }
                    }
                break;
            }
            current = current.next;
        }
    }

    public void trustSilHepsi(String ekleyen, String silinen){
        int trustuVarmi = 0;
        Link current = head;
        while (current != null) {
            if (current.oyuncu.equals(ekleyen)) {

                    for (int i = 0; i < current.trustlar.size(); i++) {
                        trustuVarmi=0;
                        for (int j = 0; j < current.trustlar.get(i).trustVerilenler.size(); j++) {
                            if (current.trustlar.get(i).trustVerilenler.get(j).equals(silinen)) {
                                trustuVarmi++;
                                break;
                            }
                        }
                        if (trustuVarmi != 0) {
                            current.trustlar.get(i).trustVerilenler.remove(silinen);
                        }
                    }
                break;
            }
            current = current.next;
        }

    }

    public void deleteOneChunk(Player silen,String dunya) {

        Link temp = head;
        while (temp != null) {
            for (int i = 0; i < temp.trustlar.size(); i++) {
                if (temp.trustlar.get(i).arsaDunya.equals(dunya)) {
                    if (temp.trustlar.get(i).arsaKonum.equals(silen.getPlayer().getLocation().getChunk().toString())) {
                        if (temp.trustlar.get(i).trustVerilenler.get(0).equals(silen.getName())) {
                            temp.trustlar.get(i).arsaKonum = null;
                            temp.trustlar.get(i).arsaDunya = null;
                            temp.trustlar.get(i).trustVerilenler.clear();
                            temp.trustlar.remove(i);
                        } else
                            silen.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Baskasinin arsasini silemezsin");
                        break;
                    }

                }

            }
            temp = temp.next;
        }

    }

    public void claimSilHepsi(Player silen) {

        Link temp = head;
        while (temp != null) {
            if (temp.oyuncu.equals(silen.getName())){
            temp.trustlar.clear();
            }

            temp = temp.next;
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
    public void chunkControlEkranaYaziYazdirma(Player p, Chunk chunk,String dunya,ARMOYUPlugin plugin){
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

    public void arsaAciklamaDegisme(Player p,String[] diziAciklama){
        String aciklama= "";
        for (int i = 1; i < diziAciklama.length; i++) {
            aciklama = aciklama + diziAciklama[i] + " ";
        }
        Link temp = head;
        while (temp != null) {
            if (temp.oyuncu.equals(p.getName())){
                if (aciklama.length()>25){
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arsa aciklamasi en fazla 25 karakter uzunlugunda olabilir");
                } else {
                    temp.arsaAciklamasi = aciklama;
                    p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + "Arsa açıklaması ayarlandı");
                }

            }

            temp = temp.next;
        }
    }

    public int chunkControl(Player p,String chunk,String dunya){
        if (head == null)
            return 0;
        int arsadolu = 0;
        int trustvarmi = 0;

            Link temp = head;
            while (temp != null) {
                for (int i = 0; i < temp.trustlar.size(); i++) {
                    if (temp.trustlar.get(i).arsaDunya.equals(dunya)) {
                        if (temp.trustlar.get(i).arsaKonum.equals(chunk)) {
                            for (int j = 0; j < temp.trustlar.get(i).trustVerilenler.size(); j++) {
                                if (temp.trustlar.get(i).trustVerilenler.get(j).equals(p.getName())) {
                                    trustvarmi++;
                                    break;
                                }
                            }

                            arsadolu++;
                            break;
                        }
                    }
                }

                temp = temp.next;
            }

        if (trustvarmi!=0){
            return 1;
        } else if (arsadolu !=0) {
            return 2;
        }
       return 0;
    }
    public String claimSahipKim(Chunk chunk,String dunya){
        if (head == null)
            return "Claimsiz";

        Link temp = head;
        while (temp != null) {
            for (int i = 0; i < temp.trustlar.size(); i++) {
                if (temp.trustlar.get(i).arsaDunya.equals(dunya)) {
                    if (temp.trustlar.get(i).arsaKonum.equals(chunk.toString())) {

                        return temp.trustlar.get(i).trustVerilenler.get(0);
                    }
                }
            }

            temp = temp.next;
        }
        return "Claimsiz";
    }
}
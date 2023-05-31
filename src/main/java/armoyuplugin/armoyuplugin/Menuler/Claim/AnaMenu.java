package armoyuplugin.armoyuplugin.Menuler.Claim;

import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AnaMenu extends Menu {
    public AnaMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Claim Bilgi";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        switch (e.getCurrentItem().getType()){
            case ITEM_FRAME:
                MenuManager.openMenu(ClaimListesi.class,playerMenuUtility.getOwner());
        }
    }

    @Override
    public void setMenuItems() {
        ArrayList<String> aciklama = new ArrayList<>();

        aciklama.add(ColorTranslator.translateColorCodes("&e&l/claim al"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lÜstünde bulunduğunuz chunkı arazilerinize ekler"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lChunkları görmek için f3 + g tuşlarına basınız"));
        ItemStack sifir = makeItem(Material.PAPER, "Claim Bilgi");
        ItemMeta meta = sifir.getItemMeta();
        meta.setLore(aciklama);
        sifir.setItemMeta(meta);
        inventory.setItem(0,sifir);


        aciklama.clear();
        aciklama.add(ColorTranslator.translateColorCodes("&e&l/claim trust <oyuncuismi>"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lÜstünde bulunduğunuz chunk için trust"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lChunkları görmek için f3 + g tuşlarına basınız"));
        ItemStack bir = makeItem(Material.PAPER, "Claim Bilgi");
        ItemMeta metabir = bir.getItemMeta();
        metabir.setLore(aciklama);
        bir.setItemMeta(metabir);
        inventory.setItem(1,bir);

        aciklama.clear();
        aciklama.add(ColorTranslator.translateColorCodes("&e&l/claim untrust <oyuncuismi>"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lÜstünde bulunduğunuz chunk için untrust"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lChunkları görmek için f3 + g tuşlarına basınız"));
        ItemStack iki = makeItem(Material.PAPER, "Claim Bilgi");
        ItemMeta metaiki = iki.getItemMeta();
        metaiki.setLore(aciklama);
        iki.setItemMeta(metaiki);
        inventory.setItem(2,iki);

        aciklama.clear();
        aciklama.add(ColorTranslator.translateColorCodes("&e&l/claim sil"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lÜstünde bulunduğunuz chunk için claiminizi siler"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lChunkları görmek için f3 + g tuşlarına basınız"));
        ItemStack uc = makeItem(Material.PAPER, "Claim Bilgi");
        ItemMeta metauc = uc.getItemMeta();
        metauc.setLore(aciklama);
        uc.setItemMeta(metauc);
        inventory.setItem(3,uc);

        aciklama.clear();
        aciklama.add(ColorTranslator.translateColorCodes("&e&l/claim heryeri sil"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lBütün arazilerinizi siler"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lChunkları görmek için f3 + g tuşlarına basınız"));
        ItemStack dort = makeItem(Material.PAPER, "Claim Bilgi");
        ItemMeta metadort = dort.getItemMeta();
        metadort.setLore(aciklama);
        dort.setItemMeta(metadort);
        inventory.setItem(4,dort);

        aciklama.clear();
        aciklama.add(ColorTranslator.translateColorCodes("&e&l/claim heryere ekle <oyuncuismi>"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lBütün arazilerinizde oyuncuya trust verirsiniz"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lChunkları görmek için f3 + g tuşlarına basınız"));
        ItemStack bes = makeItem(Material.PAPER, "Claim Bilgi");
        ItemMeta metabes = bes.getItemMeta();
        metabes.setLore(aciklama);
        bes.setItemMeta(metabes);
        inventory.setItem(5,bes);

        aciklama.clear();
        aciklama.add(ColorTranslator.translateColorCodes("&e&l/claim heryerden cikar <oyuncuismi>"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lBütün arazilerinizden oyuncunun trustunu siler"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lChunkları görmek için f3 + g tuşlarına basınız"));
        ItemStack alti = makeItem(Material.PAPER, "Claim Bilgi");
        ItemMeta metaalti = alti.getItemMeta();
        metaalti.setLore(aciklama);
        alti.setItemMeta(metaalti);
        inventory.setItem(6,alti);

        aciklama.clear();
        aciklama.add(ColorTranslator.translateColorCodes("&e&l/claim aciklama <aciklama>"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lOyuncuların arazinize girdiklerinde görecekleri yazı"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lDikkat en fazla 25 karakter olabilir"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lChunkları görmek için f3 + g tuşlarına basınız"));
        ItemStack yedi = makeItem(Material.PAPER, "Claim Bilgi");
        ItemMeta metayedi = yedi.getItemMeta();
        metayedi.setLore(aciklama);
        yedi.setItemMeta(metayedi);
        inventory.setItem(7,yedi);

        aciklama.clear();
        aciklama.add(ColorTranslator.translateColorCodes("&e&lEtrafınızdaki chunkların durumunu görmek için tıklayınız"));
        aciklama.add(ColorTranslator.translateColorCodes("&e&lChunkları görmek için f3 + g tuşlarına basınız"));
        ItemStack sekiz = makeItem(Material.ITEM_FRAME, "Claim Bilgi");
        ItemMeta metasekiz = sekiz.getItemMeta();
        metasekiz.setLore(aciklama);
        sekiz.setItemMeta(metasekiz);
        inventory.setItem(8,sekiz);


    }
}

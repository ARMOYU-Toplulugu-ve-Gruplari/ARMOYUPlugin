package armoyuplugin.armoyuplugin.ClaimPlugin.menuler;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.yeniListe;

public class ClaimListesi extends Menu {
    public ClaimListesi(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "claimler";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {



    }

    @Override
    public void setMenuItems() {
        ItemStack deneme;
        int m = 0;
        Chunk x = p.getWorld().getChunkAt(p.getLocation().getChunk().getX()-4, p.getLocation().getChunk().getZ()-2);
        Chunk z = p.getWorld().getChunkAt(p.getLocation().getChunk().getX()-4, p.getLocation().getChunk().getZ()-2);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (yeniListe.chunkControl(p.getName(),x.toString(),p.getWorld().toString()) == 0){
                    if (m!=22){
                    deneme = makeItem(Material.WHITE_STAINED_GLASS, ColorTranslator.translateColorCodes("&e&lSahipsiz arazi"));
                    inventory.setItem(m,deneme);
                    m++;}else {
                        deneme = makeItem(Material.WHITE_STAINED_GLASS, ColorTranslator.translateColorCodes("&e&lSahipsiz arazi ve buradasın"));
                        inventory.setItem(m,deneme);
                        m++;
                    }
                }else if (yeniListe.chunkControl(p.getName(),x.toString(),p.getWorld().toString())==2){
                    String sahip = yeniListe.claimSahipKim(x.toString(),p.getWorld().toString());
                    if (m!=22){
                    deneme = makeItem(Material.RED_STAINED_GLASS, ColorTranslator.translateColorCodes("&e&lArazi sahibi "+ sahip));
                    inventory.setItem(m,deneme);
                    m++;}else {
                        deneme = makeItem(Material.RED_STAINED_GLASS, ColorTranslator.translateColorCodes("&e&lArazi sahibi " + sahip+" ve buradasın"));
                        inventory.setItem(m,deneme);
                        m++;
                    }
                }else{
                    String sahip = yeniListe.claimSahipKim(x.toString(),p.getWorld().toString());
                    if (m!=22){
                        deneme = makeItem(Material.GREEN_STAINED_GLASS, ColorTranslator.translateColorCodes("&e&lArazi sahibi " + sahip));
                        inventory.setItem(m,deneme);
                        m++;}else {
                        deneme = makeItem(Material.GREEN_STAINED_GLASS, ColorTranslator.translateColorCodes("&e&lArazi sahibi " + sahip+" ve buradasın"));
                        inventory.setItem(m,deneme);
                        m++;
                    }
                }
                x = x.getWorld().getChunkAt(x.getX()+1, x.getZ());

            }
            x = x.getWorld().getChunkAt(z.getX(),x.getZ()+1);
        }

















    }
}

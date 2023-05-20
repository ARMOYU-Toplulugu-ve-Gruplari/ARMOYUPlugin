package armoyuplugin.armoyuplugin.Pluginler.OzelEsyalar;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class esyayonetim {
    public static ItemStack Poseidonunmizragi;

    public static void init(){
        createPoseidonunmizragi();
    }

    private static void createPoseidonunmizragi(){
        ItemStack item = new ItemStack(Material.TRIDENT,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Poseidonun Mızrak");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW +"Eşya Yeteneği : Tüm büyüleri bozar");
        meta.setLore(lore);

        AttributeModifier hasar= new AttributeModifier(UUID.randomUUID(),"generic.saldirihasari",30,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier hasarikinciel= new AttributeModifier(UUID.randomUUID(),"generic.saldiriikincielhasari",35,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND);
        AttributeModifier guc= new AttributeModifier(UUID.randomUUID(),"generic.saldirigucu",40,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        AttributeModifier hiz= new AttributeModifier(UUID.randomUUID(),"generic.saldirihizi",20,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier dayaniklilik= new AttributeModifier(UUID.randomUUID(),"generic.dayaniklilik",200,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,hasar);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,hasarikinciel);

        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,hiz);



        meta.addEnchant(Enchantment.LUCK,1,false);



//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(false);

        item.setItemMeta(meta);
        Poseidonunmizragi = item;
    }
}

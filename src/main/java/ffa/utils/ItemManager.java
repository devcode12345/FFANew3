package me.devcode.ffa.utils;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemManager {

    //Inet ItemManager

    private ItemStack item;
    private List<String> lore = new ArrayList<String>();
    private ItemMeta meta;
    @SuppressWarnings("unused")
    private String add;
    public static HashMap<String, String> extra = new HashMap<>();

    public ItemManager(Material mat, short subid, int amount) {
        item = new ItemStack(mat, amount, subid);
        meta = item.getItemMeta();
    }

    public ItemManager(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemManager(Material mat, short subid) {
        item = new ItemStack(mat, 1, subid);
        meta = item.getItemMeta();
    }

    public ItemManager(Material mat, int amount) {
        item = new ItemStack(mat, amount, (short)0);
        meta = item.getItemMeta();
    }

    public ItemManager(Material mat) {
        item = new ItemStack(mat, 1, (short)0);
        meta = item.getItemMeta();
    }

    public me.devcode.ffa.utils.ItemManager setExtra(String extr) {
        if(extra.containsKey(meta.getDisplayName())) {
            extra.remove(meta.getDisplayName());
        }
        extra.put(meta.getDisplayName(), extr);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager setAmount(int value) {
        item.setAmount(value);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager setNoName() {
        meta.setDisplayName(" ");
        return this;
    }

    public me.devcode.ffa.utils.ItemManager setGlow() {
        meta.addEnchant( Enchantment.DURABILITY, 1, true);
        meta.addItemFlags( ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager setData(short data) {
        item.setDurability(data);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager addLoreLine(String line) {
        lore.add(line);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager addLoreArray(String[] lines) {
        for(int x = 0; x < lines.length; x++) {
            lore.add(lines[x]);
        }
        return this;
    }

    public me.devcode.ffa.utils.ItemManager addLoreAll(List<String> lines) {
        lore.addAll(lines);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager setSkullOwner(String owner) {
        ((SkullMeta)meta).setOwner(owner);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager setColor(Color c) {
        ((LeatherArmorMeta)meta).setColor(c);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager setBannerColor(DyeColor c) {
        ((BannerMeta)meta).setBaseColor(c);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager setUnbreakable(boolean value) {
        meta.spigot().setUnbreakable(value);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager addEnchantment(Enchantment ench, int lvl) {
        meta.addEnchant(ench, lvl, true);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public me.devcode.ffa.utils.ItemManager addLeatherColor(Color color) {
        ((LeatherArmorMeta) meta).setColor( color );
        return this;
    }

    public ItemStack build() {
        if(lore.isEmpty() == false) {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

}

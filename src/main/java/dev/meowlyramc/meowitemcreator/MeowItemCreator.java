package dev.meowlyramc.meowitemcreator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import dev.lyramc.api.nms.Reflection;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntitySkull;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MeowItemCreator {
    private ItemStack item;

    public MeowItemCreator(final ItemStack item) {
        this.item = item;
    }

    public MeowItemCreator(final Material mat) {
        this.item = new ItemStack(mat);
    }

    public MeowItemCreator(final Material mat, final int amount) {
        this.item = new ItemStack(mat, amount);
    }

    public MeowItemCreator(final Material mat, final byte data) {
        this.item = new ItemStack(mat, 1, data);
    }

    public MeowItemCreator withName(final String name) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
        return this;
    }

    public MeowItemCreator withData(final Byte data) {
        final MaterialData meta = this.item.getData();
        meta.setData(data);
        return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MeowItemCreator withLore(final String... lore) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.setLore((List) Arrays.asList(lore));
        this.item.setItemMeta(meta);
        return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MeowItemCreator withArrayLore(final List<String> lore) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.setLore((List)lore);
        this.item.setItemMeta(meta);
        return this;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MeowItemCreator addLore(final ArrayList<String> lore) {
        final ItemMeta meta = this.item.getItemMeta();
        final List<String> a = (List<String>)meta.getLore();
        for (final String s : lore) {
            a.add(s);
        }
        meta.setLore((List)a);
        this.item.setItemMeta(meta);
        return this;
    }

    @SuppressWarnings("deprecation")
    public MeowItemCreator withColor(int r, int g, int b) {
        final LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
        meta.setColor(Color.fromRGB(r,g,b));
        this.item.setItemMeta(meta);
        return this;
    }

    public MeowItemCreator withOwner(final String owner) {
        if (this.item.getType().equals((Object) Material.SKULL_ITEM)) {
            this.item.setDurability((short)3);
            final SkullMeta m = (SkullMeta)this.item.getItemMeta();
            m.setOwner(owner);
            this.item.setItemMeta((ItemMeta)m);
        }
        return this;
    }

    public MeowItemCreator withAmount(final int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public MeowItemCreator withEnchant(final Enchantment e, final int lvl) {
        this.item.addUnsafeEnchantment(e, lvl);
        return this;
    }

    public MeowItemCreator withEffect(final PotionEffect e) {
        if (!this.item.getType().equals((Object) Material.POTION)) {
            return this;
        }
        final PotionMeta pm = (PotionMeta)this.item.getItemMeta();
        pm.addCustomEffect(e, true);
        this.item.setItemMeta((ItemMeta)pm);
        return this;
    }

    public MeowItemCreator unbreakable() {
        this.item.getItemMeta().spigot().setUnbreakable(true);
        return this;
    }

    public MeowItemCreator withTexture(String url) {
        if(!this.item.getType().equals(Material.SKULL_ITEM) || url == null) return this;
        url = "http://textures.minecraft.net/texture/" + url;
        this.item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        Base64 base64 = new Base64();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        if (propertyMap == null) {
            throw new IllegalStateException("Profile doesn't contain a property map");
        }
        byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        propertyMap.put("textures", new Property("textures", new String(encodedData)));
        ItemMeta headMeta = this.item.getItemMeta();
        Class<?> headMetaClass = headMeta.getClass();
        Reflection.getField(headMetaClass, "profile", GameProfile.class).set(headMeta, profile);
        this.item.setItemMeta(headMeta);
        return this;
    }

    public ItemStack done() {
        return this.item;
    }


}
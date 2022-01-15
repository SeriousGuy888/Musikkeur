package io.github.seriousguy888.musikkeur.utils;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {
  @SuppressWarnings("SameParameterValue")
  public ItemStack createItem(Material material, int count, String name, String... lore) {
    ItemStack item = new ItemStack(material);
    item.setAmount(count);
    ItemMeta meta = item.getItemMeta();
    if(meta == null)
      return item;
    meta.setDisplayName(name);
    meta.setLore(Arrays.asList(lore));
    item.setItemMeta(meta);
    return item;
  }

  public ItemStack createNoteBanner(Note note) {
    boolean sharp = note.isSharped();
    DyeColor white = DyeColor.WHITE;
    DyeColor black = DyeColor.BLACK;

    ItemStack banner = new ItemStack(sharp ? Material.BLACK_BANNER : Material.WHITE_BANNER);
    BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();
    if(bannerMeta == null)
      return null;

    bannerMeta.setDisplayName("" + ChatColor.AQUA +
        note.getTone() + (note.isSharped() ? "#" : "") + " " +
        note.getOctave());

    // hides banner patterns in tooltip https://minecraft.fandom.com/wiki/Player.dat_format#Display_Properties
    bannerMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

    if(sharp) {
      bannerMeta.addPattern(new Pattern(white, PatternType.STRAIGHT_CROSS));
      bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_TOP));
      bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_BOTTOM));
      bannerMeta.addPattern(new Pattern(black, PatternType.BORDER));
    } else {
      switch(note.getTone().name()) {
        case "C":
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_TOP));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_LEFT));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_BOTTOM));
          break;
        case "D":
          bannerMeta.addPattern(new Pattern(black, PatternType.HALF_VERTICAL));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_TOP));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_BOTTOM));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_RIGHT));
          break;
        case "E":
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_TOP));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_LEFT));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_BOTTOM));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_MIDDLE));
          break;
        case "F":
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_TOP));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_LEFT));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_MIDDLE));
          break;
        case "G":
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_RIGHT));
          bannerMeta.addPattern(new Pattern(white, PatternType.HALF_HORIZONTAL));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_TOP));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_LEFT));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_BOTTOM));
          break;
        case "A":
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_TOP));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_LEFT));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_RIGHT));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_MIDDLE));
          break;
        case "B":
          bannerMeta.addPattern(new Pattern(black, PatternType.HALF_VERTICAL));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_TOP));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_BOTTOM));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_MIDDLE));
          bannerMeta.addPattern(new Pattern(black, PatternType.STRIPE_RIGHT));
          break;
        default:
          bannerMeta.addPattern(new Pattern(black, PatternType.CREEPER));
          break;
      }
      bannerMeta.addPattern(new Pattern(white, PatternType.BORDER));
    }

    banner.setItemMeta(bannerMeta);
    return banner;
  }
}

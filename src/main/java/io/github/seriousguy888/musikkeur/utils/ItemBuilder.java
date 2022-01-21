package io.github.seriousguy888.musikkeur.utils;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemBuilder {
  public ItemStack createItem(Material material, String name, String... lore) {
    ItemStack item = new ItemStack(material);
    ItemMeta meta = item.getItemMeta();
    if(meta == null)
      return item;
    meta.setDisplayName(name);
    meta.setLore(Arrays.asList(lore));
    item.setItemMeta(meta);
    return item;
  }

  /**
   * @param note The Note object to generate an representing ItemStack for.
   * @param isSelectedNote Whether this is the currently selected note and should have an enchantment glint.
   * @return An ItemStack of a shield with an appropriate banner design on it. May be enchanted if selected.
   */
  public ItemStack getNoteItem(Note note, boolean isSelectedNote) {
    boolean sharp = note.isSharped();
    DyeColor white = DyeColor.WHITE;
    DyeColor black = DyeColor.BLACK;

    ItemStack banner = addNoteItemMetadata(new ItemStack(Material.WHITE_BANNER), note);
    BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();
    if(bannerMeta == null)
      return null;

    ArrayList<Pattern> patterns = new ArrayList<>();
    patterns.add(new Pattern(sharp ? black : white, PatternType.BASE));



    if(sharp) {
      patterns.add(new Pattern(white, PatternType.STRAIGHT_CROSS));
      patterns.add(new Pattern(black, PatternType.STRIPE_TOP));
      patterns.add(new Pattern(black, PatternType.STRIPE_BOTTOM));
      patterns.add(new Pattern(black, PatternType.BORDER));
    } else {
      switch(note.getTone().name()) {
        case "C":
          patterns.add(new Pattern(black, PatternType.STRIPE_TOP));
          patterns.add(new Pattern(black, PatternType.STRIPE_LEFT));
          patterns.add(new Pattern(black, PatternType.STRIPE_BOTTOM));
          break;
        case "D":
          patterns.add(new Pattern(black, PatternType.HALF_VERTICAL));
          patterns.add(new Pattern(black, PatternType.STRIPE_TOP));
          patterns.add(new Pattern(black, PatternType.STRIPE_BOTTOM));
          patterns.add(new Pattern(black, PatternType.STRIPE_RIGHT));
          break;
        case "E":
          patterns.add(new Pattern(black, PatternType.STRIPE_TOP));
          patterns.add(new Pattern(black, PatternType.STRIPE_LEFT));
          patterns.add(new Pattern(black, PatternType.STRIPE_BOTTOM));
          patterns.add(new Pattern(black, PatternType.STRIPE_MIDDLE));
          break;
        case "F":
          patterns.add(new Pattern(black, PatternType.STRIPE_TOP));
          patterns.add(new Pattern(black, PatternType.STRIPE_LEFT));
          patterns.add(new Pattern(black, PatternType.STRIPE_MIDDLE));
          break;
        case "G":
          patterns.add(new Pattern(black, PatternType.STRIPE_RIGHT));
          patterns.add(new Pattern(white, PatternType.HALF_HORIZONTAL));
          patterns.add(new Pattern(black, PatternType.STRIPE_TOP));
          patterns.add(new Pattern(black, PatternType.STRIPE_LEFT));
          patterns.add(new Pattern(black, PatternType.STRIPE_BOTTOM));
          break;
        case "A":
          patterns.add(new Pattern(black, PatternType.STRIPE_TOP));
          patterns.add(new Pattern(black, PatternType.STRIPE_LEFT));
          patterns.add(new Pattern(black, PatternType.STRIPE_RIGHT));
          patterns.add(new Pattern(black, PatternType.STRIPE_MIDDLE));
          break;
        case "B":
          patterns.add(new Pattern(black, PatternType.HALF_VERTICAL));
          patterns.add(new Pattern(black, PatternType.STRIPE_TOP));
          patterns.add(new Pattern(black, PatternType.STRIPE_BOTTOM));
          patterns.add(new Pattern(black, PatternType.STRIPE_MIDDLE));
          patterns.add(new Pattern(black, PatternType.STRIPE_RIGHT));
          break;
        default:
          patterns.add(new Pattern(black, PatternType.CREEPER));
          break;
      }
      patterns.add(new Pattern(white, PatternType.BORDER));
    }

    bannerMeta.setPatterns(patterns);
    banner.setItemMeta(bannerMeta);

    if(isSelectedNote) { // if this is the selected note, return an enchanted shield instead
      ItemStack shield = addNoteItemMetadata(new ItemStack(Material.SHIELD), note);
      BlockStateMeta shieldMeta = (BlockStateMeta) shield.getItemMeta();
      if(shieldMeta == null)
        return banner;

      Banner shieldBanner = (Banner) shieldMeta.getBlockState(); // get the shield's banner
      shieldBanner.setPatterns(patterns); // add the already created patterns to it
      shieldBanner.update();
      shieldMeta.setBlockState(shieldBanner); // add the banner to the shield
      shieldMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
      shield.setItemMeta(shieldMeta);

      return shield;
    } else { // if not, then just return the banner item
      return banner;
    }
  }

  /**
   * Changes an ItemStack so that it will hide any banner patterns and enchantments.
   * Also names the item accordingly to the note provided.
   * @param item The ItemStack to modify.
   * @param note The note to name the item for.
   * @return The ItemStack with the item flags added to it.
   */
  private ItemStack addNoteItemMetadata(ItemStack item, Note note) {
    ItemMeta itemMeta = item.getItemMeta();
    if(itemMeta == null)
      return item;

    itemMeta.setDisplayName("" + ChatColor.AQUA +
        note.getTone() + (note.isSharped() ? "#" : "") + " " +
        note.getOctave());

    /*
    HIDE_POTION_EFFECTS hides banner patterns in tooltip
      https://minecraft.fandom.com/wiki/Player.dat_format#Display_Properties
    HIDE_ENCHANTS hides any enchantments from the tooltip
     */
    itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ENCHANTS);
    item.setItemMeta(itemMeta);
    return item;
  }
}

package io.github.seriousguy888.musikkeur.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class NoteBlockGui implements Listener {
  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    Block block = event.getClickedBlock();
    if(block == null)
      return;
    if(block.getType() != Material.NOTE_BLOCK)
      return;

    Player player = event.getPlayer();
    if(!player.isSneaking())
      return;

    event.setCancelled(true);

    NoteBlock noteBlock = (NoteBlock) block.getBlockData();
    Instrument instrument = noteBlock.getInstrument();
    Note note = noteBlock.getNote();

    String noteString = String.format("%d-%s%s", note.getOctave(), note.getTone(), note.isSharped() ? "#" : "");

    ChestGui gui = new ChestGui(6, String.format("Note Block [%s %s]", instrument, noteString));
    gui.setOnGlobalClick(e -> e.setCancelled(true));
    gui.setOnGlobalDrag(e -> e.setCancelled(true));

    OutlinePane bgPane = new OutlinePane(0, 0, 9, 6);
    bgPane.addItem(new GuiItem(createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ")));
    bgPane.setRepeat(true);

    gui.addPane(bgPane);

    gui.show(player);
  }


  @SuppressWarnings("SameParameterValue")
  private ItemStack createItem(Material material, int count, String name, String... lore) {
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

}

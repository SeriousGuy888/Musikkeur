package io.github.seriousguy888.musikkeur.guis;

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
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.type.ChestMenu;

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

    Menu menu = ChestMenu.builder(6)
        .title(String.format("Note Block [%s %s]", instrument, noteString))
        .build();
    Mask mask = BinaryMask.builder(menu)
        .item(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))
        .pattern("111111111")
        .pattern("100000001")
        .pattern("100000001")
        .pattern("100000001")
        .pattern("100000001")
        .pattern("111111111")
        .build();
    mask.apply(menu);


    menu.open(player);
  }
}

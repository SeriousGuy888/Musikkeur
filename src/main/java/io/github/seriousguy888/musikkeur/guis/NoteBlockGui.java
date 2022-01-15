package io.github.seriousguy888.musikkeur.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import io.github.seriousguy888.musikkeur.Musikkeur;
import io.github.seriousguy888.musikkeur.utils.ItemBuilder;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class NoteBlockGui implements Listener {
  Musikkeur plugin;
  public NoteBlockGui(Musikkeur plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if(!plugin.musikkeurEnabled.get(player))
      return;

    if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
      return;

    Block block = event.getClickedBlock();
    if(block == null)
      return;
    if(block.getType() != Material.NOTE_BLOCK)
      return;

    event.setCancelled(true);

    NoteBlock noteBlock = (NoteBlock) block.getBlockData();

    ChestGui gui = new ChestGui(6, "Note Block");
    gui.setOnGlobalClick(e -> e.setCancelled(true));
    gui.setOnGlobalDrag(e -> e.setCancelled(true));

    OutlinePane bgPane = new OutlinePane(0, 0, 9, 6, Pane.Priority.LOW);
    bgPane.addItem(new GuiItem(new ItemBuilder().createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ")));
    bgPane.setRepeat(true);
    gui.addPane(bgPane);


    Note[][] guiLayout = {
        { new Note(0), new Note(2), new Note(4), null, null, null, null, null, null },
        { null, new Note(1), new Note(3), new Note(5), null, null, null, null, null },
        { new Note(7), new Note(9), null, new Note(12), new Note(14), new Note(16), null, null, null },
        { new Note(6), new Note(8), new Note(10), new Note(11), new Note(13), new Note(15), new Note(17), null, null },
        { new Note(19), new Note(21), null, new Note(24), null, null, null, null, null },
        { new Note(18), new Note(20), new Note(22), new Note(23), null, null, null, null, null },
    };


    StaticPane keyPane = new StaticPane(0, 0, 9, 6, Pane.Priority.NORMAL);

    for(int y = 0; y < guiLayout.length; y++) {
      Note[] noteRow = guiLayout[y];
      for(int x = 0; x < noteRow.length; x++) {
        Note loopNote = noteRow[x];

        if(loopNote == null)
          continue;

        ItemStack item = new ItemBuilder().createNoteBanner(loopNote);

        keyPane.addItem(new GuiItem(item), x, y);
      }
    }


    keyPane.setOnClick(clickEvent -> {
      HumanEntity clicker = clickEvent.getWhoClicked();

      int slotX = (int) Math.floor((double) clickEvent.getSlot() / 9);
      int slotY = clickEvent.getSlot() % 9;
      Note selectedNote = guiLayout[slotX][slotY];

      if(selectedNote == null)
        return;

      /*
       todo: play the selected note to the clicker when the note is clicked
       make sure to play the note with the correct instrument that the note block is using
       */

      noteBlock.setNote(selectedNote);
      block.setBlockData(noteBlock);
      clicker.closeInventory();
    });


    gui.addPane(keyPane);

    gui.show(player);
  }
}
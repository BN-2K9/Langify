package com.bn_2k9.langifyPaper.framework.gui;

import com.bn_2k9.langifyPaper.framework.gui.utils.GuiMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;

public class GuiListener implements Listener {

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        // Check if the holder instance is a Gui menu.
        if (holder instanceof GuiMenu menu) {
            menu.handleMenuClickEvent(event);
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        // Check if the holder instance is a Gui menu.
        if (holder instanceof GuiMenu menu) {
            menu.handleMenuCloseEvent(event);
        }

    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        // Check if the holder instance is a Gui menu.
        if (holder instanceof GuiMenu menu) {
            menu.handleMenuDragEvent(event);
        }
    }

}

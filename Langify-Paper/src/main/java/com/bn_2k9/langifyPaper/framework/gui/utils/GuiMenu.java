package com.bn_2k9.langifyPaper.framework.gui.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public abstract class GuiMenu implements InventoryHolder {

    protected final GuiMenuUtil guiMenuUtil;
    protected Inventory inventory;
    public ItemStack FILLER = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

    public GuiMenu(GuiMenuUtil guiMenuUtil) {
        this.guiMenuUtil = guiMenuUtil;
    }

    public abstract Component getMenuName();
    public abstract int getSlots();

    public abstract void handleMenuClickEvent(InventoryClickEvent e);
    public abstract void handleMenuDragEvent(InventoryDragEvent e);
    public abstract void handleMenuCloseEvent(InventoryCloseEvent e);

    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        this.setMenuItems();

        guiMenuUtil.getOwner().openInventory(inventory);
    }

    @Override
    public @NonNull Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the remaining inventory slots with the filler item.
     */
    public void setFiller(){
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null){
                inventory.setItem(i, FILLER);
            }
        }
    }

}

package com.bn_2k9.langifyPaper.framework.gui;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyPaper.framework.ComponentParser;
import com.bn_2k9.langifyPaper.framework.gui.utils.GuiMenu;
import com.bn_2k9.langifyPaper.framework.gui.utils.GuiMenuUtil;
import com.bn_2k9.langifyPaper.framework.utils.ItemTools;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class LanguageSelectionGui extends GuiMenu {

    public LanguageSelectionGui(GuiMenuUtil guiMenuUtil) {
        super(guiMenuUtil);
    }

    @Override
    public Component getMenuName() {
        return ComponentParser.parse(LangifyCore.getInstance().getConfig().getGuiName(), guiMenuUtil.getOwner());
    }

    @Override
    public int getSlots() {
        return LangifyCore.getInstance().getConfig().getGuiSize();
    }

    @Override
    public void setMenuItems() {
        ItemStack fillerItem = ItemTools.loadFromConfig(LangifyCore.getInstance().getConfig().getGuiFillerItem());
        // Check if the fillerItems serialized correctly. If so apply it.
        if (fillerItem.getType() != Material.AIR) {
            FILLER = fillerItem;
        }

        // TODO Add code for languages.

        // Apply the filler glass.
        setFiller();
    }

    @Override
    public void handleMenuClickEvent(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void handleMenuDragEvent(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void handleMenuCloseEvent(InventoryCloseEvent event) {}

}

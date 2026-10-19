package com.bn_2k9.langifyPaper.framework.gui;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.managers.LanguageKeysManager;
import com.bn_2k9.langifyCore.framework.managers.LanguageSelectionManager;
import com.bn_2k9.langifyCore.framework.objects.Language;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import com.bn_2k9.langifyPaper.LangifyPaper;
import com.bn_2k9.langifyPaper.framework.ComponentParser;
import com.bn_2k9.langifyPaper.framework.gui.utils.GuiMenu;
import com.bn_2k9.langifyPaper.framework.gui.utils.GuiMenuUtil;
import com.bn_2k9.langifyPaper.framework.utils.ItemTools;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
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

    NamespacedKey namespacedKey = new NamespacedKey(LangifyPaper.getInstance(), "language");

    @Override
    public void setMenuItems() {
        ItemStack fillerItem = ItemTools.loadFromConfigData(LangifyCore.getInstance().getConfig().getGuiFillerItem());
        // Check if the fillerItems serialized correctly. If so apply it.
        if (fillerItem.getType() != Material.BARRIER) {
            FILLER = fillerItem;
            FILLER.editMeta(itemMeta -> {
                itemMeta.setHideTooltip(true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            });
            FILLER.setData(DataComponentTypes.CUSTOM_NAME, ComponentParser.parse(" "));
        }

        for (String key : LanguageKeysManager.getInstance().getLanguageKeys().keySet()) {
            Language language = LanguageKeysManager.getInstance().getLanguageKeys().get(key);
            if (!language.isShowInGui()) {
                continue;
            }

            ItemStack itemStack = ItemTools.loadFromConfigData(language.getMaterialData());
            if (itemStack == null) {
                LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "Gui item for language: " + language.getName() + " has failed to parse. Check the material key.");
                continue;
            }
            // Apply the custom lore if set.
            if (!language.getLore().isEmpty()) {
                List<Component> lore = new ArrayList<>();
                for (String string : language.getLore()) {
                    lore.add(ComponentParser.parse(string));
                }
                itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(lore));
            }

            // Apply the custom name. And make sure it is not italic.
            itemStack.setData(DataComponentTypes.CUSTOM_NAME, ComponentParser.parse(language.getItemName()).decoration(TextDecoration.ITALIC, false));
            itemStack.editPersistentDataContainer(dataContainer -> dataContainer.set(namespacedKey, PersistentDataType.STRING, language.getName()));

            // Set the item in the inventory.
            int requestedSlot = language.getInventorySlot();
            if (requestedSlot < 0) {
                // it there is no requested slot set it to the first empty.
                inventory.setItem(inventory.firstEmpty(), itemStack);
            } else {
                // Set the item to the requested slot.
                if (inventory.getItem(requestedSlot) == null) {
                    inventory.setItem(requestedSlot, itemStack);
                } else {
                    LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "2 or more items share the same slot. Please check the Language configs.");
                }
            }
        }

        // Apply the filler glass.
        setFiller();
    }

    @Override
    public void handleMenuClickEvent(InventoryClickEvent event) {

        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().getPersistentDataContainer().has(namespacedKey)) {
                LanguageSelectionManager.getInstance().setSelectedLanguageForPlayer(event.getWhoClicked().getUniqueId(), event.getCurrentItem().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING));
                event.getWhoClicked().sendMessage(ComponentParser.parse("<green>Applied language: " + event.getCurrentItem().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING)));
                event.getWhoClicked().closeInventory();
            }
        }

        event.setCancelled(true);
    }

    @Override
    public void handleMenuDragEvent(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void handleMenuCloseEvent(InventoryCloseEvent event) {}

}

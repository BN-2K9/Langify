package com.bn_2k9.langifyPaper.framework.integrations;

import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import com.bn_2k9.langifyPaper.LangifyPaper;
import com.bn_2k9.langifyPaper.framework.ComponentParser;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ItemsAdderIntegration implements Listener {

    @Getter
    private static List<String> nameSpacedItems = new ArrayList<>();

    @Getter
    private static boolean isLoaded = false;

    // Make sure that ItemsAdder is loaded.
    @EventHandler
    public void itemsAdderLoadEvent(ItemsAdderLoadDataEvent event) {
        isLoaded = true;

        nameSpacedItems = CustomStack.getNamespacedIdsInRegistry().stream().toList();
    }

    /**
     * Prepares a string that is fitting for a selected gui with arguments. (Sadly because itemsAdders api sucks...)
     * @param textureNamespace The TextureNamespacedName.
     * @param textureOffset The offset of the texture.
     * @param inventoryTitle The inventory title.
     * @param titleOffset The title offset.
     * @return The generated string.
     */
    public static Component getGuiImageString(@Nullable String textureNamespace, int textureOffset, String inventoryTitle, int titleOffset) {

        if (!isLoaded()) {
            return ComponentParser.parse("<red>Could not create font image. ItemsAdder not found.");
        }

        if (inventoryTitle.isEmpty()) {
            return ComponentParser.parse("Inventory Title not given/Empty. Please provide one.");
        }

        // If no texture path is set return the inventory name.
        if (textureNamespace == null || textureNamespace.isEmpty()) {
            return ComponentParser.parse(inventoryTitle);
        }

        FontImageWrapper fontImageWrapper = new FontImageWrapper(textureNamespace);
        String image = FontImageWrapper.applyPixelsOffsetToString(fontImageWrapper.getString(), textureOffset);

        int fontImageWith = -fontImageWrapper.getWidth();
        String title = FontImageWrapper.applyPixelsOffsetToString(inventoryTitle, fontImageWith + titleOffset);

        return ComponentParser.parse(image).color(fontImageWrapper.getColor()).append(MiniMessage.miniMessage().deserialize("<white>" + title));
    }

    /**
     * Get an ItemStack configured in the itemsadder configs.
     * @param string The custom ItemsAdder namespacedID.
     * @return The custom ItemStack. If not found an Air itemStack will be returned.
     */
    public static ItemStack getCustomItem(String string) {

        if (!ItemsAdderIntegration.getNameSpacedItems().contains(string) || string.isEmpty()) {
            LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "Item: " + string + " not found in the itemsAdder cache.");
            return new ItemStack(Material.AIR);
        }

        CustomStack customStack = CustomStack.getInstance(string);
        return customStack.getItemStack();
    }

}

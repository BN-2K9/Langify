package com.bn_2k9.langifyPaper.framework.utils;

import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import com.bn_2k9.langifyPaper.LangifyPaper;
import com.bn_2k9.langifyPaper.framework.integrations.ItemsAdderIntegration;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.Base64;

@SuppressWarnings("unused")
public class ItemTools {

    /**
     * Consume a given amount of items from the itemStack. If there are no items left it wil return Material.Air
     * @param itemStack The itemStack that needs to be consumed.
     * @param integer The amount of items that need to be consumed.
     * @return The resulted itemStack.
     */
    public static ItemStack consumeItem (ItemStack itemStack, Integer integer) {

        if (itemStack.getAmount() > integer) {
            itemStack.setAmount(itemStack.getAmount() - integer);
        } else {
            itemStack = new ItemStack(Material.AIR);
        }

        return itemStack;
    }

    /**
     * Serialized an itemStack to a base64 string.
     * @param item The item that needs to be serialized.
     * @return returns the base64 string.
     */
    public static String itemToBase64(ItemStack item) {

        if (item == null) {
            return null;
        }

        byte[] bytes = item.serializeAsBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Deserialize an ItemStack form a base64 string.
     * @param base64 The base64 string that will be used to Deserialize the itemStack.
     * @return The itemStack that has been Deserialized.
     */
    public static ItemStack base64ToItem(String base64) {

        if (base64 == null) {
            return null;
        }

        byte[] bytes = Base64.getDecoder().decode(base64);
        return ItemStack.deserializeBytes(bytes);
    }

    /**
     * Get an ItemStack configured in the plugin config.
     * @param itemData The custom item. Either The ItemsAdder:namespace:item. Or the serialized base64 item string.
     * @return The custom ItemStack. If not found an Air itemStack will be returned.
     */
    public static ItemStack loadFromConfig(String itemData) {

        ItemStack failItemStack = new ItemStack(Material.AIR);

        if (itemData == null || itemData.isEmpty()) {
            return failItemStack;
        }

        if (itemData.contains("ItemsAdder:")) {

            String itemsAdderId = itemData.replace("ItemsAdder:", "");
            if (ItemsAdderIntegration.getNameSpacedItems().contains(itemsAdderId)) {
                CustomStack itemStack = CustomStack.getInstance(itemsAdderId);

                if (itemStack != null) {
                    return itemStack.getItemStack().clone();
                } else {
                    LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "Cannot get the itemsAdder stack.");
                    return failItemStack;
                }

            } else {
                LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "Did not find: " + itemsAdderId + " In ItemsAdder.");
                return failItemStack;
            }

        } else {

            ItemStack itemStack = base64ToItem(itemData);
            if (itemStack == null) {
                LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "Cannot load from base64 String to ItemStack.");
                return failItemStack;
            } else {
                return itemStack;
            }

        }

    }

}

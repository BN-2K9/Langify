package com.bn_2k9.langifyCore.framework.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Language {

    private String name;
    private String itemName;
    private String materialData;
    private int inventorySlot;
    private List<String> lore;
    private boolean showInGui;
    private HashMap<String, String> keys;

    public Language(String name, String itemName, String materialData, int inventorySlot, List<String> lore, boolean showInGui, HashMap<String, String> keys) {
        this.name = name;
        this.itemName = itemName;
        this.materialData = materialData;
        this.inventorySlot = inventorySlot;
        this.lore = lore;
        this.showInGui = showInGui;
        this.keys = keys;
    }

}

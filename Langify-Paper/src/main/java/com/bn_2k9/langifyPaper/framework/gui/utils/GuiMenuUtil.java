package com.bn_2k9.langifyPaper.framework.gui.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class GuiMenuUtil {

    private Player owner;

    public GuiMenuUtil(Player player) {
        this.owner = player;
    }

}
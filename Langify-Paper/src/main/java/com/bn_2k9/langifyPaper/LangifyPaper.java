package com.bn_2k9.langifyPaper;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyPaper.framework.Logger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class LangifyPaper extends JavaPlugin {

    public LangifyCore langifyCore;

    public Logger logger;

    @Getter
    public static LangifyPaper instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        langifyCore = new LangifyCore();
        logger = new Logger();
        langifyCore.init(logger);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

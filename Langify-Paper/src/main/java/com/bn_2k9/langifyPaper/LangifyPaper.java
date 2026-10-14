package com.bn_2k9.langifyPaper;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import com.bn_2k9.langifyPaper.framework.ConfigManager;
import com.bn_2k9.langifyPaper.framework.FileManager;
import com.bn_2k9.langifyPaper.framework.Logger;
import com.bn_2k9.langifyPaper.framework.features.UpdateChecker;
import com.bn_2k9.langifyPaper.framework.integrations.PlaceholderApiExtension;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LangifyPaper extends JavaPlugin {

    // Required project variables.
    @Getter
    public LangifyCore langifyCore;
    @Getter
    public Logger pluginLogger;
    @Getter
    public FileManager fileManager;

    @Getter
    public ConfigManager configManager;

    // The plugin instance.
    @Getter
    public static LangifyPaper instance;

    @Override
    public void onEnable() {
        // Save the main plugin instance.
        instance = this;

        // Save/create the required classes for the langifyCore to work.
        pluginLogger = new Logger();
        fileManager = new FileManager();
        configManager = new ConfigManager();

        // Create and initialize the langifyCore.
        langifyCore = new LangifyCore(pluginLogger, fileManager, configManager);
        langifyCore.init();

        // Check for updates.
        UpdateChecker updateChecker = new UpdateChecker();
        // Only check for the newest version if not in debug mode! (To reduce the api requests.)
        if (!ConfigManager.getConfig().getDebug() && ConfigManager.getConfig().getCheckForUpdates()) {
            updateChecker.checkForUpdates();
        }

        // Register the placeholder api expansion.
        if (!registerPlaceholderApi()) {
            pluginLogger.logInfo(LogInfoType.ERROR, "Failed to register the PlaceholderApi Expansion. (PlaceholderApi might not be started correctly?)");
        } else {
            pluginLogger.logInfo(LogInfoType.INFO, "Registered the PlaceholderApi Expansion successfully");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Registers the placeholderApi Expansion.
     * @return true if the Expansion was registered successfully. false if it failed to register.
     */
    private boolean registerPlaceholderApi() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderApi")) {
            new PlaceholderApiExtension().register();
            return true;
        }
        return false;
    }
}

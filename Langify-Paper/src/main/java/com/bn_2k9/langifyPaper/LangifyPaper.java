package com.bn_2k9.langifyPaper;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import com.bn_2k9.langifyPaper.framework.ConfigManager;
import com.bn_2k9.langifyPaper.framework.FileManager;
import com.bn_2k9.langifyPaper.framework.Logger;
import com.bn_2k9.langifyPaper.framework.commands.LangifyCommand;
import com.bn_2k9.langifyPaper.framework.commands.LanguageCommand;
import com.bn_2k9.langifyPaper.framework.features.UpdateChecker;
import com.bn_2k9.langifyPaper.framework.gui.GuiListener;
import com.bn_2k9.langifyPaper.framework.integrations.ItemsAdderIntegration;
import com.bn_2k9.langifyPaper.framework.integrations.PlaceholderApiExtension;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

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

    @Getter
    private static Boolean placeholderApiEnabled = false;

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
        if (!LangifyCore.getInstance().getConfig().getDebug() && LangifyCore.getInstance().getConfig().getCheckForUpdates()) {
            updateChecker.checkForUpdates();
        }

        if (LangifyCore.getInstance().getConfig().getEnableStats() && !LangifyCore.getInstance().getConfig().getDebug()) {
            new Metrics(this, 34700);
        }

        // Check witch integration is installed and can be activated.
        checkIntegrations();

        // Register the listeners used by the plugin.
        registerListeners();

        // Register the commands.
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Check witch integrations is installed and then activate it.
     */
    private void checkIntegrations() {
        // Check for the placeholder api.
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderApi")) {
            // Register the placeholderApi Extension.
            new PlaceholderApiExtension().register();
            placeholderApiEnabled = true;
            pluginLogger.logInfo(LogInfoType.INFO, "Registered the PlaceholderApi Expansion successfully.");
        } else {
            pluginLogger.logInfo(LogInfoType.ERROR, "Failed to register the PlaceholderApi Expansion. (PlaceholderApi might not be started correctly?)");
        }

        if (getServer().getPluginManager().isPluginEnabled("ItemsAdder")) {
            // Register the events.
            getServer().getPluginManager().registerEvents(new ItemsAdderIntegration(), this);
            pluginLogger.logInfo(LogInfoType.INFO, "Registered the ItemsAdder integration successfully.");
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
    }

    private void registerCommands() {
        Lamp<BukkitCommandActor> lamp = BukkitLamp.builder(this).build();
        lamp.register(new LanguageCommand());
        lamp.register(new LangifyCommand());
    }

    public static void reloadPlugin() {
        LangifyPaper.getInstance().getLangifyCore().init();
    }

}

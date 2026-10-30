package com.bn_2k9.langifyCore;

import com.bn_2k9.langifyCore.framework.interfaces.ConfigInterface;
import com.bn_2k9.langifyCore.framework.interfaces.FileInterface;
import com.bn_2k9.langifyCore.framework.interfaces.LoggerInterface;
import com.bn_2k9.langifyCore.framework.managers.DataStoreManager;
import com.bn_2k9.langifyCore.framework.managers.LanguageKeysManager;
import com.bn_2k9.langifyCore.framework.managers.LanguageSelectionManager;
import com.bn_2k9.langifyCore.framework.managers.UpdateManager;
import com.bn_2k9.langifyCore.framework.objects.Config;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import lombok.Getter;

public class LangifyCore {

    @Getter
    private static LangifyCore instance;

    @Getter
    private final LanguageSelectionManager languageSelectionManager;
    @Getter
    private final LanguageKeysManager languageKeysManager;
    @Getter
    private final DataStoreManager dataStoreManager;

    @Getter
    private final LoggerInterface logger;
    @Getter
    private final FileInterface fileInterface;
    @Getter
    private final ConfigInterface configInterface;

    @Getter
    private Config config;

    public LangifyCore(LoggerInterface loggerInterface, FileInterface fileInterface, ConfigInterface configInterface) {
        // Save the current instance.
        instance = this;

        // Register the custom logger.
        this.logger = loggerInterface;
        // Register the custom file interface.
        this.fileInterface = fileInterface;
        // Register the config interface.
        this.configInterface = configInterface;

        // Register the languageKeyManager.
        this.languageKeysManager = new LanguageKeysManager();
        // Create the languageSelectionManager.
        this.languageSelectionManager = new LanguageSelectionManager();
        // Create the dataStoreManager
        dataStoreManager = new DataStoreManager();
    }

    /**
     * Initialize the langify core. This can be called in runtime to reload the core.
     */
    public void init() {

        // Save the default language and create the folder.
        fileInterface.generateDefaults();

        // Save the default config.
        configInterface.safeDefaultConfig();

        // Load the config.
        config = configInterface.loadConfig();

        // Load the languageKeys
        languageKeysManager.loadLanguageKeys();

        // Load the dataStore manager.
        dataStoreManager.init();

        //TODO add a message whenever a language does not have a key the default language does have.

        logger.logInfo(LogInfoType.INFO, "Langify core has started.");

        // Check for updates.
        UpdateManager updateChecker = new UpdateManager();
        // Only check for the newest version if not in debug mode! (To reduce the api requests.)
        if (!LangifyCore.getInstance().getConfig().getDebug() && LangifyCore.getInstance().getConfig().getCheckForUpdates()) {
            updateChecker.checkForUpdates();
        }
    }

}

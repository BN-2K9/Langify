package com.bn_2k9.langifyCore.framework.interfaces;

import com.bn_2k9.langifyCore.framework.objects.Config;

public interface ConfigInterface {

    /**
     * Safes the default config if none exists.
     */
    void safeDefaultConfig();

    /**
     * Method for saving the config.
     */
    void saveConfig(Config config);

    /**
     * Method for loading the config.
     * @return The config object.
     */
    Config loadConfig();

}

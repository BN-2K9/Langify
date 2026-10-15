package com.bn_2k9.langifyPaper.framework;

import com.bn_2k9.langifyCore.framework.interfaces.ConfigInterface;
import com.bn_2k9.langifyCore.framework.objects.Config;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import com.bn_2k9.langifyPaper.LangifyPaper;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ConfigManager implements ConfigInterface {

    @Getter
    public static Config config;

    @Override
    public void safeDefaultConfig() {
        config = new Config();

        if (loadConfig() == null) {
            saveConfig(config);
        } else {
            saveConfig(loadConfig());
        }
    }

    @Override
    public void saveConfig(Config config) {

        // Create a new Yaml configuration.
        YamlConfiguration configuration = new YamlConfiguration();
        // Get the config keys. And comments.
        HashMap<String, Object> configKeys = config.getConfigKeys();
        HashMap<String, List<String>> configComments = config.getComments();

        // Save all the config keys to the Yaml.
        for (String key : configKeys.keySet()) {
            configuration.set(key, configKeys.get(key));
        }

        // Save all the config comments.
        for (String key : configComments.keySet()) {
            configuration.setComments(key, configComments.get(key));
        }

        // Make sure that not added values are being added again.
        Config defaultConfigObject = new Config();
        HashMap<String, Object> defaultConfig = defaultConfigObject.getConfigKeys();
        HashMap<String, List<String>> defaultConfigComments = defaultConfigObject.getComments();

        // Apply the default config keys.
        for (String key : defaultConfig.keySet()) {
            if (!configuration.contains(key)) {
                configuration.set(key, defaultConfig.get(key));
            }
        }

        // Save default config comments.
        for (String key : defaultConfigComments.keySet()) {
            if (configuration.getComments(key).isEmpty()) {
                configuration.setComments(key, defaultConfigComments.get(key));
            }
        }

        // Catch the error if the config failed to save.
        try {
            configuration.save(LangifyPaper.getInstance().getDataPath() + "/config.yml");
        } catch (IOException e) {
            LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "Failed to save Config file, " + e.getMessage());
        }

    }

    @Override
    public Config loadConfig() {

        File file = new File(LangifyPaper.getInstance().getDataPath() + "/config.yml");
        if (file.exists()) {
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            try {
                yamlConfiguration.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "Failed to load Config file, " + e.getMessage());
            }

            ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("");
            if (configurationSection == null) {
                return null;
            }

            Config config = new Config();
            HashMap<String, Object> configKeys = new HashMap<>();
            for (String string : configurationSection.getKeys(true)) {
                if (yamlConfiguration.get(string) != null) {
                    configKeys.put(string, yamlConfiguration.get(string));
                }
            }

            config.setConfigKeys(configKeys);

            return config;
        }

        return null;
    }

}

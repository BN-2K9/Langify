package com.bn_2k9.langifyCore.framework.objects;

import com.bn_2k9.langifyCore.framework.utils.LangDataStoreMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
public class Config {

    /**
     * Config keys. (Not recommended to use.)
     */
    private HashMap<String, Object> configKeys = new HashMap<>();

    // Hashmap for all the config comments.
    HashMap<String, List<String>> comments = new HashMap<>();

    /**
     * Use this to get the config defaults.
     */
    public Config() {
        configKeys.put("Prefix", "Langify >> ");
        configKeys.put("LangDataStoreMethod", LangDataStoreMethod.Dynamic_Load.toString());
        configKeys.put("Debug", false);
        configKeys.put("CheckForUpdates", true);
        configKeys.put("EnableStats", true);

        this.comments = defaultComments();
    }

    /**
     * Use this to create a config with given values.
     * @param prefix The plugin prefix.
     * @param langDataStoreMethod The LanguageStoreMethod.
     * @param debug Debug mode.
     * @param comments The comments.
     */
    public Config(String prefix, LangDataStoreMethod langDataStoreMethod, Boolean debug, Boolean checkForUpdates, Boolean enableStats, HashMap<String, String> comments) {
        configKeys.put("Prefix", prefix);
        configKeys.put("LangDataStoreMethod", langDataStoreMethod.toString());
        configKeys.put("Debug", debug);
        configKeys.put("CheckForUpdates", checkForUpdates);
        configKeys.put("EnableStats", enableStats);

        this.comments = defaultComments();
        comments.replaceAll((s, v) -> v);
    }

    // Apply all the default comments.
    public HashMap<String, List<String>> defaultComments() {
        comments.put("Prefix", List.of("The plugin prefix used for logging, and in player messages."));
        comments.put("LangDataStoreMethod", List.of("The method of loading all language entries.", "Possible Values: Dynamic_Load, Load_All"));
        comments.put("Debug", List.of("The value for enabling debug logging.", "Possible Values: true/false"));
        comments.put("CheckForUpdates", List.of("The value for enabling the update checker.", "Possible Values: true/false"));
        comments.put("EnableStats", List.of("The value for enabling the stats. These will be publicly available. (No sensitive data is gathered.)", "Possible Values: true/false"));

        return comments;
    }

    /**
     * The plugin prefix.
     */
    public String getPrefix() {
        return (String) configKeys.get("Prefix");
    }

    /**
     * The DataStoreMethod.
     */
    public LangDataStoreMethod getLangDataStoreMethod() {
        return (LangDataStoreMethod) configKeys.get("LangDataStoreMethod");
    }

    /**
     * The Debug.
     * @return the value.
     */
    public boolean getDebug() {
        return (Boolean) configKeys.get("Debug");
    }

    /**
     * The Check for updates value.
     * @return the value.
     */
    public boolean getCheckForUpdates() {
        return (Boolean) configKeys.get("CheckForUpdates");
    }

    /**
     * The enable stats value.
     * @return the value.
     */
    public boolean getEnableStats() {
        return (Boolean) configKeys.get("EnableStats");
    }

}

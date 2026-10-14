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
        configKeys.put("Prefix", "Langify >>");
        configKeys.put("LangDataStoreMethod", LangDataStoreMethod.Dynamic_Load.toString());
        configKeys.put("Debug", false);

        this.comments = defaultComments();
    }

    /**
     * Use this to create a config with given values.
     * @param prefix The plugin prefix.
     * @param langDataStoreMethod The LanguageStoreMethod.
     * @param debug Debug mode.
     * @param comments The comments.
     */
    public Config(String prefix, LangDataStoreMethod langDataStoreMethod, Boolean debug, HashMap<String, String> comments) {
        configKeys.put("Prefix", prefix);
        configKeys.put("LangDataStoreMethod", langDataStoreMethod.toString());
        configKeys.put("Debug", debug);

        this.comments = defaultComments();
        comments.replaceAll((s, v) -> v);
    }

    // Apply all the default comments.
    public HashMap<String, List<String>> defaultComments() {
        comments.put("Prefix", List.of("The plugin prefix used for logging, and in player messages."));
        comments.put("LangDataStoreMethod", List.of("The method of loading all language entries, Possible Values: Dynamic_Load, Load_All"));
        comments.put("Debug", List.of("The value for enabling debug logging."));

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
     */
    public boolean getDebug() {
        return (Boolean) configKeys.get("Debug");
    }

}

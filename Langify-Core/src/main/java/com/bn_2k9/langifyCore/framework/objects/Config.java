package com.bn_2k9.langifyCore.framework.objects;

import com.bn_2k9.langifyCore.framework.utils.DataStoreTypes;
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
        configKeys.put("Prefix", "Langify // ");
        configKeys.put("LangDataStoreMethod", LangDataStoreMethod.Dynamic_Load.toString());
        configKeys.put("Debug", String.valueOf(false));
        configKeys.put("CheckForUpdates", String.valueOf(true));
        configKeys.put("EnableStats", String.valueOf(true));
        configKeys.put("DefaultLanguage", "en_us.yml");
        configKeys.put("DataStore.Type", "SqlLite");
        configKeys.put("DataStore.JDBCUrl", "");
        configKeys.put("DataStore.UserName", "");
        configKeys.put("DataStore.Password", "");
        configKeys.put("Gui.Name", "Select Language");
        configKeys.put("Gui.Size", 54);
        configKeys.put("Gui.FillerItem", "");

        this.comments = defaultComments();
    }

    /**
     * Use this to create a config with given values.
     * @param prefix The plugin prefix.
     * @param langDataStoreMethod The LanguageStoreMethod.
     * @param debug Debug mode.
     * @param comments The comments.
     */
    public Config(String prefix, LangDataStoreMethod langDataStoreMethod, Boolean debug, Boolean checkForUpdates, Boolean enableStats, DataStoreTypes dataStoreType, String JdbcUrl, String username, String password, String guiTitle, int guiSize, String guiFillerItem, HashMap<String, String> comments) {
        configKeys.put("Prefix", prefix);
        configKeys.put("LangDataStoreMethod", langDataStoreMethod.toString());
        configKeys.put("Debug", String.valueOf(debug));
        configKeys.put("CheckForUpdates", String.valueOf(checkForUpdates));
        configKeys.put("EnableStats", String.valueOf(enableStats));
        configKeys.put("DefaultLanguage", "en_us.yml");
        configKeys.put("DataStore.Type", dataStoreType);
        configKeys.put("DataStore.JDBCUrl", JdbcUrl);
        configKeys.put("DataStore.UserName", username);
        configKeys.put("DataStore.Password", password);
        configKeys.put("Gui.Name", guiTitle);
        configKeys.put("Gui.Size", guiSize);
        configKeys.put("Gui.FillerItem", guiFillerItem);

        this.comments = defaultComments();
        comments.replaceAll((s, v) -> v);
    }

    // Apply all the default comments.
    public HashMap<String, List<String>> defaultComments() {
        comments.put("Prefix", List.of("The plugin prefix used for logging, and in player messages."));
        comments.put("LangDataStoreMethod", List.of("The method of loading all language entries.", "Possible Values: Dynamic_Load, Load_All, Disable_Cache"));
        comments.put("Debug", List.of("The value for enabling debug logging.", "Possible Values: true/false"));
        comments.put("CheckForUpdates", List.of("The value for enabling the update checker.", "Possible Values: true/false"));
        comments.put("EnableStats", List.of("The value for enabling the stats. These will be publicly available. (No sensitive data is gathered.)", "Possible Values: true/false"));
        comments.put("DefaultLanguage", List.of("The default language that the player selects whenever joining. (Also the language that is seen as the most complete and will be used to check other language files to see if they are complete.)", "Possible Values: The language file name. e.g en_us.yml"));
        comments.put("DataStore.Type", List.of("The type of DataStore method.", "Possible values: LiteSql, MySql"));
        comments.put("DataStore.JDBCUrl", List.of("The JBDC connection string.", "Only needed when DataStore.Type = MySql"));
        comments.put("DataStore.UserName", List.of("The Username to connect with to the database.", "Only needed when DataStore.Type = MySql"));
        comments.put("DataStore.Password", List.of("The Password to connect with to the database.", "Only needed when DataStore.Type = MySql"));
        comments.put("Gui.Name", List.of("The title of the gui inventory."));
        comments.put("Gui.Size", List.of("The size of the gui."));
        comments.put("Gui.FillerItem", List.of("The filler item used.", "When left empty the filling will be skipped."));

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
        return LangDataStoreMethod.valueOf((String) configKeys.get("LangDataStoreMethod"));
    }

    /**
     * The Debug.
     * @return the value.
     */
    public boolean getDebug() {
        return Boolean.parseBoolean((String) configKeys.get("Debug"));
    }

    /**
     * The Check for updates value.
     * @return the value.
     */
    public boolean getCheckForUpdates() {
        return Boolean.parseBoolean((String) configKeys.get("CheckForUpdates"));
    }

    /**
     * The enable stats value.
     * @return the value.
     */
    public boolean getEnableStats() {
        return Boolean.parseBoolean((String) configKeys.get("EnableStats"));
    }

    /**
     * The default language.
     * @return the value.
     */
    public String getDefaultLanguage() {
        return (String) configKeys.get("DefaultLanguage");
    }

    /**
     * The selected DataStoreType.
     * @return The value.
     */
    public DataStoreTypes getDataStoreType() {
        return DataStoreTypes.valueOf((String) configKeys.get("DataStore.Type"));
    }

    /**
     * Gets the JBDC connection url.
     * @return the value.
     */
    public String getDataStoreJdbcUrl() {
        return (String) configKeys.get("DataStore.JDBCUrl");
    }

    /**
     * Gets the datastore username.
     * @return the value.
     */
    public String getDataStoreUsername() {
        return (String) configKeys.get("DataStore.UserName");
    }

    /**
     * Gets the datastore password.
     * @return the value.
     */
    public String getDataStorePassword() {
        return (String) configKeys.get("DataStore.Password");
    }

    /**
     * Gets the gui name.
     * @return the value.
     */
    public String getGuiName() {
        return (String) configKeys.get("Gui.Name");
    }

    /**
     * Gets the gui size.
     * @return the value.
     */
    public int getGuiSize() {
        return (int) configKeys.get("Gui.Size");
    }

    /**
     * Gets the gui filler item.
     * @return the value.
     */
    public String getGuiFillerItem() {
        return (String) configKeys.get("Gui.FillerItem");
    }

}
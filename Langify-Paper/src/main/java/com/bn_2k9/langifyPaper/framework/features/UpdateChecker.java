package com.bn_2k9.langifyPaper.framework.features;

import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import com.bn_2k9.langifyPaper.LangifyPaper;
import com.bn_2k9.langifyPaper.framework.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UpdateChecker {

    private static final String projectSlug = "langify";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void checkForUpdates() {
        final String currentVersion = LangifyPaper.getInstance().getPluginMeta().getVersion();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://api.modrinth.com/v2/project/" + projectSlug + "/version?featured=true"
                ))
                .header("User-Agent", "BN_2K9/Langify/" + currentVersion)
                .GET()
                .build();

        client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenAccept(stringHttpResponse -> {
                    if (stringHttpResponse.statusCode() != 200) {
                        LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "Failed to check for plugin updates. (" + stringHttpResponse.statusCode() + ")");
                        return;
                    }

                    try {
                        JsonNode versions = objectMapper.readTree(stringHttpResponse.body());

                        if (!versions.isArray() || versions.isEmpty()) {
                            return;
                        }

                        // Select the newest published release.
                        JsonNode latest = null;

                        for (JsonNode version : versions) {
                            if ("release".equals(
                                    version.path("version_type").asText())) {
                                latest = version;
                                break;
                            }
                        }

                        if (latest == null) {
                            LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.WARNING, "No published release found on Modrinth.");
                            return;
                        }

                        String latestVersion = latest.path("version_number").asText();

                        if (!currentVersion.equals(latestVersion)) {
                            LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.WARNING, "A newer version may be available! Current: "
                                    + currentVersion + ", latest: " + latestVersion + " Get it at: https://modrinth.com/plugin/langify");
                        } else {
                            LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.INFO, "You are running the latest version.");
                        }

                    } catch (Exception e) {
                        Logger.getInstance().logInfo(LogInfoType.ERROR, "Could not parse Modrinth response: " + e.getMessage());
                    }
                }).exceptionally(error -> {
                    Logger.getInstance().logInfo(LogInfoType.WARNING, "Could not check Modrinth for updates: " + error.getMessage());

                    return null;
                });
    }
}

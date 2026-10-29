package com.bn_2k9.langifyCore.framework.managers;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.interfaces.LoggerInterface;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UpdateManager {

    private static final String projectSlug = "langify";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void checkForUpdates() {
        final String currentVersion = "${version}";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://api.modrinth.com/v2/project/" + projectSlug + "/version?featured=true"
                ))
                .header("User-Agent", "BN_2K9/Langify/" + currentVersion)
                .GET()
                .build();

        LoggerInterface loggerInterface = LangifyCore.getInstance().getLogger();

        client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenAccept(stringHttpResponse -> {
                    if (stringHttpResponse.statusCode() != 200) {
                        loggerInterface.logInfo(LogInfoType.ERROR, "Failed to check for plugin updates. (" + stringHttpResponse.statusCode() + ")");
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
                                    version.path("version_type").asString())) {
                                latest = version;
                                break;
                            }
                        }

                        if (latest == null) {
                            loggerInterface.logInfo(LogInfoType.WARNING, "No published release found on Modrinth.");
                            return;
                        }

                        String latestVersion = latest.path("version_number").asString();

                        if (!currentVersion.equals(latestVersion)) {
                            loggerInterface.logInfo(LogInfoType.WARNING, "A newer version may be available! Current: "
                                    + currentVersion + ", latest: " + latestVersion + " Get it at: https://modrinth.com/plugin/langify");
                        } else {
                            loggerInterface.logInfo(LogInfoType.INFO, "You are running the latest version.");
                        }

                    } catch (Exception e) {
                        loggerInterface.logInfo(LogInfoType.ERROR, "Could not parse Modrinth response: " + e.getMessage());
                    }
                }).exceptionally(error -> {
                    loggerInterface.logInfo(LogInfoType.WARNING, "Could not check Modrinth for updates: " + error.getMessage());

                    return null;
                });
    }

}

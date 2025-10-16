package gg.incash.common.command;

import gg.incash.common.config.Config;
import gg.incash.common.logger.IncashLogger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class IncashPurchaseData {

    private final UUID id;
    private final JSONArray commands;
    private final Boolean requiresOnline;
    private final String nickname;

    public IncashPurchaseData(String jsonData, IncashLogger logger, Config config) {
        UUID tempId = null;
        JSONArray tempCommands = new JSONArray();
        Boolean tempRequiresOnline = false;
        String tempNickname = null;

        try {
            JSONObject json = new JSONObject(jsonData);

            tempId = UUID.fromString(json.optString("id", UUID.randomUUID().toString()));
            tempCommands = json.optJSONArray("commands");
            tempRequiresOnline = json.optBoolean("requiresOnline", false);
            tempNickname = json.optString("nickname", "unknown");
        } catch (JSONException e) {
            logger.debug("Failed to parse purchase JSON payload: " + e.getMessage());
        }

        this.id = tempId;
        this.commands = tempCommands;
        this.requiresOnline = tempRequiresOnline;
        this.nickname = tempNickname;
    }

    public UUID getId() {
        return id;
    }

    public JSONArray getCommands() {
        return commands;
    }

    public Boolean getRequiresOnline() {
        return requiresOnline;
    }

    public String getNickname() {
        return nickname;
    }
}

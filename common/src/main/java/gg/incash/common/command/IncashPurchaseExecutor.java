package gg.incash.common.command;

import gg.incash.common.config.Config;
import gg.incash.common.logger.IncashLogger;
import org.json.JSONArray;
import org.json.JSONException;

public abstract class IncashPurchaseExecutor implements Runnable {
    private final String jsonData;
    private final IncashLogger logger;
    private final Config config;

    protected IncashPurchaseExecutor(String jsonData, IncashLogger logger, Config config) {
        this.jsonData = jsonData;
        this.logger = logger;
        this.config = config;
    }

    public abstract boolean isPlayerOnline(final String playerName);

    public abstract void executeCommand(final String command);

    @Override
    public void run() {
        IncashPurchaseData purchaseData = new IncashPurchaseData(this.jsonData, this.logger, this.config);

        if(purchaseData.getRequiresOnline() && !this.isPlayerOnline(purchaseData.getNickname())) {
            // Restore logic
            this.logger.error(String.format("Purchase %s cannot be processed because of user %s is offline", purchaseData.getId(), purchaseData.getNickname()));
            return;
        }
        this.logger.info(String.format("Purchase %s successfully completed. %s commands will be executed.", purchaseData.getId(), purchaseData.getCommands().length()));
        JSONArray commands = purchaseData.getCommands();
        for (int i = 0; i < commands.length(); i++) {
            try {
                String cmd = commands.getString(i);
                this.executeCommand(cmd);
            } catch (JSONException e) {
                this.logger.error(String.format("Error while executing command with index %s for purchase %s: ", i, purchaseData.getId()) + e.getMessage());
            }
        }
    }


}

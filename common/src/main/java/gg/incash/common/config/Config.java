package gg.incash.common.config;

public class Config {

    private final ConfigLoader configLoader;
    private String apiUrl;
    private String shopId;
    private String serverId;
    private Boolean debug;

    public Config(ConfigLoader configLoader) throws ResourceLoaderException, EmptyConfigFieldException  {
        this.configLoader = configLoader;
        this.loadValues();
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getShopId() {
        return shopId;
    }

    public String getServerId() {
        return serverId;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void loadValues() throws ResourceLoaderException, EmptyConfigFieldException {
        this.configLoader.reloadConfig();

        this.shopId = this.configLoader.getString("shopId");
        this.serverId = this.configLoader.getString("serverId");
        this.apiUrl = this.configLoader.getString("apiUri");
        this.debug = this.configLoader.getBoolean("debug");

        this.checkValues();
    }

    private void checkValues() throws EmptyConfigFieldException {
        this.checkValue(this.apiUrl, "apiUrl");
        this.checkValue(this.shopId, "shopId");
        this.checkValue(this.serverId, "serverId");
    }

    private void checkValue(final String value, final String fieldName) throws EmptyConfigFieldException {
        if (value == null || value.isEmpty()) {
            throw new EmptyConfigFieldException(fieldName);
        }
    }
}

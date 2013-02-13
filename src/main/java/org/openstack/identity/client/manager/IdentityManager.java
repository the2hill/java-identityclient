package org.openstack.identity.client.manager;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import org.openstack.identity.client.common.util.IdentityUtil;


public abstract class IdentityManager {
    private String auth_uri = "auth_uri";
    protected String url;
    protected Client client;
    protected boolean isDebugging;
    private int TIMEOUT = 300;

    /**
     * @param authUrl the url to the identity auth service
     * @param client  the Jersey client used to talk to identity
     */
    public IdentityManager(String authUrl, Client client) {
        this(authUrl, client, false);
    }

    /**
     * @param authUrl     the url to the identity auth service
     * @param client      the Jersey client used to talk to identity
     * @param isDebugging allows debugging option
     */
    public IdentityManager(String authUrl, Client client, boolean isDebugging) {
        this.url = authUrl;
        if (authUrl == null) {
            this.url = IdentityUtil.getProperty(auth_uri);
        }
        if (client != null) {
            this.client = configureClient(isDebugging);
        } else {
            this.client = client;
        }
    }

    /**
     * This method will create the clients web resource based on the auth uri
     * found in the properties file
     *
     * @param authUrl the url to the identity auth service
     */
    public IdentityManager(String authUrl) {
        this(authUrl, false);
    }

    /**
     * This method will create the clients web resource based on the auth uri
     * found in the properties file and allows debugging option
     *
     * @param authUrl the url to the identity auth service
     * @param isDebugging allows debugging option
     */
    public IdentityManager(String authUrl, boolean isDebugging) {
        this(authUrl, new Client(), isDebugging);
    }

    /**
     * This method uses the default authUrl found in
     * the properties file
     */
    public IdentityManager() {
        this(false);
    }

    /**
     * This method uses the default authUrl found in
     * the properties file and allows for debugging option
     * @param isDebugging allows for debugging option
     */
    public IdentityManager(boolean isDebugging) {
        this.url = IdentityUtil.getProperty(auth_uri);
        this.client = new Client();
        this.isDebugging = isDebugging;
    }

    private Client configureClient(boolean isDebugging) {
        DefaultClientConfig cc = new DefaultClientConfig();
        cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, false);
        cc.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, TIMEOUT);
        cc.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, TIMEOUT);
        Client client = Client.create(cc);
        if (isDebugging) {
            client.addFilter(new LoggingFilter());
        }
        return client;
    }
}

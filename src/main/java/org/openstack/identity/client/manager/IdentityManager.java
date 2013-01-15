package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.identity.client.common.util.IdentityUtil;


public abstract class IdentityManager {
    private String auth_uri = "auth_uri";
    protected String url;
    protected Client client;

    /**
     * @param authUrl the url to the identity auth service
     * @param client the Jersey client used to talk to identity
     */
    public IdentityManager(String authUrl, Client client) {
        this.url = authUrl;
        if (authUrl == null) {
            this.url = IdentityUtil.getProperty(auth_uri);
        }
        this.client = client;
    }

    /**
     * This method will create the clients web resource based on the auth uri
     * found in the properties file
     *
     * @param authUrl the url to the identity auth service
     */
    public IdentityManager(String authUrl)  {
        this(authUrl, Client.create());
    }

    /**
     * This method uses the default authUrl found in
     * the properties file
     */
    public IdentityManager() {
        this.url = IdentityUtil.getProperty(auth_uri);
        this.client = Client.create();
    }
}

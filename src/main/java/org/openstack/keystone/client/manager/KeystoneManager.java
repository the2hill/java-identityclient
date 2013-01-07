package org.openstack.keystone.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.keystone.client.fault.KeystoneFault;

import org.openstack.keystone.client.common.util.KeystoneUtil;

public abstract class KeystoneManager {
    private String auth_uri = "auth_uri";
    protected String url;
    protected Client client;

    /**
     * @param authUrl the url to the KeyStone auth service
     * @param client  the client used to talk to KeyStone
     */
    public KeystoneManager(String authUrl, Client client) throws KeystoneFault {
        this.url = authUrl;
        if (authUrl == null) {
            this.url = KeystoneUtil.getProperty(auth_uri);
        }
        this.client = client;
    }

    /**
     * This method will create the clients web resource based on the auth_public_uri
     * found in the properties file
     *
     * @param authUrl the url to the KeyStone auth service
     * @throws KeystoneFault
     */
    public KeystoneManager(String authUrl) throws KeystoneFault {
        this(authUrl, Client.create());
    }

    /**
     * This method uses the default authUrl found in
     * the properties file
     */
    public KeystoneManager() throws KeystoneFault {
        this.url = KeystoneUtil.getProperty(auth_uri);
        this.client = Client.create();
    }
}

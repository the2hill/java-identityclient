package org.openstack.identity.client.manager.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://docs.openstack.org/identity/api/v2.0", name = "credentials")
public class Credentials {
    private org.openstack.identity.client.api.credentials.ApiKeyCredentials apiKeyCredentials;

    public Credentials() {
    }

    /**
     * Create a new Credentials object
     *
     * @param apiKeyCredentials
     */

    public Credentials(org.openstack.identity.client.api.credentials.ApiKeyCredentials apiKeyCredentials) {
        this.apiKeyCredentials = apiKeyCredentials;
    }

    /**
     * Get the ApiKeyCredentials
     *
     * @return the apiKeyCredentials
     */
    public org.openstack.identity.client.api.credentials.ApiKeyCredentials getApiKeyCredentials() {
        return apiKeyCredentials;
    }

    /**
     * Set ApiKeyCredentials
     *
     * @param apiKeyCredentials
     */
    public void setApiKeyCredentials(org.openstack.identity.client.api.credentials.ApiKeyCredentials apiKeyCredentials) {
        this.apiKeyCredentials = apiKeyCredentials;
    }
}

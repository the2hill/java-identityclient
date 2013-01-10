package org.openstack.keystone.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.token.AuthenticateResponse;

import java.net.URISyntaxException;

public interface AuthenticationResourceManager {

    AuthenticateResponse authenticateUsernamePassword(Client client, String url, String userName, String password) throws KeystoneFault, URISyntaxException;

    AuthenticateResponse authenticateTenantIdTokenId(Client client, String url, String tenantId, String tokenId) throws KeystoneFault, URISyntaxException;

    AuthenticateResponse authenticateTenantNameTokenId(Client client, String url, String tenantName, String tokenId) throws KeystoneFault, URISyntaxException;

    AuthenticateResponse authenticateUsernameApiKey(Client client, String url, String tenantName, String tokenId) throws KeystoneFault, URISyntaxException;

    AuthenticateResponse authenticateTenantIdUsernameApiKey(Client client, String url, String tenantId, String tenantName, String tokenId) throws KeystoneFault, URISyntaxException;

    AuthenticateResponse authenticateTenantIdUsernamePassword(Client client, String url, String tenantId, String username, String password) throws KeystoneFault, URISyntaxException;
}

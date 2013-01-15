package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.token.AuthenticateResponse;

import java.net.URISyntaxException;

public interface AuthenticationResourceManager {

    AuthenticateResponse authenticateUsernamePassword(Client client, String url, String userName, String password) throws IdentityFault, URISyntaxException;

    AuthenticateResponse authenticateTenantIdTokenId(Client client, String url, String tenantId, String tokenId) throws IdentityFault, URISyntaxException;

    AuthenticateResponse authenticateTenantNameTokenId(Client client, String url, String tenantName, String tokenId) throws IdentityFault, URISyntaxException;

    AuthenticateResponse authenticateUsernameApiKey(Client client, String url, String tenantName, String tokenId) throws IdentityFault, URISyntaxException;

    AuthenticateResponse authenticateTenantIdUsernameApiKey(Client client, String url, String tenantId, String tenantName, String tokenId) throws IdentityFault, URISyntaxException;

    AuthenticateResponse authenticateTenantIdUsernamePassword(Client client, String url, String tenantId, String username, String password) throws IdentityFault, URISyntaxException;
}

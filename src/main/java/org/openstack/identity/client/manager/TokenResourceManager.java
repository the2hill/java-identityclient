package org.openstack.identity.client.manager;

import org.openstack.identity.client.endpoints.EndpointList;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.token.AuthenticateResponse;

import javax.ws.rs.client.Client;
import java.net.URISyntaxException;

public interface TokenResourceManager {

    public AuthenticateResponse validateToken(Client client, String url, String adminToken, String token, String username) throws IdentityFault, URISyntaxException;

    public EndpointList retrieveEndpointsForToken(Client client, String url, String token) throws IdentityFault, URISyntaxException;

    public EndpointList retrieveEndpointsForToken(Client client, String url, String adminToken, String token) throws IdentityFault, URISyntaxException;
}

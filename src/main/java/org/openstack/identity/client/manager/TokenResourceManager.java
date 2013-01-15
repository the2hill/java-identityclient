package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.identity.client.endpoints.EndpointList;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.token.AuthenticateResponse;

import java.net.URISyntaxException;

public interface TokenResourceManager {

    public AuthenticateResponse validateToken(Client client, String url, String adminToken, String token, String username) throws IdentityFault, URISyntaxException;

    public EndpointList retrieveEndpointsForToken(Client client, String url, String token) throws IdentityFault, URISyntaxException;

    public EndpointList retrieveEndpointsForToken(Client client, String url, String adminToken, String token) throws IdentityFault, URISyntaxException;
}

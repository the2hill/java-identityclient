package org.openstack.keystone.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.keystone.client.endpoints.EndpointList;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.token.AuthenticateResponse;

import java.net.URISyntaxException;

public interface TokenResourceManager {

    public AuthenticateResponse validateToken(Client client, String url, String adminToken, String token, String username) throws KeystoneFault, URISyntaxException;

    public EndpointList retrieveEndpointsForToken(Client client, String url, String token) throws KeystoneFault, URISyntaxException;

    public EndpointList retrieveEndpointsForToken(Client client, String url, String adminToken, String token) throws KeystoneFault, URISyntaxException;
}

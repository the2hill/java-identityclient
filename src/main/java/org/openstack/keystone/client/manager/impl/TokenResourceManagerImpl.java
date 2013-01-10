package org.openstack.keystone.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.openstack.keystone.client.common.constants.KeystoneConstants;
import org.openstack.keystone.client.common.wrapper.KeystoneResponseWrapper;
import org.openstack.keystone.client.endpoints.EndpointList;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.manager.TokenResourceManager;
import org.openstack.keystone.client.token.AuthenticateResponse;

import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;

public class TokenResourceManagerImpl extends ResponseManagerImpl implements TokenResourceManager{


    /**
     * Validate token with admin account
     *
     * @param client
     * @param url
     * @param token
     * @param username
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public AuthenticateResponse validateToken(Client client, String url, String adminToken, String token, String username) throws KeystoneFault, URISyntaxException {
       ClientResponse response = null;
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add(KeystoneConstants.BELONGS_TO, username);
        try {
            response = get(client, new URI(url + KeystoneConstants.TOKEN_PATH + "/" + token), adminToken, params);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(AuthenticateResponse.class);
    }

    /**
     * Retrieve the endpoints for token
     *
     * @param client
     * @param url
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public EndpointList retrieveEndpointsForToken(Client client, String url, String token) throws KeystoneFault, URISyntaxException {
       return retrieveEndpointsForToken(client, url, token, token);
    }

    /**
     * Retrieve endpoints for token with admin account
     *
     * @param client
     * @param url
     * @param adminToken
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public EndpointList retrieveEndpointsForToken(Client client, String url, String adminToken, String token) throws KeystoneFault, URISyntaxException {
       ClientResponse response = null;
        try {
            response = get(client, new URI(url + KeystoneConstants.TOKEN_PATH + "/" + token + "/" + KeystoneConstants.ENDPOINTS_PATH), adminToken);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(EndpointList.class);
    }


}

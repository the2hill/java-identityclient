package org.openstack.identity.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.endpoints.EndpointList;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.TokenResourceManager;
import org.openstack.identity.client.token.AuthenticateResponse;

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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    @Override
    public AuthenticateResponse validateToken(Client client, String url, String adminToken, String token, String username) throws IdentityFault, URISyntaxException {
       ClientResponse response = null;
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add(IdentityConstants.BELONGS_TO, username);
        try {
            response = get(client, new URI(url + IdentityConstants.TOKEN_PATH + "/" + token), adminToken, params);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    @Override
    public EndpointList retrieveEndpointsForToken(Client client, String url, String token) throws IdentityFault, URISyntaxException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    @Override
    public EndpointList retrieveEndpointsForToken(Client client, String url, String adminToken, String token) throws IdentityFault, URISyntaxException {
       ClientResponse response = null;
        try {
            response = get(client, new URI(url + IdentityConstants.TOKEN_PATH + "/" + token + "/" + IdentityConstants.ENDPOINTS_PATH), adminToken);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(EndpointList.class);
    }


}

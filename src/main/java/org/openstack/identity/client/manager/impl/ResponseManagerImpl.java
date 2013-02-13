package org.openstack.identity.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.ResponseManager;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;


public abstract class ResponseManagerImpl implements ResponseManager {
    private final Log logger = LogFactory.getLog(ResponseManagerImpl.class);

    @Override
    public ClientResponse get(Client client, URI uri, String token) {
        return get(client, uri, token, new MultivaluedMapImpl());
    }


    @Override
    public ClientResponse get(Client client, URI uri, String token, MultivaluedMap<String, String> params) {
        return client.resource(uri).queryParams(params).type(MediaType.APPLICATION_XML_TYPE)
                .accept(MediaType.APPLICATION_XML_TYPE)
                .header(IdentityConstants.X_TOKEN_HEADER, token)
                .get(ClientResponse.class);
    }

    @Override
    public ClientResponse post(Client client, URI uri, String body) {
        System.out.print(body);
        return client.resource(uri).type(MediaType.APPLICATION_XML_TYPE)
                .accept(MediaType.APPLICATION_XML_TYPE)
                .post(ClientResponse.class, body);
    }

    @Override
    public ClientResponse post(Client client, URI uri, String token, String body) {
        return client.resource(uri).type(MediaType.APPLICATION_XML_TYPE)
                .header(IdentityConstants.X_TOKEN_HEADER, token)
                .accept(MediaType.APPLICATION_XML_TYPE)
                .post(ClientResponse.class, body);
    }

    @Override
    public ClientResponse put(Client client, URI uri, String token, String body) {
        return client.resource(uri).type(MediaType.APPLICATION_XML_TYPE)
                .header(IdentityConstants.X_TOKEN_HEADER, token)
                .accept(MediaType.APPLICATION_XML_TYPE)
                .put(ClientResponse.class, body);
    }

    @Override
    public ClientResponse delete(Client client, URI uri, String token) {
        return client.resource(uri).type(MediaType.APPLICATION_XML_TYPE)
                .header(IdentityConstants.X_TOKEN_HEADER, token)
                .accept(MediaType.APPLICATION_XML_TYPE)
                .delete(ClientResponse.class);
    }

    @Override public ClientResponse head(Client client, URI uri, String body) {
        return null;
    }

    public boolean isResponseValid(ClientResponse response) {
        return (response != null && (response.getStatus() == IdentityConstants.ACCEPTED
                || response.getStatus() == IdentityConstants.NON_AUTHORATIVE
                || response.getStatus() == IdentityConstants.OK
                || response.getStatus() == IdentityConstants.NO_CONTENT
                || response.getStatus() == IdentityConstants.CREATED));
    }

    public boolean handleBadResponse(ClientResponse response) throws IdentityFault {
        if (response != null) {
            throw IdentityResponseWrapper.buildFaultMessage(response);
        } else {
            logger.error("Unable to retrieve response from server. Response: " + response);
            throw new IdentityFault("Network communication error, please try again.  ", "", 500);
        }
    }

}

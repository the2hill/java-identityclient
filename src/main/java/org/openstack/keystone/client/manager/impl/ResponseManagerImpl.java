package org.openstack.keystone.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.keystone.client.common.constants.KeystoneConstants;
import org.openstack.keystone.client.common.wrapper.KeystoneResponseWrapper;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.manager.ResponseManager;

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
                .header(KeystoneConstants.X_TOKEN_HEADER, token)
                .get(ClientResponse.class);
    }

    @Override
    public ClientResponse post(Client client, URI uri, String body) {
        return client.resource(uri).type(MediaType.APPLICATION_XML_TYPE)
                .accept(MediaType.APPLICATION_XML_TYPE)
                .post(ClientResponse.class, body);
    }

    @Override
    public ClientResponse post(Client client, URI uri, String token, String body) {
        return client.resource(uri).type(MediaType.APPLICATION_XML_TYPE)
                .header(KeystoneConstants.X_TOKEN_HEADER, token)
                .accept(MediaType.APPLICATION_XML_TYPE)
                .post(ClientResponse.class, body);
    }

    @Override public ClientResponse put(Client client, URI uri, String body) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ClientResponse delete(Client client, URI uri, String token) {
        return client.resource(uri).type(MediaType.APPLICATION_XML_TYPE)
                .header(KeystoneConstants.X_TOKEN_HEADER, token)
                .accept(MediaType.APPLICATION_XML_TYPE)
                .delete(ClientResponse.class);
    }

    @Override public ClientResponse head(Client client, URI uri, String body) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isResponseValid(ClientResponse response) {
        return (response != null && (response.getStatus() == KeystoneConstants.ACCEPTED
                || response.getStatus() == KeystoneConstants.NON_AUTHORATIVE
                || response.getStatus() == KeystoneConstants.OK
                || response.getStatus() == KeystoneConstants.NO_CONTENT
                || response.getStatus() == KeystoneConstants.CREATED));
    }

    public boolean handleBadResponse(ClientResponse response) throws KeystoneFault {
        if (response != null) {
            throw KeystoneResponseWrapper.buildFaultMessage(response);
        } else {
            logger.error("Unable to retrieve response from server. Response: " + response);
            throw new KeystoneFault("Network communication error, please try again.  ", "", 500);
        }
    }

}

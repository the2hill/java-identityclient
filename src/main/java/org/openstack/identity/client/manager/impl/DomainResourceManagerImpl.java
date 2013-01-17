package org.openstack.identity.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.domain.Domain;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.DomainResourceManager;

import java.net.URI;
import java.net.URISyntaxException;

public class DomainResourceManagerImpl extends ResponseManagerImpl implements DomainResourceManager {

    @Override
    public Domain getDomain(Client client, String url, String token, String domainId) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + IdentityConstants.RAX_AUTH + IdentityConstants.DOMAINS + "/" + domainId), token);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(Domain.class);
    }
}
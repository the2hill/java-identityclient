package org.openstack.identity.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.SecretQAResourceManager;
import org.openstack.identity.client.secretqa.SecretQA;

import java.net.URI;
import java.net.URISyntaxException;

public class SecretQAResourceManagerImpl extends ResponseManagerImpl implements SecretQAResourceManager {

    @Override
    public SecretQA listSecretQA(Client client, String url, String token, String userId) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + IdentityConstants.USER_PATH + "/" + userId + "/"
                    + IdentityConstants.RAX_KSQA + "/" + IdentityConstants.SECRET_QA), token);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(SecretQA.class);
    }

    @Override
    public SecretQA updateSecretQA(Client client, String url, String token, String userId) throws IdentityFault, URISyntaxException {
        return new SecretQA();
    }
}
package org.openstack.identity.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.util.ResourceUtil;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.SecretQAResourceManager;
import org.openstack.identity.client.secretqa.ObjectFactory;
import org.openstack.identity.client.secretqa.SecretQA;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
    public SecretQA updateSecretQA(Client client, String url, String token, String userId, String question, String answer) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = put(client, new URI(url + IdentityConstants.USER_PATH + "/" + userId + "/"
                    + IdentityConstants.RAX_KSQA + "/" + IdentityConstants.SECRET_QA), token, buildSecretQARequestObject(question, answer));
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw new IdentityFault(e.getMessage(), e.getLinkedException().getLocalizedMessage(), Integer.valueOf(e.getErrorCode()));
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(SecretQA.class);
    }

    private String buildSecretQARequestObject(String question, String answer) throws JAXBException {
        ObjectFactory factory = new ObjectFactory();
        SecretQA qa = factory.createSecretQA();
        qa.setQuestion(question);
        qa.setAnswer(answer);
        return ResourceUtil.marshallResource(factory.createAnswer(qa),
                JAXBContext.newInstance(SecretQA.class)).toString();
    }
}
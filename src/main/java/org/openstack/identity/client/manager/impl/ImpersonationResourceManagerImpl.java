package org.openstack.identity.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.util.ResourceUtil;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.impersonation.Impersonation;
import org.openstack.identity.client.impersonation.ObjectFactory;
import org.openstack.identity.client.manager.ImpersonationResourceManager;
import org.openstack.identity.client.token.AuthenticateResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.net.URI;
import java.net.URISyntaxException;

public class ImpersonationResourceManagerImpl extends ResponseManagerImpl implements ImpersonationResourceManager {

    @Override
    public AuthenticateResponse impersonateUser(Client client, String url, String token, String userName, int epireInSeconds) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            URI test = new URI(url + IdentityConstants.RAX_AUTH + "/" + IdentityConstants.IMPERSONATION_TOKENS_PATH);
            response = post(client, new URI(url + IdentityConstants.RAX_AUTH + "/" + IdentityConstants.IMPERSONATION_TOKENS_PATH), token, buildImpersonationRequestObject(userName, epireInSeconds));
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw new IdentityFault(e.getMessage(), e.getLinkedException().getLocalizedMessage(), Integer.valueOf(e.getErrorCode()));
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(AuthenticateResponse.class);
    }


    private String buildImpersonationRequestObject(String userName, int expireInSeconds) throws JAXBException {
        ObjectFactory factory = new ObjectFactory();

        org.openstack.identity.client.impersonation.User user = factory.createUser();
        user.setUsername(userName);

        Impersonation imp = factory.createImpersonation();
        imp.setUser(user);
        imp.setExpireInSeconds(expireInSeconds);

        return ResourceUtil.marshallResource(factory.createImpersonation(imp),
                JAXBContext.newInstance(Impersonation.class)).toString();
    }
}
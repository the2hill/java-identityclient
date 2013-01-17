package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.token.AuthenticateResponse;

import java.net.URISyntaxException;

public interface ImpersonationResourceManager {

    public AuthenticateResponse impersonateUser(Client client, String url, String token, String userName, int epireInSeconds) throws IdentityFault, URISyntaxException;

}
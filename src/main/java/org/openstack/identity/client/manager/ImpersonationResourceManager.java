package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.identity.client.access.Access;
import org.openstack.identity.client.fault.IdentityFault;

import javax.xml.bind.JAXBException;
import java.net.URISyntaxException;

public interface ImpersonationResourceManager {

    public Access impersonateUser(Client client, String url, String token, String userName, int epireInSeconds) throws IdentityFault, URISyntaxException, JAXBException;

}
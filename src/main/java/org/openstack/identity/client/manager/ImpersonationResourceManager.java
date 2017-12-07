package org.openstack.identity.client.manager;

import org.openstack.identity.client.access.Access;
import org.openstack.identity.client.fault.IdentityFault;

import javax.ws.rs.client.Client;
import javax.xml.bind.JAXBException;
import java.net.URISyntaxException;

public interface ImpersonationResourceManager {

    public Access impersonateUser(Client client, String url, String token, String userName, int epireInSeconds) throws IdentityFault, URISyntaxException, JAXBException;

}
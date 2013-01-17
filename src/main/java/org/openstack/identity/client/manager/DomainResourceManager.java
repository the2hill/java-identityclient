package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.identity.client.domain.Domain;
import org.openstack.identity.client.fault.IdentityFault;

import java.net.URISyntaxException;

public interface DomainResourceManager {

    public Domain getDomain(Client client, String url, String token, String domainId) throws IdentityFault, URISyntaxException;

}
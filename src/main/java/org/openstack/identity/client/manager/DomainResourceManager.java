package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.openstack.identity.client.domain.Domain;
import org.openstack.identity.client.fault.IdentityFault;

import java.net.URISyntaxException;

public interface DomainResourceManager {

    public ClientResponse createDomain(Client client, String url, String token, String domainId, String domainName, boolean enabled, String description) throws IdentityFault, URISyntaxException;

    public Domain getDomain(Client client, String url, String token, String domainId) throws IdentityFault, URISyntaxException;

    public boolean updateDomain(Client client, String url, String token, String domainId, String domainName, String enabled, String description) throws IdentityFault, URISyntaxException;

    public boolean deleteDomain(Client client, String url, String token, String domainId) throws IdentityFault, URISyntaxException;

}
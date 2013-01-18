package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.openstack.identity.client.domain.Domain;
import org.openstack.identity.client.endpoints.EndpointList;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.tenant.Tenants;
import org.openstack.identity.client.user.UserList;

import java.net.URISyntaxException;

public interface DomainResourceManager {

    public ClientResponse createDomain(Client client, String url, String token, String domainId, String domainName, boolean enabled, String description) throws IdentityFault, URISyntaxException;

    public Domain getDomain(Client client, String url, String token, String domainId) throws IdentityFault, URISyntaxException;

    public boolean updateDomain(Client client, String url, String token, String domainId, String domainName, String enabled, String description) throws IdentityFault, URISyntaxException;

    public boolean deleteDomain(Client client, String url, String token, String domainId) throws IdentityFault, URISyntaxException;

    public EndpointList getEndpointsForDomain(Client client, String url, String token, String domainId) throws IdentityFault, URISyntaxException;

    public UserList getUsersFromDomain(Client client, String url, String token, String domainId, String enabled) throws IdentityFault, URISyntaxException;

    public boolean addUserToDomain(Client client, String url, String token, String domainId, String userId) throws IdentityFault, URISyntaxException;

    public Tenants getTenantsFromDomain(Client client, String url, String token, String domainId, String enabled) throws IdentityFault, URISyntaxException;

    public boolean addTenantToDomain(Client client, String url, String token, String domainId, String tenantId) throws IdentityFault, URISyntaxException;

}
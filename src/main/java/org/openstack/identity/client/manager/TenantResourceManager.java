package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.roles.RoleList;
import org.openstack.identity.client.tenant.Tenant;
import org.openstack.identity.client.tenant.Tenants;

import java.net.URISyntaxException;

public interface TenantResourceManager {

    public Tenants retireveTenants(Client client, String url, String token) throws IdentityFault, URISyntaxException;

    public Tenant retireveTenantByName(Client client, String url, String token, String tenantName) throws IdentityFault, URISyntaxException;

    public Tenant retireveTenantById(Client client, String url, String token, String tenantId) throws IdentityFault, URISyntaxException;

    public RoleList retrieveRolesByTenantId(Client client, String url, String token, String tenantId, String userId) throws IdentityFault, URISyntaxException;
}


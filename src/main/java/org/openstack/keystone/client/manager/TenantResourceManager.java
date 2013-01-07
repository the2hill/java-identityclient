package org.openstack.keystone.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.roles.RoleList;
import org.openstack.keystone.client.tenant.Tenant;
import org.openstack.keystone.client.tenant.Tenants;

import java.net.URISyntaxException;

public interface TenantResourceManager {

    public Tenants retireveTenants(Client client, String url, String token) throws KeystoneFault, URISyntaxException;

    public Tenant retireveTenantByName(Client client, String url, String token, String tenantName) throws KeystoneFault, URISyntaxException;

    public Tenant retireveTenantById(Client client, String url, String token, String tenantId) throws KeystoneFault, URISyntaxException;

    public RoleList retrieveRolesByTenantId(Client client, String url, String token, String tenantId, String userId) throws KeystoneFault, URISyntaxException;
}


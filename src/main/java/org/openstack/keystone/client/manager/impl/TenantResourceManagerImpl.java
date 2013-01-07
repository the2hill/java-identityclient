package org.openstack.keystone.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.openstack.keystone.client.common.constants.KeystoneConstants;
import org.openstack.keystone.client.common.wrapper.KeystoneResponseWrapper;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.manager.TenantResourceManager;
import org.openstack.keystone.client.roles.RoleList;
import org.openstack.keystone.client.tenant.Tenant;
import org.openstack.keystone.client.tenant.Tenants;

import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;

public class TenantResourceManagerImpl extends ResponseManagermpl implements TenantResourceManager {


    /**
     * Retrieve tenants by token
     *
     * @param client
     * @param url
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public Tenants retireveTenants(Client client, String url, String token) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + KeystoneConstants.TENANT_PATH), token);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(Tenants.class);
    }

    /**
     * Retrieve tenant by name
     *
     * @param client
     * @param url
     * @param token
     * @param tenantName
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public Tenant retireveTenantByName(Client client, String url, String token, String tenantName) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            MultivaluedMap<String, String> params = new MultivaluedMapImpl();
            params.add(KeystoneConstants.NAME, tenantName);
            response = get(client, new URI(url + KeystoneConstants.TENANT_PATH), token, params);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(Tenant.class);
    }

    /**
     * Retrieve tenant by id
     *
     * @param client
     * @param url
     * @param token
     * @param tenantId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public Tenant retireveTenantById(Client client, String url, String token, String tenantId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        URI uri = new URI(url + KeystoneConstants.TENANT_PATH + "/" + tenantId);
        try {
            response = get(client, uri, token);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(Tenant.class);
    }


    /**
     * Retrieve roles by tenantId
     *
     * @param client
     * @param url
     * @param token
     * @param tenantId
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public RoleList retrieveRolesByTenantId(Client client, String url, String token, String tenantId, String userId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        URI uri = new URI(url + KeystoneConstants.TENANT_PATH + "/" + tenantId + "/users/" + userId);
        try {
            response = get(client, uri, token);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(RoleList.class);
    }
}


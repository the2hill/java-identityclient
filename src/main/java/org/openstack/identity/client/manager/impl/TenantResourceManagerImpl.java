package org.openstack.identity.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.TenantResourceManager;
import org.openstack.identity.client.roles.RoleList;
import org.openstack.identity.client.tenant.Tenant;
import org.openstack.identity.client.tenant.Tenants;

import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;

public class TenantResourceManagerImpl extends ResponseManagerImpl implements TenantResourceManager {


    /**
     * Retrieve tenants by token
     *
     * @param client
     * @param url
     * @param token
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    @Override
    public Tenants retireveTenants(Client client, String url, String token) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + IdentityConstants.TENANT_PATH), token);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    @Override
    public Tenant retireveTenantByName(Client client, String url, String token, String tenantName) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            MultivaluedMap<String, String> params = new MultivaluedMapImpl();
            params.add(IdentityConstants.NAME, tenantName);
            response = get(client, new URI(url + IdentityConstants.TENANT_PATH), token, params);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    @Override
    public Tenant retireveTenantById(Client client, String url, String token, String tenantId) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        URI uri = new URI(url + IdentityConstants.TENANT_PATH + "/" + tenantId);
        try {
            response = get(client, uri, token);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    @Override
    public RoleList retrieveRolesByTenantId(Client client, String url, String token, String tenantId, String userId) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        URI uri = new URI(url + IdentityConstants.TENANT_PATH + "/" + tenantId + "/users/" + userId + "/" + IdentityConstants.ROLES_PATH);
        try {
            response = get(client, uri, token);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(RoleList.class);
    }
}


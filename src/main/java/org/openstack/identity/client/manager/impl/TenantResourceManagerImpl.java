package org.openstack.identity.client.manager.impl;

import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.TenantResourceManager;
import org.openstack.identity.client.roles.RoleList;
import org.openstack.identity.client.tenant.Tenant;
import org.openstack.identity.client.tenant.Tenants;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
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
        Response response = null;
        try {
            response = get(client, new URI(url + IdentityConstants.TENANT_PATH), token);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.readEntity(Tenants.class);
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
        Response response = null;
        try {
            MultivaluedStringMap params = new MultivaluedStringMap();
            params.add(IdentityConstants.NAME, tenantName);
            response = get(client, new URI(url + IdentityConstants.TENANT_PATH), token, params);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.readEntity(Tenant.class);
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
        Response response = null;
        URI uri = new URI(url + IdentityConstants.TENANT_PATH + "/" + tenantId);
        try {
            response = get(client, uri, token);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.readEntity(Tenant.class);
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
        Response response = null;
        URI uri = new URI(url + IdentityConstants.TENANT_PATH + "/" + tenantId + "/users/" + userId + "/" + IdentityConstants.ROLES_PATH);
        try {
            response = get(client, uri, token);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.readEntity(RoleList.class);
    }
}


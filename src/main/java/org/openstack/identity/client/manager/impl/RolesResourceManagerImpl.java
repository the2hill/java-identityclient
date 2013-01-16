package org.openstack.identity.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.RolesResourceManager;
import org.openstack.identity.client.roles.Role;
import org.openstack.identity.client.roles.RoleList;

import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;

public class RolesResourceManagerImpl extends ResponseManagerImpl implements RolesResourceManager {

    /**
     * List roles with serviceId, marker and limit
     *
     * @param client
     * @param url
     * @param token
     * @param serviceId
     * @param marker
     * @param limit
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    @Override
    public RoleList listroles(Client client, String url, String token, String serviceId, String marker, String limit) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            MultivaluedMap<String, String> params = new MultivaluedMapImpl();
            if (marker != null) params.add(IdentityConstants.MARKER, marker);
            if (limit != null) params.add(IdentityConstants.LIMIT, limit);
            if (serviceId != null) params.add(IdentityConstants.SERVICE_ID, serviceId);

            if (params.isEmpty()) {
                response = get(client, new URI(url + IdentityConstants.KSDAM_PATH + "/" + IdentityConstants.ROLES_PATH), token);
            } else {
                response = get(client, new URI(url + IdentityConstants.KSDAM_PATH + "/" + IdentityConstants.ROLES_PATH), token, params);
            }
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(RoleList.class);

    }

    @Override public Role addRole(Client client, String url, String token, String name, String description) throws IdentityFault, URISyntaxException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Role getRole(Client client, String url, String token, String roleId) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + IdentityConstants.KSDAM_PATH + "/" + IdentityConstants.ROLES_PATH + "/" + roleId), token);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(Role.class);
    }

    @Override public Role addGlobalRoleToUser(Client client, String url, String token, String userId, String roleId) throws IdentityFault, URISyntaxException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean deleteGlobalRoleFromUser(Client client, String url, String token, String userId, String roleId) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + IdentityConstants.USER_PATH + "/" + userId + IdentityConstants.ROLES_PATH + "/" + IdentityConstants.KSDAM_PATH + "/" + roleId), token);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return true;
    }

    @Override
    public RoleList listUserGlobalRoles(Client client, String url, String token, String userId) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + IdentityConstants.USER_PATH + "/" + userId + "/" + IdentityConstants.ROLES_PATH), token);
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(RoleList.class);
    }
}

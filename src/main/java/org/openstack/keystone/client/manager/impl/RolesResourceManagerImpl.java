package org.openstack.keystone.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.openstack.keystone.client.common.constants.KeystoneConstants;
import org.openstack.keystone.client.common.wrapper.KeystoneResponseWrapper;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.manager.RolesResourceManager;
import org.openstack.keystone.client.roles.Role;
import org.openstack.keystone.client.roles.RoleList;

import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;

public class RolesResourceManagerImpl extends ResponseManagerImpl implements RolesResourceManager {

    /**
     * List all roles
     *
     * @param client
     * @param url
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public RoleList listroles(Client client, String url, String token) throws KeystoneFault, URISyntaxException {
        return listroles(client, url, token, null, null, null);
    }

    /**
     * List roles with serviceId
     *
     * @param client
     * @param url
     * @param token
     * @param serviceId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public RoleList listroles(Client client, String url, String token, String serviceId) throws KeystoneFault, URISyntaxException {
        return listroles(client, url, token, serviceId, null, null);
    }

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
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public RoleList listroles(Client client, String url, String token, String serviceId, String marker, String limit) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            MultivaluedMap<String, String> params = new MultivaluedMapImpl();
            if (marker != null) params.add(KeystoneConstants.MARKER, marker);
            if (limit != null) params.add(KeystoneConstants.LIMIT, limit);
            if (serviceId != null) params.add(KeystoneConstants.SERVICE_ID, serviceId);

            if (params.isEmpty()) {
                response = get(client, new URI(url + KeystoneConstants.KSDAM_PATH + "/" + KeystoneConstants.ROLES_PATH), token);
            } else {
                response = get(client, new URI(url + KeystoneConstants.KSDAM_PATH + "/" + KeystoneConstants.ROLES_PATH), token, params);
            }
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(RoleList.class);
    }

    @Override public Role addRole(Client client, String url, String token, String name, String description) throws KeystoneFault, URISyntaxException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public Role getRole(Client client, String url, String token, String roleId) throws KeystoneFault, URISyntaxException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public Role addGlobalRoleToUser(Client client, String url, String token, String userId, String roleId) throws KeystoneFault, URISyntaxException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public Role deleteGlobalRoleToUser(Client client, String url, String token, String userId, String roleId) throws KeystoneFault, URISyntaxException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public RoleList listUserGlobalRoles(Client client, String url, String token, String userId, String roleId) throws KeystoneFault, URISyntaxException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

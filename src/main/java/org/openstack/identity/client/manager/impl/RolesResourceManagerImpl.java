package org.openstack.identity.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.util.ResourceUtil;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.RolesResourceManager;
import org.openstack.identity.client.roles.ObjectFactory;
import org.openstack.identity.client.roles.Role;
import org.openstack.identity.client.roles.RoleList;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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

    /**
     * Add role
     *
     * @param client
     * @param url
     * @param token
     * @param name
     * @param description
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    @Override
    public Role addRole(Client client, String url, String token, String name, String description) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = post(client, new URI(url + IdentityConstants.KSDAM_PATH + "/" + IdentityConstants.ROLES_PATH), token, buildAddRoleRequestObject(name, description));
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw new IdentityFault(e.getMessage(), e.getLinkedException().getLocalizedMessage(), Integer.valueOf(e.getErrorCode()));
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(Role.class);
    }

    /**
     * Retrieve role by roleId
     *
     * @param client
     * @param url
     * @param token
     * @param roleId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
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

    /**
     * Add global role to user by userId and roleId
     *
     * @param client
     * @param url
     * @param token
     * @param userId
     * @param roleId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    @Override
    public boolean addGlobalRoleToUser(Client client, String url, String token, String userId, String roleId) throws IdentityFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = put(client, new URI(url + IdentityConstants.USER_PATH + "/" + userId + "/" + IdentityConstants.ROLES_PATH + "/" + IdentityConstants.KSDAM_PATH + "/" + roleId), token, "");
        } catch (UniformInterfaceException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return true;
    }

    /**
     * Delete global role from user
     *
     * @param client
     * @param url
     * @param token
     * @param userId
     * @param roleId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
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

    /**
     * List global roles for user by userId
     *
     * @param client
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
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

    /**
     * Generate add role request object
     *
     * @param name
     * @param description
     * @return
     * @throws JAXBException
     */
    private String buildAddRoleRequestObject(String name, String description) throws JAXBException {
        ObjectFactory factory = new ObjectFactory();
        Role roleAdd = factory.createRole();
        roleAdd.setName(name);
        roleAdd.setDescription(description);
        return ResourceUtil.marshallResource(factory.createRole(roleAdd),
                JAXBContext.newInstance(Role.class)).toString();
    }
}

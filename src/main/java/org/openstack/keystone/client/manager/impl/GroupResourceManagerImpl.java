package org.openstack.keystone.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.openstack.keystone.client.common.constants.KeystoneConstants;
import org.openstack.keystone.client.common.util.ResourceUtil;
import org.openstack.keystone.client.common.wrapper.KeystoneResponseWrapper;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.group.Group;
import org.openstack.keystone.client.group.GroupList;
import org.openstack.keystone.client.group.ObjectFactory;
import org.openstack.keystone.client.manager.GroupResourceManager;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.net.URI;
import java.net.URISyntaxException;

public class GroupResourceManagerImpl extends ResponseManagerImpl implements GroupResourceManager {

    /**
     * List available groups
     *
     * @param client
     * @param url
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */

    public GroupList listGroups(Client client, String url, String token) throws KeystoneFault, URISyntaxException {
        return listGroups(client, url, token, null, null, null);
    }

    /**
     * List available groups with parameters
     *
     * @param client
     * @param url
     * @param token
     * @param marker
     * @param limit
     * @param name
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public GroupList listGroups(Client client, String url, String token, String marker, String limit, String name) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            MultivaluedMap<String, String> params = new MultivaluedMapImpl();
            if (marker != null) params.add(KeystoneConstants.MARKER, marker);
            if (limit != null) params.add(KeystoneConstants.LIMIT, limit);
            if (name != null) params.add(KeystoneConstants.NAME, name);

            if (params.isEmpty()) {
                response = get(client, new URI(url + KeystoneConstants.RAX_GROUP), token);
            } else {
                response = get(client, new URI(url + KeystoneConstants.RAX_GROUP), token, params);
            }

        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(GroupList.class);
    }

    /**
     * Add group
     *
     *
     * @param client
     * @param url
     * @param token
     * @param name
     * @param description
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public Group addGroup(Client client, String url, String token, String name, String description) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = post(client, new URI(url + KeystoneConstants.RAX_GROUP), token, buildAddGroupRequest(name, description));
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            handleBadResponse(null);
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(Group.class);
    }

    /**
     * Update a Group
     *
     * @param client
     * @param url
     * @param token
     * @param name
     * @param description
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public Group updateGroup(Client client, String url, String token, String groupId, String name, String description) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = put(client, new URI(url + KeystoneConstants.RAX_GROUP + "/" + groupId), token, buildUpdateGroupRequest(name, description));
        } catch (JAXBException je) {
            handleBadResponse(null);
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(Group.class);
    }

    /**
     * Retrieve group by group id
     *
     * @param client
     * @param url
     * @param token
     * @param groupId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public Group retrieveGroup(Client client, String url, String token, String groupId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + KeystoneConstants.RAX_GROUP + "/" + groupId), token);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(Group.class);
    }

    /**
     * Delete group by groupId
     *
     *
     * @param client
     * @param url
     * @param token
     * @param groupId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public boolean deleteGroup(Client client, String url, String token, String groupId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = delete(client, new URI(url + KeystoneConstants.RAX_GROUP + "/" + groupId), token);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return true;
    }

    /**
     * Add a user to a group
     *
     * @param client
     * @param url
     * @param token
     * @param userId
     * @param groupId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public boolean addUserToGroup(Client client, String url, String token, String userId, String groupId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = put(client, new URI(url + KeystoneConstants.RAX_GROUP + "/" + groupId
                    + "/" + KeystoneConstants.USER_PATH + "/" + userId), token, null);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return true;
    }

    /**
     * Generate add group request
     *
     * @param name
     * @param description
     * @return
     * @throws JAXBException
     */
    private String buildAddGroupRequest(String name, String description) throws JAXBException {
        ObjectFactory factory = new ObjectFactory();
        Group group = factory.createGroup();
        group.setDescription(description);
        group.setName(name);
        return ResourceUtil.marshallResource(factory.createGroup(group),
                JAXBContext.newInstance(GroupList.class)).toString();
    }

    /**
     * Generate an update group request
     *
     * @param name
     * @param description
     * @return
     * @throws JAXBException
     */
    private String buildUpdateGroupRequest(String name, String description) throws JAXBException {
        // Figure out a better way to error out of the requiest
        if (name == null && description == null) {
            throw new JAXBException("");
        }
        ObjectFactory factory = new ObjectFactory();
        Group group = factory.createGroup();
        if (description != null) {
            group.setDescription(description);
        }
        if (name != null) {
            group.setName(name);
        }
        return ResourceUtil.marshallResource(factory.createGroup(group),
                JAXBContext.newInstance(GroupList.class)).toString();
    }

}

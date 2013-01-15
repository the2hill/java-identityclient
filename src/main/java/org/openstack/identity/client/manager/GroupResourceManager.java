package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.group.Group;
import org.openstack.identity.client.group.GroupList;

import java.net.URISyntaxException;

public interface GroupResourceManager {

    public GroupList listGroups(Client client, String url, String token, String marker, String limit, String name) throws IdentityFault, URISyntaxException;

    public GroupList listGroupsForUser(Client client, String url, String token, String marker, String limit, String name) throws IdentityFault, URISyntaxException;

    public Group addGroup(Client client, String url, String token, String name, String description) throws IdentityFault, URISyntaxException;

    public Group updateGroup(Client client, String url, String token, String groupId, String name, String description) throws IdentityFault, URISyntaxException;

    public Group retrieveGroup(Client client, String url, String token, String groupId) throws IdentityFault, URISyntaxException;

    public boolean deleteGroup(Client client, String url, String token, String groupId) throws IdentityFault, URISyntaxException;

    public boolean addUserToGroup(Client client, String url, String token, String userId, String groupId) throws IdentityFault, URISyntaxException;

    public boolean removeUserFromGroup(Client client, String url, String token, String groupId, String userId) throws IdentityFault, URISyntaxException;
}

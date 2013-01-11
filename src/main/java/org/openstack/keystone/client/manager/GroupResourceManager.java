package org.openstack.keystone.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.group.Group;
import org.openstack.keystone.client.group.GroupList;

import java.net.URISyntaxException;

public interface GroupResourceManager {

    public GroupList listGroups(Client client, String url, String token) throws KeystoneFault, URISyntaxException;

    public GroupList listGroups(Client client, String url, String token, String marker, String limit, String name) throws KeystoneFault, URISyntaxException;

    public Group addGroup(Client client, String url, String token, String name, String description) throws KeystoneFault, URISyntaxException;

    public Group retrieveGroup(Client client, String url, String token, String groupId) throws KeystoneFault, URISyntaxException;

    public boolean deleteGroup(Client client, String url, String token, String groupId) throws KeystoneFault, URISyntaxException;

    public boolean addUserToGroup(Client client, String url, String token, String userId, String groupId) throws KeystoneFault, URISyntaxException;
}

package org.openstack.keystone.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.roles.Role;
import org.openstack.keystone.client.roles.RoleList;

import java.net.URISyntaxException;

public interface RolesResourceManager {
    public RoleList listroles(Client client, String url, String token) throws KeystoneFault, URISyntaxException;

    public RoleList listroles(Client client, String url, String token, String serviceId) throws KeystoneFault, URISyntaxException;

    public RoleList listroles(Client client, String url, String token, String serviceId, String marker, String limit) throws KeystoneFault, URISyntaxException;

    public Role addRole(Client client, String url, String token, String name, String description) throws KeystoneFault, URISyntaxException;

    public Role getRole(Client client, String url, String token, String roleId) throws KeystoneFault, URISyntaxException;

    public Role addGlobalRoleToUser(Client client, String url, String token, String userId, String roleId) throws KeystoneFault, URISyntaxException;

    public Role deleteGlobalRoleToUser(Client client, String url, String token, String userId, String roleId) throws KeystoneFault, URISyntaxException;

    public RoleList listUserGlobalRoles(Client client, String url, String token, String userId, String roleId) throws KeystoneFault, URISyntaxException;
}

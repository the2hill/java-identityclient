package org.openstack.identity.client.manager;

import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.roles.Role;
import org.openstack.identity.client.roles.RoleList;

import javax.ws.rs.client.Client;
import java.net.URISyntaxException;

public interface RolesResourceManager {
    public RoleList listroles(Client client, String url, String token, String serviceId, String marker, String limit) throws IdentityFault, URISyntaxException;

    public Role addRole(Client client, String url, String token, String name, String description) throws IdentityFault, URISyntaxException;

    public Role getRole(Client client, String url, String token, String roleId) throws IdentityFault, URISyntaxException;

    public boolean addGlobalRoleToUser(Client client, String url, String token, String userId, String roleId) throws IdentityFault, URISyntaxException;

    public boolean deleteGlobalRoleFromUser(Client client, String url, String token, String userId, String roleId) throws IdentityFault, URISyntaxException;

    public RoleList listUserGlobalRoles(Client client, String url, String token, String userId) throws IdentityFault, URISyntaxException;
}

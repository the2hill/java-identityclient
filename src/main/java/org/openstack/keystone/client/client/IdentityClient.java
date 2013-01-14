package org.openstack.keystone.client.client;

import com.sun.jersey.api.client.Client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.keystone.client.endpoints.EndpointList;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.group.Group;
import org.openstack.keystone.client.group.GroupList;
import org.openstack.keystone.client.manager.*;
import org.openstack.keystone.client.manager.entity.Credentials;
import org.openstack.keystone.client.manager.impl.*;
import org.openstack.keystone.client.roles.RoleList;
import org.openstack.keystone.client.tenant.Tenant;
import org.openstack.keystone.client.tenant.Tenants;
import org.openstack.keystone.client.token.AuthenticateResponse;
import org.openstack.keystone.client.user.User;
import org.openstack.keystone.client.user.UserList;

import javax.xml.bind.JAXBException;
import java.net.URISyntaxException;

public class IdentityClient extends KeystoneManager {
    private final Log logger = LogFactory.getLog(IdentityClient.class);
    private AuthenticationResourceManager authenticationResourceManager = new AuthenticationResourceManagerImpl();
    private TokenResourceManager tokenResourceManager = new TokenResourceManagerImpl();
    private TenantResourceManager tenantResourceManager = new TenantResourceManagerImpl();
    private UserResourceManager userResourceManager = new UserResourceManagerImpl();
    private GroupResourceManager groupResourceManager = new GroupResourceManagerImpl();
    private RolesResourceManager rolesResourceManager = new RolesResourceManagerImpl();

    public IdentityClient(String authUrl, Client client) throws KeystoneFault {
        super(authUrl, client);
    }

    public IdentityClient(String authUrl) throws KeystoneFault {
        super(authUrl);
    }

    public IdentityClient() throws KeystoneFault {
        super();
    }

    //Authentication requests:

    /**
     * Authenticate user with username and password
     *
     * @param username
     * @param password
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateUsernamePassword(String username, String password) throws KeystoneFault, URISyntaxException {
        return authenticateUsernamePassword(url, username, password);
    }

    /**
     * Authenticate user with specific url, username and password
     *
     * @param url
     * @param username
     * @param password
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateUsernamePassword(String url, String username, String password) throws KeystoneFault, URISyntaxException {
        return authenticationResourceManager.authenticateUsernamePassword(client, url, username, password);
    }

    /**
     * Authenticate user with tenantName and tokenId
     *
     * @param tenantName
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantNameTokenId(String tenantName, String tokenId) throws KeystoneFault, URISyntaxException {
        return authenticateTenantNameTokenId(url, tenantName, tokenId);
    }

    /**
     * Authenticate user with specific url, tenantName and tokenId
     *
     * @param url
     * @param tenantName
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantNameTokenId(String url, String tenantName, String tokenId) throws KeystoneFault, URISyntaxException {
        return authenticationResourceManager.authenticateTenantNameTokenId(client, url, tenantName, tokenId);
    }

    /**
     * Authenticate user with  tenantId and tenantName
     *
     * @param tenantId
     * @param tenantName
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdUsernameApiKey(String tenantId, String tenantName, String tokenId) throws KeystoneFault, URISyntaxException {
        return authenticateTenantIdUsernameApiKey(url, tenantId, tenantName, tokenId);
    }

    /**
     * Authenticate user with specific url, tenantId, tenantName and tokenId
     *
     * @param url
     * @param tenantId
     * @param tenantName
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdUsernameApiKey(String url, String tenantId, String tenantName, String tokenId) throws KeystoneFault, URISyntaxException {
        return authenticationResourceManager.authenticateTenantIdUsernameApiKey(client, url, tenantId, tenantName, tokenId);
    }

    /**
     * Authenticate user with tenantId, username and password
     *
     * @param tenantId
     * @param username
     * @param password
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdUsernamePassword(String tenantId, String username, String password) throws KeystoneFault, URISyntaxException {
        return authenticateTenantIdUsernamePassword(url, tenantId, username, password);
    }

    /**
     * Authenticate user with specific url, tenantId username and password
     *
     * @param url
     * @param tenantId
     * @param username
     * @param password
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdUsernamePassword(String url, String tenantId, String username, String password) throws KeystoneFault, URISyntaxException {
        return authenticationResourceManager.authenticateTenantIdUsernamePassword(client, url, tenantId, username, password);
    }

    /**
     * Authenticate user with tenantId and tokenId
     *
     * @param tenantId
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdTokenId(String tenantId, String tokenId) throws KeystoneFault, URISyntaxException {
        return authenticateTenantIdTokenId(url, tenantId, tokenId);
    }

    /**
     * Authenticate user with specific url, tenantid and tokenId
     *
     * @param url
     * @param tenantId
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdTokenId(String url, String tenantId, String tokenId) throws KeystoneFault, URISyntaxException {
        return authenticationResourceManager.authenticateTenantIdTokenId(client, url, tenantId, tokenId);
    }

    /**
     * Authenticate user with tenantName and tokenId
     *
     * @param tenantName
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateUsernameApiKey(String tenantName, String tokenId) throws KeystoneFault, URISyntaxException {
        return authenticateUsernameApiKey(url, tenantName, tokenId);
    }

    /**
     * Authenticate user with specific url, tenantName and tokenId
     *
     * @param url
     * @param tenantName
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateUsernameApiKey(String url, String tenantName, String tokenId) throws KeystoneFault, URISyntaxException {
        return authenticationResourceManager.authenticateUsernameApiKey(client, url, tenantName, tokenId);
    }

    //Token requests:

    /**
     * Validate token for tenantName
     *
     * @param token
     * @param tenantName
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse validateToken(String token, String tenantName) throws KeystoneFault, URISyntaxException {
        return validateToken(url, token, token, tenantName);
    }

    /**
     * Validate token for tenantName with admin account
     *
     * @param adminToken
     * @param token
     * @param tenantName
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse validateToken(String adminToken, String token, String tenantName) throws KeystoneFault, URISyntaxException {
        return validateToken(url, adminToken, token, tenantName);
    }


    /**
     * Validate token for tenantName with specific url with admin account
     *
     * @param url
     * @param adminToken
     * @param token
     * @param tenantName
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse validateToken(String url, String adminToken, String token, String tenantName) throws KeystoneFault, URISyntaxException {
        return tokenResourceManager.validateToken(client, url, adminToken, token, tenantName);
    }

    /**
     * Retrieve endpoints for token
     *
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public EndpointList retrieveEndpointsForToken(String token) throws KeystoneFault, URISyntaxException {
        return retrieveEndpointsForToken(url, token, token);
    }

    /**
     * Retrieve endpoints for token with admin account
     *
     * @param adminToken
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public EndpointList retrieveEndpointsForToken(String adminToken, String token) throws KeystoneFault, URISyntaxException {
        return retrieveEndpointsForToken(url, adminToken, token);
    }

    /**
     * Retrieve endpoints for token with specific url with admin account
     *
     * @param url
     * @param adminToken
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public EndpointList retrieveEndpointsForToken(String url, String adminToken, String token) throws KeystoneFault, URISyntaxException {
        return tokenResourceManager.retrieveEndpointsForToken(client, url, adminToken, token);
    }

    //Tenant requests:

    /**
     * Retrieve tenants based off of the users tokenId
     *
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Tenants retrieveTenants(String tokenId) throws KeystoneFault, URISyntaxException {
        return retrieveTenants(url, tokenId);
    }

    /**
     * Retrieve tenants with specific url based off of the users tokenId
     *
     * @param url
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Tenants retrieveTenants(String url, String token) throws KeystoneFault, URISyntaxException {
        return tenantResourceManager.retireveTenants(client, url, token);
    }

    /**
     * Retrieve tenant by id
     *
     * @param token
     * @param tenantId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Tenant retireveTenantById(String token, String tenantId) throws KeystoneFault, URISyntaxException {
        return retireveTenantById(url, token, tenantId);
    }

    /**
     * Retrieve tenant by id  with specific url
     *
     * @param url
     * @param token
     * @param tenantId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Tenant retireveTenantById(String url, String token, String tenantId) throws KeystoneFault, URISyntaxException {
        return tenantResourceManager.retireveTenantById(client, url, token, tenantId);
    }

    /**
     * Retrieve tenant by name
     *
     * @param token
     * @param tenantName
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Tenant retireveTenantByname(String token, String tenantName) throws KeystoneFault, URISyntaxException {
        return retireveTenantByName(url, token, tenantName);
    }

    /**
     * Retrieve tenant by name  with specific url
     *
     * @param url
     * @param token
     * @param tenantName
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Tenant retireveTenantByName(String url, String token, String tenantName) throws KeystoneFault, URISyntaxException {
        return tenantResourceManager.retireveTenantByName(client, url, token, tenantName);
    }

    /**
     * Retrieve roles by tenantId
     *
     * @param token
     * @param tenantName
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public RoleList retireveRolesByTenantId(String token, String tenantName, String userId) throws KeystoneFault, URISyntaxException {
        return retireveRolesByTenantId(url, token, tenantName, userId);
    }

    /**
     * Retrieve roles by tenantId and specific url
     *
     * @param url
     * @param token
     * @param tenantName
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public RoleList retireveRolesByTenantId(String url, String token, String tenantName, String userId) throws KeystoneFault, URISyntaxException {
        return tenantResourceManager.retrieveRolesByTenantId(client, url, token, tenantName, userId);
    }

    //User requests:

    /**
     * List users by token
     *
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public UserList listUsers(String token) throws KeystoneFault, URISyntaxException {
        return listUsers(url, token);
    }

    /**
     * List users by token with specific url
     *
     * @param url
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public UserList listUsers(String url, String token) throws KeystoneFault, URISyntaxException {
        return userResourceManager.listUsers(client, url, token);
    }

    /**
     * List user by name
     *
     * @param token
     * @param username
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public User listUserByName(String token, String username) throws KeystoneFault, URISyntaxException {
        return listUserByName(url, token, username);
    }

    /**
     * List user by name with specific url
     *
     * @param url
     * @param token
     * @param username
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public User listUserByName(String url, String token, String username) throws KeystoneFault, URISyntaxException {
        return userResourceManager.listUserByName(client, url, token, username);
    }

    /**
     * List user by id
     *
     * @param token
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public User listUserById(String token, String userId) throws KeystoneFault, URISyntaxException {
        return listUserById(url, token, userId);
    }

    /**
     * List user by id with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public User listUserById(String url, String token, String userId) throws KeystoneFault, URISyntaxException {
        return userResourceManager.listUserById(client, url, token, userId);
    }

    /**
     * List user credentials
     *
     * @param token
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Credentials listCredentials(String token, String userId) throws KeystoneFault, URISyntaxException {
        return listCredentials(url, token, userId);
    }

    /**
     * List user credentials with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Credentials listCredentials(String url, String token, String userId) throws KeystoneFault, URISyntaxException {
        return userResourceManager.listCredentials(client, url, token, userId);
    }

    /**
     * Add user with generated password
     *
     * @param token
     * @param username
     * @param enabled
     * @param email
     * @param region
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User addUser(String token, String username, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException {
        return addUser(url, token, username, null, enabled, email, region);
    }

    /**
     * Add user with password
     *
     * @param token
     * @param username
     * @param password
     * @param enabled
     * @param email
     * @param region
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User addUser(String token, String username, String password, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException {
        return addUser(url, token, username, password, enabled, email, region);
    }

    /**
     * Add user with specified password
     *
     * @param url
     * @param token
     * @param username
     * @param password
     * @param enabled
     * @param email
     * @param region
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User addUser(String url, String token, String username, String password, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException {
        return userResourceManager.addUser(client, url, token, username, password, enabled, email, region);
    }


    /**
     * Update user based off of userId
     *
     * @param token
     * @param userId
     * @param username
     * @param enabled
     * @param email
     * @param region
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User updateUser(String token, String userId, String username, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException {
        return updateUser(url, token, userId, username, enabled, email, region);
    }

    /**
     * Update user based off of userId with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @param username
     * @param enabled
     * @param email
     * @param region
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User updateUser(String url, String token, String userId, String username, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException {
        return userResourceManager.updateUser(client, url, token, userId, username, enabled, email, region);
    }

    /**
     * Update user password
     *
     * @param token
     * @param userId
     * @param password
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User updateUserPassword(String token, String userId, String password) throws KeystoneFault, URISyntaxException, JAXBException {
        return updateUserPassword(url, token, userId, password);
    }

    /**
     * Update user password with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @param password
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User updateUserPassword(String url, String token, String userId, String password) throws KeystoneFault, URISyntaxException, JAXBException {
        return userResourceManager.updateUserPassword(client, url, token, userId, password);
    }

    /**
     * Update user credentials
     *
     * @param token
     * @param userId
     * @param apiKey
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public AuthenticateResponse updateUserCredentials(String token, String userId, String apiKey) throws KeystoneFault, URISyntaxException, JAXBException {
        return updateUserCredentials(url, token, userId, apiKey);
    }

    /**
     * Update user credentials with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @param apiKey
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public AuthenticateResponse updateUserCredentials(String url, String token, String userId, String apiKey) throws KeystoneFault, URISyntaxException, JAXBException {
        return userResourceManager.updateUserCredentials(client, url, token, userId, apiKey);
    }

    /**
     * Delete user credentials
     *
     * @param token
     * @param userId
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public void deleteUserCredentials(String token, String userId) throws KeystoneFault, URISyntaxException, JAXBException {
        deleteUserCredentials(url, token, userId);
    }

    /**
     * Delete user credentials with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public void deleteUserCredentials(String url, String token, String userId) throws KeystoneFault, URISyntaxException, JAXBException {
        userResourceManager.deleteUserCredentials(client, url, token, userId);
    }


    /**
     * Delete user
     *
     * @param token
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User deleteUser(String token, String userId) throws KeystoneFault, URISyntaxException, JAXBException {
        return deleteUser(url, token, userId);
    }


    /**
     * Delete user with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User deleteUser(String url, String token, String userId) throws KeystoneFault, URISyntaxException, JAXBException {
        return userResourceManager.deleteUser(client, url, token, userId);
    }

    //Groups requests:

    /**
     * List available groups
     *
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public GroupList listGroups(String token) throws KeystoneFault, URISyntaxException {
        return listGroups(url, token, null, null, null);
    }

    /**
     * List available group with optional paramaters
     *
     * @param token
     * @param marker
     * @param limit
     * @param name
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public GroupList listGroups(String token, String marker, String limit, String name) throws KeystoneFault, URISyntaxException {
        return listGroups(url, token, marker, limit, name);
    }

    /**
     * List available groups with specific url
     *
     * @param url
     * @param token
     * @param marker
     * @param limit
     * @param name
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public GroupList listGroups(String url, String token, String marker, String limit, String name) throws KeystoneFault, URISyntaxException {
        return groupResourceManager.listGroups(client, url, token, marker, limit, name);
    }

    /**
     * Add group
     *
     *
     * @param token
     * @param name
     * @param description
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Group addGroup(String token, String name, String description) throws KeystoneFault, URISyntaxException {
        return addGroup(url, token, name, description);
    }

    /**
     * Add group with specific url
     *
     *
     * @param url
     * @param token
     * @param name
     * @param description
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Group addGroup(String url, String token, String name, String description) throws KeystoneFault, URISyntaxException {
        return groupResourceManager.addGroup(client, url, token, name, description);
    }

    /**
     * Retrieve group by groupId
     *
     * @param token
     * @param groupId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Group retrieveGroup(String token, String groupId) throws KeystoneFault, URISyntaxException {
        return retrieveGroup(url, token, groupId);
    }

    /**
     * Retrieve group by groupId and specific url
     *
     * @param url
     * @param token
     * @param groupId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public Group retrieveGroup(String url, String token, String groupId) throws KeystoneFault, URISyntaxException {
        return groupResourceManager.retrieveGroup(client, url, token, groupId);
    }

    /**
     * Delete group by groupId
     *
     *
     * @param token
     * @param groupId
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @return
     */
    public boolean deleteGroup(String token, String groupId) throws KeystoneFault, URISyntaxException {
        return deleteGroup(url, token, groupId);
    }

    /**
     * Delete group by groupId with specific url
     *
     * @param url
     * @param token
     * @param groupId
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @return
     */
    public boolean deleteGroup(String url, String token, String groupId) throws KeystoneFault, URISyntaxException {
       return groupResourceManager.deleteGroup(client, url, token, groupId);
    }

    /**
     * Add user to group
     *
     * @param token
     * @param userId
     * @param groupId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public boolean addUserToGroup(String token, String userId, String groupId) throws KeystoneFault, URISyntaxException {
        return addUserToGroup(url, token, userId, groupId);
    }

    /**
     * Add user to group with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @param groupId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    public boolean addUserToGroup(String url, String token, String userId, String groupId) throws KeystoneFault, URISyntaxException {
        return groupResourceManager.addUserToGroup(client, url, token, userId, groupId);
    }


    /********************************************************************************************************************************/
    /********************************************************************************************************************************/
    /**********************************************************ROLES*****************************************************************/
    /********************************************************************************************************************************/
    /********************************************************************************************************************************/


    /**
     * List roles
     *
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
      public RoleList listRoles(String token) throws KeystoneFault, URISyntaxException {
        return rolesResourceManager.listroles(client, url, token);
    }
}


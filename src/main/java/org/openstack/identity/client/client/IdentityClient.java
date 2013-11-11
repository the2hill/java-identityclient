package org.openstack.identity.client.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.identity.client.access.Access;
import org.openstack.identity.client.domain.Domain;
import org.openstack.identity.client.endpoints.EndpointList;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.group.Group;
import org.openstack.identity.client.group.GroupList;
import org.openstack.identity.client.manager.*;
import org.openstack.identity.client.manager.entity.Credentials;
import org.openstack.identity.client.manager.impl.*;
import org.openstack.identity.client.roles.Role;
import org.openstack.identity.client.roles.RoleList;
import org.openstack.identity.client.secretqa.SecretQA;
import org.openstack.identity.client.tenant.Tenant;
import org.openstack.identity.client.tenant.Tenants;
import org.openstack.identity.client.token.AuthenticateResponse;
import org.openstack.identity.client.user.User;
import org.openstack.identity.client.user.UserList;

import javax.xml.bind.JAXBException;
import java.net.URISyntaxException;

public class IdentityClient extends IdentityManager {
    private final Log logger = LogFactory.getLog(IdentityClient.class);
    private AuthenticationResourceManager authenticationResourceManager = new AuthenticationResourceManagerImpl();
    private TokenResourceManager tokenResourceManager = new TokenResourceManagerImpl();
    private TenantResourceManager tenantResourceManager = new TenantResourceManagerImpl();
    private UserResourceManager userResourceManager = new UserResourceManagerImpl();
    private GroupResourceManager groupResourceManager = new GroupResourceManagerImpl();
    private RolesResourceManager rolesResourceManager = new RolesResourceManagerImpl();
    private SecretQAResourceManager secretQAResourceManager = new SecretQAResourceManagerImpl();
    private ImpersonationResourceManager impersonationResourceManager = new ImpersonationResourceManagerImpl();
    private DomainResourceManager domainResourceManager = new DomainResourceManagerImpl();

    public IdentityClient(String authUrl, Client client) throws IdentityFault {
        super(authUrl, client, false);
    }

    public IdentityClient(String authUrl, Client client, boolean isDebugging) throws IdentityFault {
        super(authUrl, client, isDebugging);
    }

    public IdentityClient(String authUrl) throws IdentityFault {
        super(authUrl);
    }

    public IdentityClient(String authUrl, int timeout) throws IdentityFault {
        super(authUrl, timeout);
    }

    public IdentityClient(String authUrl, int timeout, boolean isDebugging) throws IdentityFault {
        super(authUrl, timeout, isDebugging);
    }

    public IdentityClient(String authUrl, boolean isDebugging) throws IdentityFault {
        super(authUrl, isDebugging);
    }

    public IdentityClient() throws IdentityFault {
        super();
    }

    public IdentityClient(boolean isDebugging) throws IdentityFault {
        super(isDebugging);
    }

    /* ******************************************************************************************************************/
    /*                                                 AUTHENTICATION                                                   */
    /* ******************************************************************************************************************/

    /**
     * Authenticate user with username and password
     *
     * @param username
     * @param password
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateUsernamePassword(String username, String password) throws IdentityFault, URISyntaxException {
        return authenticateUsernamePassword(url, username, password);
    }

    /**
     * Authenticate user with specific url, username and password
     *
     * @param url
     * @param username
     * @param password
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateUsernamePassword(String url, String username, String password) throws IdentityFault, URISyntaxException {
        return authenticationResourceManager.authenticateUsernamePassword(client, url, username, password);
    }

    /**
     * Authenticate user with tenantName and tokenId
     *
     * @param tenantName
     * @param tokenId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantNameTokenId(String tenantName, String tokenId) throws IdentityFault, URISyntaxException {
        return authenticateTenantNameTokenId(url, tenantName, tokenId);
    }

    /**
     * Authenticate user with specific url, tenantName and tokenId
     *
     * @param url
     * @param tenantName
     * @param tokenId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantNameTokenId(String url, String tenantName, String tokenId) throws IdentityFault, URISyntaxException {
        return authenticationResourceManager.authenticateTenantNameTokenId(client, url, tenantName, tokenId);
    }

    /**
     * Authenticate user with  tenantId and tenantName
     *
     * @param tenantId
     * @param tenantName
     * @param tokenId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdUsernameApiKey(String tenantId, String tenantName, String tokenId) throws IdentityFault, URISyntaxException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdUsernameApiKey(String url, String tenantId, String tenantName, String tokenId) throws IdentityFault, URISyntaxException {
        return authenticationResourceManager.authenticateTenantIdUsernameApiKey(client, url, tenantId, tenantName, tokenId);
    }

    /**
     * Authenticate user with tenantId, username and password
     *
     * @param tenantId
     * @param username
     * @param password
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdUsernamePassword(String tenantId, String username, String password) throws IdentityFault, URISyntaxException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdUsernamePassword(String url, String tenantId, String username, String password) throws IdentityFault, URISyntaxException {
        return authenticationResourceManager.authenticateTenantIdUsernamePassword(client, url, tenantId, username, password);
    }

    /**
     * Authenticate user with tenantId and tokenId
     *
     * @param tenantId
     * @param tokenId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdTokenId(String tenantId, String tokenId) throws IdentityFault, URISyntaxException {
        return authenticateTenantIdTokenId(url, tenantId, tokenId);
    }

    /**
     * Authenticate user with specific url, tenantid and tokenId
     *
     * @param url
     * @param tenantId
     * @param tokenId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateTenantIdTokenId(String url, String tenantId, String tokenId) throws IdentityFault, URISyntaxException {
        return authenticationResourceManager.authenticateTenantIdTokenId(client, url, tenantId, tokenId);
    }

    /**
     * Authenticate user with tenantName and tokenId
     *
     * @param tenantName
     * @param tokenId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateUsernameApiKey(String tenantName, String tokenId) throws IdentityFault, URISyntaxException {
        return authenticateUsernameApiKey(url, tenantName, tokenId);
    }

    /**
     * Authenticate user with specific url, tenantName and tokenId
     *
     * @param url
     * @param tenantName
     * @param tokenId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse authenticateUsernameApiKey(String url, String tenantName, String tokenId) throws IdentityFault, URISyntaxException {
        return authenticationResourceManager.authenticateUsernameApiKey(client, url, tenantName, tokenId);
    }

    /* ******************************************************************************************************************/
    /*                                                      TOKENS                                                      */
    /* ******************************************************************************************************************/

    /**
     * Validate token for tenantName
     *
     * @param token
     * @param tenantName
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse validateToken(String token, String tenantName) throws IdentityFault, URISyntaxException {
        return validateToken(url, token, token, tenantName);
    }

    /**
     * Validate token for tenantName with admin account
     *
     * @param adminToken
     * @param token
     * @param tenantName
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse validateToken(String adminToken, String token, String tenantName) throws IdentityFault, URISyntaxException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public AuthenticateResponse validateToken(String url, String adminToken, String token, String tenantName) throws IdentityFault, URISyntaxException {
        return tokenResourceManager.validateToken(client, url, adminToken, token, tenantName);
    }

    /**
     * Retrieve endpoints for token
     *
     * @param token
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public EndpointList retrieveEndpointsForToken(String token) throws IdentityFault, URISyntaxException {
        return retrieveEndpointsForToken(url, token, token);
    }

    /**
     * Retrieve endpoints for token with admin account
     *
     * @param adminToken
     * @param token
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public EndpointList retrieveEndpointsForToken(String adminToken, String token) throws IdentityFault, URISyntaxException {
        return retrieveEndpointsForToken(url, adminToken, token);
    }

    /**
     * Retrieve endpoints for token with specific url with admin account
     *
     * @param url
     * @param adminToken
     * @param token
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public EndpointList retrieveEndpointsForToken(String url, String adminToken, String token) throws IdentityFault, URISyntaxException {
        return tokenResourceManager.retrieveEndpointsForToken(client, url, adminToken, token);
    }

    /* ******************************************************************************************************************/
    /*                                                      TENANTS                                                     */
    /* ******************************************************************************************************************/

    /**
     * Retrieve tenants based off of the users tokenId
     *
     * @param tokenId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Tenants retrieveTenants(String tokenId) throws IdentityFault, URISyntaxException {
        return retrieveTenants(url, tokenId);
    }

    /**
     * Retrieve tenants with specific url based off of the users tokenId
     *
     * @param url
     * @param token
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Tenants retrieveTenants(String url, String token) throws IdentityFault, URISyntaxException {
        return tenantResourceManager.retireveTenants(client, url, token);
    }

    /**
     * Retrieve tenant by id
     *
     * @param token
     * @param tenantId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Tenant retrieveTenantById(String token, String tenantId) throws IdentityFault, URISyntaxException {
        return retrieveTenantById(url, token, tenantId);
    }

    /**
     * Retrieve tenant by id  with specific url
     *
     * @param url
     * @param token
     * @param tenantId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Tenant retrieveTenantById(String url, String token, String tenantId) throws IdentityFault, URISyntaxException {
        return tenantResourceManager.retireveTenantById(client, url, token, tenantId);
    }

    /**
     * Retrieve tenant by name
     *
     * @param token
     * @param tenantName
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Tenant retrieveTenantByname(String token, String tenantName) throws IdentityFault, URISyntaxException {
        return retrieveTenantByName(url, token, tenantName);
    }

    /**
     * Retrieve tenant by name  with specific url
     *
     * @param url
     * @param token
     * @param tenantName
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Tenant retrieveTenantByName(String url, String token, String tenantName) throws IdentityFault, URISyntaxException {
        return tenantResourceManager.retireveTenantByName(client, url, token, tenantName);
    }

    /**
     * Retrieve roles by tenantId
     *
     * @param token
     * @param tenantName
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public RoleList retrieveRolesByTenantId(String token, String tenantName, String userId) throws IdentityFault, URISyntaxException {
        return retrieveRolesByTenantId(url, token, tenantName, userId);
    }

    /**
     * Retrieve roles by tenantId and specific url
     *
     * @param url
     * @param token
     * @param tenantName
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public RoleList retrieveRolesByTenantId(String url, String token, String tenantName, String userId) throws IdentityFault, URISyntaxException {
        return tenantResourceManager.retrieveRolesByTenantId(client, url, token, tenantName, userId);
    }

    /* ******************************************************************************************************************/
    /*                                                      USERS                                                       */
    /* ******************************************************************************************************************/

    /**
     * List users by token
     *
     * @param token
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public UserList listUsers(String token) throws IdentityFault, URISyntaxException {
        return listUsers(url, token);
    }

    /**
     * List users by token with specific url
     *
     * @param url
     * @param token
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public UserList listUsers(String url, String token) throws IdentityFault, URISyntaxException {
        return userResourceManager.listUsers(client, url, token);
    }

    /**
     * List user by name
     *
     * @param token
     * @param username
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public User listUserByName(String token, String username) throws IdentityFault, URISyntaxException {
        return listUserByName(url, token, username);
    }

    /**
     * List user by name with specific url
     *
     * @param url
     * @param token
     * @param username
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public User listUserByName(String url, String token, String username) throws IdentityFault, URISyntaxException {
        return userResourceManager.listUserByName(client, url, token, username);
    }

    /**
     * List user by id
     *
     * @param token
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public User listUserById(String token, String userId) throws IdentityFault, URISyntaxException {
        return listUserById(url, token, userId);
    }

    /**
     * List user by id with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public User listUserById(String url, String token, String userId) throws IdentityFault, URISyntaxException {
        return userResourceManager.listUserById(client, url, token, userId);
    }

    /**
     * List user credentials
     *
     * @param token
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Credentials listCredentials(String token, String userId) throws IdentityFault, URISyntaxException {
        return listCredentials(url, token, userId);
    }

    /**
     * List user credentials with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Credentials listCredentials(String url, String token, String userId) throws IdentityFault, URISyntaxException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User addUser(String token, String username, boolean enabled, String email, String region) throws IdentityFault, URISyntaxException, JAXBException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User addUser(String token, String username, String password, boolean enabled, String email, String region) throws IdentityFault, URISyntaxException, JAXBException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User addUser(String url, String token, String username, String password, boolean enabled, String email, String region) throws IdentityFault, URISyntaxException, JAXBException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User updateUser(String token, String userId, String username, boolean enabled, String email, String region) throws IdentityFault, URISyntaxException, JAXBException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User updateUser(String url, String token, String userId, String username, boolean enabled, String email, String region) throws IdentityFault, URISyntaxException, JAXBException {
        return userResourceManager.updateUser(client, url, token, userId, username, enabled, email, region);
    }

    /**
     * Update user password
     *
     * @param token
     * @param userId
     * @param password
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User updateUserPassword(String token, String userId, String password) throws IdentityFault, URISyntaxException, JAXBException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public User updateUserPassword(String url, String token, String userId, String password) throws IdentityFault, URISyntaxException, JAXBException {
        return userResourceManager.updateUserPassword(client, url, token, userId, password);
    }

    /**
     * Update usercredentials
     *
     * @param token
     * @param userId
     * @param apiKey
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public AuthenticateResponse updateUserCredentials(String token, String userId, String apiKey) throws IdentityFault, URISyntaxException, JAXBException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public AuthenticateResponse updateUserCredentials(String url, String token, String userId, String apiKey) throws IdentityFault, URISyntaxException, JAXBException {
        return userResourceManager.updateUserCredentials(client, url, token, userId, apiKey);
    }

    /**
     * Delete user credentials
     *
     * @param token
     * @param userId
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public void deleteUserCredentials(String token, String userId) throws IdentityFault, URISyntaxException, JAXBException {
        deleteUserCredentials(url, token, userId);
    }

    /**
     * Delete user credentials with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public void deleteUserCredentials(String url, String token, String userId) throws IdentityFault, URISyntaxException, JAXBException {
        userResourceManager.deleteUserCredentials(client, url, token, userId);
    }


    /**
     * Delete user
     *
     * @param token
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public void deleteUser(String token, String userId) throws IdentityFault, URISyntaxException, JAXBException {
        deleteUser(url, token, userId);
    }


    /**
     * Delete user with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    public void deleteUser(String url, String token, String userId) throws IdentityFault, URISyntaxException, JAXBException {
        userResourceManager.deleteUser(client, url, token, userId);
    }

    /* ******************************************************************************************************************/
    /*                                                      GROUPS                                                      */
    /* ******************************************************************************************************************/

    /**
     * List available groups
     *
     * @param token
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public GroupList listGroups(String token) throws IdentityFault, URISyntaxException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public GroupList listGroups(String token, String marker, String limit, String name) throws IdentityFault, URISyntaxException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public GroupList listGroups(String url, String token, String marker, String limit, String name) throws IdentityFault, URISyntaxException {
        return groupResourceManager.listGroups(client, url, token, marker, limit, name);
    }

    /**
     * List groups for a user by id
     *
     * @param token
     * @param id
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public GroupList listGroupsForUser(String token, String id) throws IdentityFault, URISyntaxException {
        return listGroupsForUser(token, null, null, id);
    }

    /**
     * List groups for a user by id and return constraints
     *
     * @param token
     * @param marker
     * @param limit
     * @param id
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public GroupList listGroupsForUser(String token, String marker, String limit, String id) throws IdentityFault, URISyntaxException {
        return listGroupsForUser(url, token, marker, limit, id);
    }

    /**
     * List groups for a user by id from a specific url
     *
     * @param url
     * @param token
     * @param marker
     * @param limit
     * @param id
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public GroupList listGroupsForUser(String url, String token, String marker, String limit, String id) throws IdentityFault, URISyntaxException {
        return groupResourceManager.listGroupsForUser(client, url, token, marker, limit, id);
    }

    /**
     * List the users in a group
     *
     * @param token
     * @param groupId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public UserList listUsersForGroup(String token, String groupId) throws IdentityFault, URISyntaxException {
        return listUsersForGroup(token, null, null, groupId);
    }

    /**
     * List the users in a group with parameters
     *
     * @param token
     * @param marker
     * @param limit
     * @param groupId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public UserList listUsersForGroup(String token, String marker, String limit, String groupId) throws IdentityFault, URISyntaxException {
        return listUsersForGroup(url, token, marker, limit, groupId);
    }

    /**
     * List the users in a group with a specific URL
     *
     * @param url
     * @param token
     * @param marker
     * @param limit
     * @param groupId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public UserList listUsersForGroup(String url, String token, String marker, String limit, String groupId) throws IdentityFault, URISyntaxException {
        return groupResourceManager.listUsersForGroup(client, url, token, marker, limit, groupId);
    }

    /**
     * Add group
     *
     * @param token
     * @param name
     * @param description
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Group addGroup(String token, String name, String description) throws IdentityFault, URISyntaxException {
        return addGroup(url, token, name, description);
    }

    /**
     * Add group with specific url
     *
     * @param url
     * @param token
     * @param name
     * @param description
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Group addGroup(String url, String token, String name, String description) throws IdentityFault, URISyntaxException {
        return groupResourceManager.addGroup(client, url, token, name, description);
    }

    /**
     * Update a Group
     *
     * @param token
     * @param groupId
     * @param name
     * @param description
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Group updateGroup(String token, String groupId, String name, String description) throws IdentityFault, URISyntaxException {
        return updateGroup(url, token, groupId, name, description);
    }

    /**
     * Update a Group with a specific URL
     *
     * @param url
     * @param token
     * @param groupId
     * @param name
     * @param description
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Group updateGroup(String url, String token, String groupId, String name, String description) throws IdentityFault, URISyntaxException {
        return groupResourceManager.updateGroup(client, url, token, groupId, name, description);
    }

    /**
     * Retrieve group by groupId
     *
     * @param token
     * @param groupId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Group retrieveGroup(String token, String groupId) throws IdentityFault, URISyntaxException {
        return retrieveGroup(url, token, groupId);
    }

    /**
     * Retrieve group by groupId and specific url
     *
     * @param url
     * @param token
     * @param groupId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Group retrieveGroup(String url, String token, String groupId) throws IdentityFault, URISyntaxException {
        return groupResourceManager.retrieveGroup(client, url, token, groupId);
    }

    /**
     * Delete group by groupId
     *
     * @param token
     * @param groupId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean deleteGroup(String token, String groupId) throws IdentityFault, URISyntaxException {
        return deleteGroup(url, token, groupId);
    }

    /**
     * Delete group by groupId with specific url
     *
     * @param url
     * @param token
     * @param groupId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean deleteGroup(String url, String token, String groupId) throws IdentityFault, URISyntaxException {
        return groupResourceManager.deleteGroup(client, url, token, groupId);
    }

    /**
     * Add user to group
     *
     * @param token
     * @param userId
     * @param groupId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean addUserToGroup(String token, String userId, String groupId) throws IdentityFault, URISyntaxException {
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
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean addUserToGroup(String url, String token, String userId, String groupId) throws IdentityFault, URISyntaxException {
        return groupResourceManager.addUserToGroup(client, url, token, userId, groupId);
    }

    /**
     * Remove a user from a group
     *
     * @param token
     * @param groupId
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean removeUserFromGroup(String token, String groupId, String userId) throws IdentityFault, URISyntaxException {
        return removeUserFromGroup(url, token, groupId, userId);
    }

    /**
     * Remove a user from a group using a specific url
     *
     * @param url
     * @param token
     * @param groupId
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean removeUserFromGroup(String url, String token, String groupId, String userId) throws IdentityFault, URISyntaxException {
        return groupResourceManager.removeUserFromGroup(client, url, token, groupId, userId);
    }

    /* ******************************************************************************************************************/
    /*                                                         ROLES                                                    */
    /* ******************************************************************************************************************/

    /**
     * List available roles
     *
     * @param token
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public RoleList listRoles(String token) throws IdentityFault, URISyntaxException {
        return listRoles(url, token, null, null, null);
    }

    /**
     * List available roles by serviceId
     *
     * @param token
     * @param serviceId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public RoleList listRoles(String token, String serviceId) throws IdentityFault, URISyntaxException {
        return listRoles(url, token, serviceId, null, null);
    }

    /**
     * List available roles by serviceId and specific url
     *
     * @param url
     * @param token
     * @param serviceId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public RoleList listRoles(String url, String token, String serviceId) throws IdentityFault, URISyntaxException {
        return listRoles(url, token, serviceId, null, null);
    }

    /**
     * List available roles with serviceId, marker and limit
     *
     * @param token
     * @param serviceId
     * @param marker
     * @param limit
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public RoleList listRoles(String token, String serviceId, String marker, String limit) throws IdentityFault, URISyntaxException {
        return listRoles(url, token, serviceId, marker, limit);
    }

    /**
     * List available roles by serviceId, marker, limit and specific url
     *
     * @param url
     * @param token
     * @param serviceId
     * @param marker
     * @param limit
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public RoleList listRoles(String url, String token, String serviceId, String marker, String limit) throws IdentityFault, URISyntaxException {
        return rolesResourceManager.listroles(client, url, token, serviceId, marker, limit);
    }

    /**
     * Retrieve role by roleId
     *
     * @param token
     * @param roleId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Role getRole(String token, String roleId) throws IdentityFault, URISyntaxException {
        return getRole(url, token, roleId);
    }

    /**
     * Retrieve role with specific url
     *
     * @param url
     * @param token
     * @param roleId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Role getRole(String url, String token, String roleId) throws IdentityFault, URISyntaxException {
        return rolesResourceManager.getRole(client, url, token, roleId);
    }

    /**
     * List global roles for userId
     *
     * @param token
     * @param userId
     * @return
     * @throws URISyntaxException
     * @throws IdentityFault
     */
    public RoleList listUserGlobalRoles(String token, String userId) throws URISyntaxException, IdentityFault {
        return listUserGlobalRoles(url, token, userId);
    }

    /**
     * List global roles for userId with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws URISyntaxException
     * @throws IdentityFault
     */
    public RoleList listUserGlobalRoles(String url, String token, String userId) throws URISyntaxException, IdentityFault {
        return rolesResourceManager.listUserGlobalRoles(client, url, token, userId);
    }

    /**
     * Delete role from user by userId and roleId
     *
     * @param token
     * @param userId
     * @param roleId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean deleteGlobalRoleFromUser(String token, String userId, String roleId) throws IdentityFault, URISyntaxException {
        return deleteGlobalRoleFromUser(url, token, userId, roleId);
    }

    /**
     * Delete role from user by userId and roleId with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @param roleId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean deleteGlobalRoleFromUser(String url, String token, String userId, String roleId) throws IdentityFault, URISyntaxException {
        return rolesResourceManager.deleteGlobalRoleFromUser(client, url, token, userId, roleId);
    }

    /**
     * Add role
     *
     * @param token
     * @param name
     * @param description
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Role addRole(String token, String name, String description) throws IdentityFault, URISyntaxException {
        return addRole(url, token, name, description);
    }

    /**
     * Add role with specific url
     *
     * @param url
     * @param token
     * @param name
     * @param description
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Role addRole(String url, String token, String name, String description) throws IdentityFault, URISyntaxException {
        return rolesResourceManager.addRole(client, url, token, name, description);
    }

    /**
     * Add global role to user
     *
     * @param token
     * @param userId
     * @param roleId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean addGlobalRoleToUser(String token, String userId, String roleId) throws IdentityFault, URISyntaxException {
        return addGlobalRoleToUser(url, token, userId, roleId);
    }

    /**
     * Add global roles to user with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @param roleId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean addGlobalRoleToUser(String url, String token, String userId, String roleId) throws IdentityFault, URISyntaxException {
        return rolesResourceManager.addGlobalRoleToUser(client, url, token, userId, roleId);
    }

    /* ******************************************************************************************************************/
    /*                                                      SECRET QA                                                   */
    /* ******************************************************************************************************************/

    /**
     * List secret question and answer
     *
     * @param token
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public SecretQA listSecretQA(String token, String userId) throws IdentityFault, URISyntaxException {
        return listSecretQA(url, token, userId);
    }

    /**
     * List secret question and answer with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public SecretQA listSecretQA(String url, String token, String userId) throws IdentityFault, URISyntaxException {
        return secretQAResourceManager.listSecretQA(client, url, token, userId);
    }

    /**
     * Update secret question and answer for userId
     *
     * @param token
     * @param userId
     * @param question
     * @param answer
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public SecretQA updateSecretQA(String token, String userId, String question, String answer) throws IdentityFault, URISyntaxException {
        return updateSecretQA(url, token, userId, question, answer);
    }

    /**
     * Update secret question and answer for userId with specific url
     *
     * @param url
     * @param token
     * @param userId
     * @param question
     * @param answer
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public SecretQA updateSecretQA(String url, String token, String userId, String question, String answer) throws IdentityFault, URISyntaxException {
        return secretQAResourceManager.updateSecretQA(client, url, token, userId, question, answer);
    }

    /* ******************************************************************************************************************/
    /*                                                IMPERSONATION                                                     */
    /* ******************************************************************************************************************/


    /**
     * Impersonate user by userName
     *
     * @param token
     * @param userName
     * @param expireInSeconds
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Access impersonateUser(String token, String userName, int expireInSeconds) throws IdentityFault, URISyntaxException, JAXBException {
        return impersonateUser(url, token, userName, expireInSeconds);
    }

    /**
     * Impersonate user by userName with specif url
     *
     * @param url
     * @param token
     * @param userName
     * @param expireInSeconds
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Access impersonateUser(String url, String token, String userName, int expireInSeconds) throws IdentityFault, URISyntaxException, JAXBException {
        return impersonationResourceManager.impersonateUser(client, url, token, userName, expireInSeconds);
    }

    /* ******************************************************************************************************************/
    /*                                                RATE LIMITS                                                       */
    /* ******************************************************************************************************************/
    //TODO: dont think this is ready. Docs have misleading information ...

    /* ******************************************************************************************************************/
    /*                                                DOMAINS                                                           */
    /* ******************************************************************************************************************/

    /**
     * Create domain
     *
     * @param token
     * @param domainId
     * @param domainName
     * @param enabled
     * @param description
     * @return ClientResponse: retrieve headers from .getHeaders()
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public ClientResponse createDomain(String token, String domainId, String domainName, boolean enabled, String description) throws IdentityFault, URISyntaxException {
        return createDomain(url, token, domainId, domainName, enabled, description);
    }

    /**
     * Create domain with specific url
     *
     * @param url
     * @param token
     * @param domainId
     * @param domainName
     * @param enabled
     * @param description
     * @return ClientResponse retrieve headers from .getHeaders()
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public ClientResponse createDomain(String url, String token, String domainId, String domainName, boolean enabled, String description) throws IdentityFault, URISyntaxException {
        return domainResourceManager.createDomain(client, url, token, domainId, domainName, enabled, description);
    }

    /**
     * Retrieve domain by domainId
     *
     * @param token
     * @param domainId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Domain getDomain(String token, String domainId) throws IdentityFault, URISyntaxException {
        return getDomain(url, token, domainId);
    }

    /**
     * Retrieve domain by domainId with specific url
     *
     * @param url
     * @param token
     * @param domainId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Domain getDomain(String url, String token, String domainId) throws IdentityFault, URISyntaxException {
        return domainResourceManager.getDomain(client, url, token, domainId);
    }

    /**
     * Update domain by domainId
     *
     * @param token
     * @param domainId
     * @param domainName
     * @param enabled:    must be TRUE or FALSE
     * @param description
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean updateDomain(String token, String domainId, String domainName, String enabled, String description) throws IdentityFault, URISyntaxException {
        return updateDomain(url, token, domainId, domainName, enabled, description);
    }

    /**
     * Update domain by domainId with specific url
     *
     * @param url
     * @param token
     * @param domainId
     * @param domainName
     * @param enabled:    must be TRUE or FALSE
     * @param description
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean updateDomain(String url, String token, String domainId, String domainName, String enabled, String description) throws IdentityFault, URISyntaxException {
        return domainResourceManager.updateDomain(client, url, token, domainId, domainName, enabled, description);
    }

    /**
     * Delete domain by domainId
     *
     * @param token
     * @param domainId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean deleteDomain(String token, String domainId) throws IdentityFault, URISyntaxException {
        return deleteDomain(url, token, domainId);
    }

    /**
     * Delete domain by domainId with specific url
     *
     * @param url
     * @param token
     * @param domainId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean deleteDomain(String url, String token, String domainId) throws IdentityFault, URISyntaxException {
        return domainResourceManager.deleteDomain(client, url, token, domainId);
    }

    /**
     * List endpoints for domain by domainId
     *
     * @param token
     * @param domainId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public EndpointList getEndpointsForDomain(String token, String domainId) throws IdentityFault, URISyntaxException {
        return getEndpointsForDomain(url, token, domainId);
    }

    /**
     * List endpoints for domain by domainId with specific url
     *
     * @param url
     * @param token
     * @param domainId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public EndpointList getEndpointsForDomain(String url, String token, String domainId) throws IdentityFault, URISyntaxException {
        return domainResourceManager.getEndpointsForDomain(client, url, token, domainId);
    }

    /**
     * List users associated with domainId
     *
     * @param token
     * @param domainId
     * @param enabled: optional
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public UserList getUsersForDomain(String token, String domainId, String enabled) throws IdentityFault, URISyntaxException {
        return getUsersForDomain(url, token, domainId, enabled);
    }

    /**
     * List users associated with domainId with specific url
     *
     * @param url
     * @param token
     * @param domainId
     * @param enabled
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public UserList getUsersForDomain(String url, String token, String domainId, String enabled) throws IdentityFault, URISyntaxException {
        return domainResourceManager.getUsersFromDomain(client, url, token, domainId, enabled);
    }

    /**
     * Add user to domain by domainId and userId
     *
     * @param token
     * @param domainId
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean addUserToDomain(String token, String domainId, String userId) throws IdentityFault, URISyntaxException {
        return addUserToDomain(url, token, domainId, userId);
    }

    /**
     * Add user to domain by domainId and userId with specific url
     *
     * @param url
     * @param token
     * @param domainId
     * @param userId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean addUserToDomain(String url, String token, String domainId, String userId) throws IdentityFault, URISyntaxException {
        return domainResourceManager.addUserToDomain(client, url, token, domainId, userId);
    }

    /**
     * List tenants associated with domainId
     *
     * @param token
     * @param domainId
     * @param enabled: optional
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Tenants getTenantsFromDomain(String token, String domainId, String enabled) throws IdentityFault, URISyntaxException {
        return getTenantsFromDomain(url, token, domainId, enabled);
    }

    /**
     * List tenants associated with domainId with specific url
     *
     * @param url
     * @param token
     * @param domainId
     * @param enabled
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public Tenants getTenantsFromDomain(String url, String token, String domainId, String enabled) throws IdentityFault, URISyntaxException {
        return domainResourceManager.getTenantsFromDomain(client, url, token, domainId, enabled);
    }

    /**
     * Add tenant to domain by domainId and tenantId
     *
     * @param token
     * @param domainId
     * @param tenantId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean addTenantToDomain(String token, String domainId, String tenantId) throws IdentityFault, URISyntaxException {
        return addTenantToDomain(url, token, domainId, tenantId);
    }

    /**
     * Add tenant to domain by domainId and tenantId with specific url
     *
     * @param url
     * @param token
     * @param domainId
     * @param tenantId
     * @return
     * @throws IdentityFault
     * @throws URISyntaxException
     */
    public boolean addTenantToDomain(String url, String token, String domainId, String tenantId) throws IdentityFault, URISyntaxException {
        return domainResourceManager.addTenantToDomain(client, url, token, domainId, tenantId);
    }
}


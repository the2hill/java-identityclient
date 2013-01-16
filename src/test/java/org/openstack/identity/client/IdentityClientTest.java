package org.openstack.identity.client;

import org.junit.Assert;
import org.junit.Test;
import org.openstack.identity.client.client.IdentityClient;
import org.openstack.identity.client.common.util.IdentityUtil;
import org.openstack.identity.client.endpoints.EndpointList;
import org.openstack.identity.client.entity.TestUser;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.group.Group;
import org.openstack.identity.client.group.GroupList;
import org.openstack.identity.client.manager.entity.Credentials;
import org.openstack.identity.client.roles.Role;
import org.openstack.identity.client.roles.RoleList;
import org.openstack.identity.client.secretqa.SecretQA;
import org.openstack.identity.client.tenant.Tenant;
import org.openstack.identity.client.tenant.Tenants;
import org.openstack.identity.client.token.AuthenticateResponse;
import org.openstack.identity.client.token.Token;
import org.openstack.identity.client.user.User;
import org.openstack.identity.client.user.UserList;

import java.net.URISyntaxException;

import static junit.framework.Assert.*;

public class IdentityClientTest {
    private static TestUser testUser;
    //Order matters(Need to get a token based off of other credentials, those that need token for authentication may need to run another first)...

    @Test
    public void validateCloudUsernamePassword() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        try {
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
            assertNotNull(response);
            assertEquals(IdentityUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
            assertNotNull(retrieveTenants(testUser.getTokenId()));
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void validateCloudUsernameTenantIdAndPassword() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        try {
            AuthenticateResponse response = client.authenticateTenantIdUsernamePassword(IdentityUtil.getProperty("tenant_id"), IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
            assertNotNull(response);
            assertEquals(IdentityUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void validateTenantNameTokenId() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        try {
            assertNotNull(testUser.getTokenId());
            AuthenticateResponse response = client.authenticateTenantNameTokenId(IdentityUtil.getProperty("tenant_id"), testUser.getTokenId());
            assertNotNull(response);
            assertEquals(IdentityUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void validateTenantIdTokenId() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        try {
            assertNotNull(testUser.getTokenId());
            AuthenticateResponse response = client.authenticateTenantIdTokenId(String.valueOf(IdentityUtil.getIntProperty("tenant_id")), testUser.getTokenId());
            assertNotNull(response);
            assertEquals(IdentityUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void validateCloudUsernameApiKey() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        try {
            AuthenticateResponse response = client.authenticateUsernameApiKey(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("key"));
            assertNotNull(response);
            assertEquals(IdentityUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            System.out.println("TenantName: " + response.getToken().getTenant().getName());

            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void validateCloudTenantIdUsernameApiKey() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        try {
            AuthenticateResponse response = client.authenticateTenantIdUsernameApiKey(String.valueOf(IdentityUtil.getIntProperty("tenant_id")), IdentityUtil.getProperty("username"), IdentityUtil.getProperty("key"));
            assertNotNull(response);
            assertEquals(IdentityUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            System.out.println("TenantName: " + response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getTenants() throws Exception {
        try {
            assertNotNull(testUser.getTokenId());
            Tenants response = retrieveTenants(testUser.getTokenId());
            assertNotNull(response);
            assertEquals(IdentityUtil.getProperty("tenant_id"), response.getTenant().get(0).getName());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getTenantsWithAdminAccount() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        try {
            Tenants tenants = client.retrieveTenants(response.getToken().getId());
            assertNotNull(tenants);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getTenantById() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Tenant tenant = client.retireveTenantById(response.getToken().getId(), IdentityUtil.getProperty("tenant_id"));
            assertNotNull(tenant);
            assertEquals(IdentityUtil.getProperty("tenant_id"), tenant.getId());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getTenantByName() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Tenant tenant = client.retireveTenantByname(response.getToken().getId(), IdentityUtil.getProperty("tenant_name"));
            assertNotNull(tenant);
            assertEquals(IdentityUtil.getProperty("tenant_name"), tenant.getName());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getRolesByTenantId() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User user = client.listUserByName(response.getToken().getId(), IdentityUtil.getProperty("username"));
            RoleList roles = client.retireveRolesByTenantId(response.getToken().getId(), IdentityUtil.getProperty("tenant_id"), user.getId());
            assertNotNull(roles);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

//    @Test
//    public void validateToken() throws Exception {
//        try {
//            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
//            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
//            User user = client.listUserByName(response.getToken().getId(), IdentityUtil.getProperty("username"));
//            Credentials userCreds = client.listCredentials(response.getToken().getId(), user.getId());
//            AuthenticateResponse b = client.authenticateUsernameApiKey(IdentityUtil.getProperty("username"), userCreds.getApiKeyCredentials().getApiKey());
//            AuthenticateResponse token = client.validateToken(response.getToken().getId(), b.getToken().getId(), b.getUser().getName());
//            assertNotNull(token);
//            Token tk = response.getToken();
//            System.out.println(tk.getId());
//        } catch (IdentityFault ex) {
//            System.out.println("FAILURE gathering authenticated user info.");
//            System.out.print(ex.getMessage());
//            Assert.fail(ex.getMessage());
//        }
//    }

    @Test
    public void validateTokenForAnotherUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            AuthenticateResponse responseU = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
            AuthenticateResponse roles = client.validateToken(response.getToken().getId(), responseU.getToken().getId(), responseU.getToken().getTenant().getName());
            assertNotNull(roles);
            Token token = response.getToken();
            System.out.println(token.getId());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void retrieveEndpointsForToken() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse responseU = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            EndpointList endpointList = client.retrieveEndpointsForToken(responseU.getToken().getId());
            assertNotNull(endpointList);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void retrieveEndpointsForTokenWithAdminAccount() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            AuthenticateResponse responseU = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
            EndpointList endpointList = client.retrieveEndpointsForToken(response.getToken().getId(), responseU.getToken().getId());
            assertNotNull(endpointList);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User user = client.addUser(response.getToken().getId(), "bobBuilder", "password", true, "the@mail.com", "DFW");
            assertNotNull(user);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getDetails());
        }
    }

    @Test
    public void getUserByName() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User updatedUserB = client.listUserByName(response.getToken().getId(), "bobBuilder");
            assertNotNull(updatedUserB);
            assertEquals("bobBuilder", updatedUserB.getUsername());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getUserById() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User updatedUserB = client.listUserById(response.getToken().getId(), IdentityUtil.getProperty("user_id"));
            assertNotNull(updatedUserB);
            assertEquals(IdentityUtil.getProperty("username"), updatedUserB.getUsername());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getUsers() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            UserList updatedUserB = client.listUsers(response.getToken().getId());
            assertNotNull(updatedUserB);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void updateUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User userb = client.listUserByName(response.getToken().getId(), "bobBuilder");
            User user = client.updateUser(response.getToken().getId(), userb.getId(), "bobBuilder", true, "cheese@mail.com", "DFW");
            User updatedUserB = client.listUserByName(response.getToken().getId(), "bobBuilder");
            assertNotNull(user);
            assertEquals("cheese@mail.com", updatedUserB.getEmail());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void updateUserWithEmailOnly() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User userb = client.listUserByName(response.getToken().getId(), "bobBuilder");
            User user = client.updateUser(response.getToken().getId(), userb.getId(), null, true, "bob@mail.com", null);
            User updatedUserB = client.listUserByName(response.getToken().getId(), "bobBuilder");
            assertNotNull(user);
            assertEquals("bob@mail.com", updatedUserB.getEmail());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void updateUserPassword() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User userb = client.listUserByName(response.getToken().getId(), "bobBuilder");
            User user = client.updateUserPassword(response.getToken().getId(), userb.getId(), "bobBuilder1");
            assertNotNull(user);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getDetails());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void updateUserCredentials() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User userb = client.listUserByName(response.getToken().getId(), "bobBuilder");
            AuthenticateResponse user = client.updateUserCredentials(response.getToken().getId(), userb.getId(), "asdfa-sdfsdf-sdfsd-sdfsdf-sdfsdf");
            Credentials after = client.listCredentials(response.getToken().getId(), userb.getId());
            assertNotNull(user);
            assertEquals("asdfa-sdfsdf-sdfsd-sdfsdf-sdfsdf", after.getApiKeyCredentials().getApiKey());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void deleteUserCredentials() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User userb = client.listUserByName(response.getToken().getId(), "bobBuilder");
            client.deleteUserCredentials(response.getToken().getId(), userb.getId());
            Credentials after = client.listCredentials(response.getToken().getId(), userb.getId());
            assertNull(after);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getDetails());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void deleteUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User bob = client.listUserByName(response.getToken().getId(), "bobBuilder");
            User user = client.deleteUser(response.getToken().getId(), bob.getId());
            assertNotNull(user);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getDetails());
        }
    }

    @Test
    public void listCredentials() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User bob = client.listUserByName(response.getToken().getId(), IdentityUtil.getProperty("username"));
            Credentials credentials = client.listCredentials(response.getToken().getId(), bob.getId());
            assertNotNull(credentials);
            assertEquals(bob.getUsername(), credentials.getApiKeyCredentials().getUsername());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void listGroups() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            GroupList groups = client.listGroups(response.getToken().getId());
            assertNotNull(groups);
            System.out.println(groups.toString());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addGroup() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Group group = client.addGroup(response.getToken().getId(), "ksctestgroup", "this is a test for jksc");
            assertNotNull(group);
            assertEquals("ksctestgroup", group.getName());
            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), group.getId());
            assertTrue(isGdeleted);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getGroupByName() throws Exception {
        //bug in auth fails to get group by name
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            GroupList groups = client.listGroups(response.getToken().getId(), null, null, "ksctestgroup");
            assertNotNull(groups);
            assertEquals("ksctestgroup", groups.getGroup().get(0).getName());
            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), groups.getGroup().get(0).getId());
            assertTrue(isGdeleted);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getGroupById() throws Exception {
        //bug in auth fails to get group by name
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            GroupList groups = client.listGroups(response.getToken().getId(), null, null, "ksctestgroup");
            Group g = client.retrieveGroup(response.getToken().getId(), groups.getGroup().get(0).getId());
            assertNotNull(g);
            assertEquals("ksctestgroup", g.getName());
            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), g.getId());
            assertTrue(isGdeleted);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void deleteGroup() throws Exception {
        //Fails.. bug in auth retrieve group by name
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            GroupList groups = client.listGroups("my-simple-demo-token", null, null, "ksctestgroup");
            Group group = client.retrieveGroup(response.getToken().getId(), groups.getGroup().get(0).getId());
            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), group.getId());
            assertTrue(isGdeleted);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addUserToGroup() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        GroupList groups = client.listGroups(response.getToken().getId(), null, null, "ksctestgroup");
        assertTrue(client.addUserToGroup(response.getToken().getId(), response.getUser().getId(), groups.getGroup().get(0).getId()));
    }

    @Test
    public void updateGroup() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        GroupList groups = client.listGroups(response.getToken().getId(), null, null, "ksctestgroup");
        String name = "newTestGroupName";
        Group group = client.updateGroup(response.getToken().getId(), groups.getGroup().get(0).getId(), null, name);
        assertTrue(group.getName().equals(name));
    }

    // @TODO: Rewrite this without having to add the user to a group initially
    @Test
    public void listGroupsForUser() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        GroupList groups = client.listGroups(response.getToken().getId(), null, null, "ksctestgroup");
        client.addUserToGroup(response.getToken().getId(), response.getUser().getId(), groups.getGroup().get(0).getId());
        GroupList userGroups = client.listGroupsForUser(response.getToken().getId(), response.getUser().getId());
        assertFalse(userGroups.getGroup().isEmpty());
    }

    @Test
    public void removeUserFromGroup() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        GroupList groups = client.listGroups(response.getToken().getId(), null, null, "ksctestgroup");
        assertTrue(client.addUserToGroup(response.getToken().getId(), response.getUser().getId(), groups.getGroup().get(0).getId()));
        assertTrue(client.removeUserFromGroup(response.getToken().getId(), groups.getGroup().get(0).getId(), response.getUser().getId()));
    }

    @Test
    public void listUsersFromGroup() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        GroupList groups = client.listGroups(response.getToken().getId(), null, null, "ksctestgroup");
        assertTrue(client.addUserToGroup(response.getToken().getId(), response.getUser().getId(), groups.getGroup().get(0).getId()));
        UserList users = client.listUsersForGroup(response.getToken().getId(), groups.getGroup().get(0).getId());
        assertFalse(users.getUser().isEmpty());
    }


    @Test
    public void listRoles() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            RoleList roles = client.listRoles(response.getToken().getId());
            assertNotNull(roles);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    //TODO:Update roles tests with better cases......
    @Test
    public void getRole() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Role role = client.getRole(response.getToken().getId(), "1");
            assertNotNull(role);
            assertEquals("identity:admin", role.getName());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void listRolesForUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            RoleList roles = client.listUserGlobalRoles(response.getToken().getId(), IdentityUtil.getProperty("user_id"));
            assertNotNull(roles);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void deleteRoleFromUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            boolean deleted = client.deleteGlobalRoleFromUser(response.getToken().getId(), IdentityUtil.getProperty("tenant_id"), "234455");
            assertTrue(deleted);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addRole() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Role role = client.addRole(response.getToken().getId(), "itcNAME", "itcDescription");
            assertNotNull(role);
            assertEquals("itcNAME", role.getName());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addRoleToUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            boolean role = client.addGlobalRoleToUser(response.getToken().getId(), IdentityUtil.getProperty("user_id"), "1");
            assertTrue(role);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void listSecretQAForUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            SecretQA secret = client.listSecretQA(response.getToken().getId(), response.getUser().getId());
            assertNotNull(secret.getQuestion());
            assertNotNull(secret.getAnswer());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void updateSecretQAForUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            SecretQA secret = client.listSecretQA(response.getToken().getId(), response.getUser().getId());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    private Tenants retrieveTenants(String token) throws IdentityFault, URISyntaxException {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        return client.retrieveTenants(testUser.getTokenId());
    }
}


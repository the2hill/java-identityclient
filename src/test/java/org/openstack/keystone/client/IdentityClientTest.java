package org.openstack.keystone.client;

import org.junit.Assert;
import org.junit.Test;
import org.openstack.keystone.client.client.IdentityClient;
import org.openstack.keystone.client.common.util.KeystoneUtil;
import org.openstack.keystone.client.endpoints.EndpointList;
import org.openstack.keystone.client.entity.TestUser;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.group.Group;
import org.openstack.keystone.client.group.GroupList;
import org.openstack.keystone.client.manager.entity.Credentials;
import org.openstack.keystone.client.roles.RoleList;
import org.openstack.keystone.client.tenant.Tenant;
import org.openstack.keystone.client.tenant.Tenants;
import org.openstack.keystone.client.token.AuthenticateResponse;
import org.openstack.keystone.client.token.Token;
import org.openstack.keystone.client.user.User;
import org.openstack.keystone.client.user.UserList;

import java.net.URISyntaxException;

import static junit.framework.Assert.*;

public class IdentityClientTest {
    private static TestUser testUser;
    //Order matters(Need to get a token based off of other credentials, those that need token for authentication may need to run another first)...

    @Test
    public void validateCloudUsernamePassword() throws Exception {
        IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
        try {
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("username"), KeystoneUtil.getProperty("password"));
            assertNotNull(response);
            assertEquals(KeystoneUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
            assertNotNull(retrieveTenants(testUser.getTokenId()));
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void validateCloudUsernameTenantIdAndPassword() throws Exception {
        IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
        try {
            AuthenticateResponse response = client.authenticateTenantIdUsernamePassword(KeystoneUtil.getProperty("tenant_id"), KeystoneUtil.getProperty("username"), KeystoneUtil.getProperty("password"));
            assertNotNull(response);
            assertEquals(KeystoneUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void validateTenantNameTokenId() throws Exception {
        IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
        try {
            assertNotNull(testUser.getTokenId());
            AuthenticateResponse response = client.authenticateTenantNameTokenId(KeystoneUtil.getProperty("tenant_id"), testUser.getTokenId());
            assertNotNull(response);
            assertEquals(KeystoneUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void validateTenantIdTokenId() throws Exception {
        IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
        try {
            assertNotNull(testUser.getTokenId());
            AuthenticateResponse response = client.authenticateTenantIdTokenId(String.valueOf(KeystoneUtil.getIntProperty("tenant_id")), testUser.getTokenId());
            assertNotNull(response);
            assertEquals(KeystoneUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void validateCloudUsernameApiKey() throws Exception {
        IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
        try {
            AuthenticateResponse response = client.authenticateUsernameApiKey(KeystoneUtil.getProperty("username"), KeystoneUtil.getProperty("key"));
            assertNotNull(response);
            assertEquals(KeystoneUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            System.out.println("TenantName: " + response.getToken().getTenant().getName());

            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void validateCloudTenantIdUsernameApiKey() throws Exception {
        IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
        try {
            AuthenticateResponse response = client.authenticateTenantIdUsernameApiKey(String.valueOf(KeystoneUtil.getIntProperty("tenant_id")), KeystoneUtil.getProperty("username"), KeystoneUtil.getProperty("key"));
            assertNotNull(response);
            assertEquals(KeystoneUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            System.out.println("TenantName: " + response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
        } catch (KeystoneFault ex) {
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
            assertEquals(KeystoneUtil.getProperty("tenant_id"), response.getTenant().get(0).getName());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getTenantsWithAdminAccount() throws Exception {
        IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
        try {
            Tenants tenants = client.retrieveTenants(response.getToken().getId());
            assertNotNull(tenants);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getTenantById() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            Tenant tenant = client.retireveTenantById(response.getToken().getId(), KeystoneUtil.getProperty("tenant_id"));
            assertNotNull(tenant);
            assertEquals(KeystoneUtil.getProperty("tenant_id"), tenant.getId());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getTenantByName() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            Tenant tenant = client.retireveTenantByname(response.getToken().getId(), KeystoneUtil.getProperty("tenant_name"));
            assertNotNull(tenant);
            assertEquals(KeystoneUtil.getProperty("tenant_name"), tenant.getName());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getRolesByTenantId() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User user = client.listUserByName(response.getToken().getId(), KeystoneUtil.getProperty("username"));
            RoleList roles = client.retireveRolesByTenantId(response.getToken().getId(), KeystoneUtil.getProperty("tenant_id"), user.getId());
            assertNotNull(roles);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

//    @Test
//    public void validateToken() throws Exception {
//        try {
//            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
//            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
//            User user = client.listUserByName(response.getToken().getId(), KeystoneUtil.getProperty("username"));
//            Credentials userCreds = client.listCredentials(response.getToken().getId(), user.getId());
//            AuthenticateResponse b = client.authenticateUsernameApiKey(KeystoneUtil.getProperty("username"), userCreds.getApiKeyCredentials().getApiKey());
//            AuthenticateResponse token = client.validateToken(response.getToken().getId(), b.getToken().getId(), b.getUser().getName());
//            assertNotNull(token);
//            Token tk = response.getToken();
//            System.out.println(tk.getId());
//        } catch (KeystoneFault ex) {
//            System.out.println("FAILURE gathering authenticated user info.");
//            System.out.print(ex.getMessage());
//            Assert.fail(ex.getMessage());
//        }
//    }

    @Test
    public void validateTokenForAnotherUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            AuthenticateResponse responseU = client.authenticateUsernamePassword(KeystoneUtil.getProperty("username"), KeystoneUtil.getProperty("password"));
            AuthenticateResponse roles = client.validateToken(response.getToken().getId(), responseU.getToken().getId(), responseU.getToken().getTenant().getName());
            assertNotNull(roles);
            Token token = response.getToken();
            System.out.println(token.getId());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void retrieveEndpointsForToken() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse responseU = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            EndpointList endpointList = client.retrieveEndpointsForToken(responseU.getToken().getId());
            assertNotNull(endpointList);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void retrieveEndpointsForTokenWithAdminAccount() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            AuthenticateResponse responseU = client.authenticateUsernamePassword(KeystoneUtil.getProperty("username"), KeystoneUtil.getProperty("password"));
            EndpointList endpointList = client.retrieveEndpointsForToken(response.getToken().getId(), responseU.getToken().getId());
            assertNotNull(endpointList);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User user = client.addUser(response.getToken().getId(), "bobBuilder", "password", true, "the@mail.com", "DFW");
            assertNotNull(user);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getDetails());
        }
    }

    @Test
    public void getUserByName() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User updatedUserB = client.listUserByName(response.getToken().getId(), "bobBuilder");
            assertNotNull(updatedUserB);
            assertEquals("bobBuilder", updatedUserB.getUsername());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getUserById() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User updatedUserB = client.listUserById(response.getToken().getId(), KeystoneUtil.getProperty("user_id"));
            assertNotNull(updatedUserB);
            assertEquals(KeystoneUtil.getProperty("username"), updatedUserB.getUsername());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getUsers() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            UserList updatedUserB = client.listUsers(response.getToken().getId());
            assertNotNull(updatedUserB);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void updateUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User userb = client.listUserByName(response.getToken().getId(), "bobBuilder");
            User user = client.updateUser(response.getToken().getId(), userb.getId(), "bobBuilder", true, "cheese@mail.com", "DFW");
            User updatedUserB = client.listUserByName(response.getToken().getId(), "bobBuilder");
            assertNotNull(user);
            assertEquals("cheese@mail.com", updatedUserB.getEmail());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void updateUserWithEmailOnly() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User userb = client.listUserByName(response.getToken().getId(), "bobBuilder");
            User user = client.updateUser(response.getToken().getId(), userb.getId(), null, true, "bob@mail.com", null);
            User updatedUserB = client.listUserByName(response.getToken().getId(), "bobBuilder");
            assertNotNull(user);
            assertEquals("bob@mail.com", updatedUserB.getEmail());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void updateUserPassword() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User userb = client.listUserByName(response.getToken().getId(), "bobBuilder");
            User user = client.updateUserPassword(response.getToken().getId(), userb.getId(), "bobBuilder1");
            assertNotNull(user);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getDetails());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void updateUserCredentials() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User userb = client.listUserByName(response.getToken().getId(), "bobBuilder");
            AuthenticateResponse user = client.updateUserCredentials(response.getToken().getId(), userb.getId(), "asdfa-sdfsdf-sdfsd-sdfsdf-sdfsdf");
            Credentials after = client.listCredentials(response.getToken().getId(), userb.getId());
            assertNotNull(user);
            assertEquals("asdfa-sdfsdf-sdfsd-sdfsdf-sdfsdf", after.getApiKeyCredentials().getApiKey());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void deleteUserCredentials() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User userb = client.listUserByName(response.getToken().getId(), "bobBuilder");
            client.deleteUserCredentials(response.getToken().getId(), userb.getId());
            Credentials after = client.listCredentials(response.getToken().getId(), userb.getId());
            assertNull(after);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getDetails());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void deleteUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User bob = client.listUserByName(response.getToken().getId(), "bobBuilder");
            User user = client.deleteUser(response.getToken().getId(), bob.getId());
            assertNotNull(user);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getDetails());
        }
    }

    @Test
    public void listCredentials() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            User bob = client.listUserByName(response.getToken().getId(), KeystoneUtil.getProperty("username"));
            Credentials credentials = client.listCredentials(response.getToken().getId(), bob.getId());
            assertNotNull(credentials);
            assertEquals(bob.getUsername(), credentials.getApiKeyCredentials().getUsername());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void listGroups() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            GroupList groups = client.listGroups(response.getToken().getId());
            assertNotNull(groups);
            System.out.println(groups.toString());
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addGroup() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            Group group = client.addGroup(response.getToken().getId(), "ksctestgroup", "this is a test for jksc");
            assertNotNull(group);
            assertEquals("ksctestgroup", group.getName());
            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), group.getId());
            assertTrue(isGdeleted);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getGroupByName() throws Exception {
        //bug in auth fails to get group by name
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            GroupList groups = client.listGroups(response.getToken().getId(), null, null, "ksctestgroup");
            assertNotNull(groups);
            assertEquals("ksctestgroup", groups.getGroup().get(0).getName());
            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), groups.getGroup().get(0).getId());
            assertTrue(isGdeleted);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getGroupById() throws Exception {
        //bug in auth fails to get group by name
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            GroupList groups = client.listGroups(response.getToken().getId(), null, null, "ksctestgroup");
            Group g = client.retrieveGroup(response.getToken().getId(), groups.getGroup().get(0).getId());
            assertNotNull(g);
            assertEquals("ksctestgroup", g.getName());
            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), g.getId());
            assertTrue(isGdeleted);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void deleteGroup() throws Exception {
        //Fails.. bug in auth retrieve group by name
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            GroupList groups = client.listGroups("my-simple-demo-token", null, null, "ksctestgroup");
            Group group = client.retrieveGroup(response.getToken().getId(), groups.getGroup().get(0).getId());
            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), group.getId());
            assertTrue(isGdeleted);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addUserToGroup() throws Exception {
        IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
        GroupList groups = client.listGroups(response.getToken().getId(), null, null, "ksctestgroup");
        assertTrue(client.addUserToGroup(response.getToken().getId(), response.getUser().getId(), groups.getGroup().get(0).getId()));
    }

    @Test
    public void updateGroup() throws Exception {
        // Stub for now
    }

    @Test
    public void listGroupsForUser() throws Exception {
        //Stub for now
    }

    @Test
    public void removeUserFromGroup() throws Exception {
        //Stub for now
    }

    @Test
    public void listUsersFromGroup() throws Exception {
        //Stub for now
    }


    @Test
    public void listRoles() throws Exception {
        try {
            IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(KeystoneUtil.getProperty("admin-un"), KeystoneUtil.getProperty("admin-pw"));
            RoleList roles = client.listRoles(response.getToken().getId());
            assertNotNull(roles);
        } catch (KeystoneFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    private Tenants retrieveTenants(String token) throws KeystoneFault, URISyntaxException {
        IdentityClient client = new IdentityClient(KeystoneUtil.getProperty("auth_stag_url"));
        return client.retrieveTenants(testUser.getTokenId());

    }
}


package org.openstack.identity.client;

import com.sun.jersey.api.client.ClientResponse;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openstack.identity.client.access.Access;
import org.openstack.identity.client.client.IdentityClient;
import org.openstack.identity.client.common.util.IdentityUtil;
import org.openstack.identity.client.domain.Domain;
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
import java.util.Random;

import static junit.framework.Assert.*;

public class IdentityClientTest {
    private static TestUser testUser;
    private static String kcsBuilder = "kcsBuilder" + Math.random(); // :/
    private static String kcsBuilderGroup = "kcsBuilderGroup" + Math.random(); // :/
    private static String kcsRoleName = "kcsRoleName" + Math.random(); // :/
    private static String kcsDomainId = "30006332"; // :/
    private static String kcsBuilderId;
    private static String kcsBuilderGroupId;
    private static String kcsRoleId;
    //Run as a suite
    @Test
    public void validateTimeoutSet() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        Assert.assertEquals(3000, client.timeout);

        IdentityClient client2 = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"), 100);
        Assert.assertEquals(100, client2.timeout);
    }

    @Test
    public void validateCloudUsernamePassword() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        try {
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
            assertNotNull(response);
            assertEquals(IdentityUtil.getProperty("tenant_id"), response.getToken().getTenant().getName());
            testUser = new TestUser();
            testUser.setTokenId(response.getToken().getId());
            testUser.setTenantId(response.getToken().getTenant().getId());
            testUser.setUserId(response.getUser().getId());
            testUser.setUsername(response.getUser().getName());
            assertNotNull(retrieveTenants(testUser.getTokenId()));
            assertNotNull(retrieveTenants(testUser.getTenantId()));
            assertNotNull(retrieveTenants(testUser.getUserId()));
            assertNotNull(retrieveTenants(testUser.getUsername()));
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
            assertEquals(IdentityUtil.getProperty("tenant_id"), response.getTenant().get(1).getName());
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
            Tenant tenant = client.retrieveTenantById(response.getToken().getId(), IdentityUtil.getProperty("tenant_id"));
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
            Tenant tenant = client.retrieveTenantByname(response.getToken().getId(), IdentityUtil.getProperty("tenant_name"));
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
            RoleList roles = client.retrieveRolesByTenantId(response.getToken().getId(), IdentityUtil.getProperty("tenant_id"), user.getId());
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
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
            User bob;
            try {
                bob = client.listUserByName(response.getToken().getId(), kcsBuilder);
                if (bob.getId() != null) {
                    client.deleteUser(response.getToken().getId(), bob.getId());
                }
            } catch (IdentityFault ix) {
                //ignore...
            }

            User user = client.addUser(response.getToken().getId(), kcsBuilder, "Password1234", true, "the@mail.com", "DFW");
            assertNotNull(user);
            kcsBuilderId = user.getId();
        } catch (Exception ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getUserByName() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            User updatedUserB = client.listUserByName(response.getToken().getId(), kcsBuilder);
            assertNotNull(updatedUserB);
            assertEquals(kcsBuilder, updatedUserB.getUsername());
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
            User updatedUserB = client.listUserById(response.getToken().getId(), kcsBuilderId);
            assertNotNull(updatedUserB);
            assertEquals(kcsBuilder, updatedUserB.getUsername());
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
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
            UserList updatedUserB = client.listUsers(response.getToken().getId());
            assertNotNull(updatedUserB);
//            assertTrue(updatedUserB.getUser().contains(kcsBuilder));
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
            User userb = client.listUserByName(response.getToken().getId(), kcsBuilder);
            User user = client.updateUser(response.getToken().getId(), userb.getId(), kcsBuilder, true, "cheese@mail.com", "DFW");
            User updatedUserB = client.listUserByName(response.getToken().getId(), kcsBuilder);
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
            User userb = client.listUserByName(response.getToken().getId(), kcsBuilder);
            User user = client.updateUser(response.getToken().getId(), userb.getId(), null, true, "bob@mail.com", null);
            User updatedUserB = client.listUserByName(response.getToken().getId(), kcsBuilder);
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
            User userb = client.listUserByName(response.getToken().getId(), kcsBuilder);
            User user = client.updateUserPassword(response.getToken().getId(), userb.getId(), "kcsBuilder1");
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
            User userb = client.listUserByName(response.getToken().getId(), kcsBuilder);
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
            User userb = client.listUserByName(response.getToken().getId(), kcsBuilder);
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
            User bob = client.listUserByName(response.getToken().getId(), kcsBuilder);
            client.deleteUser(response.getToken().getId(), bob.getId());
            client.listUserByName(response.getToken().getId(), kcsBuilder);
        } catch (IdentityFault ex) {
            if (!ex.getMessage().contains("User not found")) {
                System.out.print(ex.getMessage());
                Assert.fail(ex.getDetails());
            }
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
        IdentityClient client = null;
        AuthenticateResponse response = null;
        try {
            client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Group group = client.addGroup(response.getToken().getId(), kcsBuilderGroup, "this is a test for jksc");
            assertNotNull(group);
            assertEquals(kcsBuilderGroup, group.getName());
            kcsBuilderGroupId = group.getId();
//            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), group.getId());
//            assertTrue(isGdeleted);
        } catch (IdentityFault ex) {
            System.out.print(ex.getMessage());
            if (!ex.getMessage().contains("already exists")) {
                Assert.fail(ex.getMessage());
            }
        }
    }

    @Test
    public void getGroupByName() throws Exception {
        //bug in auth fails to get group by name
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Group group = client.retrieveGroupByName(response.getToken().getId(), kcsBuilderGroup);
            assertNotNull(group);
            assertEquals(kcsBuilderGroup, group.getName());
//            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), groups.getGroup().get(0).getId());
//            assertTrue(isGdeleted);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getGroupById() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Group g = client.retrieveGroup(response.getToken().getId(), kcsBuilderGroupId);
            assertNotNull(g);
            assertEquals(kcsBuilderGroup, g.getName());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addUserToGroup() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse respuser = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        assertTrue(client.addUserToGroup(response.getToken().getId(), respuser.getUser().getId(), kcsBuilderGroupId));
    }

    @Test
    public void updateGroup() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        Group group = client.retrieveGroupByName(response.getToken().getId(), kcsBuilderGroup);
        String desc = "This is a new description";
        Group g = client.updateGroup(response.getToken().getId(), group.getId(), group.getName(), desc);
        assertTrue(g.getDescription().equals(desc));
    }

    @Test
    public void listGroupsForUser() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse respuser = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        client.addUserToGroup(response.getToken().getId(), response.getUser().getId(), kcsBuilderGroupId);
        GroupList userGroups = client.listGroupsForUser(response.getToken().getId(), respuser.getUser().getId());
        assertFalse(userGroups.getGroup().isEmpty());
    }

    @Test
    public void listUsersFromGroup() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        UserList users = client.listUsersForGroup(response.getToken().getId(), kcsBuilderGroupId);
        assertFalse(users.getUser().isEmpty());
        assertEquals(response.getUser().getName(), users.getUser().get(0).getUsername());
    }

    @Test
    public void removeUserFromGroup() throws Exception {
        IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
        AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
        AuthenticateResponse respuser = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
        assertTrue(client.removeUserFromGroup(response.getToken().getId(), kcsBuilderGroupId, respuser.getUser().getId()));
    }

    @Test
    public void deleteGroup() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            UserList users = client.listUsersForGroup(response.getToken().getId(), kcsBuilderGroupId);
            if (!users.getUser().isEmpty()) {
                for (User user : users.getUser()) {
                    assertTrue(client.removeUserFromGroup(response.getToken().getId(), kcsBuilderGroupId, user.getId()));
                }
            }
            boolean isGdeleted = client.deleteGroup(response.getToken().getId(), kcsBuilderGroupId);
            assertTrue(isGdeleted);
        } catch (IdentityFault ex) {
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void listRoles() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
            RoleList roles = client.listRoles(response.getToken().getId());
            assertNotNull(roles);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

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
    public void addRole() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Role role = client.addRole(response.getToken().getId(), kcsRoleName, "itcDescription");
            kcsRoleId = role.getId();
            assertNotNull(role);
            assertEquals(kcsRoleName, role.getName());
        } catch (IdentityFault ex) {
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addRoleToUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            boolean role = client.addGlobalRoleToUser(response.getToken().getId(), testUser.getUserId(), kcsRoleId);
            assertTrue(role);
        } catch (IdentityFault ex) {
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void listRolesForUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("username"), IdentityUtil.getProperty("password"));
            RoleList roles = client.listUserGlobalRoles(response.getToken().getId(), testUser.getUserId());
            assertNotNull(roles);
            assertEquals(2, roles.getRole().size());
        } catch (IdentityFault ex) {
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void deleteRoleFromUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            RoleList roles = client.listUserGlobalRoles(response.getToken().getId(), testUser.getUserId());
            for (Role r : roles.getRole()) {
                if (!r.getName().equalsIgnoreCase("identity:user-admin")) {
                    assertTrue(client.deleteGlobalRoleFromUser(response.getToken().getId(), testUser.getUserId(), r.getId()));
                }
            }
            RoleList aroles = client.listUserGlobalRoles(response.getToken().getId(), testUser.getUserId());

            if (aroles.getRole().size() != 1) {
                Assert.fail("There are still roles left on the user that need to be removed!");
            }
        } catch (IdentityFault ex) {
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
            ClientResponse secret = client.updateSecretQA(response.getToken().getId(), response.getUser().getId(), "imATeaPot", "shortAndStout");
            assertEquals(200, secret.getStatus());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void impersonateUser() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Access re = client.impersonateUser(response.getToken().getId(), IdentityUtil.getProperty("username"), 300);
            //Only return token with expiration time of impersonation...
            assertNotNull(re);
            assertNotNull(re.getToken().getId());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void createDomain() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            ClientResponse re = client.createDomain(response.getToken().getId(), kcsDomainId, "TestDomain", true, "This is just a test");
            assertNotNull(re);
            assertTrue(re.getHeaders().containsKey("Location"));
            assertEquals(IdentityUtil.getProperty("endpoint") + "RAX-AUTH/domains/" + kcsDomainId, re.getHeaders().getFirst("Location"));
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getDomain() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Domain re = client.getDomain(response.getToken().getId(), "1");
            assertNotNull(re);
            assertEquals("Default Domain", re.getDescription());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void updateDomain() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            boolean re = client.updateDomain(response.getToken().getId(), kcsDomainId, "new NAME", "true", null);
            assertTrue(re);
            Domain dom = client.getDomain(response.getToken().getId(), kcsDomainId);
            assertEquals("new NAME", dom.getName());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void listEndpointsForDomain() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            EndpointList re = client.getEndpointsForDomain(response.getToken().getId(), kcsDomainId);
            assertNotNull(re);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addUserToDomain() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Domain dom = client.getDomain(response.getToken().getId(), kcsDomainId);
            assertTrue(dom.isEnabled());
            boolean re = client.addUserToDomain(response.getToken().getId(), dom.getId(), testUser.getUserId());
            assertTrue(re);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void listUsersForDomain() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            UserList re = client.getUsersForDomain(response.getToken().getId(), kcsDomainId, "true");
            assertNotNull(re);
            assertEquals(1, re.getUser().size());
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void addTenantToDomain() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            boolean re = client.addTenantToDomain(response.getToken().getId(), kcsDomainId, IdentityUtil.getProperty("tenant_id"));
            assertTrue(re);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void listTenantsForDomain() throws Exception {
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            Tenants re = client.getTenantsFromDomain(response.getToken().getId(), kcsDomainId, "true");
            assertNotNull(re);
        } catch (IdentityFault ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void deleteDomain() throws Exception {
        //Docs have no method to remove users/tenants from domain, therefore delete and create domain will fail....
        try {
            IdentityClient client = new IdentityClient(IdentityUtil.getProperty("auth_stag_url"));
            AuthenticateResponse response = client.authenticateUsernamePassword(IdentityUtil.getProperty("admin-un"), IdentityUtil.getProperty("admin-pw"));
            boolean re = client.deleteDomain(response.getToken().getId(), kcsDomainId);
            assertTrue(re);
            Domain dom = client.getDomain(response.getToken().getId(), kcsDomainId);
            assertNull(dom);
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


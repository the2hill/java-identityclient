package org.openstack.keystone.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.openstack.keystone.client.api.credentials.ApiKeyCredentials;
import org.openstack.keystone.client.common.constants.KeystoneConstants;
import org.openstack.keystone.client.common.util.ResourceUtil;
import org.openstack.keystone.client.common.wrapper.KeystoneResponseWrapper;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.manager.UserResourceManager;
import org.openstack.keystone.client.manager.entity.Credentials;
import org.openstack.keystone.client.token.AuthenticateResponse;
import org.openstack.keystone.client.user.ObjectFactory;
import org.openstack.keystone.client.user.User;
import org.openstack.keystone.client.user.UserList;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import java.net.URI;
import java.net.URISyntaxException;

public class UserResourceManagerImpl extends ResponseManagermpl implements UserResourceManager {

    /**
     * List users by token
     *
     * @param client
     * @param url
     * @param token
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public UserList listUsers(Client client, String url, String token) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + KeystoneConstants.USER_PATH), token);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(UserList.class);
    }

    /**
     * List user by name
     *
     * @param client
     * @param url
     * @param token
     * @param username
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public User listUserByName(Client client, String url, String token, String username) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            MultivaluedMap<String, String> params = new MultivaluedMapImpl();
            params.add(KeystoneConstants.NAME, username);
            response = get(client, new URI(url + KeystoneConstants.USER_PATH), token, params);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(User.class);
    }

    /**
     * List user by id
     *
     * @param client
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public User listUserById(Client client, String url, String token, String userId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + KeystoneConstants.USER_PATH + "/" + userId), token);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(User.class);
    }

    /**
     * List user credentials
     *
     * @param client
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public Credentials listCredentials(Client client, String url, String token, String userId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = get(client, new URI(url + KeystoneConstants.USER_PATH + "/"
                    + userId + "/"
                    + KeystoneConstants.KSDAM_PATH
                    + "/credentials"), token);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }


        Credentials credentials = null;
        try {
            AuthenticateResponse r = response.getEntity(AuthenticateResponse.class);
            if (!r.getAny().isEmpty()) {
                ApiKeyCredentials creds = (ApiKeyCredentials)
                        ResourceUtil.unmarshallResource(r.getAny().get(0),
                                JAXBContext.newInstance(ApiKeyCredentials.class))
                                .getValue();
                credentials = new Credentials();
                credentials.setApiKeyCredentials(creds);
            }
        } catch (Exception e) {
            handleBadResponse(null);
        }


        return credentials;
    }

    /**
     * Add user with generate password
     *
     * @param client
     * @param url
     * @param token
     * @param username
     * @param enabled
     * @param email
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    @Override
    public User addUser(Client client, String url, String token, String username, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException {
        return addUser(client, url, token, username, null, enabled, email, region);
    }

    /**
     * Add user with password
     *
     * @param client
     * @param url
     * @param token
     * @param username
     * @param password
     * @param enabled
     * @param email
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    @Override
    public User addUser(Client client, String url, String token, String username, String password, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException {
        ClientResponse response = null;
        String userReq = buildAddUserRequest(username, password, enabled, email, region);
        try {
            response = post(client, new URI(url + KeystoneConstants.USER_PATH), token, userReq);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(User.class);
    }

    /**
     * Update user based off of userId
     *
     * @param client
     * @param url
     * @param token
     * @param userId
     * @param username
     * @param enabled
     * @param email
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    @Override
    public User updateUser(Client client, String url, String token, String userId, String username, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException {
        ClientResponse response = null;

        User user = listUserById(client, url, token, userId);
        String userReq = buildUpdateUserRequest(user, username, enabled, email, region);
        try {
            response = post(client, new URI(url + KeystoneConstants.USER_PATH + "/" + userId), token, userReq);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(User.class);
    }

    /**
     * Update user password
     *
     * @param client
     * @param url
     * @param token
     * @param userId
     * @param password
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     * @throws JAXBException
     */
    @Override
    public User updateUserPassword(Client client, String url, String token, String userId, String password) throws KeystoneFault, URISyntaxException, JAXBException {
        ClientResponse response = null;

        User user = listUserById(client, url, token, userId);
        String userReq = buildUpdateUserPasswordRequest(user.getUsername(), password);
        try {
            response = post(client, new URI(url + KeystoneConstants.USER_PATH + "/" + userId), token, userReq);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(User.class);
    }

    @Override
    public AuthenticateResponse updateUserCredentials(Client client, String url, String token, String userId, String apiKey) throws KeystoneFault, URISyntaxException, JAXBException {
        ClientResponse response = null;

        User user = listUserById(client, url, token, userId);
        String userReq = buildUpdateUserCredentialsRequest(user.getUsername(), apiKey);
        try {
            response = post(client, new URI(url + KeystoneConstants.USER_PATH + "/"
                    + userId + "/"
                    + KeystoneConstants.KSDAM_PATH
                    + "/credentials" + "/"
                    + KeystoneConstants.RAX_API_CRED), token, userReq);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(AuthenticateResponse.class);
    }

    @Override
    public void deleteUserCredentials(Client client, String url, String token, String userId) throws KeystoneFault, URISyntaxException, JAXBException {
        ClientResponse response = null;
        try {
            response = delete(client, new URI(url + KeystoneConstants.USER_PATH + "/"
                    + userId + "/"
                    + KeystoneConstants.KSDAM_PATH
                    + "/credentials" + "/"
                    + KeystoneConstants.RAX_API_CRED), token);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
    }

    /**
     * Delete user by userId
     *
     * @param client
     * @param url
     * @param token
     * @param userId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public User deleteUser(Client client, String url, String token, String userId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = delete(client, new URI(url + KeystoneConstants.USER_PATH + "/" + userId), token);
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }
        return response.getEntity(User.class);
    }

    /**
     * Generate user request object for adding user
     *
     * @param username
     * @param password
     * @param enabled
     * @param email
     * @param region
     * @return
     * @throws JAXBException
     */
    private String buildAddUserRequest(String username, String password, boolean enabled, String email, String region) throws JAXBException {
        ObjectFactory factory = new ObjectFactory();
        User user = factory.createUser();
        user.setUsername(username);
        user.setEnabled(enabled);
        user.setEmail(email);
        if (password != null)
            user.getOtherAttributes().put(new QName(KeystoneConstants.RAX_KSADM_NS, KeystoneConstants.PASSWORD), password);
        if (region != null)
            user.getOtherAttributes().put(new QName(KeystoneConstants.RAX_KSADM_NS, KeystoneConstants.DEFAULT_REGION), region);
        System.out.println(ResourceUtil.marshallResource(factory.createUser(user),
                JAXBContext.newInstance(User.class)).toString());
        return ResourceUtil.marshallResource(factory.createUser(user),
                JAXBContext.newInstance(User.class)).toString();
    }


    /**
     * Generate user request object for updating user
     *
     * @param user
     * @param username
     * @param enabled
     * @param email
     * @param region
     * @return
     * @throws JAXBException
     */
    private String buildUpdateUserRequest(User user, String username, boolean enabled, String email, String region) throws JAXBException {
        ObjectFactory factory = new ObjectFactory();
        User updatedUser = factory.createUser();
        if (username != null) {
            updatedUser.setUsername(username);
        } else {
            updatedUser.setUsername(user.getUsername());
        }

        if (email != null) updatedUser.setEmail(email);
        if (user.isEnabled() != enabled) updatedUser.setEnabled(enabled);
        if (region != null)
            updatedUser.getOtherAttributes().put(new QName(KeystoneConstants.RAX_AUTH_NS, KeystoneConstants.DEFAULT_REGION), region);

        System.out.println(ResourceUtil.marshallResource(factory.createUser(updatedUser),
                JAXBContext.newInstance(User.class)).toString());
        return ResourceUtil.marshallResource(factory.createUser(updatedUser),
                JAXBContext.newInstance(User.class)).toString();
    }

    /**
     * Generate user password object for updating user password
     *
     * @param username
     * @param password
     * @return
     * @throws JAXBException
     */
    private String buildUpdateUserPasswordRequest(String username, String password) throws JAXBException {
        ObjectFactory factory = new ObjectFactory();
        User user = null;
        if (password != null) {
            user = new User();
            user.getOtherAttributes().put(new QName(KeystoneConstants.RAX_KSADM_NS, KeystoneConstants.PASSWORD), password);
        }
        return ResourceUtil.marshallResource(factory.createUser(user),
                JAXBContext.newInstance(User.class)).toString();
    }

    /**
     * Generate user apiKeyCredentials object for updating user credentials
     *
     * @param username
     * @param apiKey
     * @return
     * @throws JAXBException
     */
    private String buildUpdateUserCredentialsRequest(String username, String apiKey) throws JAXBException {
        org.openstack.keystone.client.api.credentials.ObjectFactory factory = new org.openstack.keystone.client.api.credentials.ObjectFactory();
        ApiKeyCredentials apiKeyCredential = factory.createApiKeyCredentials();
        apiKeyCredential.setUsername(username);
        apiKeyCredential.setApiKey(apiKey);

        System.out.println(ResourceUtil.marshallResource(factory.createApiKeyCredentials(apiKeyCredential),
                JAXBContext.newInstance(ApiKeyCredentials.class)).toString());
        return ResourceUtil.marshallResource(factory.createApiKeyCredentials(apiKeyCredential),
                JAXBContext.newInstance(ApiKeyCredentials.class)).toString();
    }
}
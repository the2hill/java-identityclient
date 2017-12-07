package org.openstack.identity.client.manager;

import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.entity.Credentials;
import org.openstack.identity.client.token.AuthenticateResponse;
import org.openstack.identity.client.user.User;
import org.openstack.identity.client.user.UserList;

import javax.ws.rs.client.Client;
import javax.xml.bind.JAXBException;
import java.net.URISyntaxException;

public interface UserResourceManager {

    public UserList listUsers(Client client, String url, String token) throws IdentityFault, URISyntaxException;

    public User listUserByName(Client client, String url, String token, String username) throws IdentityFault, URISyntaxException;

    public User listUserById(Client client, String url, String token, String userId) throws IdentityFault, URISyntaxException;

    public Credentials listCredentials(Client client, String url, String token, String userId) throws IdentityFault, URISyntaxException;

    public User addUser(Client client, String url, String token, String username, boolean enabled, String email, String region) throws IdentityFault, URISyntaxException, JAXBException;

    public User addUser(Client client, String url, String token, String username, String password, boolean enabled, String email, String region) throws IdentityFault, URISyntaxException, JAXBException;

    public User updateUser(Client client, String url, String token, String userId, String username, boolean enabled, String email, String region) throws IdentityFault, URISyntaxException, JAXBException;

    public User updateUserPassword(Client client, String url, String token, String userId, String password) throws IdentityFault, URISyntaxException, JAXBException;

    public AuthenticateResponse updateUserCredentials(Client client, String url, String token, String userId, String apiKey) throws IdentityFault, URISyntaxException, JAXBException;

    public void deleteUserCredentials(Client client, String url, String token, String userId) throws IdentityFault, URISyntaxException, JAXBException;

    public User deleteUser(Client client, String url, String token, String userId) throws IdentityFault, URISyntaxException;
}

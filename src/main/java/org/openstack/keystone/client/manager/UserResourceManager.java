package org.openstack.keystone.client.manager;

import com.sun.jersey.api.client.Client;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.manager.entity.Credentials;
import org.openstack.keystone.client.token.AuthenticateResponse;
import org.openstack.keystone.client.user.User;
import org.openstack.keystone.client.user.UserList;

import javax.xml.bind.JAXBException;
import java.net.URISyntaxException;

public interface UserResourceManager {

    public UserList listUsers(Client client, String url, String token) throws KeystoneFault, URISyntaxException;

    public User listUserByName(Client client, String url, String token, String username) throws KeystoneFault, URISyntaxException;

    public User listUserById(Client client, String url, String token, String userId) throws KeystoneFault, URISyntaxException;

    public Credentials listCredentials(Client client, String url, String token, String userId) throws KeystoneFault, URISyntaxException;

    public User addUser(Client client, String url, String token, String username, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException;

    public User addUser(Client client, String url, String token, String username, String password, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException;

    public User updateUser(Client client, String url, String token, String userId, String username, boolean enabled, String email, String region) throws KeystoneFault, URISyntaxException, JAXBException;

    public User updateUserPassword(Client client, String url, String token, String userId, String password) throws KeystoneFault, URISyntaxException, JAXBException;

    public AuthenticateResponse updateUserCredentials(Client client, String url, String token, String userId, String apiKey) throws KeystoneFault, URISyntaxException, JAXBException;

    public void deleteUserCredentials(Client client, String url, String token, String userId) throws KeystoneFault, URISyntaxException, JAXBException;

    public User deleteUser(Client client, String url, String token, String userId) throws KeystoneFault, URISyntaxException;
}

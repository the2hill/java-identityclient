package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.secretqa.SecretQA;

import java.net.URISyntaxException;

public interface SecretQAResourceManager {

    public SecretQA listSecretQA(Client client, String url, String token, String userId) throws IdentityFault, URISyntaxException;

    public ClientResponse updateSecretQA(Client client, String url, String token, String userId, String question, String answer) throws IdentityFault, URISyntaxException;
}
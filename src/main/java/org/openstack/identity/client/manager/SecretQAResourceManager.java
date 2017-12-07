package org.openstack.identity.client.manager;

import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.secretqa.SecretQA;

import javax.ws.rs.client.Client;
import java.net.URISyntaxException;

public interface SecretQAResourceManager {

    public SecretQA listSecretQA(Client client, String url, String token, String userId) throws IdentityFault, URISyntaxException;

    public SecretQA updateSecretQA(Client client, String url, String token, String userId, String question, String answer) throws IdentityFault, URISyntaxException;
}
package org.openstack.identity.client.manager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;

public interface ResponseManager {
    public ClientResponse get(Client client, URI uri, String token);

    public ClientResponse get(Client client, URI uri, String token, MultivaluedMap<String, String> params);

    public ClientResponse post(Client client, URI uri, String body);

    public ClientResponse post(Client client, URI uri, String token, String body);

    public ClientResponse put(Client client, URI uri, String token, String body);

    public ClientResponse delete(Client client, URI uri, String token);

    public ClientResponse head(Client client, URI uri, String body);
}

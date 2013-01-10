package org.openstack.keystone.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.openstack.keystone.client.api.credentials.ApiKeyCredentials;
import org.openstack.keystone.client.common.constants.KeystoneConstants;
import org.openstack.keystone.client.common.util.ResourceUtil;
import org.openstack.keystone.client.common.wrapper.KeystoneResponseWrapper;
import org.openstack.keystone.client.credentials.*;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.manager.AuthenticationResourceManager;
import org.openstack.keystone.client.token.AuthenticateResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class AuthenticationResourceManagerImpl extends ResponseManagerImpl implements AuthenticationResourceManager {
    public AuthenticationRequest authenticationRequest;

    public AuthenticationResourceManagerImpl(AuthenticationRequest authenticationRequest) {
        this.authenticationRequest = authenticationRequest;
    }

    public AuthenticationResourceManagerImpl() {
    }

    /**
     * Authenticate by username password
     *
     * @param client
     * @param url
     * @param username
     * @param password
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public AuthenticateResponse authenticateUsernamePassword(Client client, String url, String username, String password) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            buildUsernamePasswordCredentials(username, password);
            System.out.println(generateAuthenticateRequest());
            response = post(client, new URI(url + KeystoneConstants.TOKEN_PATH),
                    generateAuthenticateRequest());
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw keystoneFault(e);
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(AuthenticateResponse.class);
    }

    /**
     * Authenticate by tenantId tokenId
     *
     * @param client
     * @param url
     * @param tenantId
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public AuthenticateResponse authenticateTenantIdTokenId(Client client, String url, String tenantId, String tokenId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            buildTenantIdTokenIdCredentials(tenantId, tokenId);
            System.out.println(generateAuthenticateRequest());
            response = post(client, new URI(url + KeystoneConstants.TOKEN_PATH), generateAuthenticateRequest());
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw keystoneFault(e);
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(AuthenticateResponse.class);
    }

    /**
     * Authenticate by tenantName tokenId
     *
     * @param client
     * @param url
     * @param tenantName
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public AuthenticateResponse authenticateTenantNameTokenId(Client client, String url, String tenantName, String tokenId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            buildTenantNameTokenIdCredentials(tenantName, tokenId);
            System.out.println(generateAuthenticateRequest());
            response = post(client, new URI(url + KeystoneConstants.TOKEN_PATH), generateAuthenticateRequest());
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw keystoneFault(e);
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(AuthenticateResponse.class);
    }

    /**
     * Authenticate by username apiKey
     *
     * @param client
     * @param url
     * @param tenantName
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public AuthenticateResponse authenticateUsernameApiKey(Client client, String url, String tenantName, String tokenId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = post(client, new URI(url + KeystoneConstants.TOKEN_PATH), buildUsernameApiKeyCredentials(tenantName, tokenId));
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw keystoneFault(e);
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(AuthenticateResponse.class);
    }


    /**
     * Authenticate by tenantId username apiKey
     *
     * @param client
     * @param url
     * @param tenantId
     * @param tenantName
     * @param tokenId
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public AuthenticateResponse authenticateTenantIdUsernameApiKey(Client client, String url, String tenantId, String tenantName, String tokenId) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            response = post(client, new URI(url + KeystoneConstants.TOKEN_PATH), buildTenantIdUsernameApiKeyCredentials(tenantId, tenantName, tokenId));
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw keystoneFault(e);
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(AuthenticateResponse.class);
    }


    /**
     * Authenticate tenantId username password
     *
     * @param client
     * @param url
     * @param tenantId
     * @param username
     * @param password
     * @return
     * @throws KeystoneFault
     * @throws URISyntaxException
     */
    @Override
    public AuthenticateResponse authenticateTenantIdUsernamePassword(Client client, String url, String tenantId, String username, String password) throws KeystoneFault, URISyntaxException {
        ClientResponse response = null;
        try {
            buildTenantIdUsernamePasswordCredentials(tenantId, username, password);
            System.out.println(generateAuthenticateRequest());
            response = post(client, new URI(url + KeystoneConstants.TOKEN_PATH), generateAuthenticateRequest());
        } catch (UniformInterfaceException ux) {
            throw KeystoneResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw keystoneFault(e);
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.getEntity(AuthenticateResponse.class);
    }

    /**
     * Generate authenticateRequest object
     *
     * @param authenticationRequest
     * @return
     * @throws JAXBException
     * @throws KeystoneFault
     */
    private String generateAuthenticateRequest(AuthenticationRequest authenticationRequest) throws JAXBException, KeystoneFault {
        if (authenticationRequest != null) {
        ObjectFactory factory = new ObjectFactory();
        return ResourceUtil.marshallResource(factory.createAuth(authenticationRequest),
                JAXBContext.newInstance(AuthenticationRequest.class)).toString();
        } else {
            throw new KeystoneFault("Request object must be intialised first. ", "Internal ERROR", 500);
        }
    }

    //Request generation:

    /**
     * Return the generated authentication request
     *
     * @return
     * @throws JAXBException
     * @throws KeystoneFault
     */
    private String generateAuthenticateRequest() throws JAXBException, KeystoneFault {
        return generateAuthenticateRequest(authenticationRequest);
    }

    /**
     * Generate tenante nameTokenId  authentication request object
     *
     * @param tenantName
     * @param tokenId
     */
    private void buildTenantNameTokenIdCredentials(String tenantName, String tokenId) {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setTenantName(tenantName);
        authenticationRequest.setToken(buildTokenForAuthenticaionRequestObject(tokenId));
        this.authenticationRequest = authenticationRequest;
    }

    /**
     * Generate tenantId tokenId authentication request object
     *
     * @param tenantId
     * @param tokenId
     */
    private void buildTenantIdTokenIdCredentials(String tenantId, String tokenId) {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setTenantId(tenantId);
        authenticationRequest.setToken(buildTokenForAuthenticaionRequestObject(tokenId));
        this.authenticationRequest = authenticationRequest;
    }

    /**
     * Generate username password authentication request object
     *
     * @param username
     * @param password
     */
    private void buildUsernamePasswordCredentials(String username, String password) {
        ObjectFactory factory = new ObjectFactory();
        PasswordCredentialsRequiredUsername requiredUsername = new PasswordCredentialsRequiredUsername();
        requiredUsername.setUsername(username);
        requiredUsername.setPassword(password);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setCredential(factory.createPasswordCredentials(requiredUsername));
        this.authenticationRequest = authenticationRequest;
    }

    /**
     * Generate tenantId username password authentication request object
     *
     * @param tenantId
     * @param username
     * @param password
     */
    private void buildTenantIdUsernamePasswordCredentials(String tenantId, String username, String password) {
        ObjectFactory factory = new ObjectFactory();
        PasswordCredentialsRequiredUsername credentials = factory.createPasswordCredentialsRequiredUsername();
        credentials.setUsername(username);
        credentials.setPassword(password);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setTenantId(tenantId);
        authenticationRequest.setCredential(factory.createCredential(credentials));
        this.authenticationRequest = authenticationRequest;
    }

    /**
     * Generate tenantId username apiKey authentication request object
     *
     * @param tenantId
     * @param username
     * @param apiKey
     * @return
     * @throws JAXBException
     */
    private String buildTenantIdUsernameApiKeyCredentials(String tenantId, String username, String apiKey) throws JAXBException {
        org.openstack.keystone.client.api.credentials.ObjectFactory factory = new org.openstack.keystone.client.api.credentials.ObjectFactory();
        ApiKeyCredentials apiKeyCredential = factory.createApiKeyCredentials();
        apiKeyCredential.setTenantId(tenantId);
        apiKeyCredential.setUsername(username);
        apiKeyCredential.setApiKey(apiKey);
        org.openstack.keystone.client.api.credentials.AuthenticationRequest authenticationRequest = new org.openstack.keystone.client.api.credentials.AuthenticationRequest();
        authenticationRequest.setCredential(factory.createApiKeyCredentials(apiKeyCredential));
        return ResourceUtil.marshallResource(factory.createAuth(authenticationRequest),
                JAXBContext.newInstance(org.openstack.keystone.client.api.credentials.AuthenticationRequest.class)).toString();
    }

    /**
     * Generate username apiKey authentication request object
     *
     * @param username
     * @param apiKey
     * @return
     * @throws JAXBException
     */
    private String buildUsernameApiKeyCredentials(String username, String apiKey) throws JAXBException {
        org.openstack.keystone.client.api.credentials.ObjectFactory factory = new org.openstack.keystone.client.api.credentials.ObjectFactory();
        ApiKeyCredentials apiKeyCredential = factory.createApiKeyCredentials();
        apiKeyCredential.setUsername(username);
        apiKeyCredential.setApiKey(apiKey);
        org.openstack.keystone.client.api.credentials.AuthenticationRequest authenticationRequest = new org.openstack.keystone.client.api.credentials.AuthenticationRequest();
        authenticationRequest.setCredential(factory.createApiKeyCredentials(apiKeyCredential));
        return ResourceUtil.marshallResource(factory.createAuth(authenticationRequest),
                JAXBContext.newInstance(org.openstack.keystone.client.api.credentials.AuthenticationRequest.class)).toString();
    }

    /**
     * Generate token for authentication request object
     *
     * @param tokenId
     * @return
     */
    private TokenForAuthenticationRequest buildTokenForAuthenticaionRequestObject(String tokenId) {
        TokenForAuthenticationRequest token = new TokenForAuthenticationRequest();
        token.setId(tokenId);
        return token;
    }

    /**
     * Return generic keystoneFault
     *
     * @param ex
     * @return
     */
    private KeystoneFault keystoneFault(Exception ex) {
        return new KeystoneFault("Error processing request. ", Arrays.toString(ex.getStackTrace()), 500);
    }
}

package org.openstack.identity.client.manager.impl;

import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.openstack.identity.client.common.constants.IdentityConstants;
import org.openstack.identity.client.common.util.ResourceUtil;
import org.openstack.identity.client.common.wrapper.IdentityResponseWrapper;
import org.openstack.identity.client.domain.Domain;
import org.openstack.identity.client.domain.ObjectFactory;
import org.openstack.identity.client.endpoints.EndpointList;
import org.openstack.identity.client.fault.IdentityFault;
import org.openstack.identity.client.manager.DomainResourceManager;
import org.openstack.identity.client.tenant.Tenants;
import org.openstack.identity.client.user.UserList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.net.URI;
import java.net.URISyntaxException;

public class DomainResourceManagerImpl extends ResponseManagerImpl implements DomainResourceManager {

    @Override
    public Response createDomain(Client client, String url, String token, String domainId, String domainName, boolean enabled, String description) throws IdentityFault, URISyntaxException {
        Response response = null;
        try {
            response = post(client, new URI(url + IdentityConstants.RAX_AUTH + IdentityConstants.DOMAINS), token, buildCreateDomainRequestObject(domainId, domainName, enabled, description));
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw new IdentityFault(e.getMessage(), e.getLinkedException().getLocalizedMessage(), Integer.valueOf(e.getErrorCode()));
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response;
    }

    @Override
    public boolean updateDomain(Client client, String url, String token, String domainId, String domainName, String enabled, String description) throws IdentityFault, URISyntaxException {
        Response response = null;
        try {
            URI blah = new URI(url + IdentityConstants.RAX_AUTH + IdentityConstants.DOMAINS + "/" + domainId);
            Domain domain = getDomain(client, url, token, domainId);
            response = put(client, new URI(url + IdentityConstants.RAX_AUTH + IdentityConstants.DOMAINS + "/" + domainId), token, buildUpdateDomainRequestObject(domain, domainId, domainName, enabled, description));
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        } catch (JAXBException e) {
            throw new IdentityFault(e.getMessage(), e.getLinkedException().getLocalizedMessage(), Integer.valueOf(e.getErrorCode()));
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return true;
    }

    @Override
    public Domain getDomain(Client client, String url, String token, String domainId) throws IdentityFault, URISyntaxException {
        Response response = null;
        try {
            response = get(client, new URI(url + IdentityConstants.RAX_AUTH + IdentityConstants.DOMAINS + "/" + domainId), token);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.readEntity(Domain.class);
    }

    @Override
    public boolean deleteDomain(Client client, String url, String token, String domainId) throws IdentityFault, URISyntaxException {
        Response response = null;
        try {
            response = delete(client, new URI(url + IdentityConstants.RAX_AUTH + IdentityConstants.DOMAINS + "/" + domainId), token);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return true;
    }

    @Override
    public EndpointList getEndpointsForDomain(Client client, String url, String token, String domainId) throws IdentityFault, URISyntaxException {
        Response response = null;
        try {
            response = get(client, new URI(url + IdentityConstants.RAX_AUTH + IdentityConstants.DOMAINS + "/" + domainId + "/" + IdentityConstants.ENDPOINTS_PATH), token);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.readEntity(EndpointList.class);
    }

    @Override
    public UserList getUsersFromDomain(Client client, String url, String token, String domainId, String enabled) throws IdentityFault, URISyntaxException {
        Response response = null;
        try {
            MultivaluedStringMap params = new MultivaluedStringMap();
            if (enabled != null) {
                params.add("enabled", enabled);
            }
            response = get(client, new URI(url + IdentityConstants.RAX_AUTH
                    + IdentityConstants.DOMAINS
                    + "/" + domainId + "/"
                    + IdentityConstants.USER_PATH), token, params);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.readEntity(UserList.class);
    }

    @Override
    public boolean addUserToDomain(Client client, String url, String token, String domainId, String userId) throws IdentityFault, URISyntaxException {
        Response response = null;
        try {
            response = put(client, new URI(url + IdentityConstants.RAX_AUTH
                    + IdentityConstants.DOMAINS
                    + "/" + domainId + "/"
                    + IdentityConstants.USER_PATH
                    + "/" + userId), token, null);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return true;
    }

    @Override
    public Tenants getTenantsFromDomain(Client client, String url, String token, String domainId, String enabled) throws IdentityFault, URISyntaxException {
        Response response = null;
        try {
            MultivaluedStringMap params = new MultivaluedStringMap();
            if (enabled != null) {
                params.add("enabled", enabled);
            }
            response = get(client, new URI(url + IdentityConstants.RAX_AUTH
                    + IdentityConstants.DOMAINS
                    + "/" + domainId + "/"
                    + IdentityConstants.TENANT_PATH), token, params);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return response.readEntity(Tenants.class);
    }

     @Override
    public boolean addTenantToDomain(Client client, String url, String token, String domainId, String tenantId) throws IdentityFault, URISyntaxException {
        Response response = null;
        try {
            response = put(client, new URI(url + IdentityConstants.RAX_AUTH
                    + IdentityConstants.DOMAINS
                    + "/" + domainId + "/"
                    + IdentityConstants.TENANT_PATH
                    + "/" + tenantId), token, null);
        } catch (ResponseProcessingException ux) {
            throw IdentityResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (!isResponseValid(response)) {
            handleBadResponse(response);
        }

        return true;
    }

    private String buildCreateDomainRequestObject(String domainId, String domainName, boolean enabled, String description) throws JAXBException {
        ObjectFactory factory = new ObjectFactory();
        Domain domain = factory.createDomain();
        domain.setId(domainId);
        domain.setName(domainName);
        domain.setEnabled(enabled);
        domain.setDescription(description);
        return ResourceUtil.marshallResource(factory.createDomain(domain),
                JAXBContext.newInstance(Domain.class)).toString();
    }

    private String buildUpdateDomainRequestObject(Domain domain, String domainId, String domainName, String enabled, String description) throws JAXBException {
        ObjectFactory factory = new ObjectFactory();
        Domain domainU = factory.createDomain();
        if (domainId != null) {
            domainU.setId(domainId);
        } else {
            domainU.setId(domain.getId());
        }
        if (domainName != null) {
            domainU.setName(domainName);
        } else {
            domainU.setName(domain.getName());
        }
        domainU.setEnabled(Boolean.valueOf(enabled));
        if (description != null) {
            domainU.setDescription(description);
        } else {
            domainU.setDescription(domain.getDescription());
        }
        return ResourceUtil.marshallResource(factory.createDomain(domainU),
                JAXBContext.newInstance(Domain.class)).toString();
    }
}
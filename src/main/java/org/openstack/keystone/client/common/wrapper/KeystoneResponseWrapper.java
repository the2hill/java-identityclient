package org.openstack.keystone.client.common.wrapper;

import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.keystone.client.common.constants.KeystoneConstants;
import org.openstack.keystone.client.endpoints.IdentityFault;
import org.openstack.keystone.client.endpoints.ItemNotFoundFault;
import org.openstack.keystone.client.endpoints.UnauthorizedFault;
import org.openstack.keystone.client.endpoints.UserDisabledFault;
import org.openstack.keystone.client.fault.KeystoneFault;
import org.openstack.keystone.client.faults.BadRequestFault;
import org.openstack.keystone.client.faults.ForbiddenFault;
import org.openstack.keystone.client.faults.ServiceUnavailableFault;
import org.openstack.keystone.client.faults.TenantConflictFault;

public class KeystoneResponseWrapper {
    private static final Log logger = LogFactory.getLog(KeystoneResponseWrapper.class);

    public static KeystoneFault buildFaultMessage(ClientResponse response) throws KeystoneFault {
        if (response != null) {
            logger.info("ResponseWrapper, response status code is: " + response.getStatus());
            try {
                if (response.getStatus() == KeystoneConstants.BAD_REQUEST) {
                    BadRequestFault e = response.getEntity(BadRequestFault.class);
                    return new KeystoneFault(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeystoneConstants.UNAUTHORIZED) {
                    UnauthorizedFault e = response.getEntity(UnauthorizedFault.class);
                    return new KeystoneFault(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeystoneConstants.FORBIDDEN) {
                    ForbiddenFault e = response.getEntity(ForbiddenFault.class);
                    if (e != null) {
                        return new KeystoneFault(e.getMessage(), e.getDetails(), e.getCode());
                    } else {
                        UserDisabledFault udf = response.getEntity(UserDisabledFault.class);
                        return new KeystoneFault(udf.getMessage(), udf.getDetails(), udf.getCode());
                    }
                }
                if (response.getStatus() == KeystoneConstants.NOT_FOUND) {
                    ItemNotFoundFault e = response.getEntity(ItemNotFoundFault.class);
                    return new KeystoneFault(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeystoneConstants.NOT_PERMITTED) {
                    return new KeystoneFault("Operation not allowed", "The requested resource or operation could not be found", KeystoneConstants.NOT_PERMITTED);
                }
                if (response.getStatus() == KeystoneConstants.NAME_CONFLICT) {
                    TenantConflictFault e = response.getEntity(TenantConflictFault.class);
                    return new KeystoneFault(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeystoneConstants.SERVICE_UNAVAILABLE) {
                    ServiceUnavailableFault e = response.getEntity(ServiceUnavailableFault.class);
                    return new KeystoneFault(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeystoneConstants.NOT_IMPLMENTED) {
                    BadRequestFault e = response.getEntity(BadRequestFault.class);
                    return new KeystoneFault(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeystoneConstants.AUTH_FAULT) {
                    IdentityFault e = response.getEntity(IdentityFault.class);
                    return new KeystoneFault(e.getMessage(), e.getDetails(), e.getCode());
                }
            } catch (Exception ex) {
                logger.error("Exception was thrown for response with status: " + response.getStatus());
                if (ex instanceof UnsupportedOperationException) {
                    throw new KeystoneFault("There was an error communicating with the auth service...", ex.getLocalizedMessage(), response.getStatus());
                }
                logger.error("Exception was thrown and could not be handled by the client. Response status code: " + response.getStatus());
                throw new KeystoneFault(ex.getMessage(), ex.getLocalizedMessage(), response.getStatus());
            }
        }
        return new KeystoneFault("Unable to process request.", "There was an unexpected error communicating with the auth service.", KeystoneConstants.AUTH_FAULT);
    }
}

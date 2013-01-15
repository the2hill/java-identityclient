package org.openstack.identity.client.common.util;

import org.w3c.dom.Node;

import javax.xml.bind.*;
import java.io.StringWriter;

public class ResourceUtil {
    public static StringWriter marshallResource(JAXBElement element, JAXBContext context) throws JAXBException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
        StringWriter writer = new StringWriter();
        marshaller.marshal(element, writer);
        return writer;
    }

    public static JAXBElement unmarshallResource(Object content, JAXBContext context) throws JAXBException {
        Unmarshaller um = context.createUnmarshaller();
        return (JAXBElement) um.unmarshal((Node) content);
    }
}

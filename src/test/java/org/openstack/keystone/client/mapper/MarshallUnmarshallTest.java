package org.openstack.keystone.client.mapper;

import org.junit.Test;
import org.openstack.keystone.client.common.util.ResourceUtil;
import org.openstack.keystone.client.entity.TestUser;
import org.openstack.keystone.client.group.Group;
import org.openstack.keystone.client.group.GroupList;
import org.openstack.keystone.client.group.ObjectFactory;

import javax.xml.bind.JAXBContext;

import static junit.framework.Assert.assertEquals;

public class MarshallUnmarshallTest {
    private static TestUser testUser;

    @Test
    public void shouldMarshallGroup() throws Exception {
        ObjectFactory factory = new ObjectFactory();
        Group group = factory.createGroup();
        GroupList groupList = factory.createGroupList();
        group.setId("234");
        group.setName("bobBuilding");
        group.setDescription("desc");
        groupList.getGroup().add(group);

        String result = ResourceUtil.marshallResource(factory.createGroups(groupList),
                JAXBContext.newInstance(GroupList.class)).toString();
        System.out.print(result);

        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<groups xmlns:ns2=\"http://www.w3.org/2005/Atom\" xmlns=\"http://docs.rackspace.com/identity/api/ext/RAX-KSGRP/v1.0\">\n" +
                        "    <group name=\"bobBuilding\" id=\"234\">\n" +
                        "        <description>desc</description>\n" +
                        "    </group>\n" +
                        "</groups>", result.trim());
    }
}


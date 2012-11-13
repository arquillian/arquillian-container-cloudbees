package org.jboss.arquillian.container.cloudbees.client;

import org.jboss.arquillian.container.cloudbees.server.CloudbeesRemoteExtension;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class ArchiveAppender implements AuxiliaryArchiveAppender {
    @Override
    public Archive<?> createAuxiliaryArchive() {
        return ShrinkWrap.create(JavaArchive.class, "arquillian-cloudbees.jar")
                .addPackage(CloudbeesRemoteExtension.class.getPackage())
                .addAsServiceProvider(RemoteLoadableExtension.class, CloudbeesRemoteExtension.class);
    }
}

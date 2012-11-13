package org.jboss.arquillian.container.cloudbees.client;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.core.spi.LoadableExtension;

/**
 * Cloudbees extension
 *
 * @author <a href="mailto:alexis@sewatech.org">Alexis Hassler</a>
 */
public class CloudbeesExtension implements LoadableExtension {

    @Override
    public void register(ExtensionBuilder builder) {
        builder.service(DeployableContainer.class, CloudbeesContainer.class);
        builder.service(AuxiliaryArchiveAppender.class, ArchiveAppender.class);
    }
}

package org.jboss.arquillian.container.cloudbees.server;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.test.spi.TestEnricher;

/**
 * Cloudbees server-side extension
 *
 * @author <a href="mailto:alexis@sewatech.org">Alexis Hassler</a>
 */
public class CloudbeesRemoteExtension implements RemoteLoadableExtension {

    @Override
    public void register(LoadableExtension.ExtensionBuilder builder) {
        builder.service(TestEnricher.class, CloudbeesEJBInjectionEnricher.class);
    }
}

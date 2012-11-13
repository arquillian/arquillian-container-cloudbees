package org.jboss.arquillian.container.cloudbees.client;

import java.util.logging.Logger;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.deployment.Validate;
import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.HTTPContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.spi.client.protocol.metadata.Servlet;
import org.jboss.arquillian.container.spi.context.annotation.ContainerScoped;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.spi.ServiceLoader;
import org.jboss.arquillian.protocol.servlet.ServletMethodExecutor;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptor;

/**
 * Cloudbees container.
 *
 * <p> See {@link CloudbeesConfiguration} for required configuration </p>
 *
 * @author <a href="mailto:alexis@sewatech.org">Alexis Hassler</a>
 *
 */
public class CloudbeesContainer implements DeployableContainer<CloudbeesConfiguration> {

    private static final Logger log = Logger.getLogger(CloudbeesContainer.class.getName());
    @Inject @ContainerScoped
    private InstanceProducer<CloudbeesConfiguration> configurationProducer;
    @Inject
    private Instance<ServiceLoader> serviceLoader;
    
    private CloudbeesClient cloudbees;

    @Override
    public ProtocolDescription getDefaultProtocol() {
        return new ProtocolDescription("Servlet 3.0");
    }

    @Override
    public Class<CloudbeesConfiguration> getConfigurationClass() {
        return CloudbeesConfiguration.class;
    }

    @Override
    public void setup(CloudbeesConfiguration configuration) {
        this.configurationProducer.set(configuration);
    }

    @Override
    public void start() throws LifecycleException {
        log.info("Start");
        CloudbeesConfiguration configuration = configurationProducer.get();
        cloudbees = new CloudbeesClient(configuration);
    }

    @Override
    public void stop() throws LifecycleException {
        log.info("Stop");
    }

    @Override
    public void deploy(Descriptor descriptor) throws DeploymentException {
        throw new UnsupportedOperationException("deploy with descriptor not implemented");
    }

    @Override
    public void undeploy(Descriptor descriptor) throws DeploymentException {
        throw new UnsupportedOperationException("undeploy with descriptor not implemented");
    }

    @Override
    public ProtocolMetaData deploy(Archive<?> archive) throws DeploymentException {
        log.info("Deploy archive of type " + archive.getClass().getSimpleName());
        Validate.notNull(archive, "Archive must not be null");
        if (archive instanceof EnterpriseArchive) {
            throw new IllegalArgumentException("Enterprise Archive is not supported for the Cloudbees container");
        }
        
        CloudbeesConfiguration configuration = configurationProducer.get();

        String appId = configuration.getAppId();
        String archivePath = ShrinkWrapUtil.toURL(archive).getPath();
        if (archive instanceof WebArchive) {
            cloudbees.deployWar(appId, "Test war from Arquillian", archivePath, configuration.getContainerType());
            return buildWarMetadata(configuration);
        } else if (archive instanceof EnterpriseArchive) {
            cloudbees.deployEar(appId, "Test ear from Arquillian", archivePath, configuration.getContainerType());
            return buildEarMetadata(configuration);
        } else {
            throw new UnsupportedOperationException("undeploy with descriptor not implemented");            
        }

    }

    @Override
    public void undeploy(Archive<?> archive) throws DeploymentException {
        log.info("Undeploy archive");
        CloudbeesConfiguration configuration = configurationProducer.get();
        cloudbees.delete(configuration.getAppId());
    }

    private ProtocolMetaData buildWarMetadata(CloudbeesConfiguration conf) {
        ProtocolMetaData metaData = new ProtocolMetaData();
        HTTPContext context = new HTTPContext(conf.getHostName(), 80);
        context.add(new Servlet(ServletMethodExecutor.ARQUILLIAN_SERVLET_NAME, ""));
        metaData.addContext(context);
        return metaData;
    }
    private ProtocolMetaData buildEarMetadata(CloudbeesConfiguration conf) {
        ProtocolMetaData metaData = new ProtocolMetaData();
        HTTPContext context = new HTTPContext(conf.getHostName(), 80);
        context.add(new Servlet(ServletMethodExecutor.ARQUILLIAN_SERVLET_NAME, "test.war"));
        metaData.addContext(context);
        return metaData;
    }
}

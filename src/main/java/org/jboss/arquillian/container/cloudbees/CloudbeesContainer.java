package org.jboss.arquillian.container.cloudbees;

import com.cloudbees.api.ApplicationDeployArchiveResponse;
import com.cloudbees.api.BeesClient;
import java.util.HashMap;
import java.util.Map;
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
    private InstanceProducer<CloudbeesConfiguration> configuration;
    @Inject
    private Instance<ServiceLoader> serviceLoader;

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
        this.configuration.set(configuration);
    }

    @Override
    public void start() throws LifecycleException {
        log.info("Start");
    }

    @Override
    public void stop() throws LifecycleException {
        log.info("Stop");
        CloudbeesConfiguration conf = configuration.get();
        cloudbees.delete(conf.getAppId());
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
        log.info("Deploy archive");
        Validate.notNull(archive, "Archive must not be null");
        CloudbeesConfiguration conf = configuration.get();

        String appId = conf.getAppId();

        String archivePath = ShrinkWrapUtil.toURL(archive).getPath();
        BeesClient client = CloudbeesClientBuilder.build();

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("containerType", conf.getContainerType());
        try {
            ApplicationDeployArchiveResponse deployResponse = client.applicationDeployArchive(appId, null, "Test war from Arquillian", archivePath, null, "war", true, parameters, null);
        } catch (Exception ex) {
            throw new DeploymentException("Cannot deploy", ex);
        }

        ProtocolMetaData metaData = new ProtocolMetaData();
        HTTPContext context = new HTTPContext(conf.getHostName(), 80);
        context.add(new Servlet(ServletMethodExecutor.ARQUILLIAN_SERVLET_NAME, ""));
        metaData.addContext(context);

        return metaData;
    }

    @Override
    public void undeploy(Archive<?> archive) throws DeploymentException {
        log.info("Undeploy archive");
        CloudbeesConfiguration conf = configuration.get();
    }
}
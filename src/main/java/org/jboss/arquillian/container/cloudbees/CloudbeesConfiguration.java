package org.jboss.arquillian.container.cloudbees;

import org.jboss.arquillian.container.spi.ConfigurationException;
import org.jboss.arquillian.container.spi.client.container.ContainerConfiguration;

/**
 * @author <a href="mailto:alexis@sewatech.org">Alexis Hassler</a>
 * 
 * @version $Revision: $
 */
public class CloudbeesConfiguration implements ContainerConfiguration {

    public void validate() throws ConfigurationException {
        
    }

    String getHostName() {
        return "test.sewatech.cloudbees.net";
    }
    
    String getAccount() {
        return "sewatech";
    }
    String getApplication() {
        return "arq-test";
    }
    String getAppId() {
        return getAccount() + "/" +getApplication();
    }
    String getContainerType() {
        return "jboss";
    }
}
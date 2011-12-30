package org.jboss.arquillian.container.cloudbees;

import org.jboss.arquillian.container.spi.ConfigurationException;
import org.jboss.arquillian.container.spi.client.container.ContainerConfiguration;

/**
 * @author <a href="mailto:alexis@sewatech.org">Alexis Hassler</a>
 * 
 * @version $Revision: $
 */
public class CloudbeesConfiguration implements ContainerConfiguration {

    private String account = "sewatech";
    private String application = "arq-test";
    private String containerType = "jboss";
    
    public void validate() throws ConfigurationException {
        
    }

    String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
    
    String getContainerType() {
        return containerType;
    }
    
    String getAppId() {
        return getAccount() + "/" +getApplication();
    }
    String getHostName() {
        return getApplication() + "." + getAccount() + ".cloudbees.net";
    }
    
}
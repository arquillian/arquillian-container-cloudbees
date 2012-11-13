package org.jboss.arquillian.container.cloudbees.client;

import org.jboss.arquillian.container.spi.ConfigurationException;
import org.jboss.arquillian.container.spi.client.container.ContainerConfiguration;
import org.jboss.arquillian.container.spi.client.deployment.Validate;

/**
 * @author <a href="mailto:alexis@sewatech.org">Alexis Hassler</a>
 * 
 * @version $Revision: $
 */
public class CloudbeesConfiguration implements ContainerConfiguration {

    private String account;
    private String application = "arqtest";
    private String containerType = "jboss";
    private String apiUrl = "https://api.cloudbees.com/api";
    private String propertiesFile = System.getProperty("user.home") + "/.cloudbees/cloudbees-api.properties";
    
    public void validate() throws ConfigurationException {
        Validate.notNullOrEmpty(account,
                "Cloudbees account must be specified, please fill in \"account\" property in Arquillian configuration");
        
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
    
    public String getContainerType() {
        return containerType;
    }
    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }
    
    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }
    
    String getAppId() {
        return getAccount() + "/" +getApplication();
    }
    String getHostName() {
        return getApplication() + "." + getAccount() + ".cloudbees.net";
    }

   
}
package org.jboss.arquillian.container.cloudbees;

import com.cloudbees.api.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;

/**
 *
 * Proxy to the real BeesClient. Make more easy to support API changes and make
 * the API a bit more fluent
 *
 * @author alexis
 */
public class CloudbeesClient {

    
    private BeesClient client;

    CloudbeesClient(CloudbeesConfiguration configuration) {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(configuration.getPropertiesFile()));

            client = new BeesClient(configuration.getApiUrl(),
                                    properties.getProperty("key"),
                                    properties.getProperty("secret"),
                                    "xml", "1.0");
            client.setVerbose(false);
        } catch (IOException ex) {
            throw new RuntimeException("Fichier properties non trouvé", ex);
        }
    }

    void deployWar(String appId, String description, String archivePath, String containerType) throws DeploymentException {
        deployArchive(appId, description, archivePath, ArchiveType.WAR, containerType);
    }
    void deployEar(String appId, String description, String archivePath, String containerType) throws DeploymentException {
        deployArchive(appId, description, archivePath, ArchiveType.EAR, containerType);
    }

    private void deployArchive(String appId, String description, String archivePath, ArchiveType archiveType, String containerType) throws DeploymentException {
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("containerType", containerType);
            boolean deltaUpload = true;
            client.applicationDeployArchive(appId, null, description, archivePath, null, archiveType.toString(), deltaUpload, parameters, null);
        } catch (Exception ex) {
            throw new DeploymentException("Cannot deploy app " + appId, ex);
        }
    }

    void delete(String appId) throws DeploymentException {
        try {
//            client.applicationDelete(appId);
        } catch (Exception ex) {
            throw new DeploymentException("Cannot delete app " + appId, ex);
        }
    }
    boolean isExisting(String appId) throws LifecycleException {
        try {
            ApplicationListResponse applicationList = client.applicationList();
            for (ApplicationInfo applicationInfo : applicationList.getApplications()) {
                if (applicationInfo.getId().equals(appId)) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            throw new LifecycleException("", ex);
        }
    }

    private enum ArchiveType {

        WAR("war"), EAR("ear");
        
        private String textValue;

        private ArchiveType(String textValue) {
            this.textValue = textValue;
        }

        @Override
        public String toString() {
            return textValue;
        }
    }
}

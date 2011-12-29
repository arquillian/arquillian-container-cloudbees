package org.jboss.arquillian.container.cloudbees;

import com.cloudbees.api.BeesClient;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author <a href="mailto:alexis@sewatech.org">Alexis Hassler</a>
 */
public class CloudbeesClientBuilder {

    public static BeesClient build() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(System.getProperty("user.home") + "/.cloudbees/cloudbees-api.properties"));
            
            BeesClient client = new BeesClient(properties.getProperty("url"),
                                               properties.getProperty("key"),
                                               properties.getProperty("secret"),
                                               "xml", "1.0");
            client.setVerbose(false);

            return client;
        } catch (IOException ex) {
            throw new RuntimeException("Fichier properties non trouvé", ex);
        }
    }
}

package org.jboss.arquillian.container.cloudbees;

import java.net.URL;
import org.jboss.arquillian.container.cloudbees.web.Servlet1;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static  org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ServletAsClientWarTest {

    @Deployment(testable = false)
    public static WebArchive createWarDeployment() throws Exception {
        return ShrinkWrap.create(WebArchive.class)
                         .addClass(Servlet1.class);
    }

    @Test @RunAsClient
    public void shouldRootBeInjected(@ArquillianResource URL baseURL) throws Exception {
        assertNotNull("Root URL has not been injected", baseURL);
    }

    @Test @RunAsClient @Ignore
    public void shouldServletBeInjected(@ArquillianResource(Servlet1.class) URL servletURL) throws Exception {
        assertNotNull("Servlet URL has not been injected", servletURL);
    }
}

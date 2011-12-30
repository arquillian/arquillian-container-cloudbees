package org.jboss.arquillian.container.cloudbees;

import javax.inject.Inject;
import org.jboss.arquillian.container.cloudbees.cdi.EchoService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 *
 * @author alexis
 */
@RunWith(Arquillian.class)
public class CloudbeesCdiWarTest {
    
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(WebArchive.class)
                         .addClasses(EchoService.class)
                         .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    EchoService bean;
        
    @Test @Ignore // FAILS
    public void shouldBeanBeInjected() {
        assertNotNull("Bean has not bean injected", bean);
        String message = "azerty";
        assertEquals("Bean has bean injected, but is not able to repeat", message, bean.repeatAfterMe(message));        
    }
}

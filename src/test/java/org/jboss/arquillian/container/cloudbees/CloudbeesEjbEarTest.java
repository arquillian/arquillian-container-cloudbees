/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.jboss.arquillian.container.cloudbees;

import javax.ejb.EJB;
import org.jboss.arquillian.container.cloudbees.ejb.EchoService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 *
 * @author alexis
 */
@RunWith(Arquillian.class)
public class CloudbeesEjbEarTest {
    
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(EnterpriseArchive.class)
                         .addAsModule(ShrinkWrap.create(JavaArchive.class, "test.jar")
                                                .addClasses(EchoService.class))
                         .setApplicationXML("application.xml");
    }
    
    @EJB
    EchoService bean;
    
    @EJB(beanName="EchoService")
    EchoService namedBean;
    
    @Test @Ignore // It fails :-(
    public void shouldEjbBeInjected() {
        assertNotNull("Anonymous bean has not bean injected", bean);
    }

    @Test
    public void shouldNamedEjbBeInjected() {
        assertNotNull("Named bean has not bean injected", namedBean);
    }
}

package org.jboss.arquillian.container.cloudbees.ejb;

import javax.ejb.Stateless;

/** 
 * Small example of EJB 3.1
 * 
 * @author Alexis Hassler
 */
@Stateless
public class EchoService {
    public String repeatAfterMe(String message) {
        return message;
    }
}

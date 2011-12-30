package org.jboss.arquillian.container.cloudbees.cdi;

/** 
 * Small example of CDI 1.0 bean
 * 
 * @author Alexis Hassler
 */
public class EchoService {
    public String repeatAfterMe(String message) {
        return message;
    }
}

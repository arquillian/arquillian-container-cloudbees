package org.jboss.arquillian.container.cloudbees.server;

import org.jboss.arquillian.core.spi.Validate;
import org.jboss.arquillian.testenricher.ejb.EJBInjectionEnricher;

import java.text.MessageFormat;
import java.util.logging.Logger;

public class CloudbeesEJBInjectionEnricher extends EJBInjectionEnricher {
    private static final Logger log = Logger.getLogger(CloudbeesEJBInjectionEnricher.class.getName());

    @Override
    protected String[] resolveJNDINames(Class<?> fieldType, String mappedName, String beanName) {
        log.info("My own enricher");
        MessageFormat msg = new MessageFormat(
                "Trying to resolve JNDI name for field \"{0}\" with mappedName=\"{1}\" and beanName=\"{2}\"");
        log.finer(msg.format(new Object[]
                {fieldType, mappedName, beanName}));

        Validate.notNull(fieldType, "EJB enriched field cannot to be null.");

        boolean isMappedNameSet = hasValue(mappedName);
        boolean isBeanNameSet = hasValue(beanName);

        if (isMappedNameSet && isBeanNameSet)
        {
            throw new IllegalStateException(
                    "@EJB annotation attributes 'mappedName' and 'beanName' cannot be specified at the same time.");
        }

        String[] jndiNames;

        // If set, use only mapped name or bean name to lookup the EJB.
        if (isMappedNameSet)
        {
            jndiNames = new String[]
                    {mappedName};
        }
        else if (isBeanNameSet)
        {
            jndiNames = new String[]
                    {"java:module/" + beanName + "!" + fieldType.getName()};
        }
        else
        {
            jndiNames = new String[] {
                    "java:global/app/webapp/" + fieldType.getSimpleName()
                };
        }
        return jndiNames;
    }
    private boolean hasValue(String value) {
        if (value != null && (!value.trim().equals(""))) {
            return true;
        } else {
            return false;
        }
    }

}

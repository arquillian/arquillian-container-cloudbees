Arquillian Container for Cloudbees
==================================

Configuration example :

```xml
    <container qualifier="cloudbees" default="true">
        <configuration>
            <property name="account">sewatech</property>
            <property name="application">arqtest</property>
            <property name="containerType">jboss</property>
        </configuration>
    </container>
```

API URL, key and secret are in `~/.cloudbees/cloudbees-api.properties`.

CDI is working when deployed in a jar file. Not in a war or ear.

JAX-RS as-client is working on my example.

EJB is working only when specifying beanName in @EJB :
```
    @EJB(beanName="Greeter")
    Greeter greeter;
```
It does not work with a simple @EJB
```
    @EJB
    Greeter greeter;
```

The EJB enricher does not work because the JNDI names in Cloudbees are not compliant with Arquillian's lookup.

  * EAR deployment is not supported yet.
  * Servlet URL injection is not supported yet.

Restrictions
============
Multiple `@Deployment` per test class does not work because only one archive is really deployed in an application.

Api archetype
===

This is a maven api archetype for dropwizard. The source files are in the `source` directory and were used to generate the `archetype` folder. 

To install, clone the project and do

```
cd archetype; mvn install
mvn archetype:generate
```

Or search for it using the `dropwizard` search using

```
mvn archetype:generate
```

## Features

- Dropwizard API set up with integration tests
- Logging and correlation ID header tracking (via the `X-Correlation-Id` header value)
- Service client using Retrofit bundled so you can easily distribute a java api client to your rest service
- Docker artifacts out of the box. Doing `mvn clean package` builds you a production ready docker container to ship
- Docker container is set up to enable debug ports if you pass in environment varialbes of `DEBUG_JAVA` (for port 1044 remote debug) and/or `DEBUG_JAVA_SUSPEND` (if set to `y` will suspend before debug)
- Dependency injection with Guice 4.0 bundled and pre-wired

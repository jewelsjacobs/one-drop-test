# One Drop Api Read Cache Test

# DON'T TRY TO RUN, WON'T WORK YET! :P

Microservices for the One Drop [interview code test](CHALLANGE.md)

Project is built using the [lagom Microservices](https://www.lagomframework.com/) framework for the following reasons:
1. Required to use [Scala](https://www.scala-lang.org/) and [Play Framework](https://www.playframework.com/). lagom incorporates both.
1. To consider the project requirements:
 - Separation of API reads to a separate persistent, fault tolerant, distributed datasource for cache storage
   - lagom uses [Event Sourcing](https://martinfowler.com/eaaDev/EventSourcing.html), and [CQRS](https://martinfowler.com/bliki/CQRS.html) patterns, [Akka](http://akka.io/), [Kafka](https://kafka.apache.org/) and a simple Message Broker API, and [Cassandra](http://cassandra.apache.org/). [All of lagom's component technologies](https://www.lagomframework.com/documentation/1.3.x/scala/ComponentTechnologies.html)
 - Ease of maintenance and deployment
   - lagom uses the [sbt builder](http://www.scala-sbt.org/), hot reloading in development, and automatic sandbox and production container based deployment with [ConductR](https://conductr.lightbend.com/)
 - Service discovery
   - lagom has [service discovery included](https://www.lagomframework.com/documentation/1.3.x/scala/ServiceDiscovery.html)
 
# Requirements

View the lagom [Introduction and prerequisites](https://www.lagomframework.com/documentation/1.3.x/scala/Installation.html#JDK) guide for requirements

# Install

- clone repo or download source
```
$ cd one-drop-test
$ sbt
```

# Run

```
$ cd one-drop-test
$ sbt
$ runAll
```

# References

## Lagom Sample Projects

### Service Discovery
- https://github.com/typesafehub/service-locator-dns
- https://github.com/jboner/lagom-service-locator-consul (out of date)
- https://github.com/jboner/lagom-service-locator-zookeeper (out of date)

### Twitter like app
https://github.com/purijatin/lagom-scala-chirper

### Descriptor Generator
https://github.com/lagom/sbt-lagom-descriptor-generator

## Learning
https://blog.codecentric.de/en/2017/02/cqrs-event-sourcing-lagom/
https://www.lagomframework.com/documentation/1.3.x/java/ReadSide.html
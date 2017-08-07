package com.onedrop.github.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.onedrop.github.api.GithubService
import com.softwaremill.macwire._

class GithubLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new GithubApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new GithubApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[GithubService])
}

abstract class GithubApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[GithubService](wire[GithubServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = GithubSerializerRegistry


  lazy val github = serviceClient.implement[GithubService]

  // Register the github persistent entity
  persistentEntityRegistry.register(wire[GithubEntity])
}

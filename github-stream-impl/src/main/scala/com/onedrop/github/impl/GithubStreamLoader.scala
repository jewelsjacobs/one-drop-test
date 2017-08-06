package com.onedrop.github.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.onedrop.github.api.{GithubService, GithubStreamService}
import com.softwaremill.macwire._

class GithubStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new GithubStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new GithubStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[GithubStreamService])
}

abstract class GithubStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[GithubStreamService](wire[GithubStreamServiceImpl])

  // Bind the GithubService client
  lazy val GithubService = serviceClient.implement[GithubService]
}

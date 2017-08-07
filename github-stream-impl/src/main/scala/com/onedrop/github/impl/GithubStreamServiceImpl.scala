package com.onedrop.github.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.onedrop.github.api.{GithubService, GithubStreamService}

import scala.concurrent.Future

/**
  * Implementation of the GithubStreamService.
  */
class GithubStreamServiceImpl(githubService: GithubService) extends GithubStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(githubService.hello(_).invoke()))
  }
}

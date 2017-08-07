package com.onedrop.github.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import com.lightbend.lagom.scaladsl.api.transport.Method

trait GithubService extends Service {

  def getOrg(org: String): ServiceCall[NotUsed, Seq[Org]]
  def getMembers(org: String): ServiceCall[NotUsed, Seq[Member]]
  def getRepos(org: String): ServiceCall[NotUsed, Seq[Repo]]

  def descriptor = {
    import Service._
    // @formatter:off
    named("github").withCalls(
      restCall(Method.GET,   "/orgs/:org",         getOrg _),
      restCall(Method.GET,    "/orgs/:org/members", getMembers _),
      restCall(Method.GET,    "/orgs/:org/members", getStargazers _),
      restCall(Method.GET, "/orgs/:org/repos", getRepos _)
    )
    // @formatter:on
  }
}

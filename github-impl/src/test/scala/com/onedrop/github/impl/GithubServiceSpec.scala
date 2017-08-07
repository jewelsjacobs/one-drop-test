package com.onedrop.github.impl

import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import com.onedrop.github.api.{GithubService, GreetingMessage}
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}

class GithubServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  private val server = ServiceTest.startServer(
    ServiceTest.defaultSetup
      .withCassandra(true)
  ) { ctx =>
    new GithubApplication(ctx) with LocalServiceLocator
  }

  val client = server.serviceClient.implement[GithubService]

  override protected def afterAll() = server.stop()

  "github service" should {

    "say hello" in {
      client.hello("Alice").invoke().map { answer =>
        answer should ===("Hello, Alice!")
      }
    }

    "allow responding with a custom message" in {
      for {
        _ <- client.useGreeting("Bob").invoke(GreetingMessage("Hi"))
        answer <- client.hello("Bob").invoke()
      } yield {
        answer should ===("Hi, Bob!")
      }
    }
  }
}

package com.onedrop.github.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import com.onedrop.github
import com.onedrop.github.api.GithubService

/**
  * Implementation of the GithubService.
  */
class GithubServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends GithubService {

  override def getOrg(org: String) = ServiceCall { _ =>
    // Look up the github entity for the given ID.
    val ref = persistentEntityRegistry.refFor[GithubEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id))
  }

  override def getMembers(org: String) = ServiceCall { request =>
    // Look up the github entity for the given ID.
    val ref = persistentEntityRegistry.refFor[GithubEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }


  override def getRepos(org: String): Topic[github.api.GreetingMessageChanged] =
    TopicProducer.singleStreamWithOffset {
      fromOffset =>
        persistentEntityRegistry.eventStream(GithubEvent.Tag, fromOffset)
          .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(helloEvent: EventStreamElement[GithubEvent]): github.api.GreetingMessageChanged = {
    helloEvent.event match {
      case GreetingMessageChanged(msg) => github.api.GreetingMessageChanged(helloEvent.entityId, msg)
    }
  }
}

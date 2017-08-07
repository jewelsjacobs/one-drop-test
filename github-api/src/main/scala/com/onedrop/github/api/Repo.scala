package com.onedrop.github.api

import play.api.libs.json.Json

import scala.collection.immutable.Seq

case class Repo (userId: String, name: String, friends: Seq[String]) {
  def this(userId: String, name: String) = this(userId, name, Seq.empty)
}

object Repo {
  implicit val repoJson = Json.format[Repo]
}

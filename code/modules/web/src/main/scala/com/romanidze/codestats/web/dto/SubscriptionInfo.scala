package com.romanidze.codestats.web.dto

import tethys._
import tethys.jackson._

case class SubscriptionInfo(username: String)

object SubscriptionInfo {

  implicit val reader: JsonReader[SubscriptionInfo] = JsonReader.builder
    .addField[String]("login")
    .buildReader(SubscriptionInfo.apply)

}

package com.romanidze.codestats.web.client

import tethys._
import tethys.derivation.semiauto._

case class ClientError(message: String, description: Option[String], traceback: Option[String])

object ClientError {

  implicit val reader: JsonReader[ClientError] = jsonReader[ClientError]
  implicit val writer: JsonWriter[ClientError] = jsonWriter[ClientError]

}

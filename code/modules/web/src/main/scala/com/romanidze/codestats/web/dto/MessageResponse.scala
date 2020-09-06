package com.romanidze.codestats.web.dto

import tethys._
import tethys.derivation.semiauto._

case class MessageResponse(message: String)

object MessageResponse {
  implicit val writer: JsonWriter[MessageResponse] = jsonWriter[MessageResponse]
}

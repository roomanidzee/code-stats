package com.romanidze.codestats.web

import tethys._
import tethys.jackson._
import tethys.JsonReader
import tethys.readers.ReaderError
import java.nio.ByteBuffer

import com.typesafe.scalalogging.LazyLogging
import monix.eval.Task
import monix.reactive.Observable
import sttp.client.{Request, Response, SttpBackend}
import sttp.client.asynchttpclient.WebSocketHandler

package object client extends LazyLogging {

  type MonixBackend = SttpBackend[Task, Observable[ByteBuffer], WebSocketHandler]
  type ClientRequest = Request[Either[String, String], Nothing]
  type ClientSimpleResponse = Task[Response[Either[String, String]]]

  def processBody[A: JsonReader](body: Either[String, String]): Either[ClientError, A] = {

    val errorMessage = "Error while processing response body"

    if (body.isLeft) {
      val messageLeft: String = body.swap.toOption.get
      println(messageLeft)
      Left(ClientError(errorMessage, Some(messageLeft), None))
    } else {

      val queryStatus: Either[ReaderError, A] = body.toOption.get.jsonAs[A]

      if (queryStatus.isLeft) {
        val errorLocalMessage: String = queryStatus.swap.toOption.get.getMessage
        println(errorLocalMessage)
        Left(ClientError(errorMessage, None, Some(errorLocalMessage)))
      }

      Right(queryStatus.toOption.get)
    }

  }

}

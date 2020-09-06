package com.romanidze.codestats.web.client

import com.romanidze.codestats.web.dto.{StarredRepositoryInfo, SubscriptionInfo, UserRepositoryInfo}
import monix.eval.Task
import org.asynchttpclient.{AsyncHttpClient, DefaultAsyncHttpClient}
import sttp.client._
import sttp.client.asynchttpclient.monix._

class MonixClientInterpreter(baseURL: String, httpClient: AsyncHttpClient)
    extends ClientInterpreter[Task] {

  def this(baseURL: String) = this(baseURL, new DefaultAsyncHttpClient())

  private implicit val backend: MonixBackend = AsyncHttpClientMonixBackend.usingClient(httpClient)

  override def getSubscriptions(
    username: String
  ): Task[Either[ClientError, List[SubscriptionInfo]]] = {

    val requestURL: String = s"${baseURL}/users/${username}/following"

    val request: ClientRequest = basicRequest
      .header("Accept", "application/json")
      .header("Content-Type", "application/json")
      .get(uri"${requestURL}")

    val response: ClientSimpleResponse = request.send()

    response.map(elem => processBody[List[SubscriptionInfo]](elem.body))

  }

  override def getUserRepositories(
    username: String,
    limit: Short,
    page: Short
  ): Task[Either[ClientError, List[UserRepositoryInfo]]] = {

    val requestURL: String = s"${baseURL}/users/${username}/repos?page=${page}&per_page=${limit}"

    val request: ClientRequest = basicRequest
      .header("Accept", "application/json")
      .header("Content-Type", "application/json")
      .get(uri"${requestURL}")

    val response: ClientSimpleResponse = request.send()

    response.map(elem => processBody[List[UserRepositoryInfo]](elem.body))

  }

  override def getStarredRepositories(
    username: String,
    limit: Short,
    page: Short
  ): Task[Either[ClientError, List[StarredRepositoryInfo]]] = {

    val requestURL: String = s"${baseURL}/users/${username}/starred?page=${page}&per_page=${limit}"

    val request: ClientRequest = basicRequest
      .header("Accept", "application/vnd.github.v3.star+json")
      .header("Content-Type", "application/vnd.github.v3.star+json")
      .get(uri"${requestURL}")

    val response: ClientSimpleResponse = request.send()

    response.map(elem => processBody[List[StarredRepositoryInfo]](elem.body))

  }

}

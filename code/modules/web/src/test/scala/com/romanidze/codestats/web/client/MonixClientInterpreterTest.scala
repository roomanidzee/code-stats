package com.romanidze.codestats.web.client

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.romanidze.codestats.web.config.ClientConfig
import com.romanidze.codestats.web.dto.{StarredRepositoryInfo, SubscriptionInfo, UserRepositoryInfo}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

class MonixClientInterpreterTest extends munit.FunSuite {

  private val wireMockServer = new WireMockServer(
    wireMockConfig()
      .port(9001)
      .usingFilesUnderClasspath("wiremock")
  )

  override def beforeAll(): Unit = wireMockServer.start()
  override def afterAll(): Unit = wireMockServer.stop()

  private val config = new ClientConfig("http://localhost:9001", "roomanidzee", 1, 100, "")
  private val client = new MonixClientInterpreter(config)

  test("get subscriptions") {

    val responseTask: Task[Either[ClientError, List[SubscriptionInfo]]] =
      client.getSubscriptions("roomanidzee")

    val responseEither: Either[ClientError, List[SubscriptionInfo]] = responseTask.runSyncUnsafe()

    assume(responseEither.isRight)

    val subscriptions: List[SubscriptionInfo] = responseEither.toOption.get

    assertEquals(subscriptions.size, 2)
    assertEquals(subscriptions.head.username, "zjffdu")
    assertEquals(subscriptions(1).username, "pshirshov")

  }

  test("retrieve repositories") {

    val responseTask: Task[Either[ClientError, List[UserRepositoryInfo]]] =
      client.getUserRepositories("roomanidzee")

    val responseEither: Either[ClientError, List[UserRepositoryInfo]] = responseTask.runSyncUnsafe()

    assume(responseEither.isRight)

    val repos: List[UserRepositoryInfo] = responseEither.toOption.get

    assertEquals(repos.size, 1)
    assertEquals(repos.head.language.get, "Java")

  }

  test("retrieve starred repositories") {

    val responseTask: Task[Either[ClientError, List[StarredRepositoryInfo]]] =
      client.getStarredRepositories("roomanidzee")

    val responseEither: Either[ClientError, List[StarredRepositoryInfo]] =
      responseTask.runSyncUnsafe()

    assume(responseEither.isRight)

    val starredRepos: List[StarredRepositoryInfo] = responseEither.toOption.get

    assertEquals(starredRepos.size, 1)
    assume(starredRepos.head.repo.hasIssues)

  }

}

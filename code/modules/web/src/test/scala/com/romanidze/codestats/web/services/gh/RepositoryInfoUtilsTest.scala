package com.romanidze.codestats.web.services.gh

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.romanidze.codestats.db.config.{DBConfig, DBInitializer}
import com.romanidze.codestats.db.repositories.OperationInfoRepository
import com.romanidze.codestats.web.client.{ClientError, MonixClientInterpreter}
import com.romanidze.codestats.web.dto.{StarredRepositoryInfo, SubscriptionInfo, UserRepositoryInfo}
import com.romanidze.codestats.web.services.db.OperationInfoService
import com.zaxxer.hikari.HikariDataSource
import doobie.util.transactor.Transactor.Aux
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

class RepositoryInfoUtilsTest extends munit.FunSuite {

  private val config = DBConfig("org.h2.Driver", s"jdbc:h2:mem:test;", "sa", "", 10, 1000, 10)

  private val transactor: Aux[Task, HikariDataSource] =
    DBInitializer.getTransactor(config)

  private val repository = new OperationInfoRepository(transactor)
  private val memoryService = new OperationInfoService(repository)

  private val wireMockServer = new WireMockServer(
    wireMockConfig()
      .port(9003)
      .usingFilesUnderClasspath("wiremock")
  )

  override def beforeAll(): Unit = wireMockServer.start()
  override def afterAll(): Unit = wireMockServer.stop()

  private val client = new MonixClientInterpreter("http://localhost:9003")
  private val repositoryInfoUtils = new RepositoryInfoUtils(client, memoryService)

  test("validate repos (left case)") {

    val result: Either[ClientError, (String, String)] =
      repositoryInfoUtils
        .validateRepos(Left(ClientError("test", None, None)), "test")
        .runSyncUnsafe()

    assume(result.isLeft)

  }

  test("validate repos (right case)") {

    val result: Either[ClientError, (String, String)] =
      repositoryInfoUtils
        .validateRepos(Right(Some(SubscriptionInfo("test"))), "test")
        .runSyncUnsafe()

    assume(result.isRight)

  }

  test("validate user repo input") {

    val result: Task[Either[ClientError, List[UserRepositoryInfo]]] =
      repositoryInfoUtils
        .validateUserRepoInput(Right(("roomanidzee", "user_repos")))

    result.map(elem => assume(elem.isRight))
    result.map(elem => {

      val repos: List[UserRepositoryInfo] = elem.toOption.get

      assertEquals(repos.size, 1)
      assertEquals(repos.head.language, "Java")

    })

  }

  test("validate starred repo input") {

    val result: Task[Either[ClientError, List[StarredRepositoryInfo]]] =
      repositoryInfoUtils
        .validateStarredRepoInput(Right(("roomanidzee", "starred_repos")))

    result.map(elem => assume(elem.isRight))

    result.map(elem => {

      val starredRepos: List[StarredRepositoryInfo] = elem.toOption.get

      assertEquals(starredRepos.size, 1)
      assume(starredRepos.head.repo.hasIssues)

    })

  }

}

package com.romanidze.codestats.web.services.gh

import java.nio.file.Paths

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.romanidze.codestats.db.config.{DBConfig, DBInitializer}
import com.romanidze.codestats.db.repositories.OperationInfoRepository
import com.romanidze.codestats.web.client.ClientError
import com.romanidze.codestats.web.config.ClientConfig
import com.romanidze.codestats.web.dto.{StarredRepositoryInfo, UserRepositoryInfo}
import com.romanidze.codestats.web.services.db.OperationInfoService
import com.zaxxer.hikari.HikariDataSource
import doobie.util.transactor.Transactor.Aux
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

class GHClientServiceTest extends munit.FunSuite {

  private val schemaFilePath =
    Paths.get("modules", "db", "src", "main", "resources", "schema.sql").toRealPath()

  private val testDataFilePath =
    Paths.get("modules", "db", "src", "test", "resources", "data.sql").toRealPath()

  private val config = DBConfig("org.h2.Driver", s"jdbc:h2:mem:test;", "sa", "", 10, 1000, 10)

  private val transactor: Aux[Task, HikariDataSource] =
    DBInitializer.getTransactor(config)

  private val repository = new OperationInfoRepository(transactor)
  private val memoryService = new OperationInfoService(repository)

  private val ghClientService =
    new GHClientService(ClientConfig("http://localhost:9002", "roomanidzee"), memoryService)

  private val wireMockServer = new WireMockServer(
    wireMockConfig()
      .port(9002)
      .usingFilesUnderClasspath("wiremock")
  )

  override def beforeAll(): Unit = wireMockServer.start()
  override def afterAll(): Unit = wireMockServer.stop()

  test("retrieve user repositories".ignore) {

    val userRepositories: Either[ClientError, List[UserRepositoryInfo]] =
      ghClientService
        .getUserRepositories("roomanidzee")
        .runSyncUnsafe()

    assume(userRepositories.isRight)

    val repos: List[UserRepositoryInfo] = userRepositories.toOption.get

    assertEquals(repos.size, 1)
    assertEquals(repos.head.language, "Java")

  }

  test("retrieve starred repositories".ignore) {

    val responseEither: Either[ClientError, List[StarredRepositoryInfo]] =
      ghClientService
        .getStarredRepositories("roomanidzee")
        .runSyncUnsafe()

    assume(responseEither.isRight)

    val starredRepos: List[StarredRepositoryInfo] = responseEither.toOption.get

    assertEquals(starredRepos.size, 1)
    assume(starredRepos.head.repo.hasIssues)

  }

}

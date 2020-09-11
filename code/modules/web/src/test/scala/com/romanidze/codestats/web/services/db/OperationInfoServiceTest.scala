package com.romanidze.codestats.web.services.db

import java.nio.file.Paths

import com.romanidze.codestats.db.config.{DBConfig, DBInitializer}
import com.romanidze.codestats.db.repositories.OperationInfoRepository
import com.zaxxer.hikari.HikariDataSource
import doobie.util.transactor.Transactor.Aux
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

class OperationInfoServiceTest extends munit.FunSuite {

  private val schemaFilePath =
    Paths.get("modules", "db", "src", "main", "resources", "schema.sql").toRealPath()

  private val testDataFilePath =
    Paths.get("modules", "db", "src", "test", "resources", "data.sql").toRealPath()

  private val config = DBConfig(
    "org.h2.Driver",
    s"jdbc:h2:mem:test;INIT=RUNSCRIPT FROM '${schemaFilePath.toString}'\\;" +
    s"RUNSCRIPT FROM '${testDataFilePath.toString}'",
    "sa",
    "",
    10,
    1000,
    10
  )

  private val transactor: Aux[Task, HikariDataSource] =
    DBInitializer.getTransactor(config)

  private val repository = new OperationInfoRepository(transactor)

  private val service = new OperationInfoService(repository)

  test("get info by username and operation") {

    val selectResult: (Short, Short) = service
      .getByUsernameAndOperation("test_3", "test_3")
      .runSyncUnsafe()

    assertEquals(selectResult, (4: Short, 103: Short))

  }

}

package com.romanidze.codestats.db

import java.nio.file.Paths

import com.romanidze.codestats.db.config.{DBConfig, DBInitializer}
import com.romanidze.codestats.db.domain.{OperationInfo, OperationInsertInfo}
import com.romanidze.codestats.db.repositories.OperationInfoRepository
import doobie.hikari.HikariTransactor
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

class OperationInfoRepositoryTest extends munit.FunSuite {

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

  private val transactor: HikariTransactor[Task] =
    DBInitializer.getTransactor(config).runSyncUnsafe()

  private val repository = new OperationInfoRepository(transactor)

  test("select all records") {

    val selectResult: List[OperationInfo] = repository.getAll.runSyncUnsafe()

    assertEquals(selectResult.size, 5)

  }

  test("select by username and operation") {

    val selectResult: List[OperationInfo] =
      repository
        .getByUserAndOperation("test_3", "test_3")
        .runSyncUnsafe();

    assertEquals(selectResult.size, 1)

    val operationInfo: OperationInfo = selectResult.head

    assertEquals(operationInfo.id, 3L)
    assertEquals(operationInfo.username, "test_3")
    assertEquals(operationInfo.operation, "test_3")
    assertEquals(operationInfo.pageValue, 3: Short)
    assertEquals(operationInfo.limitValue, 3: Short)

  }

  test("record insert") {

    val operationInfo = OperationInsertInfo("test_5", "test_3", 10, 10)

    val insertResult: List[Long] = repository.insert(operationInfo).runSyncUnsafe()

    assertEquals(insertResult.size, 1)

    val selectResult: List[OperationInfo] = repository.getAll.runSyncUnsafe()

    assertEquals(selectResult.size, 6)

  }

}

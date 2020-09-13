package com.romanidze.codestats.web.services.parquet

import com.romanidze.codestats.protobuf.Definition.GitHubInfoRecord
import com.romanidze.codestats.web.config.ParquetConfig
import monix.execution.Scheduler.Implicits.global

class ParquetWriterServiceTest extends munit.FunSuite {

  test("should write records") {

    val testRecord: GitHubInfoRecord = GitHubInfoRecord
      .newBuilder()
      .setGhRequestUser("test")
      .setRecordCreatedTime(0L)
      .setName("test")
      .setIsStarred(true)
      .setStarredAt(0L)
      .setOwnerLogin("test")
      .setOwnerType("test")
      .setFork(true)
      .setCreatedAt(0L)
      .setUpdatedAt(0L)
      .setPushedAt(0L)
      .setSize(1)
      .setStargazersCount(1)
      .setWatchersCount(1)
      .setLanguage("test")
      .setHasIssues(true)
      .setHasProjects(true)
      .setHasDownloads(true)
      .setHasWiki(true)
      .setHasPages(true)
      .setForksCount(10)
      .setArchived(true)
      .setDisabled(true)
      .setOpenIssues(10)
      .build()

    val parquetWriterService = new ParquetWriterService(ParquetConfig("target"))

    val result: Long = parquetWriterService
      .writeRecords("test", List(testRecord))
      .runSyncUnsafe()

    assertEquals(result, 1L)

  }

}

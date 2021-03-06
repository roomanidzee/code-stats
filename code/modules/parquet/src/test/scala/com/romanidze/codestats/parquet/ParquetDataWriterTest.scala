package com.romanidze.codestats.parquet

import java.nio.file.{Files, Paths}

import com.romanidze.codestats.protobuf.Definition.GitHubInfoRecord
import monix.execution.Scheduler.Implicits.global

class ParquetDataWriterTest extends munit.FunSuite {

  test("data write for github records") {

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

    val result: Long = ParquetDataWriter
      .writeAggregatedData("target/test.parquet", List(testRecord))
      .runSyncUnsafe()

    assertEquals(result, 1L)

  }

}

package com.romanidze.codestats.web.services

import java.nio.file.{Files, Path, Paths}
import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

import com.romanidze.codestats.parquet.ParquetDataWriter
import com.romanidze.codestats.protobuf.Definition.GitHubInfoRecord
import com.romanidze.codestats.web.config.ParquetConfig
import com.romanidze.codestats.web.dto.Utils
import monix.eval.Task

class ParquetWriterService(config: ParquetConfig) {

  private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

  //TODO: wrap exceptions
  def writeRecords(username: String, records: List[GitHubInfoRecord]): Task[Long] = {

    val currentDate: String = LocalDate.now().format(dateFormatter)

    val dirPath: Path = Paths.get(config.directory, currentDate).toRealPath()

    if (!Files.exists(dirPath)) {
      Files.createDirectory(dirPath)
    }

    val currentTime: String = LocalDateTime.now().format(Utils.dateTimeFormatter)

    val filePath: String =
      Paths
        .get(dirPath.toString, s"${currentTime}-${username}.parquet")
        .toRealPath()
        .toString

    ParquetDataWriter.writeAggregatedData(filePath, records)

  }

}

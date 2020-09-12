package com.romanidze.codestats.web.services.parquet

import java.nio.file.{Files, Path, Paths}
import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

import com.romanidze.codestats.parquet.ParquetDataWriter
import com.romanidze.codestats.protobuf.Definition.GitHubInfoRecord
import com.romanidze.codestats.web.config.ParquetConfig
import monix.eval.Task

/**
 * Service for data loading to Apache Parquet format
 * @param config parquet config
 * @author Andrey Romanov
 */
class ParquetWriterService(config: ParquetConfig) {

  private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
  private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")

  def writeRecords(username: String, records: List[GitHubInfoRecord]): Task[Long] = {

    val currentDate: String = LocalDate.now().format(dateFormatter)

    val dirPath: Path = Paths.get(config.directory, currentDate)

    if (!Files.exists(dirPath)) {
      Files.createDirectory(dirPath)
    }

    val currentTime: String = LocalDateTime.now().format(dateTimeFormatter)

    val filePath: Path =
      Paths
        .get(dirPath.toString, s"${currentTime}-${username}.parquet")

    ParquetDataWriter.writeAggregatedData(filePath.toUri.toString, records)

  }

}

package com.romanidze.codestats.web.services.parquet

import java.nio.file.{Files, Path, Paths}
import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

import com.romanidze.codestats.parquet.ParquetDataWriter
import com.romanidze.codestats.protobuf.Definition.GitHubInfoRecord
import com.romanidze.codestats.web.config.ParquetConfig
import com.romanidze.codestats.web.dto.Utils
import monix.eval.Task

import scala.util.Try

/**
 * Service for data loading to Apache Parquet format
 * @param config parquet config
 * @author Andrey Romanov
 */
class ParquetWriterService(config: ParquetConfig) {

  private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

  def writeRecords(username: String, records: List[GitHubInfoRecord]): Task[Long] = {

    val currentDate: String = LocalDate.now().format(dateFormatter)

    val dirTryPath: Try[Path] = Try {
      Paths.get(config.directory, currentDate).toRealPath()
    }

    val dirPath: Path = dirTryPath.getOrElse(Paths.get("").toAbsolutePath)

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

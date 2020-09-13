package com.romanidze.codestats.web.services.stats

import java.time.LocalDateTime

import com.romanidze.codestats.protobuf.Definition.GitHubInfoRecord
import com.romanidze.codestats.web.client.ClientError
import com.romanidze.codestats.web.dto.{MessageResponse, StarredRepositoryInfo, UserRepositoryInfo}
import com.romanidze.codestats.web.services.parquet.ParquetWriterService
import com.typesafe.scalalogging.LazyLogging
import com.romanidze.codestats.web.dto.Converters._
import monix.eval.Task

/**
 * Utils for working with stats service
 * @param parquetService parquet service
 * @author Andrey Romanov
 */
class StatsServiceUtils(parquetService: ParquetWriterService) extends LazyLogging {

  private def writeData(username: String, input: List[GitHubInfoRecord]): Task[MessageResponse] = {

    for {
      _ <- parquetService
        .writeRecords(username, input)
        .onErrorRecoverWith(_ => {
          logger.error("No data was collected for username {}", username)
          Task.now(0L)
        })
    } yield MessageResponse(s"Processed repo data for username $username")

  }

  def processUserRepoResponse(
    processUsername: String,
    input: List[UserRepositoryInfo]
  ): Task[Either[ClientError, MessageResponse]] = {

    val processed: List[GitHubInfoRecord] = input
      .map(elem => elem.convert(processUsername, LocalDateTime.now()))

    for {
      messageResp <- writeData(processUsername, processed)
    } yield Right(messageResp)

  }

  def processStarredRepoResponse(
    processUsername: String,
    input: List[StarredRepositoryInfo]
  ): Task[Either[ClientError, MessageResponse]] = {

    val processed: List[GitHubInfoRecord] = input
      .map(elem => elem.convert(processUsername, LocalDateTime.now()))

    for {
      messageResp <- writeData(processUsername, processed)
    } yield Right(messageResp)

  }

}

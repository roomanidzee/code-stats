package com.romanidze.codestats.web.services.stats

import com.romanidze.codestats.web.client.ClientError
import com.romanidze.codestats.web.dto.MessageResponse
import com.romanidze.codestats.web.services.gh.GHClientService
import com.romanidze.codestats.web.services.parquet.ParquetWriterService
import com.typesafe.scalalogging.LazyLogging
import monix.eval.Task

/**
 * Service for working with GitHub statistics
 * @param parquetService service for writing data to parquet format
 * @param ghClientService service for working with github data
 * @author Andrey Romanov
 */
class StatsService(parquetService: ParquetWriterService, ghClientService: GHClientService)
    extends LazyLogging {

  private val statsServiceUtils = new StatsServiceUtils(parquetService)

  def processUserRepos(username: String): Task[Either[ClientError, MessageResponse]] =
    for {
      ghResponse  <- ghClientService.getUserRepositories(username)
      finalResult <- statsServiceUtils.processUserRepoResponse(username, ghResponse)
    } yield finalResult

  def processStarredRepos(username: String): Task[Either[ClientError, MessageResponse]] = {
    for {
      ghResponse  <- ghClientService.getStarredRepositories(username)
      finalResult <- statsServiceUtils.processStarredRepoResponse(username, ghResponse)
    } yield finalResult
  }

}

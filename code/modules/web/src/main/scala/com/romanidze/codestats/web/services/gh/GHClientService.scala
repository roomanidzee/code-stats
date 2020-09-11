package com.romanidze.codestats.web.services.gh

import com.romanidze.codestats.web.client.{ClientError, MonixClientInterpreter}
import com.romanidze.codestats.web.config.ClientConfig
import com.romanidze.codestats.web.dto.{StarredRepositoryInfo, UserRepositoryInfo}
import com.romanidze.codestats.web.services.db.OperationInfoService
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

/**
 * Service for working with GitHub data
 * @param clientConfig config for GitHub client
 * @param operationInfoService service for working with in-memory db
 *
 * @author Andrey Romanov
 */
class GHClientService(clientConfig: ClientConfig, operationInfoService: OperationInfoService) {

  private val client = new MonixClientInterpreter(clientConfig.baseURL)
  private val ghClientUtils = new GHClientUtils(clientConfig, client)

  private val repositoryInfoUtils = new RepositoryInfoUtils(client, operationInfoService)

  def getUserRepositories(username: String): Task[Either[ClientError, List[UserRepositoryInfo]]] = {

    for {
      validationResult <- ghClientUtils.getFollowers
      filteredData     <- ghClientUtils.processFollowersData(username, validationResult)
      filteredResult   <- repositoryInfoUtils.validateRepos(filteredData, "user_repos")
      finalResult      <- repositoryInfoUtils.validateUserRepoInput(filteredResult)
    } yield finalResult

  }

  def getStarredRepositories(
    username: String
  ): Task[Either[ClientError, List[StarredRepositoryInfo]]] = {

    for {
      validationResult <- ghClientUtils.getFollowers
      filteredData     <- ghClientUtils.processFollowersData(username, validationResult)
      filteredResult   <- repositoryInfoUtils.validateRepos(filteredData, "starred_repos")
      finalResult      <- repositoryInfoUtils.validateStarredRepoInput(filteredResult)
    } yield finalResult

  }

}

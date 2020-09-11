package com.romanidze.codestats.web.services.gh

import com.romanidze.codestats.web.client.{ClientError, MonixClientInterpreter}
import com.romanidze.codestats.web.dto.{StarredRepositoryInfo, SubscriptionInfo, UserRepositoryInfo}
import com.romanidze.codestats.web.services.db.OperationInfoService
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

/**
 * Utils for working with user repository info data
 * @param client github client
 * @param operationInfoService service for working operation info data
 * @author Andrey Romanov
 */
class RepositoryInfoUtils(
  client: MonixClientInterpreter,
  operationInfoService: OperationInfoService
) {

  def validateRepos(
    input: Either[ClientError, Option[SubscriptionInfo]],
    operation: String
  ): Task[Either[ClientError, (String, String)]] = {

    if (input.isLeft) {
      Task(Left(input.swap.toOption.get))
    }

    val subInfo: Option[SubscriptionInfo] = input.toOption.get

    if (subInfo.isEmpty) {
      Task(
        Left(
          ClientError("Unknown username", Some("No such username in person subscriptions"), None)
        )
      )
    } else {
      Task(Right(subInfo.get.username, operation))
    }

  }

  def validateUserRepoInput(
    input: Either[ClientError, (String, String)]
  ): Task[Either[ClientError, List[UserRepositoryInfo]]] = {

    if (input.isLeft) {
      Task(Left(input.swap.toOption.get))
    }

    val subClientData: (String, String) = input.toOption.get

    for {
      clientData <- operationInfoService.getByUsernameAndOperation(
        subClientData._1,
        subClientData._2
      )
      finalData <- client.getUserRepositories(subClientData._1, clientData._2, clientData._1)
    } yield finalData

  }

  def validateStarredRepoInput(
    input: Either[ClientError, (String, String)]
  ): Task[Either[ClientError, List[StarredRepositoryInfo]]] = {

    if (input.isLeft) {
      Task(Left(input.swap.toOption.get))
    }

    val subClientData: (String, String) = input.toOption.get

    for {
      clientData <- operationInfoService.getByUsernameAndOperation(
        subClientData._1,
        subClientData._2
      )
      finalData <- client.getStarredRepositories(subClientData._1, clientData._2, clientData._1)
    } yield finalData

  }

}

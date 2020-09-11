package com.romanidze.codestats.web.services.gh

import com.romanidze.codestats.web.client.{ClientError, MonixClientInterpreter}
import com.romanidze.codestats.web.config.ClientConfig
import com.romanidze.codestats.web.dto.SubscriptionInfo
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

/**
 * Utils for working with GitHub client
 * @param clientConfig client configuration
 * @param client client instance
 *
 * @author Andrey Romanov
 */
class GHClientUtils(clientConfig: ClientConfig, client: MonixClientInterpreter) {

  def getFollowers: Task[Either[ClientError, List[SubscriptionInfo]]] =
    client.getSubscriptions(clientConfig.requestUsername)

  def processFollowersData(
    username: String,
    input: Either[ClientError, List[SubscriptionInfo]]
  ): Task[Either[ClientError, Option[SubscriptionInfo]]] = {

    if (input.isLeft) {
      Task(Left(input.swap.toOption.get))
    } else {

      val subInfoOpt: Option[SubscriptionInfo] =
        input.toOption.get
          .find(elem => elem.username == username)

      if (subInfoOpt.isEmpty || username == clientConfig.requestUsername) {
        Task(Right(SubscriptionInfo(clientConfig.requestUsername)))
      }

      Task(Right(subInfoOpt))

    }

  }

}

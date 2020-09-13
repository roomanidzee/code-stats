package com.romanidze.codestats.web.app

import com.romanidze.codestats.web.services.stats.StatsServiceUtils
import com.romanidze.codestats.web.client.{ClientError, MonixClientInterpreter}
import com.romanidze.codestats.web.dto.{StarredRepositoryInfo, UserRepositoryInfo}
import com.romanidze.codestats.web.config.ClientConfig
import com.romanidze.codestats.web.dto.MessageResponse

import monix.eval.Task
import monix.execution.Scheduler

// think, that all requests are correct (for education purposes)
class DataProcessing(statsUtils: StatsServiceUtils, config: ClientConfig) {

  def process(implicit monixScheduler: Scheduler): Task[MessageResponse] = {

    val client = new MonixClientInterpreter(config)

    val username = config.origin

    println("started to process data")

    for {
      userRepos <- client.getUserRepositories(username)
      userReposFinal = userRepos.toOption.get
      _                <- statsUtils.processUserRepoResponse(username, userReposFinal)
      userStarredRepos <- client.getStarredRepositories(username)
      userStarredReposFinal = userStarredRepos.toOption.get
      _             <- statsUtils.processStarredRepoResponse(username, userStarredReposFinal)
      subscriptions <- client.getSubscriptions(username)
      subscriptionsInstance = subscriptions.toOption.get
      userSubsRepos <- Task.parTraverse(subscriptionsInstance)(
        elem => client.getUserRepositories(elem.username)
      )
      userSubsReposFinal = (subscriptionsInstance, userSubsRepos).zipped.toList
      _ <- Task.parTraverse(userSubsReposFinal)(elem => {

        val subsReposData = elem._2

        val items: List[UserRepositoryInfo] = subsReposData.toOption.get

        statsUtils.processUserRepoResponse(elem._1.username, items)

      })
      userSubsStarredRepos <- Task.parTraverse(subscriptionsInstance)(
        elem => client.getStarredRepositories(elem.username)
      )
      userSubsStarredReposFinal = (subscriptionsInstance, userSubsStarredRepos).zipped.toList
      _ <- Task.parTraverse(userSubsStarredReposFinal)(elem => {

        val subsStarredReposData = elem._2

        val items: List[StarredRepositoryInfo] = subsStarredReposData.toOption.get

        statsUtils.processStarredRepoResponse(elem._1.username, items)

      })

    } yield MessageResponse("Data successfully processed")

  }

}

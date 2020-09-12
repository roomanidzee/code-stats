package com.romanidze.codestats.web.app

import cats.Applicative.ops.toAllApplicativeOps
import cats.effect.ExitCode
import com.romanidze.codestats.web.client.MonixClientInterpreter
import com.romanidze.codestats.web.config.{ApplicationConfig, ClientConfig, ConfigurationLoader}
import com.romanidze.codestats.web.dto.MessageResponse
import com.romanidze.codestats.web.services.parquet.ParquetWriterService
import com.romanidze.codestats.web.services.stats.StatsServiceUtils
import monix.eval.{Task, TaskApp}
import monix.execution.Scheduler

object Launcher extends TaskApp {

  // think, that all requests are correct (for education purposes)
  def launchProcessing(
    config: ClientConfig,
    statsUtils: StatsServiceUtils,
    client: MonixClientInterpreter
  ): Task[MessageResponse] = {

    implicit val schedulerInstance: Scheduler = scheduler
    val username = config.origin

    for {
      userRepos        <- client.getUserRepositories(username, config.limit, config.page)
      _                <- statsUtils.processUserRepoResponse(username, userRepos)
      userStarredRepos <- client.getStarredRepositories(username, config.limit, config.page)
      _                <- statsUtils.processStarredRepoResponse(username, userStarredRepos)
      subscriptions    <- client.getSubscriptions(username)
      subscriptionsInstance = subscriptions.toOption.get
      userSubsRepos <- Task.parTraverse(subscriptionsInstance)(
        elem => client.getUserRepositories(elem.username, config.limit, config.page)
      )
      _ <- Task.parTraverse(userSubsRepos)(
        elem => statsUtils.processUserRepoResponse(username, elem)
      )
      userSubsStarredRepos <- Task.parTraverse(subscriptionsInstance)(
        elem => client.getStarredRepositories(elem.username, config.limit, config.page)
      )
      _ <- Task.parTraverse(userSubsStarredRepos)(
        elem => statsUtils.processStarredRepoResponse(username, elem)
      )

    } yield MessageResponse("Data successfully processed")
  }

  override def run(args: List[String]): Task[ExitCode] = {

    val appConfig: ApplicationConfig = ConfigurationLoader.load
      .fold(e => sys.error(s"Failed to load configuration:\n${e.toList.mkString("\n")}"), identity)

    val parquetWriterService = new ParquetWriterService(appConfig.parquet)
    val statsServiceUtils = new StatsServiceUtils(parquetWriterService)

    val monixClientInterpreter = new MonixClientInterpreter(appConfig.client.base)

    launchProcessing(appConfig.client, statsServiceUtils, monixClientInterpreter)
      .as(ExitCode.Success)

  }
}

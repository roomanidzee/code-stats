package com.romanidze.codestats.web.app

import cats.Applicative.ops.toAllApplicativeOps
import cats.effect.ExitCode
import com.romanidze.codestats.web.client.ClientError
import com.romanidze.codestats.web.config.{ApplicationConfig, ConfigurationLoader}
import com.romanidze.codestats.web.dto.MessageResponse
import com.romanidze.codestats.web.services.stats.StatsService
import monix.eval.{Task, TaskApp}

object Launcher extends TaskApp {

  def launchProcessing(
    input: String,
    service: StatsService
  ): Task[Either[ClientError, MessageResponse]] = {

    service
      .processUserRepos(input)
      .flatMap(_ => service.processStarredRepos(input))

  }

  override def run(args: List[String]): Task[ExitCode] = {

    val appConfig: ApplicationConfig = ConfigurationLoader.load
      .fold(e => sys.error(s"Failed to load configuration:\n${e.toList.mkString("\n")}"), identity)

    val module = new ApplicationModule(appConfig)

    args.headOption match {
      case None       => launchProcessing("zjffdu", module.statsService).as(ExitCode.Success)
      case Some(name) => launchProcessing(name, module.statsService).as(ExitCode.Success)
    }

  }
}

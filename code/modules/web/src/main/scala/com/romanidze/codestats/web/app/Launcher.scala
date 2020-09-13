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

  override def run(args: List[String]): Task[ExitCode] = {

    val appConfig: ApplicationConfig = ConfigurationLoader.load
      .fold(e => sys.error(s"Failed to load configuration:\n${e.toList.mkString("\n")}"), identity)

    val parquetWriterService = new ParquetWriterService(appConfig.parquet)
    val statsServiceUtils = new StatsServiceUtils(parquetWriterService)

    val dataProcessing = new DataProcessing(statsServiceUtils, appConfig.client)
    implicit val schedulerInstance: Scheduler = scheduler

    dataProcessing.process.as(ExitCode.Success)

  }
}

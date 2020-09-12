package com.romanidze.codestats.web.app

import com.romanidze.codestats.db.config.DBInitializer
import com.romanidze.codestats.db.repositories.OperationInfoRepository
import com.romanidze.codestats.web.config.ApplicationConfig
import com.romanidze.codestats.web.services.db.OperationInfoService
import com.romanidze.codestats.web.services.gh.GHClientService
import com.romanidze.codestats.web.services.parquet.ParquetWriterService
import com.romanidze.codestats.web.services.stats.StatsService

class ApplicationModule(config: ApplicationConfig) {

  private val transactor = DBInitializer.getTransactor(config.db)

  private val operationInfoService = new OperationInfoService(
    new OperationInfoRepository(transactor)
  )

  private val ghClientService = new GHClientService(config.client, operationInfoService)

  val statsService = new StatsService(new ParquetWriterService(config.parquet), ghClientService)

}

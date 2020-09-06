package com.romanidze.codestats.db.config

import java.util.concurrent.Executors

import cats.effect.Blocker
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import doobie.hikari.HikariTransactor
import monix.eval.Task

import scala.concurrent.ExecutionContext

object DBInitializer {

  def getTransactor(config: DBConfig): Task[HikariTransactor[Task]] = {

    val poolConfig = new HikariConfig()
    poolConfig.setDriverClassName(config.driverClassName)
    poolConfig.setJdbcUrl(config.jdbcURL)
    poolConfig.setUsername(config.user)
    poolConfig.setPassword(config.password)
    poolConfig.setMaximumPoolSize(config.poolSize.toInt)
    poolConfig.setConnectionTimeout(config.connectionTimeout.toInt)

    Task(
      HikariTransactor.apply[Task](
        new HikariDataSource(poolConfig),
        ExecutionContext.fromExecutor(Executors.newFixedThreadPool(config.threadNumber.toInt)),
        Blocker.liftExecutorService(Executors.newCachedThreadPool())
      )
    )

  }

}

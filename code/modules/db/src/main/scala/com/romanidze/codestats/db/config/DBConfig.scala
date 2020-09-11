package com.romanidze.codestats.db.config

/**
 * Configuration for DB Initializer
 * @param driverClassName jdbc driver name
 * @param jdbcURL jdbc url for connection
 * @param user username for connection
 * @param password password for connection
 * @param poolSize number for connection pool size
 * @param connectionTimeout time for wait before disconnect
 * @param threadNumber number of threads of configuration init
 *
 * @author Andrey Romanov
 */
case class DBConfig(
  driverClassName: String,
  jdbcURL: String,
  user: String,
  password: String,
  poolSize: Long,
  connectionTimeout: Long,
  threadNumber: Long
)

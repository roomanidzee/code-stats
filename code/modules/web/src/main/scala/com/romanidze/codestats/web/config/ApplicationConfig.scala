package com.romanidze.codestats.web.config

import com.romanidze.codestats.db.config.DBConfig

/**
 * Case class for system config definition
 * @param server server config
 * @param h2 in-memory db config
 * @param client github client config
 * @param parquet parquet writing config
 * @author Andrey Romanov
 */
case class ApplicationConfig(
  server: ServerConfig,
  h2: DBConfig,
  client: ClientConfig,
  parquet: ParquetConfig
)

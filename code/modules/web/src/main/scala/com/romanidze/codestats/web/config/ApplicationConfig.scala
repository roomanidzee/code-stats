package com.romanidze.codestats.web.config

import com.romanidze.codestats.db.config.DBConfig

case class ApplicationConfig(
  server: ServerConfig,
  h2: DBConfig,
  client: ClientConfig,
  parquet: ParquetConfig
)

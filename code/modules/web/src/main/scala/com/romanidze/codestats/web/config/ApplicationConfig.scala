package com.romanidze.codestats.web.config

import com.romanidze.codestats.db.config.DBConfig

/**
 * Case class for system config definition
 * @param db in-memory db config
 * @param client github client config
 * @param parquet parquet writing config
 * @author Andrey Romanov
 */
case class ApplicationConfig(db: DBConfig, client: ClientConfig, parquet: ParquetConfig)

package com.romanidze.codestats.web.config

/**
 * Case class for system config definition
 * @param client github client config
 * @param parquet parquet writing config
 * @author Andrey Romanov
 */
case class ApplicationConfig(client: ClientConfig, parquet: ParquetConfig)

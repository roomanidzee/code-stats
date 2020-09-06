package com.romanidze.codestats.db.config

case class DBConfig(
  driverClassName: String,
  jdbcURL: String,
  user: String,
  password: String,
  poolSize: Long,
  connectionTimeout: Long,
  threadNumber: Long
)

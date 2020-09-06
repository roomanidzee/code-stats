package com.romanidze.codestats.web.dto

import java.time.{LocalDateTime, ZoneOffset}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}

object Utils {

  private val dateTimeFormatter: DateTimeFormatter =
    new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .append(DateTimeFormatter.ISO_LOCAL_DATE)
      .appendLiteral('T')
      .append(DateTimeFormatter.ISO_LOCAL_TIME)
      .appendLiteral('Z')
      .toFormatter()

  def convertToTime(input: String): LocalDateTime = {
    LocalDateTime.parse(input, dateTimeFormatter)
  }

  def convertToLong(input: LocalDateTime): Long = {
    input.toInstant(ZoneOffset.UTC).toEpochMilli
  }

}

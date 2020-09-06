package com.romanidze.codestats.web.dto

import java.time.LocalDateTime

class UtilsTest extends munit.FunSuite {

  test("convert string to time") {

    val conversionResult: LocalDateTime = Utils.convertToTime("2019-09-26T17:50:00Z")

    assertEquals(conversionResult.getYear, 2019)

  }

  test("convert time to long value") {

    val conversionResult: Long = Utils.convertToLong(LocalDateTime.now())

    assume(conversionResult > 0)

  }

}

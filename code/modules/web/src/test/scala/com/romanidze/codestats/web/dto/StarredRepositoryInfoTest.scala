package com.romanidze.codestats.web.dto

class StarredRepositoryInfoTest extends munit.FunSuite {

  test("decode from json") {

    val expectedObj = StarredRepositoryInfo(
      "2020-09-05T07:13:33Z",
      RepoObject(
        StarredOwnerInfo("disneystreaming", "Organization"),
        "weaver-test",
        false,
        "2020-04-24T17:21:55Z",
        "2020-09-05T08:09:46Z",
        "2020-09-05T08:28:16Z",
        951,
        95,
        95,
        "Scala",
        true,
        true,
        true,
        true,
        true,
        13,
        false,
        false,
        2
      )
    )

    ValidationUtils
      .validateDecode[List[StarredRepositoryInfo]](List(expectedObj), "dto/starred.json")

  }

}

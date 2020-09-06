package com.romanidze.codestats.web.dto

class UserRepositoryInfoTest extends munit.FunSuite {

  test("decode from json") {

    val expectedObj: UserRepositoryInfo = UserRepositoryInfo(
      OwnerInfo("roomanidzee", "User"),
      "algos",
      false,
      "2019-09-26T17:50:00Z",
      "2019-12-14T07:46:10Z",
      "2019-12-13T07:06:28Z",
      947,
      0,
      0,
      "Java",
      true,
      true,
      true,
      true,
      false,
      0,
      false,
      false,
      0
    )

    ValidationUtils
      .validateDecode[List[UserRepositoryInfo]](List(expectedObj), "wiremock/__files/repos.json")

  }

}

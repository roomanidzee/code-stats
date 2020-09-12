package com.romanidze.codestats.web.dto

import java.time.LocalDateTime

import com.romanidze.codestats.protobuf.Definition.GitHubInfoRecord
import com.romanidze.codestats.web.dto.Converters._

class ConvertersTest extends munit.FunSuite {

  test("convert user repository info") {

    val obj: UserRepositoryInfo =
      ValidationUtils
        .getDecodedObject[List[UserRepositoryInfo]]("wiremock/__files/repos.json")
        .head

    val gitHubInfoRecord: GitHubInfoRecord = obj.convert("test", LocalDateTime.now())

    assertEquals(gitHubInfoRecord.getName, obj.repoName)
    assertEquals(gitHubInfoRecord.getLanguage, obj.language.get)

  }

  test("convert starred repository info") {

    val obj: StarredRepositoryInfo =
      ValidationUtils
        .getDecodedObject[List[StarredRepositoryInfo]]("wiremock/__files/starred.json")
        .head

    val gitHubInfoRecord: GitHubInfoRecord = obj.convert("test", LocalDateTime.now())

    assume(gitHubInfoRecord.getIsStarred)
    assertEquals(gitHubInfoRecord.getName, obj.repo.repoName)
    assertEquals(gitHubInfoRecord.getLanguage, obj.repo.language.get)

  }

}

package com.romanidze.codestats.web.dto

import tethys._
import tethys.jackson._

/**
 * Github classes for data extracting
 * @author Andrey Romanov
 */
case class OwnerInfo(username: String, typeInfo: String)

object OwnerInfo {

  implicit val reader: JsonReader[OwnerInfo] = JsonReader.builder
    .addField[String]("login")
    .addField[String]("type")
    .buildReader(OwnerInfo.apply)

}

case class UserRepositoryInfo(
  ownerInfo: OwnerInfo,
  repoName: String,
  isFork: Boolean,
  createdAt: String,
  updatedAt: String,
  pushedAt: String,
  size: Long,
  stargazersCount: Long,
  watchersCount: Long,
  language: String,
  hasIssues: Boolean,
  hasProjects: Boolean,
  hasDownloads: Boolean,
  hasWiki: Boolean,
  hasPages: Boolean,
  forksCount: Long,
  archived: Boolean,
  disabled: Boolean,
  openIssues: Long
)

object UserRepositoryInfo {

  implicit val reader: JsonReader[UserRepositoryInfo] = JsonReader.builder
    .addField[OwnerInfo]("owner")
    .addField[String]("name")
    .addField[Boolean]("fork")
    .addField[String]("created_at")
    .addField[String]("updated_at")
    .addField[String]("pushed_at")
    .addField[Long]("size")
    .addField[Long]("stargazers_count")
    .addField[Long]("watchers_count")
    .addField[String]("language")
    .addField[Boolean]("has_issues")
    .addField[Boolean]("has_projects")
    .addField[Boolean]("has_downloads")
    .addField[Boolean]("has_wiki")
    .addField[Boolean]("has_pages")
    .addField[Long]("forks_count")
    .addField[Boolean]("archived")
    .addField[Boolean]("disabled")
    .addField[Long]("open_issues_count")
    .buildReader(UserRepositoryInfo.apply)
}

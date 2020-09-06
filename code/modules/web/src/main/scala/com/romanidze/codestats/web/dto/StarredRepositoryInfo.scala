package com.romanidze.codestats.web.dto

import tethys._
import tethys.jackson._

case class StarredOwnerInfo(username: String, typeInfo: String)

object StarredOwnerInfo {

  implicit val reader: JsonReader[StarredOwnerInfo] = JsonReader.builder
    .addField[String]("login")
    .addField[String]("type")
    .buildReader(StarredOwnerInfo.apply)

}

case class RepoObject(
  ownerInfo: StarredOwnerInfo,
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

object RepoObject {

  implicit val reader: JsonReader[RepoObject] = JsonReader.builder
    .addField[StarredOwnerInfo]("owner")
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
    .buildReader(RepoObject.apply)

}

case class StarredRepositoryInfo(starredAt: String, repo: RepoObject)

object StarredRepositoryInfo {

  implicit val reader: JsonReader[StarredRepositoryInfo] = JsonReader.builder
    .addField[String]("starred_at")
    .addField[RepoObject]("repo")
    .buildReader(StarredRepositoryInfo.apply)

}

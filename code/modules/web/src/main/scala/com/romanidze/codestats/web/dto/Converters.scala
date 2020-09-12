package com.romanidze.codestats.web.dto

import java.time.LocalDateTime

import com.romanidze.codestats.protobuf.Definition.GitHubInfoRecord

/**
 * Converters from github classes to protobuf model definition
 * @author Andrey Romanov
 */

object Converters {

  implicit class UserRepositoryInfoConverter(val obj: UserRepositoryInfo) {

    def convert(ghRequestUser: String, recordCreatedTime: LocalDateTime): GitHubInfoRecord = {

      GitHubInfoRecord
        .newBuilder()
        .setGhRequestUser(ghRequestUser)
        .setRecordCreatedTime(Utils.convertToLong(recordCreatedTime).toDouble)
        .setName(obj.repoName)
        .setIsStarred(false)
        .setStarredAt(0L)
        .setOwnerLogin(obj.ownerInfo.username)
        .setOwnerType(obj.ownerInfo.typeInfo)
        .setFork(obj.isFork)
        .setCreatedAt(Utils.convertToLong(Utils.convertToTime(obj.createdAt)))
        .setUpdatedAt(Utils.convertToLong(Utils.convertToTime(obj.updatedAt)))
        .setPushedAt(Utils.convertToLong(Utils.convertToTime(obj.pushedAt)))
        .setSize(obj.size)
        .setStargazersCount(obj.stargazersCount)
        .setWatchersCount(obj.watchersCount)
        .setLanguage(obj.language.getOrElse(""))
        .setHasIssues(obj.hasIssues)
        .setHasProjects(obj.hasProjects)
        .setHasDownloads(obj.hasDownloads)
        .setHasWiki(obj.hasWiki)
        .setHasPages(obj.hasPages)
        .setForksCount(obj.forksCount)
        .setArchived(obj.archived)
        .setDisabled(obj.disabled)
        .setOpenIssues(obj.openIssues)
        .build()

    }

  }

  implicit class StarredRepositoryInfoConverter(val obj: StarredRepositoryInfo) {

    def convert(ghRequestUser: String, recordCreatedTime: LocalDateTime): GitHubInfoRecord = {

      GitHubInfoRecord
        .newBuilder()
        .setGhRequestUser(ghRequestUser)
        .setRecordCreatedTime(Utils.convertToLong(recordCreatedTime).toDouble)
        .setName(obj.repo.repoName)
        .setIsStarred(true)
        .setStarredAt(Utils.convertToLong(Utils.convertToTime(obj.starredAt)))
        .setOwnerLogin(obj.repo.ownerInfo.username)
        .setOwnerType(obj.repo.ownerInfo.typeInfo)
        .setFork(obj.repo.isFork)
        .setCreatedAt(Utils.convertToLong(Utils.convertToTime(obj.repo.createdAt)))
        .setUpdatedAt(Utils.convertToLong(Utils.convertToTime(obj.repo.updatedAt)))
        .setPushedAt(Utils.convertToLong(Utils.convertToTime(obj.repo.pushedAt)))
        .setSize(obj.repo.size)
        .setStargazersCount(obj.repo.stargazersCount)
        .setWatchersCount(obj.repo.watchersCount)
        .setLanguage(obj.repo.language.getOrElse(""))
        .setHasIssues(obj.repo.hasIssues)
        .setHasProjects(obj.repo.hasProjects)
        .setHasDownloads(obj.repo.hasDownloads)
        .setHasWiki(obj.repo.hasWiki)
        .setHasPages(obj.repo.hasPages)
        .setForksCount(obj.repo.forksCount)
        .setArchived(obj.repo.archived)
        .setDisabled(obj.repo.disabled)
        .setOpenIssues(obj.repo.openIssues)
        .build()

    }

  }

}

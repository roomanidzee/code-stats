package com.romanidze.codestats.db.domain

/**
 * Case class for operation information
 * @param id identifier
 * @param username github username
 * @param operation operation type (user_repos or starred_repos)
 * @param pageValue page number for a github client
 * @param limitValue limit number for a github client(maximum 100)
 * @author Andrey Romanov
 */
case class OperationInfo(
  id: Long,
  username: String,
  operation: String,
  pageValue: Short,
  limitValue: Short
)

package com.romanidze.codestats.db.domain

/**
 * Case class for data insertion
 *
 * @param username github username
 * @param operation operation type (user_repos or starred_repos)
 * @param pageValue page number for a github client
 * @param limitValue limit number for a github client(maximum 100)
 * @author Andrey Romanov
 */
case class OperationInsertInfo(
  username: String,
  operation: String,
  pageValue: Short,
  limitValue: Short
)

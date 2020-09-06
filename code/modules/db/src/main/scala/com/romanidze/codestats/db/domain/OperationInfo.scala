package com.romanidze.codestats.db.domain

case class OperationInfo(
  id: Long,
  username: String,
  operation: String,
  pageValue: Short,
  limitValue: Short
)

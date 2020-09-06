package com.romanidze.codestats.db.domain

case class OperationInsertInfo(
  username: String,
  operation: String,
  pageValue: Short,
  limitValue: Short
)

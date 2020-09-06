package com.romanidze.codestats.db.queries

import com.romanidze.codestats.db.domain.{OperationInfo, OperationInsertInfo}
import doobie.implicits._

object OperationInfoQueries {

  def insert(operationInfo: OperationInsertInfo): doobie.Update0 = {

    sql"""
         |INSERT INTO operation_info(
         |  username,
         |  operation,
         |  pageValue,
         |  limitValue
         |)
         |VALUES (
         |  ${operationInfo.username},
         |  ${operationInfo.operation},
         |  ${operationInfo.pageValue},
         |  ${operationInfo.limitValue}
         |)
       """.stripMargin.update

  }

  def getAll: doobie.Query0[OperationInfo] = {

    sql"""|SELECT id, username, operation, pageValue, limitValue
          |FROM operation_info
       """.stripMargin.query[OperationInfo]

  }

  def getByUserAndOperation(username: String, operation: String): doobie.Query0[OperationInfo] = {

    sql"""|SELECT id, username, operation, pageValue, limitValue
          |FROM operation_info
          |WHERE username = ${username} AND operation = ${operation}
       """.stripMargin.query[OperationInfo]

  }

}

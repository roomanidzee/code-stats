package com.romanidze.codestats.web.services.db

import com.romanidze.codestats.db.domain.{OperationInfo, OperationInsertInfo}
import com.romanidze.codestats.db.repositories.OperationInfoRepository
import monix.eval.Task

/**
 * Service for interaction with a in-memory db
 * @param operationInfoRepository repository with data access
 * @author Andrey Romanov
 */
class OperationInfoService(operationInfoRepository: OperationInfoRepository) {

  def getByUsernameAndOperation(username: String, operation: String): Task[(Short, Short)] =
    for {

      dbResult <- operationInfoRepository.getByUserAndOperation(username, operation)
      finalResult <- {

        if (dbResult.isEmpty) {
          return Task((1: Short, 100: Short))
        }

        val dbOldData: OperationInfo = dbResult.last

        val dbInfo: OperationInfo = dbOldData
          .copy(
            pageValue = (dbOldData.pageValue + 1).toShort,
            limitValue = (dbOldData.limitValue + 100).toShort
          )

        Task((dbInfo.pageValue, dbInfo.limitValue))

      }
      _ <- operationInfoRepository.insert(
        OperationInsertInfo(username, operation, finalResult._1.toShort, finalResult._2.toShort)
      )

    } yield finalResult

}

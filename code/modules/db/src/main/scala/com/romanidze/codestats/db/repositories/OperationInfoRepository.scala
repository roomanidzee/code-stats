package com.romanidze.codestats.db.repositories

import com.romanidze.codestats.db.domain.{OperationInfo, OperationInsertInfo}
import com.romanidze.codestats.db.queries.OperationInfoQueries
import doobie.Transactor
import doobie.implicits._
import monix.eval.Task
import fs2.interop.reactivestreams._
import monix.execution.Scheduler.Implicits.global
import monix.reactive.Observable

class OperationInfoRepository(xa: Transactor[Task]) {

  def insert(operationInfo: OperationInsertInfo): Task[List[Long]] = {

    val publisher: StreamUnicastPublisher[Task, Long] =
      OperationInfoQueries
        .insert(operationInfo)
        .withGeneratedKeys[Long]("id")
        .transact(xa)
        .toUnicastPublisher

    Observable.fromReactivePublisher(publisher).toListL

  }

  def getAll: Task[List[OperationInfo]] = {

    val publisher = OperationInfoQueries.getAll.stream
      .transact(xa)
      .toUnicastPublisher

    Observable.fromReactivePublisher(publisher).toListL

  }

  def getByUserAndOperation(username: String, operation: String): Task[List[OperationInfo]] = {

    val publisher =
      OperationInfoQueries
        .getByUserAndOperation(username, operation)
        .stream
        .transact(xa)
        .toUnicastPublisher

    Observable.fromReactivePublisher(publisher).toListL

  }

}

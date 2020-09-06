package com.romanidze.codestats.web.services

import com.romanidze.codestats.db.repositories.OperationInfoRepository
import com.romanidze.codestats.web.client.{ClientError, MonixClientInterpreter}
import com.romanidze.codestats.web.config.ClientConfig
import com.romanidze.codestats.web.dto.{MessageResponse, SubscriptionInfo}
import monix.eval.Task

class StatsService(
  parquetService: ParquetWriterService,
  clientConfig: ClientConfig,
  localDBRepo: OperationInfoRepository
) {

  /*TODO
  def processRepos(peopleLimit: Int): Task[Either[ClientError, MessageResponse]] = {


    val client = new MonixClientInterpreter(clientConfig.baseURL)

    val followers: Task[Either[ClientError, List[SubscriptionInfo]]] =
      client.getSubscriptions(clientConfig.requestUsername)

    for {
      followers <- client.getSubscriptions(clientConfig.requestUsername)
    } yield followers


  }

 */

}

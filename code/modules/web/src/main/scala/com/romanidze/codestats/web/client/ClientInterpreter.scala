package com.romanidze.codestats.web.client

import com.romanidze.codestats.web.dto.{StarredRepositoryInfo, SubscriptionInfo, UserRepositoryInfo}

trait ClientInterpreter[F[_]] {

  def getSubscriptions(username: String): F[Either[ClientError, List[SubscriptionInfo]]]

  def getUserRepositories(username: String): F[Either[ClientError, List[UserRepositoryInfo]]]

  def getStarredRepositories(username: String): F[Either[ClientError, List[StarredRepositoryInfo]]]

}

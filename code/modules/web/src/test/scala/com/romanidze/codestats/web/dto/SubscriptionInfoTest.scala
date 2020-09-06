package com.romanidze.codestats.web.dto

class SubscriptionInfoTest extends munit.FunSuite {

  test("decode from json") {

    val expectedObjects: List[SubscriptionInfo] =
      List(SubscriptionInfo("zjffdu"), SubscriptionInfo("pshirshov"))

    ValidationUtils
      .validateDecode[List[SubscriptionInfo]](expectedObjects, "dto/subscriptions.json")

  }

}

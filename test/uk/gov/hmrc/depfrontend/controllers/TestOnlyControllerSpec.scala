/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.depfrontend.controllers

import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.mvc.Cookie
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.depfrontend.config.AppConfig
import uk.gov.hmrc.depfrontend.views.html._
import uk.gov.hmrc.play.bootstrap.tools.Stubs.stubMessagesControllerComponents

class TestOnlyControllerSpec
    extends WordSpec
    with Matchers
    with GuiceOneAppPerSuite
    with ScalaCheckPropertyChecks {

  implicit private val fakeRequest = FakeRequest("GET", "/").withCookies(Cookie("mdtp", "12345"))

  implicit val appConfig = app.injector.instanceOf[AppConfig]

  val testOnlyView = app.injector.instanceOf[TestOnlyView]

  val mcc = stubMessagesControllerComponents()
  implicit val messages = mcc.messagesApi.preferred(fakeRequest)

  private val controller = new TestOnlyController(
    mcc,
    testOnlyView)

  "Query parameter URLs" should {

    "All strings should be 200" in {
        val result = controller.testOnly()(fakeRequest)
        status(result) shouldBe OK
    }

    s"non-supported test-only page should render default page" in {
      val result = controller.testOnly()(fakeRequest)
      contentAsString(result) shouldBe testOnlyView().toString
    }


    "test-only should render the self-assessment webchat page" in {
      val result = controller.testOnly()(fakeRequest)
      contentAsString(result) shouldBe testOnlyView().toString
    }
  }
}

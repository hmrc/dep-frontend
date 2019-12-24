/*
 * Copyright 2019 HM Revenue & Customs
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
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.depfrontend.config.AppConfig
import uk.gov.hmrc.depfrontend.views.html._
import uk.gov.hmrc.play.bootstrap.tools.Stubs.stubMessagesControllerComponents

class WebchatControllerSpec extends WordSpec with Matchers with GuiceOneAppPerSuite with ScalaCheckPropertyChecks {
  private val fakeRequest = FakeRequest("GET", "/")

  lazy val appConfig = app.injector.instanceOf[AppConfig]

  val mcc = stubMessagesControllerComponents()
  val messages = mcc.messagesApi.preferred(fakeRequest)

  private val controller = new WebchatController(appConfig, mcc)

  "Query paramater URLs" should {
    "All optionable strings should be 200" in {
      forAll { (fromUrl: Option[String]) =>
        val result = controller.webchat(fromUrl)(fakeRequest)
        status(result) shouldBe OK
      }
    }

    Seq(Some("non-page"), None).map { from =>
      s"non-supported ($from) pages should render default page" in {
        val result = controller.webchat(from)(fakeRequest)
        contentAsString(result) shouldBe web_chat()(fakeRequest, messages, appConfig).toString
      }
    }

    "self-assessment should render the self-assessment webchat page" in {
      val from = Some("self-assessment")
      val result = controller.webchat(from)(fakeRequest)

      contentAsString(result) shouldBe self_assessment()(fakeRequest, messages, appConfig).toString
    }

  }

  "fixed URLs" should {
    "render self-assessment page" in {
      val result = controller.selfAssessment(fakeRequest)
      status(result) shouldBe OK
      contentAsString(result) shouldBe self_assessment()(fakeRequest, messages, appConfig).toString
    }
  }
}

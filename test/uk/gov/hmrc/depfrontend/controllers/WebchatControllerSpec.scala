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

class WebchatControllerSpec
    extends WordSpec
    with Matchers
    with GuiceOneAppPerSuite
    with ScalaCheckPropertyChecks {

  implicit private val fakeRequest = FakeRequest("GET", "/").withCookies(Cookie("mdtp", "12345"))

  implicit val appConfig = app.injector.instanceOf[AppConfig]
  val selfAssessmentView = app.injector.instanceOf[SelfAssessmentView]
  val taxCreditsView = app.injector.instanceOf[TaxCreditsView]
  val childBenefitView = app.injector.instanceOf[ChildBenefitView]
  val customsEnquiriesView = app.injector.instanceOf[CustomsEnquiriesView]
  val employerEnquiriesView = app.injector.instanceOf[EmployerEnquiriesView]
  val incomeTaxEnquiriesView = app.injector.instanceOf[IncomeTaxEnquiriesView]
  val nationalInsuranceNumbersView = app.injector.instanceOf[NationalInsuranceNumbersView]
  val onlineServiceHelpdeskView = app.injector.instanceOf[OnlineServiceHelpdeskView]
  val vatEnquiriesView = app.injector.instanceOf[VatEnquiriesView]
  val webChatView = app.injector.instanceOf[WebChatView]

  val mcc = stubMessagesControllerComponents()
  implicit val messages = mcc.messagesApi.preferred(fakeRequest)

  private val controller = new WebchatController(
    appConfig,
    mcc,
    selfAssessmentView,
    taxCreditsView,
    childBenefitView,
    customsEnquiriesView,
    employerEnquiriesView,
    incomeTaxEnquiriesView,
    nationalInsuranceNumbersView,
    onlineServiceHelpdeskView,
    vatEnquiriesView,
    webChatView)

  "should throw a RuntimeException if there is no mdtp cookie" in {
    assertThrows[RuntimeException] {
      controller.selfAssessment(FakeRequest("GET", "/"))
    }
  }

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
        contentAsString(result) shouldBe webChatView().toString
      }
    }

    "self-assessment should render the self-assessment webchat page" in {
      val from = Some("self-assessment")
      val result = controller.webchat(from)(fakeRequest)

      contentAsString(result) shouldBe selfAssessmentView().toString
    }

    "tax-credits should render the tax-credits webchat page" in {
      val from = Some("tax-credits")
      val result = controller.webchat(from)(fakeRequest)

      contentAsString(result) shouldBe taxCreditsView().toString()
    }
  }

  "fixed URLs" should {
    "render self-assessment page" in {
      val result = controller.selfAssessment(fakeRequest)
      status(result) shouldBe OK

      contentAsString(result) shouldBe selfAssessmentView().toString
    }

    "render tax-credits page" in {
      val result = controller.taxCredits(fakeRequest)
      status(result) shouldBe OK
      contentAsString(result) shouldBe taxCreditsView().toString()
    }

    "render child benefit page" in {
      val result = controller.childBenefit(fakeRequest)
      status(result) shouldBe OK
      contentAsString(result) shouldBe childBenefitView().toString
    }

    "render employer enquiries page" in {
      val result = controller.employerEnquiries(fakeRequest)
      status(result) shouldBe OK
      contentAsString(result) shouldBe employerEnquiriesView().toString
    }

    "render vat enquiries page" in {
      val result = controller.vatEnquiries(fakeRequest)
      status(result) shouldBe OK
      contentAsString(result) shouldBe vatEnquiriesView().toString
    }

    "render online services helpdesk page" in {
      val result = controller.onlineServicesHelpdesk(fakeRequest)
      status(result) shouldBe OK
      contentAsString(result) shouldBe onlineServiceHelpdeskView().toString
    }

    "render national insurance page" in {
      val result = controller.nationalInsuranceNumbers(fakeRequest)
      status(result) shouldBe OK
      contentAsString(result) shouldBe nationalInsuranceNumbersView().toString
    }

    "render customs page" in {
      val result = controller.customsEnquiries(fakeRequest)
      status(result) shouldBe OK
      contentAsString(result) shouldBe customsEnquiriesView().toString
    }

    "render income tax enquiries page" in {
      val result = controller.incomeTaxEnquiries(fakeRequest)
      status(result) shouldBe OK
      contentAsString(result) shouldBe incomeTaxEnquiriesView().toString
    }
  }
}

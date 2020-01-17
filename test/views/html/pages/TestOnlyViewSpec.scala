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

package views.html.pages

import play.api.mvc.Cookie
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.depfrontend.views.html.TestOnlyView

class TestOnlyViewSpec extends ChatViewBehaviours {

  val view = app.injector.instanceOf[TestOnlyView]

  def createView: () => HtmlFormat.Appendable = () => view()(fakeRequest, messages)

  def page(view: () => HtmlFormat.Appendable,
           messageKeyPrefix: String,
           messageHeading: String): Unit = {

    "behave like a page" when {
      "rendered" must {
        "have the correct banner title" in {
          val doc = asDocument(view())
          val nav = doc.getElementById("proposition-menu")
          val span = nav.children.first
          span.text mustBe messages("global.nav.title")
        }

        "display the correct browser title" in {
          val doc = asDocument(view())
          assertEqualsMessage(doc, "title", s"$messageKeyPrefix")
        }

        "display the correct page title" in {
          val doc = asDocument(view())
          doc.getElementsByTag("h1")
          assertPageTitleEqualsMessage(doc, s"$messageHeading")
        }
      }

      "Test only view" must {
        behave like page(
          createView,
          "Ask HMRC - Webchat",
          "Test only page")
      }
    }
  }
}
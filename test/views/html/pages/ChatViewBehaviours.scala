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

import play.twirl.api.HtmlFormat

trait ChatViewBehaviours extends ViewSpecBase {

  def normalPage(view: () => HtmlFormat.Appendable,
                 messageKeyPrefix: String,
                 messageHeading: String,
                 urlLinkText: String,
                 returnUrlLink: String): Unit = {

    "behave like a normal page" when {
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

        "display the correct link text" in {
          val doc = asDocument(view())
          assertContainsText(doc, s"$urlLinkText")
        }

        "confirm the correct return link back to the correct gov.uk page" in {
          val doc = asDocument(view())
          val a = doc.getElementById("return-link")
          val href = a.attr("href")

          href mustBe returnUrlLink
        }
      }
    }
  }
}
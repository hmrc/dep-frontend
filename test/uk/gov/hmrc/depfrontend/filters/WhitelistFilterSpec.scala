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

package uk.gov.hmrc.depfrontend.filters

import akka.stream.Materializer
import com.typesafe.config.ConfigException
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import play.api.Configuration

class WhitelistFilterSpec extends WordSpec with MustMatchers with MockitoSugar {

  val materializer = mock[Materializer]

  "WhiteList Filter" must {

    "throw an exception when ip configurations are missing" in {
      val destination = ""
      val excluded = ""
      val config =
        Configuration(("filters.whitelist.destination" -> destination), ("filters.whitelist.excludedPaths" -> excluded))

      assertThrows[ConfigException] {
        new WhitelistFilter(config, materializer)
      }
    }

    "give an empty sequence for empty string" in {
      val destination = ""
      val excluded = ""
      val ips = ""
      val config = Configuration(
        ("filters.whitelist.destination"   -> destination),
        ("filters.whitelist.excludedPaths" -> excluded),
        ("filters.whitelist.ips"           -> ips)
      )

      val whitelistFilter = new WhitelistFilter(config, materializer)

      whitelistFilter.whitelist mustBe Seq.empty
    }

    "give a valid sequence for comma separated string" in {
      val destination = ""
      val excluded = ""
      val ips = "0.0.0.0, 255.255.255.255"
      val config = Configuration(
        ("filters.whitelist.destination"   -> destination),
        ("filters.whitelist.excludedPaths" -> excluded),
        ("filters.whitelist.ips"           -> ips)
      )

      val whitelistFilter = new WhitelistFilter(config, materializer)

      whitelistFilter.whitelist mustBe Seq("0.0.0.0", "255.255.255.255")
    }
  }
}


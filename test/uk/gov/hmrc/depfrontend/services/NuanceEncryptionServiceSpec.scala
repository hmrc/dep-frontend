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

package uk.gov.hmrc.depfrontend.services

import org.scalatest.{Matchers, WordSpec}
import play.api.Configuration

class NuanceEncryptionServiceSpec extends WordSpec with Matchers {

  "Crypto service" should {

    "encrypt plain text field which can be decrypted using correct algorithm" in {
      val configuration = Configuration.from(
        Map(
          "request-body-encryption.hashing-key" -> "yNhI04vHs9<_HWbC`]20u`37=NGLGYY5:0Tg5?y`W<NoJnXWqmjcgZBec@rOxb^G",
          "request-body-encryption.key" -> "QmFyMTIzNDVCYXIxMjM0NQ==",
          "request-body-encryption.previousKeys" -> List.empty
        )
      )

      val service = new NuanceEncryptionService(configuration)

      val fieldValue = "abc123"
      val expectedHash: String = service.nuanceSafeHash(fieldValue)
      val hashedValue: String = "PEc4edH3KVYOl8NZ1kN8jTVhO2YRWJh70BS55IhtgjjBe0iZVtnNp6iSi1gMhdgcNqnLMFEJXmaDORRB2VSNRwJJ"

      fieldValue shouldNot be(expectedHash)
      hashedValue shouldBe expectedHash
      expectedHash should fullyMatch regex "^[a-zA-Z0-9]*$"
    }
  }
}

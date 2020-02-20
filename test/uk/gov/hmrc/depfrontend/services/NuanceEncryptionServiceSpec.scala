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
import uk.gov.hmrc.crypto

class NuanceEncryptionServiceSpec extends WordSpec {

  class NuanceCryptoServiceTest(configuration: Configuration) extends NuanceEncryptionService(configuration) {

    def decryptField(cipherText: String): (String, String) = {
      val stripped: String = cipherText.stripPrefix("FIELD_PREFIX")
      val plainText: String = crypto.Crypted(stripped).value
      plainText.split("-").toList match {
        case ::(hashed, ::(raw, Nil)) => (hashed, raw)
        case _ => throw new RuntimeException(s"Unable to decrypt cipherText: $cipherText")
      }
    }


    "Crypto service" should {

      "encrypt plain text field which can be decrypted using correct algorithm" in {
        val configuration = Configuration.from(
          Map(
            "request-body-encryption.hashing-key" -> "yNhI04vHs9<_HWbC`]20u`37=NGLGYY5:0Tg5?y`W<NoJnXWqmjcgZBec@rOxb^G",
            "request-body-encryption.key" -> "QmFyMTIzNDVCYXIxMjM0NQ==",
            "request-body-encryption.previousKeys" -> List.empty
          )
        )

        val service = new NuanceCryptoServiceTest(configuration)

        val fieldValue = "abc123"

        val expectedHash: String = service.hashField(fieldValue)

        val (outputHash, outputRaw) = service.decryptField(fieldValue)

        outputHash shouldBe expectedHash // hash seems correct

        outputRaw shouldBe fieldValue // we got the original value out
      }
    }
  }
}
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

import com.google.inject.Inject
import play.api.Configuration
import uk.gov.hmrc.crypto.{PlainText, Scrambled, Sha512Crypto}

class NuanceEncryptionService @Inject()(configuration: Configuration) {

  private val baseSettingsKey = "request-body-encryption"
  private val hashingKey: String = configuration.get[String](s"$baseSettingsKey.hashing-key")

  protected lazy val hasher: Sha512Crypto = new Sha512Crypto(hashingKey)

  protected def hashValue(rawValue: String): Scrambled = hasher.hash(PlainText(rawValue))

  def hashField(rawValue: String): String = hashValue(rawValue).value

  /**
    * Make a Nuance safe hash value from a raw value by hashing and then
    * mapping non-alphanumeric characters to alphanumeric characters.
    * Why? Nuance cannot handle chars such as "+", "-", "%" etc
    * Algorithm takes any non-alpha char and maps to A - Z
    */
  def nuanceSafeHash(rawValue: String): String =
    hashField(rawValue)
      .map {
        char => if (char.isLetterOrDigit) char else ((char.toInt % 26) + 'A'.toInt).toChar
      }
}

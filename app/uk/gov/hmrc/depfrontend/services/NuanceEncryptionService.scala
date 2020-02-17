package uk.gov.hmrc.depfrontend.services

import com.google.inject.Inject
import play.api.Configuration
import uk.gov.hmrc.crypto.{PlainText, Scrambled, Sha512Crypto}

class NuanceEncryptionService @Inject()(configuration: Configuration){

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
  def naunceSafeHash(rawValue: String): String =
    hashField(rawValue)
      .map {
        char => if (char.isLetterOrDigit) char else ((char.toInt % 26) + 'A'.toInt).toChar
      }
}

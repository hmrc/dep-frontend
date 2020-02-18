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

package uk.gov.hmrc.depfrontend.models

import uk.gov.hmrc.depfrontend.services.NuanceEncryptionService
import uk.gov.hmrc.http.HeaderCarrier

case class EncryptedNuanceData(nuanceSessionId: String)

object EncryptedNuanceData {

  def create(encryptionService: NuanceEncryptionService, hc: HeaderCarrier): EncryptedNuanceData = {
    EncryptedNuanceData(
      encryptionService.nuanceSafeHash(sessionId(hc))
    )
  }

  private def sessionId(hc: HeaderCarrier): String =
    hc.sessionId.fold("")(_.value)
}


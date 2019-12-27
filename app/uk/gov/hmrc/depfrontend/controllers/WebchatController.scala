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

import javax.inject.{Inject, Singleton}
import play.api.i18n.Messages
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.depfrontend.config.AppConfig
import uk.gov.hmrc.depfrontend.views.html._
import uk.gov.hmrc.play.bootstrap.controller.FrontendController

import scala.concurrent.Future

@Singleton
class WebchatController @Inject()(appConfig: AppConfig,
                                  mcc: MessagesControllerComponents)
    extends FrontendController(mcc) {

  implicit val config: AppConfig = appConfig

  def webchat(from: Option[String]): Action[AnyContent] = Action.async {
    implicit request =>
      from match {
        case Some("self-assessment") => Future.successful(Ok(self_assessment()))
        case Some("tax-credits")     => Future.successful(Ok(tax_credits()))
        case _ =>
          Future.successful(Ok(web_chat()))
      }
  }

  def selfAssessment: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(self_assessment()))
  }

  def taxCredits: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(tax_credits()))
  }

  def childBenefit: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(child_benefit()))
  }

  def employerEnquiries: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(employer_enquiries()))
  }

  def vatEnquiries: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(vat_enquiries()))
  }

  def onlineServicesHelpdesk: Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(online_service_helpdesk()))
  }

  def nationalInsuranceNumbers: Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(national_insurance_numbers()))
  }

  def customsEnquiries: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(customs_enquiries()))
  }
}

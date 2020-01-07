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

import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.depfrontend.config.AppConfig
import uk.gov.hmrc.depfrontend.views.html._
import uk.gov.hmrc.play.bootstrap.controller.FrontendController

import scala.concurrent.Future

@Singleton
class WebchatController @Inject()(appConfig: AppConfig,
                                  mcc: MessagesControllerComponents,
                                  selfAssessmentView: SelfAssessmentView,
                                  taxCreditsView: TaxCreditsView,
                                  childBenefitView: ChildBenefitView,
                                  customsEnquiriesView: CustomsEnquiriesView,
                                  employerEnquiriesView: EmployerEnquiriesView,
                                  incomeTaxEnquiriesView: IncomeTaxEnquiriesView,
                                  nationalInsuranceNumbersView: NationalInsuranceNumbersView,
                                  onlineServiceHelpdeskView: OnlineServiceHelpdeskView,
                                  vatEnquiriesView: VatEnquiriesView,
                                  vatOnlineServiceHelpdeskView: VatOnlineServicesHelpdeskView,
                                  webChatView: WebChatView)
    extends FrontendController(mcc) {

  implicit val config: AppConfig = appConfig

  def webchat(from: Option[String]): Action[AnyContent] = Action.async {
    implicit request =>
      from match {
        case Some("self-assessment") => Future.successful(Ok(selfAssessmentView()))
        case Some("tax-credits")     => Future.successful(Ok(taxCreditsView()))
        case _ =>
          Future.successful(Ok(webChatView()))
      }
  }

  def selfAssessment: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(selfAssessmentView()))
  }

  def taxCredits: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(taxCreditsView()))
  }

  def childBenefit: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(childBenefitView()))
  }

  def employerEnquiries: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(employerEnquiriesView()))
  }

  def vatEnquiries: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(vatEnquiriesView()))
  }

  def onlineServicesHelpdesk: Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(onlineServiceHelpdeskView()))
  }

  def vatOnlineServicesHelpdesk: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(vatOnlineServiceHelpdeskView()))
  }

  def nationalInsuranceNumbers: Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(nationalInsuranceNumbersView()))
  }

  def customsEnquiries: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(customsEnquiriesView()))
  }

  def incomeTaxEnquiries: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(incomeTaxEnquiriesView()))
  }
}

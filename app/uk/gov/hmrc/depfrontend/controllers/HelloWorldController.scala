package uk.gov.hmrc.depfrontend.controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc._
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import uk.gov.hmrc.depfrontend.config.AppConfig
import uk.gov.hmrc.depfrontend.views.html.hello_world

import scala.concurrent.Future

@Singleton
class HelloWorldController @Inject()(appConfig: AppConfig, mcc: MessagesControllerComponents)
    extends FrontendController(mcc) {

  implicit val config: AppConfig = appConfig

  val helloWorld: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(hello_world()))
  }

}

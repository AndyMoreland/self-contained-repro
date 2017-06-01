package self.contained.user.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import self.contained.user.api.{EmailAndPassword, UserService}

import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl()(implicit ec: ExecutionContext) extends UserService {
  override def checkPassword: ServiceCall[EmailAndPassword, Boolean] = ServiceCall {
    _ => Future.successful(true)
  }
}
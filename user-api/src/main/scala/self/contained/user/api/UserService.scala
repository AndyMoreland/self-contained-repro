package self.contained.user.api

import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

object UserService {
  val USER_ENTITY_EVENT = "USER_ENTITY_EVENT"
}

trait UserService extends Service {
  def checkPassword: ServiceCall[EmailAndPassword, Boolean]

  override def descriptor: Descriptor = {
    import Service._
    named("user")
      .withCalls(
        pathCall("/rpc/user/password/check", checkPassword)
      )
  }
}

package self.contained.user.impl

import com.lightbend.lagom.scaladsl.client.ConfigurationServiceLocatorComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import play.api.Environment
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.mvc.EssentialFilter
import play.filters.cors.CORSComponents
import self.contained.user.api.UserService

import scala.concurrent.ExecutionContext


trait UserComponents
    extends LagomServerComponents
    with CassandraPersistenceComponents {

  implicit def executionContext: ExecutionContext

  def environment: Environment

  override lazy val lagomServer: LagomServer = LagomServer.forServices(
    bindService[UserService].to(wire[UserServiceImpl])
  )

  override lazy val jsonSerializerRegistry = UserSerializerRegistry

  persistentEntityRegistry.register(wire[UserEntity])
}

abstract class UserApplication(context: LagomApplicationContext)
    extends LagomApplication(context)
    with AhcWSComponents
    with UserComponents
    with CassandraPersistenceComponents
    with CORSComponents {
  override lazy val httpFilters: Seq[EssentialFilter] = Seq(corsFilter)
}

class UserApplicationLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext) =
    new UserApplication(context) with ConfigurationServiceLocatorComponents {

    }

  override def loadDevMode(context: LagomApplicationContext) =
    new UserApplication(context) with LagomDevModeComponents {
    }

  override def describeServices = List(
    readDescriptor[UserService]
  )
}

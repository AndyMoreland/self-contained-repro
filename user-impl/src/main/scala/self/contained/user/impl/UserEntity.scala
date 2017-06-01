package self.contained.user.impl

import com.lightbend.lagom.scaladsl.persistence.PersistentEntity


class UserEntity extends PersistentEntity {
  override type Command = Any
  override type State = Option[Any]
  override type Event = Any

  override def initialState: Option[Any] = None

  override def behavior: Behavior = {
    case None => notCreated
    case Some(user) => existing(user)
  }

  private val notCreated = {
    Actions()
  }

  private def existing(user: Any) = {
    Actions()
  }
}

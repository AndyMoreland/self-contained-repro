package self.contained.user.api

import self.contained.utils.AutoJson

@AutoJson
case class EmailAndPassword(email: String, password: String)

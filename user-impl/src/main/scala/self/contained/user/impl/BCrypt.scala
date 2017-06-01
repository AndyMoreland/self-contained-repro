package self.contained.user.impl

object BCrypt {
  def check(recoveryPassword: String, recDig: String) = true

  def generateSaltedHash(password: String) = "hi"

}

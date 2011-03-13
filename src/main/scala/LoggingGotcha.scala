package logginggotcha

trait Logger {
  
  private val loggerName = getClass.getName
  
  def info(msg: String): Unit = println(loggerName + ": " + msg)
}

case class User(username: String, password: String)

trait UserRepositoryComponent extends Logger {

  val userRepository: UserRepository
  
  class UserRepository {  
    def create(user: User) = {
       info("In UserRepository")
       user
    }
  }  
}

trait UserServiceComponent { 
      self: UserRepositoryComponent => 
  
  val userService: UserService

  class UserService extends Logger {  
    def create(username: String, password: String) = {  
      info("In userservice")
      userRepository.create(new User(username, password))  
    }
  }  
}

object ComponentRegistry extends  
  UserServiceComponent with   
  UserRepositoryComponent {
  
  val userRepository = new UserRepository  
  val userService = new UserService  
}

object Main extends Application {
  ComponentRegistry.userService.create("user", "pwd")
  // Prints out:
  // logginggotcha.UserServiceComponent$UserService: In userservice
  // logginggotcha.ComponentRegistry$: In UserRepository
  // Remember to instatiate logger at correct position (not on component!)
}
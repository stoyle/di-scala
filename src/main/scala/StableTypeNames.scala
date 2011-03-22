package stabletypenames
    
case class User(username: String, password: String)


trait UserRepository {
  def create(user: User): User
}

trait UserRepositoryComponent {

  val userRepository: UserRepository
  
  class UserRepositoryImpl extends UserRepository {  
    def create(user: User) = user
  }  
}


trait UserService {
  def create(username: String, password: String): User
}

trait UserServiceComponent { 
  self: UserRepositoryComponent => 
  
  val userService: UserService

  class UserServiceImpl extends UserService {  
    def create(username: String, password: String) =   
      userRepository.create(new User(username, password))  
  }  
}

object ComponentRegistry extends UserServiceComponent
                         with UserRepositoryComponent {

  val userRepository: UserRepository = new UserRepositoryImpl
  val userService: UserService = new UserServiceImpl
}
  
class TestRegistry extends UserServiceComponent
                   with UserRepositoryComponent {

  val userRepository: UserRepository = new UserRepositoryImpl
  val userService: UserService = new UserServiceImpl
}

object Usage {

  // Using stable external traits gives stable names. Check out NamingGotcha.scala to se the problem.
  val service1: UserService = ComponentRegistry.userService

  val testRegistry = new TestRegistry()
  val service2: UserService = testRegistry.userService

}

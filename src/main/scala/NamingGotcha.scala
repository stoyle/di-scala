package naminggotcha
    
case class User(username: String, password: String)
    
trait UserRepositoryComponent {

  val userRepository: UserRepository
  
  class UserRepository {  
    def create(user: User) = user
  }  
}

trait UserServiceComponent { 
  self: UserRepositoryComponent => 
  
  val userService: UserService

  class UserService {  
    def create(username: String, password: String) =   
      userRepository.create(new User(username, password))  
  }  
}                          

object ComponentRegistry extends UserServiceComponent
                         with UserRepositoryComponent {

  val userRepository: UserRepository = new UserRepository
  val userService: UserService = new UserService                        
}
  
class TestRegistry extends UserServiceComponent
                   with UserRepositoryComponent {

  val userRepository: UserRepository = new UserRepository
  val userService: UserService = new UserService                        
}

object Main extends Application {
  // Following is incorrect namespace, UserService belongs to object ComponentRegistry
  // val service: UserServiceComponent.UserService = ComponentRegistry.userService

  val service1: ComponentRegistry.UserService = ComponentRegistry.userService
  // type inference also fixes the problem
  val service2 = ComponentRegistry.userService
                                                
  // When created through a class type names are very different
  val testRegistry = new TestRegistry()
  val service3: testRegistry.UserService = testRegistry.userService

  // Type inference to the resque
  val service4 = testRegistry.userService
  
  val testRegistry2 = new TestRegistry()
  // Typenames follow instance. This it not allowed:
  // val service5: testRegistry.UserService = testRegistry2.userService

    
  // Should consider a stable external interface, e.g. TheUserService
  // val service5: TheUserService = ComponentRegistry.userService
}

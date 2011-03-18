package Cake

case class User(username: String, password: String)

trait UserRepositoryComponent {

  val userRepository: UserRepository
  
  class UserRepository {  
    def authenticate(user: User): User = {   
      println("authenticating user: " + user)  
      user  
    }  
    def create(user: User) = println("creating user: " + user)  
    def delete(user: User) = println("deleting user: " + user)  
  }  
}

trait UserServiceComponent { 
      self: UserRepositoryComponent => 
  
  val userService: UserService

  class UserService {  
    def authenticate(username: String, password: String): User =   
      userRepository.authenticate(new User(username, password))    
    def create(username: String, password: String) =   
      self.userRepository.create(new User(username, password))  
    def delete(user: User) = userRepository.delete(user)  
  }  
}

object ComponentRegistry extends  
  UserServiceComponent with   
  UserRepositoryComponent {
  
  val userRepository = new UserRepository  
  val userService = new UserService  
}

import org.mockito.Mockito._

trait TestingEnvironment extends  
  UserServiceComponent with  
  UserRepositoryComponent {
 
  val userRepository = mock(classOf[UserRepository])  
  val userService = new UserService  
}

object Main extends Application {
    // The "real thing"
    val userService = ComponentRegistry.userService  
    val user1 = userService.authenticate("user", "password")
    println("When authenticating againt ComponentRegistry: " + user1)
                       
    // Using a test environment
    val testEnv = new TestingEnvironment{}
    when(testEnv.userRepository.authenticate(User("user", "password"))).thenReturn(User("mock", "mockpwd"))
    val user2 = testEnv.userService.authenticate("user", "password")
    println("When authenticating againt TestingEnvironment: " + user2)
}
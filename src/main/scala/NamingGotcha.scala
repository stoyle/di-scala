package no.stoyle.domain {
    
  case class User(username: String, password: String)
    
}

package no.stoyle.repository {

import no.stoyle.domain._

  trait UserRepositoryComponent {

    val userRepository: UserRepository
  
    class UserRepository {  
      def create(user: User) = user
    }  
  }
}

package no.stoyle.service {

import no.stoyle.domain._
import no.stoyle.repository._

  trait UserServiceComponent { 
    self: UserRepositoryComponent => 
  
    val userService: UserService

    class UserService {  
      def create(username: String, password: String) =   
        userRepository.create(new User(username, password))  
    }  
  }                          
}

package no.stoyle.registry {

import no.stoyle.repository._
import no.stoyle.service._

  object ComponentRegistry extends UserServiceComponent
                           with UserRepositoryComponent {

    val userRepository: UserRepository = new UserRepository
    val userService: UserService = new UserService                        
  }

}

package no.stoyle.app {
  
import no.stoyle.registry._

  object Main extends Application {
    // Following is incorrect namespace, UserService belongs to object ComponentRegistry
    // val service: UserServiceComponent.UserService = ComponentRegistry.userService

    val service1: ComponentRegistry.UserService = ComponentRegistry.userService
    // type inference also fixes the problem
    val service2 = ComponentRegistry.userService
    val user = service2.create("user", "pwd")
    println(user)
  }
    
}


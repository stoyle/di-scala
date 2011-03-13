package ExceptionInInitializerGotcha

trait A {
    throw new RuntimeException("This would be the root cause")
}                                                                      

object Main extends Application {
    
    object ComponentManagerObject extends A
    
    try {
        ComponentManagerObject.toString
    }
    catch {
        case e: ExceptionInInitializerError => {
            println("When instantiated as part of an object, the root cause is what we are after: " + e.getCause)
        }
    }
    
    class ComponentManagerClass extends A
    
    try {
        new ComponentManagerClass
    }
    catch {
        case e: RuntimeException => {
            println("When instantiated as a part of a class, we get the real exception: " + e)
        }
    }
    
    
}
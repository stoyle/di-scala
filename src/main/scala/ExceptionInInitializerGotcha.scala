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
            println("When instantiated as part of an object, the root cause is what want: " + e.getCause)
            println("getMessage() is useless really: " + e.getMessage)
            println("printStackTrace() is a little bit better, but with uneccessary nesting:")
            e.printStackTrace
        }
    }
    
    class ComponentManagerClass extends A
    
    try {
        new ComponentManagerClass
    }
    catch {
        case e: RuntimeException => {
            println("When instantiated as a part of a class, we get the real exception straight away : " + e)
            println("getMessage() is useful: " + e.getMessage())
            println("printStackTrace() does not wrap anything: ")
            e.printStackTrace
        }
    }
    
    
}
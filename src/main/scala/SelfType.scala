package SelfType

trait A {
    def foo: Unit = println("In A")
    override def toString = "A"
}

trait B {
    self: A =>
    def bar: Unit = {
        self.foo
        println(self.string)
    }
    def string = "B"
}

object C extends B with A with Application {
    bar
}


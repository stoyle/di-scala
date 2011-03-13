package SelfType

trait A {
    def foo: Unit = println("In A.foo")
    override def toString = "A"
}

trait B {
    self: A =>
    def bar: Unit = {
        println("In B.bar")
        self.foo
        this.foo
    }   
}

trait C {
    self: B =>
    def baz: Unit = {
        // Does not compile
        // self.foo
        // this.foo

        println("In C.baz")
        bar
    }   
}

object Main extends C with B with A with Application {
    baz
}


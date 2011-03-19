package SelfType

trait A {
  def foo: Unit = println("In A.foo")
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

trait D {
  selfName: A with B =>
  
  def callSelfTypes {
    foo
    this.foo
    selfName.foo
    bar
    selfName.bar
    this.bar
  }
  
}

object Main extends C with B with A with Application {
    baz
}


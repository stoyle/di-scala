package Circular
trait A {
    self: B =>
}

trait B {
    self: A =>
}

class C extends A with B
class D extends B with A

object Foo {
    new B with A{}
    new A with B{}
    // Not legal
    // new A{} 
    // new B{}
}

// Circular inheritance not legal
// trait E extends F
// trait F extends E
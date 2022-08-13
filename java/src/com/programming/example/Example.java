package com.programming.example;

public abstract class Example {

    public static void main(String[] args) {
        Example example1 = new ExampleChild1();
        Example.Inner inner1 = example1.new Inner();
        inner1.innerMethod();

        Example example2 = new ExampleChild2();
        Example.Inner inner2 = example2.new Inner();
        inner2.innerMethod();
    }

    public abstract void abstractMethod();

    class Inner {

        public void innerMethod() {
            Example.this.abstractMethod();
        }
    }
}

class ExampleChild1 extends Example {

    @Override
    public void abstractMethod() {
        System.out.println("In ExampleChild1.abstractMethod()");
    }
}

class ExampleChild2 extends Example {

    @Override
    public void abstractMethod() {
        System.out.println("In ExampleChild2.abstractMethod()");
    }
}
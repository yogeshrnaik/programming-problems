package com.programming.interview;

import java.util.HashMap;
import java.util.Map;

public class SingletonHierarchy {
    public static void main(String[] args) {
        A a1 = new A();
        B b1 = new B();
        C c1 = new C();
        D d1 = new D();
        A a2 = new A();
        B b2 = new B();
        C c2 = new C();
        D d2 = new D();
    }
}

class A {

    private static final Map<String, Boolean> objectCreated = new HashMap<>();

    public A() {
        String className = this.getClass().getName();
        if (objectCreated.containsKey(className)) {
            throw new RuntimeException("not allowed to create more than one object of: " + className);
        }
        objectCreated.putIfAbsent(className, Boolean.TRUE);
    }
}

class B extends A {
}

class C extends A {
}

class D extends B {
}

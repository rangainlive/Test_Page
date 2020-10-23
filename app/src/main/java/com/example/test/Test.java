package com.example.test;

public class Test {
    private int id;
    private String test_name;
    private int no_of_test;

    public Test() {
    }

    public Test(int id, String test_name, int no_of_test) {
        this.id = id;
        this.test_name = test_name;
        this.no_of_test = no_of_test;
    }

    public int getId() {
        return id;
    }

    public String getTest_name() {
        return test_name;
    }

    public int getNo_of_test() {
        return no_of_test;
    }
}

package com.example.demo;

public class Customer {
    private int id;
    private String login;
    private double balance;

    public Customer(int id, String login, double balance) {
        this.id = id;
        this.login = login;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        String s = String.format(
                "Customer[id=%d, login='%s', balance=%.2g%n] ",
                id, login, balance);
        System.out.println(s);
        return s;
    }
}

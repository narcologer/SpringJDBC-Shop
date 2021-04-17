package com.example.demo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;


@RestController
public class CustomerController {
    private final AtomicLong counter = new AtomicLong();
    private ArrayList<String> passwords = new ArrayList<String>();
    private ArrayList<String> logged = new ArrayList<String>();
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/register")
    public String register(@RequestParam(value="login") String login, @RequestParam(value="pass") String password) {
        String res="";
        boolean logn = jdbcTemplate.queryForObject("SELECT EXISTS(SELECT LOGIN FROM customers WHERE LOGIN=?)", new Object[]{login}, Boolean.class);
        if (logn==true)
            res = "There is already such user";
        else {
            Customer c = new Customer((int) counter.incrementAndGet(), login, 8.0d);
            passwords.add(password);
            String sql = "Insert into customers(id,login,balance) values(" + c.getId() + ",'" + c.getLogin() + "'," + c.getBalance() + ")";
            jdbcTemplate.execute(sql);
            res="User registered";
        }
        return res;
    }

    @GetMapping("/viewreg")
    public String selectreg(){
        ArrayList<Customer> gett = new ArrayList<Customer>();
        jdbcTemplate.query(
                "SELECT * FROM customers",
                (rs, rowNum) -> new Customer(rs.getInt("id"), rs.getString("login"), rs.getDouble("balance"))
        ).forEach(customer -> gett.add(customer));
        String s="";
        for (int i=0; i<gett.size(); i++)
            s+=gett.get(i).toString();
        return s;
    }

    @GetMapping("/login")
    public String loginattempt(@RequestParam(value="login") String login, @RequestParam(value="pass") String password){
        String res="";
        ArrayList<Customer> gett = new ArrayList<Customer>();
        boolean logn = jdbcTemplate.queryForObject("SELECT EXISTS(SELECT LOGIN FROM customers WHERE LOGIN=?)", new Object[]{login}, Boolean.class);
        if (logn==false)
            res = "Wrong login";
        else
        {
            int id = jdbcTemplate.queryForObject("SELECT id FROM customers WHERE LOGIN=?", new Object[]{login}, Integer.class);
            String pass=passwords.get(id-1);
            if (!password.equals(pass))
                res="Wrong password";
            else
            {
                logged.add(login);
                res="Login succesful";
            }
        }
        return res;
    }

    @GetMapping("/logout")
    public String logoutattempt(@RequestParam(value="login") String login){
        String res="";
        if (!logged.contains(login))
            res = "No such user logged in";
        else {
            logged.remove(login);
            res="Logout succesful";
        }
        return res;
    }

    @GetMapping("/payment")
    public String paymentattempt(@RequestParam(value="login") String login){
        String res ="";
        if (!logged.contains(login))
            res = "No such user logged in";
        else
        {
            double bal = jdbcTemplate.queryForObject("SELECT balance FROM customers WHERE LOGIN=?", new Object[]{login}, Double.class);
            String sql = "Update customers set balance="+(bal-1.1)+" where login='"+login+"'";
            jdbcTemplate.execute(sql);
            jdbcTemplate.execute("Insert into payrecords(login,new_balance) values('"+login+"',"+(bal-1.1)+")");
            res="Payment succesful";
        }
        return res;
    }
}

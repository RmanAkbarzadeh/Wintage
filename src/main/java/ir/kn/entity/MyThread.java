package ir.kn.entity;

import ir.kn.dblayer.DbHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyThread extends Thread {

   private ResultSet rs;
    private String query;

    public MyThread(ResultSet rs, String query) {
        this.rs = rs;
        this.query = query;
    }

    @Override
    public void run() {
        try {
            rs = DbHelper.executeQuery(query);
            System.out.println("this thread " + this.getName() + " is working");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

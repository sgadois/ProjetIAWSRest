package SgadAmahRmal.ugmont_rest;

import java.sql.*;

/**
 * Created by mahamat on 28/03/15.
 */
public class GestionBase {
    private Connection connection;
    private Statement stat;

    public GestionBase() {
        this.connection();
    }

    private boolean connection() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:~/test");
            stat = connection.createStatement();
            stat.execute("drop table if EXISTS  film");
            stat.execute("create table film(imdbID varchar(255) primary key, " +
                    "title varchar(255)," +
                    "year varchar(255)," +
                    "rated varchar(255)," +
                    "released varchar(255)," +
                    "runtime varchar(255)," +
                    "genre varchar(255)," +
                    "director varchar(255)," +
                    "writer varchar(255)," +
                    "actors varchar(255)," +
                    "plot  varchar(1000)," +
                    "language varchar(255)," +
                    "country varchar(255)," +
                    "awards varchar(255)," +
                    "poster varchar(255)," +
                    "metascore varchar(255)," +
                    "imdbRating varchar(255)," +
                    "imdbVotes varchar(255)," +
                    "type varchar(255))");
            /*stat.execute("create table test(id int primary key, name varchar(255))");
            stat.execute("insert into test values(1, 'Hello')");*/
            ResultSet rs;
            /*rs = stat.executeQuery("select * from test");
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }*/

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void deconnection() {
        try {
            stat.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void executeSql(String intructionSql) {
        try {
            stat.execute(intructionSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

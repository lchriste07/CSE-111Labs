// STEP: Import required packages
import java.sql.*;

import javax.print.attribute.standard.Sides;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.nio.file.WatchService;
import java.io.File;

public class Lab_7 {
    private Connection c = null;
    private String dbName;
    private boolean isConnected = false;

    private int[] nationkey = new int[2];
    private String[] nationname = new String[2];

    private void openConnection(String _dbName) {
        dbName = _dbName;

        if (false == isConnected) {
            System.out.println("++++++++++++++++++++++++++++++++++");
            System.out.println("Open database: " + _dbName);

            try {
                String connStr = new String("jdbc:sqlite:");
                connStr = connStr + _dbName;

                // STEP: Register JDBC driver
                Class.forName("org.sqlite.JDBC");

                // STEP: Open a connection
                c = DriverManager.getConnection(connStr);

                // STEP: Diable auto transactions
                c.setAutoCommit(false);

                isConnected = true;
                System.out.println("success");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            System.out.println("++++++++++++++++++++++++++++++++++");
        }
    }

    private void closeConnection() {
        if (true == isConnected) {
            System.out.println("++++++++++++++++++++++++++++++++++");
            System.out.println("Close database: " + dbName);

            try {
                // STEP: Close connection
                c.close();

                isConnected = false;
                dbName = "";
                System.out.println("success");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            System.out.println("++++++++++++++++++++++++++++++++++");
        }
    }

    private void createTable() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Create table");
        
        try
        {
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE warehouse (" +
                "w_warehousekey decimal(9,0) not null,"+
                "w_name char(100) not null, "+
                "w_capacity decimal(6,0) not null," +
                "w_suppkey decimal(9,0) not null," + 
                "w_nationkey decimal(2,0) not null)";

            stmt.execute(sql);
            c.commit();
            stmt.close();

            System.out.println("success!");
        }
        catch (Exception e) 
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try 
            {
                c.rollback();
            } catch (Exception e1) 
            {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void insert(int w_warehousekey, String w_name, int w_capacity, int w_suppkey, int w_nationkey) {
        try
        {
            String sql = "INSERT INTO warehouse(w_warehousekey, w_name, w_capacity, w_suppkey, w_nationkey) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, w_warehousekey);
            stmt.setString(2, w_name);
            stmt.setInt(3, w_capacity);
            stmt.setInt(4, w_suppkey);
            stmt.setInt(5, w_nationkey);

            stmt.executeUpdate();
            c.commit();
            stmt.close();
        }
        catch (Exception e) 
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try 
            {
                c.rollback();
            } catch (Exception e1) 
            {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
    }

     private int Capacity(int _suppkey) {
        int w_cap = 0;

        try {
            String sql = "SELECT MAX(size) AS w_cap " +
            "FROM (SELECT s_suppkey AS sKey, s_name AS sName, n_name, n_nationkey, 2 * SUM(p_size) AS size " +
                "FROM supplier " +
                "JOIN customer ON c_custkey = o_custkey " +
                "JOIN nation ON c_nationkey = n_nationkey " +
                "JOIN orders ON o_orderkey = l_orderkey " +
                "JOIN lineitem ON l_suppkey = s_suppkey " +
                "JOIN part ON p_partkey = l_partkey " +
                "WHERE l_suppkey = ? " +
                "GROUP BY sKey, c_nationkey) AS R " +
            "GROUP BY R.sKey;";
            
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, _suppkey);
            ResultSet rs = stmt.executeQuery();

            w_cap = rs.getInt("w_cap");

            rs.close();
            stmt.close();
        }
        catch (Exception e) 
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try 
            {
                c.rollback();
            } catch (Exception e1) 
            {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }

        return w_cap;
    }

    private void Nation(int _suppkey) {
        try {
            String sql = "SELECT l_suppkey, n_name, n_nationkey, COUNT(*) AS nItems " +
            "FROM lineitem " +
            "JOIN orders ON o_orderkey = l_orderkey " +
            "JOIN customer ON c_custkey = o_custkey " +
            "JOIN nation ON n_nationkey = c_nationkey " +
            "WHERE l_suppkey = ? " +
            "GROUP BY l_suppkey, c_nationkey " +
            "ORDER BY l_suppkey, nItems DESC, n_name " +
            "LIMIT 2";

            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, _suppkey);
            ResultSet rs = stmt.executeQuery();

            nationkey[0] = rs.getInt("n_nationkey");
            nationname[0] = rs.getString("n_name");
            rs.next();
            rs.next();
            nationkey[1] = rs.getInt("n_nationkey");
            nationname[1] = rs.getString("n_name");

            rs.close();
            stmt.close();
        }
        catch (Exception e) 
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try 
            {
                c.rollback();
            } catch (Exception e1) 
            {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
    } 

    private void populateTable() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Populate table");

        try
        {
            String sql = "SELECT s_name, s_suppkey FROM supplier";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            int wId = 1;
            while(rs.next())
            {                
                int sId = rs.getInt("s_suppkey");
                int wCap = Capacity(sId);
                Nation(sId);

                for(int i = 0; i < 2; i++) {
                    String wName = rs.getString("s_name") + "___" + nationname[i];
                    int nId = nationkey[i];
    
                    insert(wId++, wName, wCap, sId, nId);
                }
            }

            rs.close();
            stmt.close();
        }
        catch (Exception e) 
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try 
            {
                c.rollback();
            } catch (Exception e1) 
            {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void dropTable() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Drop table");
        System.out.println("success");

        try
        {
            Statement stmt = c.createStatement();
            String sql = "DROP TABLE warehouse";

            stmt.execute(sql);
            c.commit();
            stmt.close();
        }
        catch (Exception e) 
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try 
            {
                c.rollback();
            } catch (Exception e1) 
            {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q1() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q1");

        try {
            FileWriter writer = new FileWriter("output/1.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT * FROM warehouse " +
                "GROUP BY w_warehousekey";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            printer.printf("%10s %-40s %10s %10s %10s\n", "wId", "wName", "wCap", "sId", "nId");

            while(rs.next()) {
                int wId = rs.getInt(1);
                String wName = rs.getString(2);
                int wCap = rs.getInt(3);
                int sId = rs.getInt(4);
                int nId = rs.getInt(5);

                printer.printf("%10s %-40s %10s %10s %10s\n", wId, wName, wCap, sId, nId);
            }

            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q2() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q2");

        try {
            FileWriter writer = new FileWriter("output/2.out", false);
            PrintWriter printer = new PrintWriter(writer);

            printer.printf("%-40s %10s %10s\n", "nation", "numW", "totCap");

            String sql = "SELECT n_name, count(*), sum(w_capacity) AS totCap FROM warehouse " +
            "JOIN nation ON n_nationkey = w_nationkey " +
            "GROUP BY n_name " +
            "ORDER BY totCap DESC";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String nName = rs.getString(1);
                int numW = rs.getInt(2);
                int totCap = rs.getInt(3);

                printer.printf("%-40s %10s %10s\n", nName, numW, totCap);
            }

            rs.close();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q3() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q3");

        try {
            File fn = new File("input/3.in");
            FileReader reader = new FileReader(fn);
            BufferedReader in = new BufferedReader(reader);
            String nation = in.readLine();
            in.close();

            FileWriter writer = new FileWriter("output/3.out", false);
            PrintWriter printer = new PrintWriter(writer);

            printer.printf("%-20s %-20s %-40s\n", "supplier", "nation", "warehouse");

            String sql = "SELECT s_name, n_name, w_name " +
            "FROM warehouse " +
            "JOIN supplier ON s_suppkey = w_suppkey " +
            "JOIN nation ON n_nationkey = s_nationkey " +
            "WHERE w_name LIKE ? " +
            "GROUP BY s_name ";

            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, "%" + nation + "%");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String sName = rs.getString(1);
                String nName = rs.getString(2);
                String wName = rs.getString(3);

                printer.printf("%-20s %-20s %-40s\n", sName, nName, wName);
            }

            rs.close();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q4() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q4");

        try {
            File fn = new File("input/4.in");
            FileReader reader = new FileReader(fn);
            BufferedReader in = new BufferedReader(reader);
            String region = in.readLine();
            int cap = Integer.parseInt(in.readLine());
            in.close();

            FileWriter writer = new FileWriter("output/4.out", false);
            PrintWriter printer = new PrintWriter(writer);

            printer.printf("%-40s %10s\n", "warehouse", "capacity");

            String sql = "SELECT w_name, w_capacity " +
            "FROM warehouse " +
            "JOIN nation ON n_nationkey = w_nationkey " +
            "JOIN region ON r_regionkey = n_regionkey " +
            "WHERE w_capacity > ? AND r_name = ? " +
            "GROUP BY w_capacity " +
            "ORDER BY w_capacity DESC";

            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, cap);
            stmt.setString(2, region);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String wName = rs.getString(1);
                int wCap = rs.getInt(2);

                printer.printf("%-40s %10s\n", wName, wCap);
            }

            rs.close();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q5() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q5");

        try {
            File fn = new File("input/5.in");
            FileReader reader = new FileReader(fn);
            BufferedReader in = new BufferedReader(reader);
            String nation = in.readLine();
            in.close();

            FileWriter writer = new FileWriter("output/5.out", false);
            PrintWriter printer = new PrintWriter(writer);

            printer.printf("%-20s %20s\n", "region", "capacity");

            String sql = "SELECT R1.r_name, " +
            "CASE " + 
                "WHEN R2.totcap IS NULL THEN 0 " +
                "ELSE R2.totcap " +    
            "END " +
            "FROM (" +
                "region AS R1 " +
                "LEFT JOIN " +
                "(SELECT r_name, SUM(w_capacity) as totcap " +
                    "FROM warehouse, nation suppnat " +
                    "JOIN supplier ON s_suppkey = w_suppkey " +
                    "JOIN nation N ON N.n_nationkey = w_nationkey " +
                    "JOIN region ON r_regionkey = N.n_regionkey " +
                    "WHERE suppnat.n_name = ? AND suppnat.n_nationkey = s_nationkey " +
                    "GROUP BY r_name) AS R2 " +
                "ON R1.r_name = R2.r_name) " +
            "ORDER BY R1.r_name";

            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, nation);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String rName = rs.getString(1);
                int wCap = rs.getInt(2);

                printer.printf("%-20s %20s\n", rName, wCap);
            }

            rs.close();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }


    public static void main(String args[]) {
        Lab_7 sj = new Lab_7();
        
        sj.openConnection("tpch.sqlite");

        sj.dropTable();
        sj.createTable();
        sj.populateTable();

        sj.Q1();
        sj.Q2();
        sj.Q3();
        sj.Q4();
        sj.Q5();

        sj.closeConnection();
    }
}

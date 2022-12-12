// STEP: Import required packages
import java.sql.*;

import javax.naming.spi.DirStateFactory.Result;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Lab_9 {
    private Connection c = null;
    private String dbName;
    private boolean isConnected = false;

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

    private void create_View1() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Create V1");

        try
        {
            String sql = "CREATE VIEW IF NOT EXISTS V1(c_custkey, c_name, c_address, " +
            "c_phone, c_acctbal, c_mktsegment, c_comment, c_nation, c_region) AS " + 
            "SELECT c_custkey, c_name, c_address, c_phone, c_acctbal, c_mktsegment, c_comment, n_name, r_name " +
            "FROM customer, nation, region " +
            "WHERE c_nationkey = n_nationkey AND n_regionkey = r_regionkey " +
            "GROUP BY c_custkey";
            Statement stmt = c.createStatement();
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

            try
            {
                String sql = "SELECT c_name, ROUND(sum(o_totalprice), 2) AS tot_price " +
                "FROM V1, orders " +
                "WHERE c_custkey = o_custkey AND c_nation = 'FRANCE' " +
                "AND o_orderdate LIKE '1995-___%' " +
                "GROUP BY c_name;";
                PreparedStatement stmt = c.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while(rs.next())
                {
                    String c_name = rs.getString("c_name");
                    double tot_price = rs.getDouble("tot_price");
                    printer.printf("%s|%s\n", c_name, tot_price);
                }

                rs.close();
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
                
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void create_View2() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Create V2");

        try
        {
            String sql = "CREATE VIEW IF NOT EXISTS V2(s_suppkey, s_name, s_address, " +
            "s_phone, s_acctbal, s_comment, s_nation, s_region) AS " + 
            "SELECT s_suppkey, s_name, s_address, s_phone, s_acctbal, s_comment, n_name, r_name " +
            "FROM supplier, nation, region " +
            "WHERE s_nationkey = n_nationkey AND n_regionkey = r_regionkey " +
            "GROUP BY s_suppkey";
            Statement stmt = c.createStatement();
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

    private void Q2() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q2");

        try {
            FileWriter writer = new FileWriter("output/2.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = " SELECT s_region, count(*) " +
            "FROM V2 " +
            "GROUP BY s_region";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String s_region = rs.getString("s_region");
                int count = rs.getInt("count(*)");
                printer.printf("%s|%s\n", s_region, count);
            }

            rs.close();
            c.commit();
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
            FileWriter writer = new FileWriter("output/3.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT c_nation, count(*) " +
            "FROM orders, V1 " +
            "WHERE c_custkey = o_custkey " +
            "    AND c_region = 'AMERICA' " +
            "GROUP BY c_nation";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String s_region = rs.getString("c_nation");
                int count = rs.getInt("count(*)");
                printer.printf("%s|%s\n", s_region, count);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
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
            FileWriter writer = new FileWriter("output/4.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT s_name, count(ps_partkey) " + 
            "FROM partsupp, part, V2 " +
            "WHERE p_partkey = ps_partkey " +
            "    AND ps_suppkey = s_suppkey " +
            "    AND s_nation = 'CANADA' " +
            "    and p_size < 20 " + 
            "GROUP BY s_name";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String s_name = rs.getString("s_name");
                int count = rs.getInt("count(ps_partkey)");
                printer.printf("%s|%s\n", s_name, count);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void create_View5() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Create V5");

        try
        {
            String sql = "CREATE VIEW IF NOT EXISTS V5(o_orderkey, o_custkey, o_orderstatus, o_totalprice, " +
            "o_orderyear, o_orderpriority, o_clerk, o_shippriority, o_comment) AS " + 
            "SELECT o_orderkey, o_custkey, o_orderstatus, o_totalprice, " +
            "   strftime('%Y', o_orderdate), o_orderpriority, o_clerk, o_shippriority, o_comment " +
            "FROM orders ";
            Statement stmt = c.createStatement();
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

    private void Q5() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q5");

        try {
            FileWriter writer = new FileWriter("output/5.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT c_name, count(*) " +
            "FROM V1, V5 " +
            "WHERE o_custkey = c_custkey " +
            "    AND c_nation = 'GERMANY' " +
            "    AND o_orderyear = '1993' " +
            "GROUP BY c_name";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String c_name = rs.getString("c_name");
                int count = rs.getInt("count(*)");
                printer.printf("%s|%s\n", c_name, count);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q6() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q6");

        try {
            FileWriter writer = new FileWriter("output/6.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT s_name, o_orderpriority, count(DISTINCT ps_partkey) " +
            "FROM partsupp, V5, lineitem, supplier, nation " +
            "WHERE l_orderkey = o_orderkey " +
            "    AND l_partkey = ps_partkey " +
            "    AND l_suppkey = ps_suppkey " +
            "    AND ps_suppkey = s_suppkey " +
            "    AND s_nationkey = n_nationkey " +
            "    AND n_name = 'CANADA' " +
            "GROUP BY s_name, o_orderpriority";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String s_name = rs.getString("s_name");
                String o_orderpriority = rs.getString("o_orderpriority");
                int count = rs.getInt("count(DISTINCT ps_partkey)");
                printer.printf("%s|%s|%s\n", s_name, o_orderpriority, count);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q7() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q7");

        try {
            FileWriter writer = new FileWriter("output/7.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT c_nation, o_orderstatus, count(*) " +
            "FROM V1, V5 " +
            "WHERE o_custkey = c_custkey AND c_region = 'AMERICA' " +
            "GROUP BY c_nation, o_orderstatus";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String c_nation = rs.getString("c_nation");
                String o_orderstatus = rs.getString("o_orderstatus");
                int count = rs.getInt("count(*)");
                printer.printf("%s|%s|%s\n", c_nation, o_orderstatus, count);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q8() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q8");

        try {
            FileWriter writer = new FileWriter("output/8.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT s_nation, count(DISTINCT l_orderkey) AS co " +
            "FROM V2, V5, lineitem " +
            "WHERE o_orderkey = l_orderkey " +
            "    AND l_suppkey = s_suppkey " +
            "    AND o_orderstatus = 'F' " +
            "    AND o_orderyear = '1995' " +
            "GROUP BY s_nation " +
            "HAVING co > 50";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String s_nation = rs.getString("s_nation");
                int co = rs.getInt("co");
                printer.printf("%s|%s\n", s_nation, co);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q9() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q9");

        try {
            FileWriter writer = new FileWriter("output/9.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT count(DISTINCT o_clerk) " +
            "FROM V2, V5, lineitem " +
            "WHERE o_orderkey = l_orderkey " +
            "    AND l_suppkey = s_suppkey " +
            "    AND s_nation = 'UNITED STATES'";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                int count = rs.getInt("count(DISTINCT o_clerk)");
                printer.printf("%s\n", count);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void create_View10() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Create V10");

        try
        {
            String sql = "CREATE VIEW IF NOT EXISTS V10(p_type, min_discount, max_discount) AS " + 
            "SELECT p_type, min(l_discount), max(l_discount) " +
            "FROM part, lineitem " +
            "WHERE p_partkey = l_partkey " +
            "GROUP BY p_type";
            Statement stmt = c.createStatement();
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

    private void Q10() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q10");

        try {
            FileWriter writer = new FileWriter("output/10.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT p_type, min_discount, max_discount " +
            "FROM V10 " +
            "WHERE p_type LIKE '%ECONOMY%' AND p_type LIKE '%COPPER%' " +
            "GROUP BY p_type";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String p_type = rs.getString("p_type");
                int min_discount = rs.getInt("min_discount");
                double max_discount = rs.getDouble("max_discount");
                printer.printf("%s|%s|%s\n", p_type, min_discount, max_discount);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q11() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q11");

        try {
            FileWriter writer = new FileWriter("output/11.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT s_region, s_name, s_acctbal FROM V2 s1 " +
            "WHERE s1.s_acctbal = (SELECT max(s_acctbal) " + 
                                    "FROM V2 s2 " + 
                                    "WHERE s1.s_region = s2.s_region) " +
            "GROUP BY s_region";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String region = rs.getString("s_region");
                String supp = rs.getString("s_name");
                double acctbal = rs.getDouble("s_acctbal");
                printer.printf("%s|%s|%s\n", region, supp, acctbal);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q12() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q12");

        try {
            FileWriter writer = new FileWriter("output/12.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT s_nation, max(s_acctbal) AS mb " +
            "FROM V2 " +
            "GROUP BY s_nation " +
            "HAVING mb > 9000";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String nation = rs.getString("s_nation");
                double mb = rs.getDouble("mb");
                printer.printf("%s|%s\n", nation, mb);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q13() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q13");

        try {
            FileWriter writer = new FileWriter("output/13.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT count(*) " +
            "FROM orders, lineitem, V1, V2 " +
            "WHERE o_orderkey = l_orderkey " +
                "AND o_custkey = c_custkey " +
                "AND l_suppkey = s_suppkey " +
                "AND s_region = 'AFRICA' " +
                "AND c_nation = 'UNITED STATES'";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String cnt = rs.getString("count(*)");
                printer.printf("%s\n", cnt);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void Q14() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q14");

        try {
            FileWriter writer = new FileWriter("output/14.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT s_region, c_region, max(o_totalprice) " +
            "FROM orders, lineitem, V1, V2 " +
            "WHERE l_suppkey = s_suppkey  " +
                "AND l_orderkey = o_orderkey " +
                "AND o_custkey = c_custkey " + 
            "GROUP BY s_region, c_region";

            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                String suppRegion = rs.getString("s_region");
                String custRegion = rs.getString("c_region");
                double max_price = rs.getDouble("max(o_totalprice)");
                printer.printf("%s|%s|%s\n", suppRegion, custRegion, max_price);
            }

            rs.close();
            c.commit();
            stmt.close();
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    private void create_View151() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Create V151");

        try
        {
            String sql = "CREATE VIEW IF NOT EXISTS V151(c_custkey, c_name, c_nationkey, c_acctbal) AS " + 
            "SELECT c_custkey, c_name, c_nationkey, c_acctbal " +
            "FROM customer " +
            "GROUP BY c_custkey " +
            "HAVING c_acctbal > 0";
            Statement stmt = c.createStatement();
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

    private void create_View152() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Create V152");
        try
        {
            String sql = "CREATE VIEW IF NOT EXISTS V152(s_suppkey, s_name, s_nationkey, s_acctbal) AS " + 
            "SELECT s_suppkey, s_name, s_nationkey, s_acctbal " +
            "FROM supplier " +
            "GROUP BY s_suppkey " +
            "HAVING s_acctbal < 0";
            Statement stmt = c.createStatement();
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

    private void Q15() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Q15");

        try {
            FileWriter writer = new FileWriter("output/15.out", false);
            PrintWriter printer = new PrintWriter(writer);

            String sql = "SELECT count(DISTINCT l_orderkey) " +
            "FROM lineitem, orders, V151, V152 " +
            "WHERE l_suppkey = s_suppkey " +
                "AND l_orderkey = o_orderkey " +
                "AND o_custkey = c_custkey";
            
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
    
            while(rs.next())
            {
                String count = rs.getString("count(DISTINCT l_orderkey)");
                printer.printf("%s\n", count);
            }
    
            rs.close();
            c.commit();
            stmt.close();    
            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
    }

    public static void main(String args[]) {
        Lab_9 sj = new Lab_9();
        
        sj.openConnection("tpch.sqlite");

        sj.create_View1();
        sj.Q1();

        sj.create_View2();
        sj.Q2();

        sj.Q3();
        sj.Q4();

        sj.create_View5();
        sj.Q5();

        sj.Q6();
        sj.Q7();
        sj.Q8();
        sj.Q9();

        sj.create_View10();
        sj.Q10();

        sj.Q11();
        sj.Q12();
        sj.Q13();
        sj.Q14();

        sj.create_View151();
        sj.create_View152();
        sj.Q15();

        sj.closeConnection();
    }
}

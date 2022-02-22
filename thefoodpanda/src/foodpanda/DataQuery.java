package foodpanda;

import javafx.scene.control.Alert;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class DataQuery {

    public static List<Product> getProducts(int orderType) {
        List<Product> productList = new ArrayList<>();
        OracleConnect oc = null;
        try {
            oc = new OracleConnect();
            String ext = null;
            if(orderType == 1){
                ext = "price";
            }
            else{
                ext = "id";
            }
            String query = "select * from products order by "+ ext;
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));
                productList.add(p);
            }
        } catch (Exception e) {
            System.out.println("Exception in listProducts: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return productList;
    }

    public static void addProduct(int id, String name, double price, String desc) {
        OracleConnect oc = null;
        try {
            oc = new OracleConnect();
            String query = String.format("select * from products where id = %d", id);
            ResultSet rs = oc.searchDB(query);
            if (rs.next()) {
                String message = "Product with this Id already exists";
                new DisplayMessage(message, Alert.AlertType.INFORMATION).display();
                //System.out.println(message);
            } else {
                String message = "Product inserted successfully";
                String insertQuery = String.format(
                        "insert into products(id, name, price, description) values (%d, '%s', %f, '%s')", id, name,
                        price, desc);
                oc.updateDB(insertQuery);
                new DisplayMessage(message, Alert.AlertType.INFORMATION).display();
            }
        } catch (Exception e) {
            String message = "insertion failed";
            System.out.println("Exception in addProduct: " + e);
            new DisplayMessage(message, Alert.AlertType.ERROR).display();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteProduct(int id){
        OracleConnect oc = null;

        try {
            oc = new OracleConnect();
            String deleteQuery = String.format(
                    "delete from products where id=%d", id);
            oc.updateDB(deleteQuery);
            new DisplayMessage("product deleted successfully", Alert.AlertType.INFORMATION).display();

        } catch (Exception e) {
            //e.printStackTrace();
            new DisplayMessage("couldn't be deleted!", Alert.AlertType.ERROR).display();
        }finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //add user

    public static void addUser(String userId, String userName,String userPassword) {
        OracleConnect oc = null;
        try {
            oc = new OracleConnect();
            String query = String.format("select * from persons where userid like '%s'", userId);
            ResultSet rs = oc.searchDB(query);
            if (rs.next()) {
                String message = "Invalid user";
                new DisplayMessage(message, Alert.AlertType.INFORMATION).display();
                //System.out.println(message);
            } else {
                String message = "user created successfully";
                String insertQuery = String.format(
                        "insert into persons(userid, username, password) values ('%s', '%s', '%s')", userId, userName,
                        userPassword);
                oc.updateDB(insertQuery);
                new DisplayMessage(message, Alert.AlertType.INFORMATION).display();
            }
        } catch (Exception e) {
            String message = "insertion failed";
            System.out.println("Exception in creating user: " + e);
            new DisplayMessage(message, Alert.AlertType.ERROR).display();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Person checkUser(String userId,String userPassword) {
        OracleConnect oc = null;
        Person person = null;
        try {
            oc = new OracleConnect();
            String query = String.format("select * from persons where userid like '%s'", userId);
            ResultSet rs = oc.searchDB(query);
            if (rs.next()) {
                String message = "valid user";
                String the_id = rs.getString("userid");
                String the_name = rs.getString("username");
                String the_password = rs.getString("password");
                person = new Person(the_id,the_name,the_password);
                System.out.println("result set :"+the_name);
                //new DisplayMessage(message, Alert.AlertType.INFORMATION).display();
                //System.out.println(message);
            } else {
                String message = "user doesn't exist";
                new DisplayMessage(message, Alert.AlertType.INFORMATION).display();

            }
        } catch (Exception e) {
            String message = "unknown error";
            System.out.println("Exception in log in: " + e);
            new DisplayMessage(message, Alert.AlertType.ERROR).display();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return person;
    }

    public static float billCalculation(List<Integer> itemId, List<Integer> itemCount){
        OracleConnect oc = null;
        float summation = 0;

        oc = new OracleConnect();
        try {
            CallableStatement callableStatement = oc.getConnection().prepareCall("{?= call func_get_bill(?,?)}");


            for(int i=0;i<itemId.size();i++){
                callableStatement.setInt(2, itemId.get(i));
                callableStatement.setInt(3,itemCount.get(i));
                callableStatement.registerOutParameter(1, Types.FLOAT);
                callableStatement.execute();
                summation += callableStatement.getInt(1);

            }
            //System.out.println("summation found:"+ summation);

        } catch (SQLException e) {
            new DisplayMessage("no data found", Alert.AlertType.ERROR).display();

            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return summation;
    }

    public static void insertOrder(Person person, String phone, float bill, List<Integer> itemId, List<Integer> itemCount){
        OracleConnect oc = null;
        oc = new OracleConnect();
        String date = "sysdate";
        String details = "";


        try {
            for(int i=0;i<itemId.size();i++){
                details += "<";
                details += itemId.get(i);
                details += ",";
                details += itemCount.get(i);
                details += "> ";
            }

            String insertQuery = String.format(
                    "INSERT INTO FOODORDER(PATRONID,ORDERDATE,CONTACT,BILL,DETAILS) values ('%s', sysdate, '%s', '%f', '%s')",
                    person.getUserId(),phone,bill,details
            );
            oc.updateDB(insertQuery);
            new DisplayMessage("inserted , bill:"+bill, Alert.AlertType.INFORMATION).display();
        } catch (Exception e) {
            e.printStackTrace();
            new DisplayMessage("failed to order!", Alert.AlertType.INFORMATION).display();
        }
    }


}

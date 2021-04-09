/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Car;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.MyConnection;

/**
 *
 * @author DELL
 */
public class CarDAO {
    public static ArrayList<Car> searchByName(String name) throws SQLException{
        ArrayList<Car> list = new ArrayList<>();
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select carID,name,color,year,img,price,quantity,rating,categoryID\n" +
                            "from tblCar \n" +
                            "where name like ?\n"
                            + "order by year desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, "%"+name+"%");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new Car(rs.getInt(1), rs.getString(2), rs.getString(3), 
                            rs.getInt(4), rs.getString(5), rs.getFloat(6), rs.getInt(7), 
                            rs.getInt(8), rs.getInt(9)));
            }
            cn.close();
        }
        return list;
    }
    public static ArrayList<Car> searchByCategory(int id) throws SQLException{
        ArrayList<Car> list = new ArrayList<>();
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select carID,name, color,year,img,price,quantity,rating,categoryID\n" +
                            "from tblCar \n" +
                            "where categoryID = ?\n"
                            + "order by year desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new Car(rs.getInt(1), rs.getString(2), rs.getString(3), 
                            rs.getInt(4), rs.getString(5), rs.getFloat(6), rs.getInt(7), 
                            rs.getInt(8), rs.getInt(9)));
            }
            cn.close();
        }
        return list;
    }
    public static Car getCarById(int id) throws SQLException{
        Car c = null;
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select carID,name,color,year,img,price,quantity,rating,categoryID\n" +
                            "from tblCar \n" +
                            "where carID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                c = new Car(rs.getInt(1), rs.getString(2), rs.getString(3), 
                            rs.getInt(4), rs.getString(5), rs.getFloat(6), rs.getInt(7), 
                            rs.getInt(8), rs.getInt(9));
            }
            cn.close();
        }
        return c;
    }
    public static int updateQuantity(int n, int id) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="update tblCar\n" +
                        "set quantity -= ?\n" +
                         "where carID = ?";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setInt(1, n);
            pst.setInt(2, id);
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
    public static int returnQuantity(int n, int id) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="update tblCar\n" +
                        "set quantity += ?\n" +
                         "where carID = ?";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setInt(1, n);
            pst.setInt(2, id);
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
    public static int updateRating(int n, int id) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="update tblCar\n" +
                        "set rating = ?\n" +
                         "where carID = ?";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setInt(1, n);
            pst.setInt(2, id);
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
}

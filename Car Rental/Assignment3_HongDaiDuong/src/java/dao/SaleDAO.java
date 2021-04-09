/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Sale;
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
public class SaleDAO {
    public static ArrayList<Sale> getSaleCode() throws SQLException{
        ArrayList<Sale> list = new ArrayList<>();
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql="select code,expireDate,percentSale\n" +
                        "from tblSale\n" +
                        "where percentSale > 0\n" +
                        "order by NEWID()";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new Sale(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
            cn.close();
        }
        return list;
    }
    public static Sale getSaleCodeById(String id) throws SQLException{
        Sale s = null;
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select code,expireDate,percentSale\n" +
                        "from tblSale\n" +
                        "where percentSale > 0\n" +
                        "and code = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
               s = new Sale(rs.getString(1), rs.getString(2), rs.getInt(3));
            }
            cn.close();
        }
        return s;
    }
}

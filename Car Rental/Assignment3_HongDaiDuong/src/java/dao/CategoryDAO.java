/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Category;
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
public class CategoryDAO {
    public static ArrayList<Category> getAllCategory() throws SQLException{
        ArrayList<Category> list = new ArrayList<>();
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql="select categoryID, [name]\n" +
                        "from tblCategory";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new Category(rs.getInt(1), rs.getString(2)));
            }
            cn.close();
        }
        return list;
    }
}

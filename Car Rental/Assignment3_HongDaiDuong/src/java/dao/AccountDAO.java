/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.MyConnection;

/**
 *
 * @author DELL
 */
public class AccountDAO {
    public static Account getAccount(String email, String password) throws SQLException{
        Account a=null;
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select email, [password], phone, [name], [address], createDate, isActive, code, isAdmin\n" +
                        "from tblAccount\n" +
                        "where email = ? and [password] = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                a = new Account(rs.getString(1), rs.getString(2), rs.getString(3), 
                                rs.getString(4), rs.getString(5), rs.getString(6), 
                                rs.getInt(7), rs.getString(8), rs.getInt(9));
            }
            cn.close();
        }
        return a;
    }
    public static Account getAccountById(String email) throws SQLException{
        Account a=null;
        Connection cn=MyConnection.makeConnection();
        if(cn!=null){
           String sql="select email, [password], phone, [name], [address], createDate, isActive, code, isAdmin\n" +
                        "from tblAccount\n" +
                        "where email = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                a = new Account(rs.getString(1), rs.getString(2), rs.getString(3), 
                                rs.getString(4), rs.getString(5), rs.getString(6), 
                                rs.getInt(7), rs.getString(8), rs.getInt(9));
            }
            cn.close();
        }
        return a; 
    }
    public static int createAccount(Account a) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="insert into tblAccount(email,[password],phone,[name],[address],createDate,isActive,code,isAdmin) \n" +
                        "values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setString(1, a.getEmail());
            pst.setString(2, a.getPassword());
            pst.setString(3, a.getPhone());
            pst.setString(4, a.getName());
            pst.setString(5, a.getAddress());
            pst.setString(6, a.getCreatedate());
            pst.setInt(7, a.getIsactive());
            pst.setString(8, a.getCode());
            pst.setInt(9, a.getIsadmin());
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
    public static int verifyAccount(String email) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="update tblAccount\n" +
                        "set isActive = 1\n" +
                        "where email = ?";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setString(1,email);
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
    public static int updateCode(String code, String email) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="update tblAccount\n" +
                        "set code = ?\n" +
                        "where email = ?";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setString(1, code);
            pst.setString(2, email);
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
}

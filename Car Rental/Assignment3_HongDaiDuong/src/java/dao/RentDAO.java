/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Rent;
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
public class RentDAO {
    public static int getNumberRent() throws SQLException{
        int result=0;
        Connection cn=MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select max(id)\n" +
                            "from tblRent";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                result = rs.getInt(1);
            }
            cn.close();
        }
        return result;
    }
    public static int addRent(Rent r) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="insert into tblRent(id,createDate,totalQuantity,totalPrice,payment,isDelete,email,isSale,code)\n" +
                        "values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setInt(1, r.getId());
            pst.setString(2, r.getCreatedate());
            pst.setInt(3, r.getTotalquantity());
            pst.setFloat(4, r.getTotalprice());
            pst.setString(5, r.getPayment());
            pst.setInt(6, r.getIsdelete());
            pst.setString(7, r.getEmail());
            pst.setInt(8, r.getIssale());
            pst.setString(9, r.getCode());
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
    public static ArrayList<Rent> getRentIsSale(String email) throws SQLException{
        ArrayList<Rent> list = new ArrayList<>();
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select id,createDate,totalQuantity,totalPrice,payment,isDelete,email,isSale,code\n" +
                        "from tblRent\n" +
                        "where email=?\n" +
                        "and isSale=1 and isDelete=0";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new Rent(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getFloat(4), 
                            rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getString(9)));
            }
            cn.close();
        }
        return list;
    }
    public static ArrayList<Rent> getRentNoDelete(String email) throws SQLException{
        ArrayList<Rent> list = new ArrayList<>();
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select id,createDate,totalQuantity,totalPrice,payment,isDelete,email,isSale,code\n" +
                            "from tblRent\n" +
                            "where isDelete = 0 and email = ?\n" +
                            "order by id desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new Rent(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getFloat(4), 
                            rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getString(9)));
            }
            cn.close();
        }
        return list;
    }
    
    public static int updateStatusDelete(int id) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="update tblRent\n" +
                        "set isDelete=1\n" +
                        "where id = ?";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setInt(1, id);
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
    public static ArrayList<Rent> getRentYesDelete(String email) throws SQLException{
        ArrayList<Rent> list = new ArrayList<>();
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select id,createDate,totalQuantity,totalPrice,payment,isDelete,email,isSale,code\n" +
                            "from tblRent\n" +
                            "where isDelete = 1 and email = ?\n" +
                            "order by id desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new Rent(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getFloat(4), 
                            rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getString(9)));
            }
            cn.close();
        }
        return list;
    }
    public static ArrayList<Rent> getRentForName(String email, String name) throws SQLException{
        ArrayList<Rent> list = new ArrayList<>();
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select distinct r.id,r.createDate,r.totalQuantity,r.totalPrice,r.payment,r.isDelete,r.email,r.isSale,r.code\n" +
                        "from tblRent r, tblRentDetail rd\n" +
                        "where r.id = rd.rentID and r.isDelete=0 and r.email=?\n" +
                        "and rd.carName like ?\n" +
                        "order by r.id desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, "%"+name+"%");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new Rent(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getFloat(4), 
                            rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getString(9)));
            }
            cn.close();
        }
        return list;
    }
}

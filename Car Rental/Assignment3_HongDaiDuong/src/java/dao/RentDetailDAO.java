/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.RentDetail;
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
public class RentDetailDAO {
    public static ArrayList<RentDetail> getDetailForSearch() throws SQLException{
        ArrayList<RentDetail> list = new ArrayList<>();
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select rentID,carID,carName,unitPrice,quantity,totalPrice,rentDate,returnDate,isFeedBack,rating,isReturn\n" +
                            "from tblRentDetail\n"
                            + "where isReturn = 0";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new RentDetail(rs.getInt(1), rs.getInt(2), rs.getString(3), 
                            rs.getFloat(4), rs.getInt(5), rs.getFloat(6), rs.getString(7), rs.getString(8), 
                            rs.getInt(9), rs.getInt(10), rs.getInt(11)));
            }
            cn.close();
        }
        return list;
    }
    public static int addRentDetail(RentDetail rd) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="insert into tblRentDetail(rentID,carID,carName,unitPrice,quantity,totalPrice,rentDate,returnDate,isFeedBack,rating,isReturn)\n" +
                        "values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setInt(1, rd.getRentid());
            pst.setInt(2, rd.getCarid());
            pst.setString(3, rd.getCarname());
            pst.setFloat(4, rd.getUnitprice());
            pst.setInt(5, rd.getQuantity());
            pst.setFloat(6, rd.getTotalprice());
            pst.setString(7, rd.getRentdate());
            pst.setString(8, rd.getReturndate());
            pst.setInt(9, rd.getIsfeedback());
            pst.setInt(10, rd.getRating());
            pst.setInt(11, rd.getIsreturn());
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
    public static ArrayList<RentDetail> getDetailByRentId(int id) throws SQLException{
        ArrayList<RentDetail> list = new ArrayList<>();
        Connection cn = MyConnection.makeConnection();
        if(cn!=null){
            String sql = "select rentID,carID,carName,unitPrice,quantity,totalPrice,rentDate,returnDate,isFeedBack,rating,isReturn\n" +
                            "from tblRentDetail\n" +
                            "where rentID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new RentDetail(rs.getInt(1), rs.getInt(2), rs.getString(3), 
                            rs.getFloat(4), rs.getInt(5), rs.getFloat(6), rs.getString(7), rs.getString(8), 
                            rs.getInt(9), rs.getInt(10), rs.getInt(11)));
            }
            cn.close();
        }
        return list;
    }
    public static int updateStatusReturn(int id) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="update tblRentDetail\n" +
                        "set isReturn=1\n" +
                        "where rentID = ?";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setInt(1, id);
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
    public static RentDetail getDetailForFeedBack(int rentid, int carid, String rentdate, String returndate) throws SQLException{
        RentDetail rd=null;
        Connection cn=MyConnection.makeConnection();
        if(cn!=null){
           String sql="select rentID,carID,carName,unitPrice,quantity,totalPrice,rentDate,returnDate,isFeedBack,rating,isReturn\n" +
                        "from tblRentDetail\n" +
                        "where rentID = ? and carID = ? and rentDate = ? and returnDate = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, rentid);
            pst.setInt(2, carid);
            pst.setString(3, rentdate);
            pst.setString(4, returndate);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                rd = new RentDetail(rs.getInt(1), rs.getInt(2), rs.getString(3), 
                            rs.getFloat(4), rs.getInt(5), rs.getFloat(6), rs.getString(7), rs.getString(8), 
                            rs.getInt(9), rs.getInt(10), rs.getInt(11));
            }
            cn.close();
        }
        return rd; 
    }
    public static int updateRating(int rate, int rentid, int carid, String rentdate, String returndate) throws SQLException{
        Connection cn=MyConnection.makeConnection();
        int result=0;
        if(cn!=null){
            String sql="update tblRentDetail\n" +
                        "set rating = ?, isFeedBack = 1\n" +
                        "where rentID = ? and carID = ? and rentDate = ? and returnDate = ?";
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setInt(1, rate);
            pst.setInt(2, rentid);
            pst.setInt(3, carid);
            pst.setString(4, rentdate);
            pst.setString(5, returndate);
            result=pst.executeUpdate();
            cn.close();
        }
        return result;
    }
}

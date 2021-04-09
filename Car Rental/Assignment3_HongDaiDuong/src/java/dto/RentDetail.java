/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.ArrayList;
import verify.CheckDate;

/**
 *
 * @author DELL
 */
public class RentDetail implements Serializable{
    private int rentid;
    private int carid;
    private String carname;
    private float unitprice;
    private int quantity;
    private float totalprice;
    private String rentdate;
    private String returndate;
    private int isfeedback;
    private int rating;
    private int isreturn;

    public RentDetail() {
    }

    public RentDetail(int rentid, int carid, String carname, float unitprice, int quantity, float totalprice, String rentdate, String returndate, int isfeedback, int rating, int isreturn) {
        this.rentid = rentid;
        this.carid = carid;
        this.carname = carname;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.totalprice = totalprice;
        this.rentdate = rentdate;
        this.returndate = returndate;
        this.isfeedback = isfeedback;
        this.rating = rating;
        this.isreturn = isreturn;
    }

    public int getRentid() {
        return rentid;
    }

    public void setRentid(int rentid) {
        this.rentid = rentid;
    }

    public int getCarid() {
        return carid;
    }

    public void setCarid(int carid) {
        this.carid = carid;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public float getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(float unitprice) {
        this.unitprice = unitprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(float totalprice) {
        this.totalprice = totalprice;
    }

    public String getRentdate() {
        return rentdate;
    }

    public void setRentdate(String rentdate) {
        this.rentdate = rentdate;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public int getIsfeedback() {
        return isfeedback;
    }

    public void setIsfeedback(int isfeedback) {
        this.isfeedback = isfeedback;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIsreturn() {
        return isreturn;
    }

    public void setIsreturn(int isreturn) {
        this.isreturn = isreturn;
    }
    
    public static ArrayList<RentDetail> getFollowInput(String rentdate, String returndate, ArrayList<RentDetail> detail){
        ArrayList<RentDetail> list = new ArrayList<>();
        for (RentDetail rd : detail) {
            if(CheckDate.convertDate(rentdate).compareTo(CheckDate.convertDate(rd.getReturndate()))>0){
                list.add(rd);
            }
            else if(CheckDate.convertDate(rentdate).compareTo(CheckDate.convertDate(rd.getRentdate()))<0 &&
                    CheckDate.convertDate(returndate).compareTo(CheckDate.convertDate(rd.getRentdate()))<0){
                list.add(rd);
            }
        }
        return list;
    }
}

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
public class Cart implements Serializable{
    private Car car;
    private int amount;
    private String rentdate;
    private String returndate;

    public Cart() {
    }

    public Cart(Car car, int amount, String rentdate, String returndate) {
        this.car = car;
        this.amount = amount;
        this.rentdate = rentdate;
        this.returndate = returndate;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
    public static int isExist(int id, String rentdate, String returndate, ArrayList<Cart> list){
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getCar().getId() == id && 
                CheckDate.convertDate(rentdate).compareTo(CheckDate.convertDate(list.get(i).getRentdate()))==0 &&
                CheckDate.convertDate(returndate).compareTo(CheckDate.convertDate(list.get(i).getReturndate()))==0){
                return i;
            }
        }
        return -1;
    }
    
    public static ArrayList<Cart> getDateLess(String rentdate, String returndate, ArrayList<Cart> list){
        ArrayList<Cart> cart = new ArrayList<>();
        for (Cart c : list) {
            if(CheckDate.convertDate(c.getRentdate()).compareTo(CheckDate.convertDate(rentdate))>=0 &&
                    CheckDate.convertDate(c.getRentdate()).compareTo(CheckDate.convertDate(returndate))<=0){
                cart.add(c);
            }
            else if(CheckDate.convertDate(c.getRentdate()).compareTo(CheckDate.convertDate(rentdate))<=0 &&
                    CheckDate.convertDate(c.getReturndate()).compareTo(CheckDate.convertDate(rentdate))>=0 ){
                cart.add(c);
            }
        }
        return cart;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class Rent implements Serializable{
    private int id;
    private String createdate;
    private int totalquantity;
    private float totalprice;
    private String payment;
    private int isdelete;
    private String email;
    private int issale;
    private String code;

    public Rent() {
    }

    public Rent(int id, String createdate, int totalquantity, float totalprice, String payment, int isdelete, String email, int issale, String code) {
        this.id = id;
        this.createdate = createdate;
        this.totalquantity = totalquantity;
        this.totalprice = totalprice;
        this.payment = payment;
        this.isdelete = isdelete;
        this.email = email;
        this.issale = issale;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public int getTotalquantity() {
        return totalquantity;
    }

    public void setTotalquantity(int totalquantity) {
        this.totalquantity = totalquantity;
    }

    public float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(float totalprice) {
        this.totalprice = totalprice;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIssale() {
        return issale;
    }

    public void setIssale(int issale) {
        this.issale = issale;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}

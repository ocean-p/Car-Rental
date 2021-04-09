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
public class AccountErr implements Serializable{
    private String emailErr;
    private String passErr;
    private String cpassErr;
    private String phoneErr;
    private String nameErr;
    private String addErr;

    public AccountErr() {
    }

    public AccountErr(String emailErr, String passErr, String cpassErr, String phoneErr, String nameErr, String addErr) {
        this.emailErr = emailErr;
        this.passErr = passErr;
        this.cpassErr = cpassErr;
        this.phoneErr = phoneErr;
        this.nameErr = nameErr;
        this.addErr = addErr;
    }

    public String getEmailErr() {
        return emailErr;
    }

    public void setEmailErr(String emailErr) {
        this.emailErr = emailErr;
    }

    public String getPassErr() {
        return passErr;
    }

    public void setPassErr(String passErr) {
        this.passErr = passErr;
    }

    public String getCpassErr() {
        return cpassErr;
    }

    public void setCpassErr(String cpassErr) {
        this.cpassErr = cpassErr;
    }

    public String getPhoneErr() {
        return phoneErr;
    }

    public void setPhoneErr(String phoneErr) {
        this.phoneErr = phoneErr;
    }

    public String getNameErr() {
        return nameErr;
    }

    public void setNameErr(String nameErr) {
        this.nameErr = nameErr;
    }

    public String getAddErr() {
        return addErr;
    }

    public void setAddErr(String addErr) {
        this.addErr = addErr;
    }
    
}

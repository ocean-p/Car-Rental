/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verify;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class CheckDate {
    public static boolean isValid(String s) {
        boolean test = false;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //Date date = null;
        df.setLenient(false);
        try {
            df.parse(s);
            test = true;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return test;
    }

    public static Date convertDate(String a) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        df.setLenient(false);
        try {
            date = df.parse(a);
        } catch (Exception e) {
            
        }
        return date;
    }
}

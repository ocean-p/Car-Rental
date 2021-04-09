/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.AccountDAO;
import dto.Account;
import dto.AccountErr;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import verify.ActiveEmail;

/**
 *
 * @author DELL
 */
@WebServlet(name = "CreateServlet", urlPatterns = {"/CreateServlet"})
public class CreateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            String email = request.getParameter("txtemail").trim();
            String name = request.getParameter("txtname").trim();
            String password = request.getParameter("txtpass").trim();
            String confirm = request.getParameter("txtconfirm").trim();
            String phone = request.getParameter("txtphone").trim();
            String address = request.getParameter("txtaddress").trim();
            
            boolean err = false;
            AccountErr ae = new AccountErr();
            //email
            if(email.length()<1){
                err = true;
                ae.setEmailErr("Email is not empty");
            }
            else if(!email.matches("^[a-zA-Z][a-zA-Z0-9_]+@[a-zA-Z]+(\\.[a-zA-Z]+){1,3}$")){
                err = true;
                ae.setEmailErr("Email must be correct fomart");
            }
            else if(email.length()>50){
                err = true;
                ae.setEmailErr("Email's max length is 50");
            }
            else if(AccountDAO.getAccountById(email) != null){
                err = true;
                ae.setEmailErr("This email existed");
            }
            //end email
            //password
            if(password.length()<6){
                err = true;
                ae.setPassErr("Password's length must >= 6");
            }
            else if(!password.matches("^[a-zA-Z0-9]+$")){
                err = true;
                ae.setPassErr("Password must be letters or numbers");
            }
            else if(!confirm.equals(password)){
                err = true;
                ae.setCpassErr("Confirm does not match");
            }
            //end password
            //phone
            if(!phone.matches("^[0-9]{10,11}$")){
                err = true;
                ae.setPhoneErr("Phone must be 10 or 11 numbers");
            }
            //end phone
            //name
            if(name.length()<5 || name.length()>50){
                err = true;
                ae.setNameErr("Name's length is 5 between 50");
            }
            //end name
            //address
            if(address.length()<5 || address.length()>50){
                err = true;
                ae.setAddErr("Address's length is 5 between 50");
            }
            //end address
            
            request.setAttribute("ERRORS", ae);
            String url = "create.jsp";
            if(err == false){
                //create date
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dt = new Date();
                String date = df.format(dt);
                //
                //active code
                ActiveEmail active = new ActiveEmail();
                String code = active.getRandom();
                //
                Account a = new Account(email, password, phone, name, address, date, 0, code, 0);
                int res = AccountDAO.createAccount(a);
                if(res > 0){
                    boolean test = active.sendEmail(a);
                    if(test){
                        url = "active.jsp";
                        HttpSession s = request.getSession();
                        s.setAttribute("remember", a);
                    }
                }
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

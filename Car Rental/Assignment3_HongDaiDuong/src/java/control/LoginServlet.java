/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.AccountDAO;
import dao.CategoryDAO;
import dto.Account;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import recaptcha.RecaptchaValid;

/**
 *
 * @author DELL
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private final String INVALID = "login.jsp";
    private final String RENTER = "home.jsp";
    private final String ADMIN = "admin.jsp";
    private final String VERIFY = "active.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String email = request.getParameter("email").trim();
            String pass = request.getParameter("password").trim();
            Account a = AccountDAO.getAccount(email, pass);
            RecaptchaValid r = new RecaptchaValid();
            boolean v = r.isValid(request.getParameter("g-recaptcha-response"));
            HttpSession s = request.getSession();
            if(v){
                if(a != null){
                    if(a.getIsactive() == 1){
                        s.setAttribute("user", a);
                        if(a.getIsadmin() == 0){
                            request.setAttribute("category", CategoryDAO.getAllCategory());
                            request.getRequestDispatcher(RENTER).forward(request, response);
                        }
                        else{
                            request.getRequestDispatcher(ADMIN).forward(request, response);
                        }
                        //end if isadmin
                    }
                    else{
                        s.setAttribute("remember", a);
                        request.getRequestDispatcher(VERIFY).forward(request, response);
                    }
                    //end if isactive
                }
                else{
                    request.setAttribute("error", "Email or Password is invalid !");
                    request.getRequestDispatcher(INVALID).forward(request, response);
                }
                //end if a != null
            }
            else{
                request.setAttribute("error", "You missed the reCAPTCHA !");
                request.getRequestDispatcher(INVALID).forward(request, response);
            }
            // end if recaptcha
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.RentDetailDAO;
import dto.RentDetail;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import verify.CheckDate;

/**
 *
 * @author DELL
 */
@WebServlet(name = "FormSerlvet", urlPatterns = {"/FormServlet"})
public class FormSerlvet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            request.setAttribute("form", 1);
            String rentid = request.getParameter("rentid");
            String carid = request.getParameter("carid");
            String rentdate = request.getParameter("rentdate");
            String returndate = request.getParameter("returndate");
            RentDetail rd = RentDetailDAO.getDetailForFeedBack(Integer.parseInt(rentid), Integer.parseInt(carid), rentdate, returndate);
            if(rd!=null){
                
                if(rd.getIsfeedback() == 1){
                    request.setAttribute("already", 1);
                }
                else {
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date dt = new Date();
                    String current = df.format(dt);
                    if(CheckDate.convertDate(current).compareTo(CheckDate.convertDate(rd.getReturndate())) >= 0){
                        request.setAttribute("canfeedback", 1);
                    }
                    else{
                        request.setAttribute("notfeedback", 1);
                    }
                }
                
                request.setAttribute("rd", rd);
            }
            else{
                request.setAttribute("nothing", 1);
            }
            request.getRequestDispatcher("history1.jsp").forward(request, response);
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

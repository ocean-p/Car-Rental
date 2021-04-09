/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.CarDAO;
import dao.RentDAO;
import dao.RentDetailDAO;
import dto.RentDetail;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@WebServlet(name = "CancelServlet", urlPatterns = {"/CancelServlet"})
public class CancelServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String id = request.getParameter("id");
            ArrayList<RentDetail> detail = RentDetailDAO.getDetailByRentId(Integer.parseInt(id));
            //current date
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dt = new Date();
            String current = df.format(dt);
            boolean not = false;
            for (RentDetail rd : detail) {
                if(CheckDate.convertDate(current).compareTo(CheckDate.convertDate(rd.getRentdate())) >= 0){
                    not = true;
                }
            }
            if (not == true) {
                request.setAttribute("notcancel", "Cancel the Rental ID:" + id + " fail. Beacause the rental date came in.");
            } 
            else {
                int a = RentDAO.updateStatusDelete(Integer.parseInt(id));
                int b = RentDetailDAO.updateStatusReturn(Integer.parseInt(id));
                int c = 0;
                for (RentDetail rd : detail) {
                    c = CarDAO.returnQuantity(rd.getQuantity(), rd.getCarid());
                }
                if (a > 0 && b > 0 && c > 0) {
                    request.setAttribute("cancancel", "Cancel the Rental ID:" + id + " success.");
                } else {
                    request.setAttribute("notcancel", "Cancel the Rental ID:" + id + " fail.");
                }
            }

            request.getRequestDispatcher("History1Servlet").forward(request, response);
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

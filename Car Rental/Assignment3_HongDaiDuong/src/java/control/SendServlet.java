/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.CarDAO;
import dao.RentDetailDAO;
import dto.Car;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
@WebServlet(name = "SendServlet", urlPatterns = {"/SendServlet"})
public class SendServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String rentid = request.getParameter("rentid");
            String carid = request.getParameter("carid");
            String rentdate = request.getParameter("rentdate");
            String returndate = request.getParameter("returndate");
            String tmp = request.getParameter("rate").trim();
            String rate = tmp.split("/")[0].trim();
            int a = RentDetailDAO.updateRating(Integer.parseInt(rate),Integer.parseInt(rentid), 
                                    Integer.parseInt(carid), rentdate, returndate);
            Car car = CarDAO.getCarById(Integer.parseInt(carid));
            int rating = (car.getRating()+Integer.parseInt(rate))/2;
            int b = CarDAO.updateRating(rating, Integer.parseInt(carid));
            if(a>0 && b>0){
                request.getRequestDispatcher("DetailServlet?id="+rentid).forward(request, response);
            }
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

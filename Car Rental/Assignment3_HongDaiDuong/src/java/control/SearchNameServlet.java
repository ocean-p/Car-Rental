/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.RentDAO;
import dao.RentDetailDAO;
import dto.Account;
import dto.Rent;
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
import javax.servlet.http.HttpSession;
import verify.CheckDate;

/**
 *
 * @author DELL
 */
@WebServlet(name = "SearchNameServlet", urlPatterns = {"/SearchNameServlet"})
public class SearchNameServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("user")!=null){
                String name = request.getParameter("txtname").trim();
                if (!name.isEmpty()) {
                    Account a = (Account) session.getAttribute("user");
                    ArrayList<Rent> rent = RentDAO.getRentForName(a.getEmail(), name);
                    ArrayList<Integer> store1 = new ArrayList<>();
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date dt = new Date();
                    String current = df.format(dt);
                    for (Rent r : rent) {
                        ArrayList<RentDetail> detail = RentDetailDAO.getDetailByRentId(r.getId());
                        for (RentDetail rd : detail) {
                            if (CheckDate.convertDate(rd.getRentdate()).compareTo(CheckDate.convertDate(current)) > 0) {
                                store1.add(r.getId());
                            }
                        }
                    }
                    ArrayList<Integer> store2 = new ArrayList<>();
                    for (int i = 0; i < store1.size(); i++) {
                        if (!store2.contains(store1.get(i))) {
                            store2.add(store1.get(i));
                        }
                    }
                    request.setAttribute("history", rent);
                    request.setAttribute("store", store2);
                    request.setAttribute("searchevent", 1);
                }
                else{
                     request.setAttribute("errname", "Name not empty.");
                }
                request.getRequestDispatcher("history1.jsp").forward(request, response);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dto.Cart;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
@WebServlet(name = "RemoveServlet", urlPatterns = {"/RemoveServlet"})
public class RemoveServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            
            if (session.getAttribute("cart") != null) {
                ArrayList<Cart> cart = (ArrayList<Cart>) session.getAttribute("cart");
                String id = request.getParameter("id").trim();
                String rentdate = request.getParameter("rent").trim();
                String returndate = request.getParameter("return").trim();
                if (!id.isEmpty() && !rentdate.isEmpty() && !returndate.isEmpty()) {
                    int index = Cart.isExist(Integer.parseInt(id), rentdate, returndate, cart);
                    if (index >= 0) {
                        cart.remove(index);
                    }
                    if (cart.isEmpty()) {
                        if (session.getAttribute("sale") != null) {
                            session.removeAttribute("sale");
                        }
                        session.removeAttribute("cart");
                    } else {
                        session.setAttribute("cart", cart);
                    }
                }
            }

            response.sendRedirect("viewcart.jsp");
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

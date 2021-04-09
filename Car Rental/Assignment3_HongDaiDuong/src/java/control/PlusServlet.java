/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.CarDAO;
import dao.RentDetailDAO;
import dto.Car;
import dto.Cart;
import dto.RentDetail;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "PlusServlet", urlPatterns = {"/PlusServlet"})
public class PlusServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("cart")!=null){
                ArrayList<Cart> cart = (ArrayList<Cart>) session.getAttribute("cart");
                String carid = request.getParameter("id").trim();
                String rentdate = request.getParameter("rent").trim();
                String returndate = request.getParameter("return").trim();
                if(!carid.isEmpty() && !rentdate.isEmpty() && !returndate.isEmpty()){
                    ArrayList<RentDetail> detailtmp = RentDetailDAO.getDetailForSearch();
                    ArrayList<RentDetail> detail = RentDetail.getFollowInput(rentdate, returndate, detailtmp);
                    ArrayList<Cart> less = Cart.getDateLess(rentdate, returndate, cart);
                    Car car = CarDAO.getCarById(Integer.parseInt(carid));
                    for (Cart les : less) {
                        if (les.getCar().getId() == car.getId() && 
                                CheckDate.convertDate(rentdate).compareTo(CheckDate.convertDate(les.getRentdate())) == 0 &&
                                CheckDate.convertDate(returndate).compareTo(CheckDate.convertDate(les.getReturndate())) == 0) {
                            
                        }
                        else if(les.getCar().getId() == car.getId()){
                            car.setQuantity(car.getQuantity() - les.getAmount());
                        }
                    }
                    for (RentDetail rd : detail) {
                        if (rd.getCarid() == car.getId()) {
                            car.setQuantity(car.getQuantity() + rd.getQuantity());
                        }
                    }
                    int index = Cart.isExist(Integer.parseInt(carid), rentdate, returndate, cart);
                    int current = cart.get(index).getAmount();
                    if(current < car.getQuantity()){
                        cart.get(index).setAmount(current+1);
                        session.setAttribute("cart", cart);
                    }
                    else{
                        String warning = "During "+rentdate+ " - "+returndate+ " , the car with id: "+carid+" , available: "+car.getQuantity();
                        request.setAttribute("warning", warning);
                    }
                    request.getRequestDispatcher("viewcart.jsp").forward(request, response);
                }
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

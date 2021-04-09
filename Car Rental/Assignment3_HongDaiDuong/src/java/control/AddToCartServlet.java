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
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            request.setAttribute("adding", 1);
            HttpSession session = request.getSession();
            if(session.getAttribute("user") == null){
                response.sendRedirect("goLogin");
            }
            else{
                String carid = request.getParameter("id").trim();
                String rentdate = request.getParameter("rent").trim();
                String returndate = request.getParameter("return").trim();
                String amount = request.getParameter("amount").trim();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date dt = new Date();
                String current = df.format(dt);
                if(CheckDate.convertDate(rentdate).compareTo(CheckDate.convertDate(current))>0 &&
                    CheckDate.convertDate(returndate).compareTo(CheckDate.convertDate(rentdate))>0 && 
                    Integer.parseInt(amount)>0 && CarDAO.getCarById(Integer.parseInt(carid))!=null){
                    //
                    ArrayList<RentDetail> detailtmp = RentDetailDAO.getDetailForSearch();
                    ArrayList<RentDetail> detail = RentDetail.getFollowInput(rentdate, returndate, detailtmp);
                    
                    if(session.getAttribute("cart") == null){
                        ArrayList<Cart> list = new ArrayList<>();
                        Car car = CarDAO.getCarById(Integer.parseInt(carid));
                        for (RentDetail rd : detail) {
                            if(rd.getCarid() == car.getId()){
                                car.setQuantity(car.getQuantity()+rd.getQuantity());
                            }
                        }
                        if (Integer.parseInt(amount) <= car.getQuantity()) {
                            list.add(new Cart(CarDAO.getCarById(Integer.parseInt(carid)),
                                    Integer.parseInt(amount), rentdate, returndate));
                            session.setAttribute("cart", list);
                            request.setAttribute("success", "Add to cart success.");
                        }
                        else{
                            request.setAttribute("fail", "Add to cart fail. The amount is greater than the available");
                        }
                        
                    }
                    else{
                        ArrayList<Cart> list = (ArrayList<Cart>) session.getAttribute("cart");
                        ArrayList<Cart> less = Cart.getDateLess(rentdate, returndate, list);
                        Car car = CarDAO.getCarById(Integer.parseInt(carid));
                        for (Cart les : less) {
                            if (les.getCar().getId() == car.getId()) {
                                car.setQuantity(car.getQuantity() - les.getAmount());
                            }
                        }
                        for (RentDetail rd : detail) {
                            if(rd.getCarid() == car.getId()){
                                car.setQuantity(car.getQuantity()+rd.getQuantity());
                            }
                        }
                        int index = Cart.isExist(Integer.parseInt(carid), rentdate, returndate, list);
                        if (index < 0) {
                            
                            if (Integer.parseInt(amount) <= car.getQuantity()) {
                                list.add(new Cart(CarDAO.getCarById(Integer.parseInt(carid)),
                                        Integer.parseInt(amount), rentdate, returndate));
                                request.setAttribute("success", "Add to cart success. The new item has been added.");
                            } else {
                                request.setAttribute("fail", "Add to cart fail. The amount is greater than the available");
                            }
                        }
                        else{
                          
                            if(Integer.parseInt(amount) <= car.getQuantity()){
                               int update = list.get(index).getAmount() + Integer.parseInt(amount); 
                               list.get(index).setAmount(update); 
                               request.setAttribute("success", "Add to cart success. The amount has been updated.");
                            }
                            else{
                                request.setAttribute("fail", "Add to cart fail. The amount is greater than the available");
                            }
                        }
                        session.setAttribute("cart", list);
                    }
                }
                else{
                    request.setAttribute("fail", "Add to cart fail. Please try again");
                }
                request.getRequestDispatcher("viewcart.jsp").forward(request, response);
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

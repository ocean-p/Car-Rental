/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.CarDAO;
import dao.CategoryDAO;
import dao.RentDAO;
import dao.RentDetailDAO;
import dto.Account;
import dto.Car;
import dto.Cart;
import dto.Rent;
import dto.RentDetail;
import dto.Sale;
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
@WebServlet(name = "RentServlet", urlPatterns = {"/RentServlet"})
public class RentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("cart") != null){
                ArrayList<Cart> cart1 = (ArrayList<Cart>) session.getAttribute("cart");
                ArrayList<String> message = new ArrayList<>();
                for (Cart cart : cart1) {
                    int id = cart.getCar().getId();
                    String rentdate = cart.getRentdate();
                    String returndate = cart.getReturndate();
                    Car car = CarDAO.getCarById(id);
                    int available = car.getQuantity();
                    ArrayList<RentDetail> detailtmp = RentDetailDAO.getDetailForSearch();
                    ArrayList<RentDetail> detail = RentDetail.getFollowInput(rentdate, returndate, detailtmp);
                    ArrayList<Cart> less = Cart.getDateLess(rentdate, returndate, cart1);
                    for (Cart les : less) {
                        if (les.getCar().getId() == car.getId() && 
                                CheckDate.convertDate(rentdate).compareTo(CheckDate.convertDate(les.getRentdate())) == 0 &&
                                CheckDate.convertDate(returndate).compareTo(CheckDate.convertDate(les.getReturndate())) == 0) {
                            
                        }
                        else if(les.getCar().getId() == car.getId()){
                            available = available - les.getAmount();
                        }
                    }
                    for (RentDetail rd : detail) {
                        if (rd.getCarid() == car.getId()) {
                            available = available + rd.getQuantity();
                        }
                    }
                    if(cart.getAmount()>available){
                        String notice = "The car with id:"+id+", "+rentdate+"-"+returndate+", available:"+available;
                        message.add(notice);
                    }
                }
                if(!message.isEmpty()){
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("viewcart.jsp").forward(request, response);
                }
                else{
                    //rent id
                    int rentid = RentDAO.getNumberRent()+1;
                    //rent create date
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date dt = new Date();
                    String current = df.format(dt);
                    //rent totalquantity
                    int totalquantity = 0;
                    for (Cart c : cart1) {
                        totalquantity += c.getAmount();
                    }
                    //rent payment
                    String payment = request.getParameter("payment").trim();
                    //rent email
                    Account account = (Account) session.getAttribute("user");
                    String email = account.getEmail();
                    int a;
                    if(session.getAttribute("sale") == null){
                        //ko sale
                        //rent totalprice
                        float totalprice = Float.parseFloat(session.getAttribute("total").toString());
                        Rent r = new Rent(rentid, current, totalquantity, totalprice, payment, 0, email, 0, "0");
                        a = RentDAO.addRent(r);
                    }
                    else{
                        //co sale
                        //rent totalprice
                        Sale sale = (Sale) session.getAttribute("sale");
                        float total = Float.parseFloat(session.getAttribute("total").toString());
                        float totalprice = total - total*sale.getPercent()/100;
                        Rent r = new Rent(rentid, current, totalquantity, totalprice, payment, 0, email, 1, sale.getCode());
                        a = RentDAO.addRent(r);
                    }
                    int b1 = 0;
                    int b2 = 0;
                    for (Cart c : cart1) {
                        RentDetail rd = new RentDetail(rentid, c.getCar().getId(), c.getCar().getName(), 
                                c.getCar().getPrice(), c.getAmount(), c.getCar().getPrice()*c.getAmount(), 
                                c.getRentdate(), c.getReturndate(), 0, 0, 0);
                        b1 = RentDetailDAO.addRentDetail(rd);
                        b2 = CarDAO.updateQuantity(c.getAmount(), c.getCar().getId());
                    }
                    if(a>0 && b1>0 && b2>0){
                        session.removeAttribute("cart");
                        request.setAttribute("category", CategoryDAO.getAllCategory());
                        String success = "Check out successfully - Rent id:"+rentid+" - Date:"+current+" - Thank you very much.";
                        request.setAttribute("success", success);
                    }
                    else{
                        String fail = "Check out fail.\nPlease try again.";
                        request.setAttribute("fail", fail);
                    }
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                }
            }
            else{
                String fail = "This rental have been checked out or not in cart.";
                request.setAttribute("fail", fail);
                request.getRequestDispatcher("home.jsp").forward(request, response);
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

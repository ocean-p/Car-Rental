/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.CarDAO;
import dao.CategoryDAO;
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
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            request.setAttribute("category", CategoryDAO.getAllCategory());
            if(request.getParameter("pick") == null){
                request.setAttribute("message", "Please choose category or name!");
                request.getRequestDispatcher("home.jsp").forward(request, response);
            }
            else{
                String name = request.getParameter("txtname").trim();
                String rent = request.getParameter("txtrental").trim();
                String returnd = request.getParameter("txtreturn").trim();
                String amount = request.getParameter("txtamount").trim();
                
                //current date
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date dt = new Date();
                String current = df.format(dt);
                //
                //get category
                String category = request.getParameter("cbcategory").trim();
                String categoryid = category.split("-")[0];
                request.setAttribute("number", categoryid);
                //
                //set radio
                request.setAttribute("radio", "1");
                //
                boolean err = false;
                if(request.getParameter("pick").trim().equals("name")){
                    request.removeAttribute("number");
                    request.setAttribute("radio", "2");
                    if(name.isEmpty()){
                        err = true;
                        request.setAttribute("namex", "Name is empty!");
                    }
                }
                //check rental date
                if(rent.isEmpty()){
                    err = true;
                    request.setAttribute("rentx", "Not empty!");
                }
                else if(CheckDate.isValid(rent) == false){
                    err = true;
                    request.setAttribute("rentx", "Invalid date!");
                }
                else if(CheckDate.convertDate(rent).compareTo(CheckDate.convertDate(current))<=0){
                    err = true;
                    request.setAttribute("rentx", "Rental date must be after today!");
                }
                //
                //check return date
                if(returnd.isEmpty()){
                    err = true;
                    request.setAttribute("returnx", "Not empty!");
                }
                else if(CheckDate.isValid(returnd) == false){
                    err = true;
                    request.setAttribute("returnx", "Invalid date!");
                }
                else if (CheckDate.isValid(rent) == true) {
                    if (CheckDate.convertDate(returnd).compareTo(CheckDate.convertDate(rent)) <= 0) {
                        err = true;
                        request.setAttribute("returnx", "Return date must be after rental date!");
                    }
                }
                //
                //check amount
                if(amount.isEmpty()){
                    err = true;
                    request.setAttribute("amountx", "Not empty!");
                }
                else if(!amount.matches("^[0-9]+$")){
                    err = true;
                    request.setAttribute("amountx", "Amount must be number!");
                }
                else if(Integer.parseInt(amount) <= 0){
                    err = true;
                    request.setAttribute("amountx", "Amount must be > 0 !");
                }
                //
                String url = "home.jsp";
                request.setAttribute("search", 0);
                if(err == false){
                    request.setAttribute("message", "OK");
                    request.setAttribute("search", 1);
                    request.setAttribute("valuerent", rent);
                    request.setAttribute("valuereturn", returnd);
                    request.setAttribute("valueamount", amount);
                    ArrayList<RentDetail> detailtmp = RentDetailDAO.getDetailForSearch();
                    ArrayList<RentDetail> detail = RentDetail.getFollowInput(rent, returnd, detailtmp);
                    HttpSession session = request.getSession();
                    //radio name
                    if(request.getParameter("pick").trim().equals("name")){
                        ArrayList<Car> carlist = CarDAO.searchByName(name);
                        ArrayList<Car> car = new ArrayList<>();
                        if(detail.isEmpty()){
                            if(session.getAttribute("cart") == null){
                                //TH1: detail null, session(cart) null  
                                //quantity hiển thị = số lượng car có trong carlist
                                for (Car each : carlist) {
                                    if(each.getQuantity() >= Integer.parseInt(amount)){
                                        car.add(each);
                                    }
                                }
                                request.setAttribute("car", car);
                            }//end TH1
                            else{
                                //TH2: detail null, session(cart) có
                                //quantity hiển thị = số lượng car trong carlist - quantity trong session
                                ArrayList<Cart> cart = (ArrayList<Cart>) session.getAttribute("cart");
                                ArrayList<Cart> less = Cart.getDateLess(rent, returnd, cart);
                                //- quantity trong cart khi sản phẩm đó chưa qua ngày return
                                for (Car each : carlist) {
                                    for (Cart le : less) {
                                        if(each.getId() == le.getCar().getId()){
                                            each.setQuantity(each.getQuantity()-le.getAmount());
                                        }
                                    }
                                }
                                //so sánh với amount
                                for (Car each : carlist) {
                                    if(each.getQuantity() >= Integer.parseInt(amount)){
                                        car.add(each);
                                    }
                                }
                                request.setAttribute("car", car);
                            }//end TH2
                        }
                        else{
                            if(session.getAttribute("cart") == null){
                                //TH3: detail có, cart null
                                //quantity hiển thị = số lượng car trong carlist + từng số lượng theo carID trong detail
                                for (Car each : carlist) {
                                    for (RentDetail rd : detail) {
                                        if(each.getId() == rd.getCarid()){
                                            each.setQuantity(each.getQuantity()+rd.getQuantity());
                                        }
                                    }
                                }
                                for (Car each : carlist) {
                                    if(each.getQuantity() >= Integer.parseInt(amount)){
                                        car.add(each);
                                    }
                                }
                                request.setAttribute("car", car);
                            }//end TH3
                            else{
                                //TH4: detail có, cart có
                                //quantity hiển thị = sl car trong carlist + từng số lượng theo carID trong detail - quantity trong session
                                ArrayList<Cart> cart = (ArrayList<Cart>) session.getAttribute("cart");
                                ArrayList<Cart> less = Cart.getDateLess(rent, returnd, cart);
                                
                                //- quantity trong cart khi sản phẩm đó chưa qua ngày return
                                for (Car each : carlist) {
                                    for (Cart le : less) {
                                        if(each.getId() == le.getCar().getId()){
                                            each.setQuantity(each.getQuantity()-le.getAmount());
                                        }
                                    }
                                }
                                //+ quantity trong detail
                                for (Car each : carlist) {
                                    for (RentDetail rd : detail) {
                                        if(each.getId() == rd.getCarid()){
                                            each.setQuantity(each.getQuantity()+rd.getQuantity());
                                        }
                                    }
                                }
                                // so sánh với amount
                                for (Car each : carlist) {
                                    if(each.getQuantity() >= Integer.parseInt(amount)){
                                        car.add(each);
                                    }
                                }
                                request.setAttribute("car", car);
                            }// end TH4
                        }
                    }//end radio name
                    //radio category
                    else{
                        ArrayList<Car> carlist = CarDAO.searchByCategory(Integer.parseInt(categoryid));
                        ArrayList<Car> car = new ArrayList<>();
                        if(detail.isEmpty()){
                            if(session.getAttribute("cart") == null){
                                //TH1: detail null, session(cart) null  
                                //quantity hiển thị = số lượng car có trong carlist
                                for (Car each : carlist) {
                                    if(each.getQuantity() >= Integer.parseInt(amount)){
                                        car.add(each);
                                    }
                                }
                                request.setAttribute("car", car);
                            }
                            else{
                                //TH2: detail null, session(cart) có
                                //quantity hiển thị = số lượng car trong carlist - quantity trong session
                                ArrayList<Cart> cart = (ArrayList<Cart>) session.getAttribute("cart");
                                ArrayList<Cart> less = Cart.getDateLess(rent, returnd, cart);
                                //- quantity trong cart khi sản phẩm đó chưa qua ngày return
                                for (Car each : carlist) {
                                    for (Cart le : less) {
                                        if(each.getId() == le.getCar().getId()){
                                            each.setQuantity(each.getQuantity()-le.getAmount());
                                        }
                                    }
                                }
                                //so sánh với amount
                                for (Car each : carlist) {
                                    if(each.getQuantity() >= Integer.parseInt(amount)){
                                        car.add(each);
                                    }
                                }
                                request.setAttribute("car", car);
                            }
                        }
                        else{
                            if(session.getAttribute("cart") == null){
                                //TH3: detail có, cart null
                                //quantity hiển thị = số lượng car trong carlist + từng số lượng theo carID trong detail
                                for (Car each : carlist) {
                                    for (RentDetail rd : detail) {
                                        if(each.getId() == rd.getCarid()){
                                            each.setQuantity(each.getQuantity()+rd.getQuantity());
                                        }
                                    }
                                }
                                for (Car each : carlist) {
                                    if(each.getQuantity() >= Integer.parseInt(amount)){
                                        car.add(each);
                                    }
                                }
                                request.setAttribute("car", car);
                            }
                            else{
                                //TH4: detail có, cart có
                                //quantity hiển thị = sl car trong carlist + từng số lượng theo carID trong detail - quantity trong session
                                ArrayList<Cart> cart = (ArrayList<Cart>) session.getAttribute("cart");
                                ArrayList<Cart> less = Cart.getDateLess(rent, returnd, cart);
                                
                                //- quantity trong cart khi sản phẩm đó chưa qua ngày return
                                for (Car each : carlist) {
                                    for (Cart le : less) {
                                        if(each.getId() == le.getCar().getId()){
                                            each.setQuantity(each.getQuantity()-le.getAmount());
                                        }
                                    }
                                }
                                //+ quantity trong detail
                                for (Car each : carlist) {
                                    for (RentDetail rd : detail) {
                                        if(each.getId() == rd.getCarid()){
                                            each.setQuantity(each.getQuantity()+rd.getQuantity());
                                        }
                                    }
                                }
                                // so sánh với amount
                                for (Car each : carlist) {
                                    if(each.getQuantity() >= Integer.parseInt(amount)){
                                        car.add(each);
                                    }
                                }
                                request.setAttribute("car", car);
                            }
                        }
                    }//end raido category
                }
                request.getRequestDispatcher(url).forward(request, response);
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

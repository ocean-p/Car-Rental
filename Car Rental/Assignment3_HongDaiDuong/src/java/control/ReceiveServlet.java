/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.CategoryDAO;
import dao.RentDAO;
import dao.SaleDAO;
import dto.Account;
import dto.Category;
import dto.Rent;
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
@WebServlet(name = "ReceiveServlet", urlPatterns = {"/ReceiveServlet"})
public class ReceiveServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            ArrayList<Category> c = CategoryDAO.getAllCategory();
            request.setAttribute("category", c);
            HttpSession session = request.getSession();
            if(session.getAttribute("user")!=null){
                Account account = (Account) session.getAttribute("user");
                ArrayList<Rent> rent = RentDAO.getRentIsSale(account.getEmail());
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date dt = new Date();
                String current = df.format(dt);
                boolean test = false;
                for (Rent r : rent) {
                    if(CheckDate.convertDate(current).compareTo(CheckDate.convertDate(r.getCreatedate()))==0){
                        test = true;
                    }
                }
                if(test == true){
                    String already = "Today, you add sale code and rent successfully.Comeback tomorrow.";
                    request.setAttribute("already", already);
                }
                else{
                    ArrayList<Sale> sale = SaleDAO.getSaleCode();
                    ArrayList<String> store = new ArrayList<>();
                    for (Sale s: sale) {
                        if(CheckDate.convertDate(s.getExpiredate()).compareTo(CheckDate.convertDate(current))>=0){
                            store.add(s.getCode());
                        }
                    }
                    if(store.isEmpty()){
                        String already = "Sorry, no sale code available.";
                        request.setAttribute("already", already);
                    }
                    else{
                        String code = store.get(0);
                        request.setAttribute("receive", code);
                    }
                }
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

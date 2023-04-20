
import entities.Product;
import helper.FactoryProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Customer;
@WebServlet("/")
public class Functions extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        switch (action) {
            case "/UserRegistration":
                UserRegistration(req, resp);
                break;
            case "/loginServ":
                UserLogin(req, resp);
                break;

            case "/DisplayUser":
                DisplayUser(req, resp);
                break;

            case "/ShowProduct":
                ShowProduct(req, resp);
                break;
            case "/UserEdit":
                UserEdit(req, resp);
                break;
            case "/LogoutServ":
                LogoutServ(req, resp);
                break;
            case "/UserDelete":
                UserDelete(req, resp);
                break;

        }
    }

    private void UserDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session= req.getSession(false);
        int id= (int) session.getAttribute("cid");


            Session s = FactoryProvider.getFactory().openSession();
            Transaction tx = s.beginTransaction();
            Customer customer = s.get(Customer.class, id);
            s.delete(customer);
            tx.commit();
            s.close();
            resp.sendRedirect("showProduct.jsp");
        }


    private void UserEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");

        HttpSession hs= request.getSession(false);
        int id= (int) hs.getAttribute("cid");

        Session session = FactoryProvider.getFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Customer updateCust=new Customer(name);

        Customer customer = session.get(Customer.class, id);
        customer.setName(updateCust.getName());

        session.update(customer);

        transaction.commit();
        session.close();

        response.sendRedirect("showProduct.jsp");
    }
    private void LogoutServ(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        HttpSession session=request.getSession();
        session.invalidate();

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        request.getRequestDispatcher("login.jsp").forward(request, response);
        out.print("You are successfully logged out!");

        out.close();
    }

    private void DisplayUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        Session session = FactoryProvider.getFactory().openSession();
        Transaction tx = session.beginTransaction();
        HttpSession hs = request.getSession(false);
        String email = (String) request.getAttribute("email");

        Query query = session.createQuery("FROM Customer ");

        List<Customer> list = query.getResultList();

        out.println("<html>"); // Added opening <html> tag
        out.println("<head>");
        out.println("<title>User Details</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>User Details</h1>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th> Name</th>");
        out.println("<th>Email</th>");

        out.println("</tr>");

        for (Customer user : list) {
            out.println("<tr>");
            out.println("<td>" + user.getCid() + "</td>");
            out.println("<td>" + user.getName() + "</td>");
            out.println("<td>" + user.getEmail() + "</td>");
            out.println("<td> <a href=UserDelete?email>Delete</td>");
            out.println("<td> <a href=edit.jsp?email>Edit</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<a href='LogoutServ' class='btn btn-danger'>Logout</a>"); // Logout button

        out.println("</body>");
        out.println("</html>"); // Added closing </html> tag

        tx.commit();
        session.close();
    }


    protected void ShowProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        HttpSession hs= request.getSession(false);
        Session session = FactoryProvider.getFactory().openSession();
        Transaction tx = session.beginTransaction();

        int cusId= (int) hs.getAttribute("cid");

        List<Product> productList = new ArrayList<>();
        Product product = new Product("Bag",50,15,0);
        Product product1 = new Product("Bottle",40,20,0);
        Product product2 = new Product("Shoes",240,25,0);
        productList.add(product);
        productList.add(product1);
        productList.add(product2);
         Customer customer=new Customer();
         customer.setProducts(productList);


        for (Product p : productList) {
            session.save(p);
        }

        tx.commit();
        session.close();

        out.println("<html><head><title>Product Table</title></head><body>");
        out.println("<h1>Product Table</h1>");
        out.println("<table border=\"1\">");
        out.println("<tr><th>Name</th><th>Quantity</th><th>Price</th></tr>");
        for (Product p : productList) {
            out.println("<tr><td>" + p.getPname() + "</td><td>" + p.getQuantity() + "</td><td>" + p.getPprice() + "</td></tr>");
        }
        out.println("</table>");
        out.println("</body></html>");







        tx.commit();
            session.close();
        }

    private void UserLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        HttpSession hs=request.getSession();

        Session session = FactoryProvider.getFactory().openSession();
        Transaction tx = session.beginTransaction();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        {
            Query query = session.createQuery("FROM Customer WHERE email = '" + email + "' and password = '" + password + "'");

            List<Customer> list = query.getResultList();
            if (list.size() > 0) {

                 for(Customer customer: list){
                     hs.setAttribute("cid",customer.getCid());
                     System.out.println("check id :"+ hs.getAttribute("cid") );
                 }
                out.println("<h6 style='color: green;'>Login Successful</h6>");
                RequestDispatcher rd = request.getRequestDispatcher("showProduct.jsp");
                rd.forward(request, response);

            } else {
                out.println("<h6 style='color: red;'>Login failed Register first ! </h6>");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.include(request, response);
            }

            tx.commit();
            session.close();
        }
    }

    protected void UserRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        Session session = FactoryProvider.getFactory().openSession();
        Transaction tx = session.beginTransaction();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        Customer ci = new Customer();
        ci.setEmail(email);
        ci.setPassword(password);
        ci.setName(name);

        session.save(ci);
        tx.commit();
        session.close();

        out.println("<h6 style='color: green;'>Registered Successfully</h6>");
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.include(request, response);

    }


}







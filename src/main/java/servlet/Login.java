/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dto.Matriculados;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ALEXANDER
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        super.init();
        emf = Persistence.createEntityManagerFactory("group_Preg01_war_1.0-SNAPSHOTPU");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dni = request.getParameter("dni");
        String password = request.getParameter("password");

        EntityManager em = emf.createEntityManager();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            TypedQuery<Matriculados> query = em.createQuery(
                    "SELECT m FROM Matriculados m WHERE m.ndniEstdWeb = :dni AND m.passEstd = :password",
                    Matriculados.class
            );
            query.setParameter("dni", dni);
            query.setParameter("password", password);

            List<Matriculados> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                Matriculados user = resultList.get(0);

                // Guardar sesión
                HttpSession session = request.getSession();
                session.setAttribute("usuario", user);

                // Devolver éxito como JSON
                out.print("{\"success\": true}");
            } else {
                // Error de credenciales
                out.print("{\"success\": false, \"message\": \"DNI o contraseña incorrectos.\"}");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        super.destroy();
    }
}

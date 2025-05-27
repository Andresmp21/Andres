/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.google.gson.Gson;
import dto.Matriculados;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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

/**
 *
 * @author ALEXANDER
 */
@WebServlet(name = "MatriculadosServlet", urlPatterns = {"/matriculadosServlet"})
public class MatriculadosServlet extends HttpServlet {

 private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("group_Preg01_war_1.0-SNAPSHOTPU");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if ("agregar".equals(accion)) {
            agregarMatriculado(request, response);
        }
    }

    private void agregarMatriculado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Matriculados m = new Matriculados();
            m.setCodiEstdWeb(Integer.parseInt(request.getParameter("codiEstdWeb")));
            m.setNdniEstdWeb(request.getParameter("ndniEstdWeb"));
            m.setAppaEstdWeb(request.getParameter("appaEstdWeb"));
            m.setApmaEstdWeb(request.getParameter("apmaEstdWeb"));
            m.setNombEstdWeb(request.getParameter("nombEstdWeb"));
            m.setFechNaciEstdWeb(java.sql.Date.valueOf(request.getParameter("fechNaciEstdWeb")));
            m.setLogiEstd(request.getParameter("logiEstd"));
            m.setPassEstd(request.getParameter("passEstd"));

            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            em.close();

            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al registrar estudiante");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if ("listar".equals(accion)) {
            listarMatriculados(response);
        }
    }

    private void listarMatriculados(HttpServletResponse response) throws IOException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Matriculados> query = em.createQuery("SELECT m FROM Matriculados m", Matriculados.class);
        List<Matriculados> lista = query.getResultList();
        em.close();

        Gson gson = new Gson();
        String json = gson.toJson(lista);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

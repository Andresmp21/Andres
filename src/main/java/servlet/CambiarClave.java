/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dto.Matriculados;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
@WebServlet(name = "CambiarClave", urlPatterns = {"/cambiarClave"})
public class CambiarClave extends HttpServlet {

      private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("group_Preg01_war_1.0-SNAPSHOTPU"); // cambia al nombre real de tu unidad
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.html");
            return;
        }

        // Recuperar el usuario de la sesión
        Matriculados usuario = (Matriculados) session.getAttribute("usuario");

        // Obtener parámetros del formulario
        String claveActual = request.getParameter("claveActual");
        String nuevaClave = request.getParameter("nuevaClave");
        String confirmaClave = request.getParameter("confirmaClave");

        // Validaciones
        if (!usuario.getPassEstd().equals(claveActual)) {
            request.setAttribute("mensajeError", "La clave actual es incorrecta.");
            request.getRequestDispatcher("cambiar-clave.jsp").forward(request, response);
            return;
        }

        if (nuevaClave == null || nuevaClave.isEmpty() || !nuevaClave.equals(confirmaClave)) {
            request.setAttribute("mensajeError", "La nueva clave y la confirmación no coinciden.");
            request.getRequestDispatcher("cambiar-clave.jsp").forward(request, response);
            return;
        }

        // Actualizar clave en BD
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Matriculados usuarioBD = em.find(Matriculados.class, usuario.getCodiEstdWeb());
            usuarioBD.setPassEstd(nuevaClave);
            em.getTransaction().commit();

            // Actualizar la sesión con la nueva clave
            usuario.setPassEstd(nuevaClave);
            session.setAttribute("usuario", usuario);

            request.setAttribute("mensajeExito", "Contraseña actualizada correctamente.");
            request.getRequestDispatcher("cambiar-clave.jsp").forward(request, response);
        } catch (Exception e) {
            em.getTransaction().rollback();
            request.setAttribute("mensajeError", "Error al actualizar la contraseña.");
            request.getRequestDispatcher("cambiar-clave.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

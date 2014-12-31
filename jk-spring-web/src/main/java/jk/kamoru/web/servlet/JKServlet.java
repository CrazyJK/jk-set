package jk.kamoru.web.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jk.kamoru.JK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class JKServlet
 */
public final class JKServlet extends HttpServlet {
	
	protected static final Logger logger = LoggerFactory.getLogger(JKServlet.class);

	private static final long serialVersionUID = JK.SERIAL_VERSION_UID;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JKServlet() {
        super();
        logger.info("JKServlet creat");
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		logger.info("JKServlet init");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("I'm alive!");
	}

}

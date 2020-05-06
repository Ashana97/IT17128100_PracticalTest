package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DoctorsAPI
 */
@WebServlet("/DoctorsAPI")
public class DoctorsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Doctor docObj = new Doctor();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorsAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//NOT USED
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String output = docObj.insertDoctor(request.getParameter("dName"),
				   request.getParameter("dPhone"),
				   request.getParameter("dEmail"),
				   request.getParameter("dSpecial"),
				   request.getParameter("dHospital"),
				   request.getParameter("dDate"),
				   request.getParameter("dStatus")
				   );

		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		
		String output = docObj.updateDoctor(paras.get("hidDocIDSave").toString(),
										   paras.get("dName").toString(),
										   paras.get("dPhone").toString(),
										   paras.get("dEmail").toString(),
										   paras.get("dSpecial").toString(),
										   paras.get("dHospital").toString(),
										   paras.get("dDate").toString(),
										   paras.get("dStatus").toString());
		
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		
		String output = docObj.deleteDoctor(paras.get("docID").toString());
		
		response.getWriter().write(output);
	}
	
	// Convert request parameters to a Map
		private static Map getParasMap(HttpServletRequest request)
		{
			Map<String, String> map = new HashMap<String, String>();
			
			try
			{
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
				String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
				scanner.close();
				
				String[] params = queryString.split("&");
				
				for (String param : params)
				{
					String[] p = param.split("=");
					map.put(p[0], p[1]);
				}
			}
			catch (Exception e)
			{
			}
			
			return map;
		}

}


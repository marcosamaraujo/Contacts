package com.contact.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.contact.jdbc.ConnectionFactory;

@WebFilter("/*")
public class FilterExecutionTime implements Filter{
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		long initialTime = System.currentTimeMillis();
		try{
			Connection connection = new ConnectionFactory().getConnection();
			request.setAttribute("connection", connection);
			chain.doFilter(request, response);
			long finalTime = System.currentTimeMillis();
			String uri = ((HttpServletRequest)request).getRequestURI();
			String parametros = ((HttpServletRequest) request).getParameter("logic");
			connection.close();
			System.out.println("Request time: " + uri + "?logic="	+ parametros + " is (ms): " + (finalTime - initialTime));
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public void destroy(){
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException{
	}

}

<%@page import="java.util.Vector"%>
<%@page import="java.sql.*"%>
<%@ page import="com.tou4u.sentour.content.ContentRequester" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%

	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");

	String radius = request.getParameter("radius");
	String contentType = request.getParameter("contentType");
	String sky = request.getParameter("sky");
	String hashTag = request.getParameter("HashTag");
	String lat = request.getParameter("lat");
	String lng = request.getParameter("lng");

	ContentRequester contentRequester = new ContentRequester();
	String result = contentRequester.response(contentType, radius, lat, lng, sky, hashTag);

//	result = String.format("%s, %s, %s, %s, %s, %s", radius, contentType, sky, hashTag, lat, lng);
	out.println(result);
%>

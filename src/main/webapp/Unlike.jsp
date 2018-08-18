<%@ page import="com.tou4u.sentour.content.UnlikeRequester" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<%
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");

    String contentID = request.getParameter("contentID");
    String sky = request.getParameter("sky");
    String hashTag = request.getParameter("HashTag");
    String unlike = request.getParameter("Unlike");

    UnlikeRequester unlikeRequester = new UnlikeRequester();
    unlikeRequester.unlike(contentID, sky, hashTag, unlike);

    String result = String.format("%s, %s, %s, %s", contentID, sky, hashTag, unlike);
    out.println(result);
%>

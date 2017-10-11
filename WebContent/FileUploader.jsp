    <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
    <html>  
        <head>  
            <title>上传页面</title>  
        </head>  
        <body>  
            <form action="uploadsuc.jsp" method="post"  
                enctype="multipart/form-data">  
                <input type="file" name="pic" id="pic" />  
                <input type="submit" value="上传" />  
            </form>  
        </body>  
    </html>  
    <%@ page language="java" import="java.util.*,com.jspsmart.upload.*"  
        pageEncoding="utf-8"%>  
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
    <html>  
        <head>  
            <title>上传处理页面</title>  
        </head>  
      
        <body>  
            <%   
                //放到异常抛出结构里面，避免直接访问此页报错   
                try{   
                    //指定动作   
                    SmartUpload smart=new SmartUpload();   
                    smart.initialize(pageContext);   
                    smart.upload();   
                    //把文件保存到同目录的pic文件夹   
                    smart.save("UploadedFile");   
                }   
                catch(Exception e){   
                }   
             %>  
            <a href="FileUploader.jsp">返回</a>  
        </body>  
    </html>  
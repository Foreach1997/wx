package xl;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import xl.service.*;

public class Wxservlet extends HttpServlet {
	 
  @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
   req.setCharacterEncoding("utf-8");
   resp.setCharacterEncoding("utf-8");
   PrintWriter out=resp.getWriter();
   try {
	connect(req,out);
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   }
  
  @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	
	   req.setCharacterEncoding("utf-8");
	   resp.setCharacterEncoding("utf-8");
//	   
	   try {
		echoMsg(req,resp);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
}
   
  private void echoMsg(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	// TODO Auto-generated method stub
	PrintWriter writer=resp.getWriter();
    Map<String,String> xmlMap=null;
    xmlMap=MessageUtil.parseXml(req);
    Mes message=new Mes();
    message=MessageUtil.toMessage(xmlMap);
    String from =message.getFromUserName();
    String to=message.getToUserName();
    message.setFromUserName(from);
    message.setToUserName(to);
//     message.getFromUserName();
//     message.getToUserName();
    message.setCreateTime(System.currentTimeMillis());
    String Content =message.getContent();
    String type=message.getMsgType();
    Content=null==Content?"":Content.trim();
    String reply="";
    //关注事件的获得
    String event=message.getEvent();
    if(type.equals(MessageType.REQ_Messagee_text)){
    	if(Content.equals("1")){
    	    reply=xlText();
    	}else{
    		reply=xlelse();
    	}
    }else if(type.equals(MessageEvent.REQ_MessageeEvent_Event)){
    	if(event.equals("subscribe")){
    		reply=xlText();
    		message.setMsgType(MessageType.REQ_Messagee_text);
    	}else if(event.equals("unsubscribe")){
    		System.out.println("有人退出");
    	}
    }
    message.setContent(reply);
    String xmlstr=MessageUtil.toXml(message);
    System.out.println(xmlstr);
    writer.print(xmlstr);
    writer.flush();
    
}
private String xlelse() {
	// TODO Auto-generated method stub
	StringBuffer buffer=new StringBuffer();
	buffer.append("无法识别你输入的信息");
	return buffer.toString();
}

private String xlText() {
	// TODO Auto-generated method stub
	StringBuffer buffer=new StringBuffer();
	buffer.append("1.QQ");	
	return buffer.toString();
}
private final String token = "abc123";

private void connect(HttpServletRequest req, PrintWriter out) throws ServletException, IOException,Exception {
	// TODO Auto-generated method stub
	   String signature=req.getParameter("signature");
	   System.out.println(signature);
	   String timestamp=req.getParameter("timestamp");
	   System.out.println(timestamp);
	   String nonce=req.getParameter("nonce");
	   System.out.println(nonce);
	   String echostr=req.getParameter("echostr");
	   System.out.println(echostr);
	   
//	   List<String> list=new ArrayList<String>();
//	   list.add("123abc");
//	   list.add(timestamp);
//	   list.add(nonce);
//	   Collections.sort(list);
        String[] list = new String[] {token, timestamp, nonce };
        Arrays.sort(list);
	    StringBuffer buffer=new StringBuffer();
	   for(String s:list){
		buffer.append(s);
	   }
	   String shalStr=DigestUtils.sha1Hex(buffer.toString());
	   boolean flag =shalStr.equals(signature);
	   if(flag){
		   System.out.println("接入成功");  
		   out.print(echostr);
		   out.flush();
	   }
	   
}
}

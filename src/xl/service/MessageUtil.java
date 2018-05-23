package xl.service;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import xl.Mes;




public class MessageUtil {
	   
	   public static Map<String,String> parseXml(HttpServletRequest req) throws Exception{
	   Map<String,String> map=new HashMap<String, String>();
	   InputStream inputStream=null;
	   try{
		   inputStream=req.getInputStream();
	   }catch (Exception e) {
		// TODO: handle exception
		   e.printStackTrace();
	}
	   //��ȡsaxReader
	   SAXReader reader = new SAXReader();
	   //��ȡ�ĵ�
	   Document doc=reader.read(inputStream);
	   //��ȡ��Ԫ��
	   Element  root=doc.getRootElement();
	   //��ȡ����Ԫ��
	   @SuppressWarnings("unchecked")
	   List<Element> elements=root.elements();
	   //������map��
	   //Map<String, String> map=new HashMap<String, String>();
	   for (Element e : elements) {
		map.put(e.getName(), e.getText());
	}
	   inputStream.close();
	   return map;
	   }
	   public static Mes toMessage(Map<String, String> map){
			 Mes  message=new Mes();
			 message.setFromUserName(map.get("FromUserName"));
			 message.setToUserName(map.get("ToUserName"));
			 message.setCreateTime(Integer.valueOf(map.get("CreateTime")));
		     message.setContent(map.get("Content"));
			 message.setMsgId(map.get("MsgId"));
		     message.setMsgType(map.get("MsgType"));
	          //�¼�
		     message.setEvent(map.get("Event"));
			 return message;		  
	} 
	   public static String toXml(Mes Message) {
	        XStream xStream=new XStream();
	       xStream.alias("xml",Message.getClass());
	       return xStream.toXML(Message);
	   }
//	   public static String toXml(Message Message) {
//		   StringBuffer buffer=new StringBuffer();
//		   Message  message=new Message();
//		   buffer.append("<xml><ToUserName><![CDATA[");  
//		   buffer.append(message.getToUserName());  
//		   buffer.append("]]></ToUserName><FromUserName><![CDATA[");  
//		   buffer.append(message.getFromUserName());  
//		   buffer.append("]]></FromUserName><CreateTime>");  
//		   buffer.append(message.getCreateTime());  
//		   buffer.append("</CreateTime><MsgType><![CDATA[");
//		   buffer.append(message.getMsgType());
//		   buffer.append("]]></MsgType><Content><![CDATA[");  
//		   buffer.append(message.getContent());  
//		   buffer.append("]]></Content>");  
//		   buffer.append( "<MsgId>");
//		   buffer.append(message.getMsgId());
//		   buffer.append("</MsgId></xml>");
//		  return buffer.toString();
//		   
//	   }
	}
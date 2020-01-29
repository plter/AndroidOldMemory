import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.plter.lib.java.xml.XML;
import com.plter.lib.java.xml.XMLList;
import com.plter.lib.java.xml.XMLRoot;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new Main();
		
	}
	
	
	public Main() {
		
		XMLRoot cont = readXML("data/cont.xml");
		XMLRoot data = readXML("data/data.xml");
		
		try {
			JSONObject out = new JSONObject();
			out.put("title", "梦里花落知多少");
			
			JSONObject author = new JSONObject();
			author.put("name", "敦敬明");
			author.put("description", "出生：1983年6月6日<br>" +
					"籍贯：四川省自贡市<br>" +
					"出生地：四川省自贡市<br>" +
					"现居住地：上海市<br>" +
					"血型：AB型<br>" +
					"身高：155cm<br>" +
					"体重：58kg<br>" +
					"民族：汉族<br>" +
					"星座：双子座<br>" +
					"爱好：羽毛球<br>" +
					"属相：猪<br>" +
					"网名:第四维<br>" +
					"别名:小四，四维，四爷，某boss，boss四，巴掌脸<br>" +
					"学历:曾在在自贡市贡井区向阳小学、自贡市第九中学、自贡市富顺二中读书。2002年进入上海大学念影视艺术工程专业,05-06年休学两年。07年重新上学，转为影视艺术技学院。08年退学。<br>" +
					"喜欢搜集的东西：玻璃杯子和白衬衣，<br>" +
					"性格:一半明媚一半忧伤。<br>" +
					"喜欢女孩:干净，善良，聪明，漂亮<br>" +
					"主要作品 <br>" +
					"2002年《爱与痛的边缘》<br>" +
					"2003年《幻城》《左手倒影右手年华》 《梦里花落知多少》<br>" +
					"2003年12月，庄羽向北京市一中院起诉，称郭敬明所著小说《梦里花落知多少》一书剽窃了其《圈里圈外》一书的部分故事情节。随后，北京市一中院一审判决，认定《梦里花落知多少》在12个主要情节上均与《圈里圈外》中相应的情节相同或者相似，在整体上构成对《圈里圈外》的抄袭。对此判决，郭敬明与春风文艺出版社均表示不满，上诉至北京市高级人民法院。<br>" +
					"2004年《1995-2005夏至未至》 音乐小说《迷藏》 <br>" +
					"2004~2006年主编《岛》系列《岛.柢步》《岛.陆眼》《岛.锦年》《岛.普瑞尔》《岛.埃泽尔》《岛.泽塔泽塔》《岛.瑞雷克》《岛.天王海王》<br>" +
					"《岛.庞贝》 《岛.银千特》（已出版）<br>" +
					"2005年 修改《1995-2005夏至未至》并再次出版<br>" +
					"2007年《悲伤逆流成河》<br>" +
					"2006年主编《关于莉莉周的一切·电影手册》<br>" +
					"2006年主编《最小说》（月刊）目前仍在发行中…… <br>" +
					"2007年底《岛9.庞贝》,《N世界》<br>" +
					"2007年11月《最小说》连载《小时代》．<br>" +
					"2008年《岛10.银千特》.<br>" +
					"2008年10月《小时代1.0折纸时代》（new)<br>" +
					"2008年10月15日《小时代1.5青木时代》连载于《最小说》<br>" +
					"2008年12月《小时代2.0虚铜时代》连载于《最小说》<br>" +
					"2008年12月被天娱公司聘为“文学总监”。<br>" +
					"2009年1月出任敬明正式接受聘书，成为长江出版集团北京中心副总编辑，这里实行的是全员聘用制度，跟级别没什么关系，副处级一说纯属无稽之谈。据悉，郭敬明负责主抓青春类图书及杂志体系建设，确定选题、统一调配、确定发行量等等。1月7日，他就要以副总编的身份出席我们中心的年度代理商年会。成为了“80后”青年作家中担任出版社社长级领导职务的第一人。 <br>" +
					"2009年7月“The Next 文学之新选拔大赛”结束。<br>" +
					"2009年8月《小时代2.0虚铜时代》震撼上市。");
			
			out.put("author", author);
			
			JSONArray chapter=new JSONArray();
			
			XMLList chapterList = (XMLList) data.getChild("classes").getChild("class");
			XMLList contentList = (XMLList) cont.getChild("classes").getChild("class");
			
			JSONObject obj;
			XML xml;
			File outF;
			FileOutputStream fos;
			
			for (int i = 0; i < chapterList.length(); i++) {
				obj = new JSONObject();
				
				xml = chapterList.get(i);
				obj.put("name", xml.getAttr("name"));
				
				xml = contentList.get(i);
				outF=new File("out/"+i);
				if (!outF.exists()) {
					outF.createNewFile();
				}
				fos=new FileOutputStream(outF);
				fos.write(xml.getAttr("name").getBytes("utf-8"));
				fos.close();
				
				obj.put("ref",i+"");
				chapter.put(obj);
			}
			
			out.put("chapter", chapter);
			
			outF = new File("out/main.json");
			if (!outF.exists()) {
				outF.createNewFile();
			}
			fos=new FileOutputStream(outF);
			fos.write(out.toString().getBytes("utf-8"));
			fos.close();
			
			System.out.println(out);
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private XMLRoot readXML(String name){
		try {
			File count = new File(name);
			FileInputStream fis = new FileInputStream(count);
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			fis.close();
			
			return XMLRoot.parse(new String(bytes,"utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}

package cn.fairyshop.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerTest {
	
	@Test
	public void testFreemarker() throws IOException, TemplateException {
		
		FreeMarkerConfig config;
		
		// 1、添加jar包到工程中國
		// 2、freemarker不依賴于web容器
		// 3、创建Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		
		// 4、设置模板存放位置
		configuration.setDirectoryForTemplateLoading(new File("D:\\WorkSpace\\FairyShop\\fairyshop-portal\\src\\main\\webapp\\WEB-INF\\ftl"));
		
		// 5、设置默认字符集
		configuration.setDefaultEncoding("utf-8");
		
		// 6、获得指定的模板对象
		Template template = configuration.getTemplate("third.ftl");
		
		// 7、创建模板需要的数据集，可以是map，也可以是pojo
		Map root = new HashMap<>();
		root.put("title", "world");
		root.put("stu", new Student(2, "Bob", "西安"));
		List<Student> stus = new ArrayList<>();
		stus.add(new Student(1, "Mary01", "China01"));
		stus.add(new Student(2, "Mary02", "China02"));
		stus.add(new Student(3, "Mary03", "China03"));
		stus.add(new Student(4, "Mary04", "China04"));
		stus.add(new Student(5, "Mary05", "China05"));
		stus.add(new Student(6, "Mary06", "China06"));
		root.put("stus", stus);
		root.put("currDate", new Date());
		
		// 8、创建Writer对象，指定html输出路径
		Writer out = new FileWriter(new File("E:\\Temp\\html\\third.html"));
		
		// 9、调用模板对象的process方法，生成静态页面
		template.process(root, out);
		
		// 10、关闭writer对象
		out.flush();
		out.close();		
		
	}
	
	public class Student{
		private int id;
		private String name;
		private String addr;
		
		public Student(int id, String name, String addr) {
			// TODO Auto-generated constructor stub
			this.id = id;
			this.name = name;
			this.addr = addr;
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddr() {
			return addr;
		}
		public void setAddr(String addr) {
			this.addr = addr;
		}
		
	}

}

package cn.fairyshop.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerTest {
	
	@Test
	public void testFreemarker() throws IOException, TemplateException {
		// 1、添加jar包到工程中國
		// 2、freemarker不依賴于web容器
		// 3、创建Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		
		// 4、设置模板存放位置
		configuration.setDirectoryForTemplateLoading(new File("D:\\WorkSpace\\FairyShop\\fairyshop-portal\\src\\main\\webapp\\WEB-INF\\ftl"));
		
		// 5、设置默认字符集
		configuration.setDefaultEncoding("utf-8");
		
		// 6、获得指定的模板对象
		Template template = configuration.getTemplate("first.ftl");
		
		// 7、创建模板需要的数据集，可以是map，也可以是pojo
		Map root = new HashMap<>();
		root.put("hello", "world");
		
		// 8、创建Writer对象，指定html输出路径
		Writer out = new FileWriter(new File("E:\\Temp\\html\\hello.html"));
		
		// 9、调用模板对象的process方法，生成静态页面
		template.process(root, out);
		
		// 10、关闭writer对象
		out.flush();
		out.close();		
		
	}

}

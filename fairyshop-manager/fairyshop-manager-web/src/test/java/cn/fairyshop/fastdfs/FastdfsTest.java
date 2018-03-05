package cn.fairyshop.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class FastdfsTest {
	
	public static void main(String[] args) {
		try {
			testUpload();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public static void testUpload() throws FileNotFoundException, IOException, MyException {
		// 1、导入需要的jar包
		// 2、初始化全局配置，加载配置文件
		ClientGlobal.init("D:\\WorkSpace\\FairyShop\\fairyshop-manager\\fairyshop-manager-web\\src\\main\\resources\\properties\\client.conf");
		
		// 3、创建TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		
		// 4、创建TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		
		// 5、申明一个StorageServer对象
		StorageServer storageServer = null;
		
		// 6、获取StorageClient对象
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		
		// 7、用StorageClient上传文件，返回一个上传信息数组
		String[] infos = storageClient.upload_file("E:\\hello.jpg", "jpg", null);
		
		for(String i : infos) {
			System.out.println(i);
		}
	}

}

package cn.fairyshop.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastdfsClient {
	
	TrackerClient trackerClient = null;
	TrackerServer trackerServer = null;
	StorageServer storageServer = null;
	StorageClient storageClient = null;
	
	public FastdfsClient(String conf) throws FileNotFoundException, IOException, MyException {
		if(conf.contains("classpath:")) {
			conf = conf.replaceAll("classpath:", this.getClass().getResource("/").getPath());
		}
		
		ClientGlobal.init(conf);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient(trackerServer, storageServer);
	}
	
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws IOException, MyException {
		String[] info = storageClient.upload_file(fileName, extName, metas);
		return info[0] + "/" + info[1];
	}
	
	public String uploadFile(String fileName, String extName) throws IOException, MyException {
		String[] info = storageClient.upload_file(fileName, extName, null);
		return info[0] + "/" + info[1];
	}
	
	public String uploadFile(String fileName) throws IOException, MyException {
		String[] info = storageClient.upload_file(fileName, null, null);
		return info[0] + "/" + info[1];
	}
	
	public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws IOException, MyException {
		String[] info = storageClient.upload_file(fileContent, extName, metas);
		return info[0] + "/" + info[1];
	}
	
	public String uploadFile(byte[] fileContent, String extName) throws IOException, MyException {
		String[] info = storageClient.upload_file(fileContent, extName, null);
		return info[0] + "/" + info[1];
	}
	
	public String uploadFile(byte[] fileContent) throws IOException, MyException {
		String[] info = storageClient.upload_file(fileContent, null, null);
		return info[0] + "/" + info[1];
	}
	
}

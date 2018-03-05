package cn.fairyshop.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class FastdfsClient {
	
	TrackerClient trackerClient = null;
	TrackerServer trackerServer = null;
	StorageServer storageServer = null;
	StorageClient storageClient = null;
	
	public FastdfsClient(String conf) throws FileNotFoundException, IOException, MyException {
		ClientGlobal.init(conf);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient(trackerServer, storageServer);
	}
	
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws IOException, MyException {
		String[] info = storageClient.upload_file(fileName, extName, metas);
		return info[0] + "/" + info[1];
		CommonsMultipartResolver
	}

}

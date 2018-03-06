package cn.fairyshop.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.fairyshop.common.pojo.PictureResult;
import cn.fairyshop.common.utils.FastdfsClient;
import cn.fairyshop.service.PictureService;

/*
 * 图片上传service
 */
@Service
public class PictureServiceImpl implements PictureService {
	
	@Value("${IMAGE_SERVER_BASE_URL}")
	String IMAGE_SERVER_BASE_URL;

	@Override
	public PictureResult uploadPic(MultipartFile picFile) {
		PictureResult result = new PictureResult();
		if(picFile.isEmpty()) {
			result.setError(1);
			result.setMessage("图片为空");
		}else {
			try {
				// 取原始文件名
				String originalFileName = picFile.getOriginalFilename();
				// 取文件扩展名
				String extName = originalFileName.substring( originalFileName.lastIndexOf(".") + 1);
				// 初始化图片上传工具
				FastdfsClient client = new FastdfsClient("classpath:properties/client.conf");
				// 上传图片
				String url = IMAGE_SERVER_BASE_URL + client.uploadFile(picFile.getBytes(), extName);
				
				result.setError(0);
				result.setUrl(url);
			} catch (Exception e) {
				e.printStackTrace();
				result.setError(1);
				result.setMessage("图片上传失败");
			}
		}
		
		return result;
	}

}

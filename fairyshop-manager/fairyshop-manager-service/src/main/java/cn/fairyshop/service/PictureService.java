package cn.fairyshop.service;

import org.springframework.web.multipart.MultipartFile;

import cn.fairyshop.common.pojo.PictureResult;

public interface PictureService {
	
	public PictureResult uploadPic(MultipartFile picFile); 

}

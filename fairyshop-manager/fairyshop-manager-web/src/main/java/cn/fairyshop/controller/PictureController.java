package cn.fairyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.fairyshop.common.pojo.PictureResult;
import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.service.PictureService;

/*
 * 图片上传controller
 */
@Controller
public class PictureController {
	
	@Autowired
	PictureService pictureService = null;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		PictureResult result = pictureService.uploadPic(uploadFile);
		String json = JsonUtils.objectToJson(result);
		return json;
	}

}

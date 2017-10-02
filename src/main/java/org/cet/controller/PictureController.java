package org.cet.controller;

import org.cet.common.PictureResult;
import org.cet.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author zhang
 * @Date 2017/9/9 14:10
 * @Content 文件上传Controller
 */
@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    /**
     * 图片上传
     * @param uploadFile
     * @return
     */
    @RequestMapping("/pic/upload")
    @ResponseBody
    public PictureResult uploadImages(MultipartFile uploadFile){
        PictureResult result = pictureService.uploadFile(uploadFile);
        return result;
    }
}

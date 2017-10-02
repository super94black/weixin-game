package org.cet.service.impl;

import org.cet.common.PictureResult;
import org.cet.service.PictureService;
import org.cet.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author zhang
 * @Date 2017/9/9 14:02
 * @Content
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Value("${IMAGE_SERVER_BASE_ADDRESS}")
    private String IMAGE_SERVER_BASE_ADDRESS;
    public PictureResult uploadFile(MultipartFile picFile){
        PictureResult result = new PictureResult();
        if(picFile.isEmpty()){
            result.setError(1);
            result.setMessage("图片为空");
            return result;
        }
        //上传到服务器

        try{
            //取得图片扩展名
            String originalFileName = picFile.getOriginalFilename();
            //去掉 .
            String extName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/client.conf");
            String url = fastDFSClient.uploadFile(picFile.getBytes(),extName);
            url = IMAGE_SERVER_BASE_ADDRESS + url;
            result.setError(0);
            result.setUrl(url);

        }catch (Exception e){
            e.printStackTrace();
            result.setError(1);
            result.setMessage("上传失败");

        }
        return result;
    }

}

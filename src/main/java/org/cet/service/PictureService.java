package org.cet.service;

import org.cet.common.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author zhang
 * @Date 2017/9/9 14:10
 * @Content
 */
public interface PictureService {
    public PictureResult uploadFile(MultipartFile picFile);
}

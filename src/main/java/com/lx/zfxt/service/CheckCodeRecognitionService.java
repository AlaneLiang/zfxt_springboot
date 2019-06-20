package com.lx.zfxt.service;

import java.awt.image.BufferedImage;

public interface CheckCodeRecognitionService {
    /**
     * 使用libsvm识别验证码
     * @param image
     * @return
     */
    String getCheckCode( BufferedImage image);
}

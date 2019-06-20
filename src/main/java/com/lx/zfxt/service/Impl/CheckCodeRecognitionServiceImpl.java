package com.lx.zfxt.service.Impl;

import com.lx.zfxt.service.CheckCodeRecognitionService;

import java.awt.image.BufferedImage;

import com.lx.zfxt.utils.BizException;
import com.lx.zfxt.utils.CheckCodeUtil;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

//验证码识别

@Service
public class CheckCodeRecognitionServiceImpl implements CheckCodeRecognitionService {
    public Logger log = LoggerFactory.getLogger(this.getClass());

    private final static String modelName = "model.txt";
    private svm_model model;

    public CheckCodeRecognitionServiceImpl() throws IOException {
        Reader reader = new StringReader(loadModel(modelName));
        this.model = svm.svm_load_model(new BufferedReader(reader));
    }

    private String loadModel(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream(fileName);
        if (null == in) {
            log.error("can not read file by",fileName);
            throw new BizException("can not read file by " + fileName);
        }

        return IOUtils.toString(in, "UTF-8");
    }

    public String convert(BufferedImage image) {

        StringBuffer sb = new StringBuffer();
        svm_node[][] nodes = CheckCodeUtil.convertImageToVector(image);

        for (int i = 0; i < 4; i++) {
            double val = svm.svm_predict(model, nodes[i]);
            sb.append((char) (int) val);
        }
        return sb.toString();
    }

    @Override
    public String getCheckCode(BufferedImage image) {

        String codeResult = convert(image);

        log.info("验证码识别为:{}",codeResult);

        return codeResult;
    }
}

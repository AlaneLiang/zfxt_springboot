package com.lx.zfxt.controller;

import com.lx.zfxt.utils.BizException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 基类
 */

public class BaseController {

    public Logger log = LoggerFactory.getLogger(this.getClass());

    int getInt(HttpServletRequest request, String key) throws Exception {
        try {
            String num = request.getParameter(key);
            return Integer.parseInt(num);
        } catch (Exception e) {
            throw e;
        }
    }

    int getInt(HttpServletRequest request, String key, String errMsg) {
        try {
            return getInt(request, key);
        } catch (Exception e) {
            throw new BizException(errMsg);
        }
    }

    int getInt(HttpServletRequest request, String key, String errMsg, int min) {
        int num = getInt(request, key, errMsg);
        if (min > num) {
            throw new BizException(errMsg);
        }
        return num;
    }

    String getString(HttpServletRequest request, String key) {
        String v = request.getParameter(key);
        if (null != v) {
            return v.trim();
        }
        return null;
    }

    String getNotBlankString(HttpServletRequest request, String key, String errMsg) {
        String v = request.getParameter(key);
        if (StringUtils.isBlank(v)) {
            throw new BizException(errMsg);
        }
        return v.trim();
    }

    String getNotBlankString(HttpServletRequest request, String key, String errMsg, int maxLength) {
        String v = request.getParameter(key);
        if (StringUtils.isBlank(v)) {
            throw new BizException(errMsg);
        }
        String val = v.trim();
        if (maxLength < val.length()) {
            throw new BizException(errMsg);
        }
        return val;
    }

    String getNotBlankString(HttpServletRequest request, String key) {
        String v = request.getParameter(key);
        if (StringUtils.isBlank(v)) {
            return null;
        }
        return v.trim();
    }

    /**
     * 获取","分割的字符串数组
     *
     * @param request
     * @param key
     * @return
     */
    List<String> getListStringForKey(HttpServletRequest request, String key) {
        String ids = getString(request, key);
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(ids)) {
            String[] ss = ids.split(",");
            for (String s : ss) {
                list.add(s.trim());
            }
        }
        return list;
    }

    List<Integer> getListIntegerForKey(HttpServletRequest request, String key) {
        String ids = getString(request, key);
        List<Integer> list = new ArrayList<>();
        if (StringUtils.isNotBlank(ids)) {
            String[] ss = ids.split(",");
            for (String s : ss) {
                list.add(Integer.parseInt(s.trim()));
            }
        }
        return list;
    }


}

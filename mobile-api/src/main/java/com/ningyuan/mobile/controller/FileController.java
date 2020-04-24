package com.ningyuan.mobile.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.bean.front.Rets;
import com.ningyuan.mobile.model.SysFileInfoModel;
import com.ningyuan.mobile.service.IFileService;
import com.ningyuan.utils.CryptUtil;
import com.ningyuan.utils.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("rest/file")
public class FileController extends BaseController {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IFileService fileService;

    /**
     * 获取base64图片数据
     * @param fileName
     * @return
     */
    @RequestMapping(value="getImgBase64",method = RequestMethod.GET)
    @ResponseBody
    public Object getImgBase64(@RequestParam("idFile")String fileName){

        SysFileInfoModel fileInfo = fileService.getByName(fileName);
        FileInputStream fis = null;
        try {
            File file = new File(fileInfo.getAblatePath());
            byte[] bytes = new byte[(int) file.length()];
            fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytes);
            String base64 = CryptUtil.encodeBASE64(bytes);
            return Rets.success(Maps.newHashMap("imgContent", base64));
        } catch (Exception e) {
            logger.error("get img error",e);
            return Rets.failure("获取图片异常");
        }finally{
            try {
                fis.close();
            }catch (Exception e){
                logger.error("close getImgBase64 error",e);
            }
        }

    }

    /**
     * 获取图片流
     * @param response
     * @param fileName
     */
    @RequestMapping(value="getImgStream",method = RequestMethod.GET)
    public void getImgStream(HttpServletResponse response, @RequestParam("idFile")String fileName){
        SysFileInfoModel fileInfo = fileService.getByName(fileName);
        FileInputStream fis = null;
        response.setContentType("image/"+fileInfo.getRealFileName().split("\\.")[1]);
        try {
            OutputStream out = response.getOutputStream();
            File file = new File(fileInfo.getAblatePath());
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            logger.error("getImgStream error",e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("close getImgStream error",e);
                }
            }
        }
    }
}

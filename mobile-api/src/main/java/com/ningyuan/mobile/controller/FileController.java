package com.ningyuan.mobile.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.bean.front.Rets;
import com.ningyuan.mobile.dto.Base64FileDto;
import com.ningyuan.mobile.model.SysFileInfoModel;
import com.ningyuan.mobile.service.IFileService;
import com.ningyuan.utils.CryptUtil;
import com.ningyuan.utils.HttpUtil;
import com.ningyuan.utils.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private IFileService fileService;

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    @RequestMapping(value="upload",method = RequestMethod.POST)
    public Object upload(@RequestPart("file") MultipartFile multipartFile) {

        try {
            SysFileInfoModel fileInfo = fileService.upload(multipartFile);
            return Rets.success(fileInfo);
        } catch (Exception e) {
            logger.error("上传文件异常",e);
            return Rets.failure("上传文件失败");
        }
    }

    @RequestMapping(value = "upload/base64",method = RequestMethod.POST)
    public Object uploadUploadFileBase64(@RequestBody Base64FileDto base64File) {

        try {
            fileService.upload(base64File);
            return Rets.success();
        } catch (Exception e) {
            logger.error("上传文件异常",e);
            return Rets.failure("上传文件失败");
        }
    }
    /**
     * 下载文件
     * @param fileName
     */
    @RequestMapping(value="download",method = RequestMethod.GET)
    public void download(@RequestParam("idFile") String fileName){
        SysFileInfoModel fileInfo = fileService.getByName(fileName);
        fileName = StringUtils.isEmpty(fileName)? fileInfo.getOriginalFileName():fileName;
        HttpServletResponse response = HttpUtil.getResponse();
        response.setContentType("application/x-download");
        try {
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        OutputStream os = null;
        try {
            File file = new File(fileInfo.getAblatePath());
            os = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while(i != -1){
                os.write(buffer);
                i = bis.read(buffer);
            }

        } catch (Exception e) {
            logger.error("download error",e);
        }finally {
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                logger.error("close inputstream error", e);
            }
        }

    }

    /**
     * 获取base64图片数据
     * @param fileName
     * @return
     */
    @RequestMapping(value="getImgBase64",method = RequestMethod.GET)
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
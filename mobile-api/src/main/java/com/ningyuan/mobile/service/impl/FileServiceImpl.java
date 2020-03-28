package com.ningyuan.mobile.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.core.Conf;
import com.ningyuan.mobile.constant.cache.Cache;
import com.ningyuan.mobile.constant.cache.CacheKey;
import com.ningyuan.mobile.daomapper.mapper.FileMapper;
import com.ningyuan.mobile.dto.Base64FileDto;
import com.ningyuan.mobile.model.SysFileInfoModel;
import com.ningyuan.mobile.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl extends BaseServiceImpl<FileMapper, SysFileInfoModel> implements IFileService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 文件上传
     * @param multipartFile
     * @return
     */
    @Override
    public SysFileInfoModel upload(MultipartFile multipartFile){
        String uuid = UUID.randomUUID().toString();
        String originalFileName = multipartFile.getOriginalFilename();
        String realFileName =   uuid +"."+ originalFileName.split("\\.")[originalFileName.split("\\.").length-1];
        try {

            File file = new File(Conf.get("system.file.upload.path"));
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            multipartFile.transferTo(file);
            return save(multipartFile.getOriginalFilename(),file);
        } catch (Exception e) {

            logger.error("保存文件异常",e);

        }
        return null;
    }
    /**
     * 文件上传
     * @param base64File
     * @return
     */
    @Override
    public SysFileInfoModel upload(Base64FileDto base64File){
        String uuid = UUID.randomUUID().toString();
        String originalFileName = base64File.getName();
        String realFileName =   uuid +"."+ originalFileName.split("\\.")[originalFileName.split("\\.").length-1];
        try {
            File file = new File(Conf.get("system.file.upload.path"));
            if(base64ToFile(base64File.getBase64(),file)){
                return save(originalFileName,file);
            }

        } catch (Exception e) {
            logger.error("保存文件异常",e);
        }
        return null;
    }

    private boolean base64ToFile(String base64, File file) {
        base64 = base64.substring(base64.indexOf(",")+1);
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {

            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            return true;
        } catch (Exception e) {
            logger.error("base64转视频文件失败", e);
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    logger.error("关闭文件流bos失败", e);
                    return false;
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error("关闭文件流fos失败", e);
                    return false;
                }
            }
        }
    }

    /**
     * 创建文件
     * @param originalFileName
     * @param file
     * @return
     */
    @Override
    public SysFileInfoModel save(String originalFileName,File file){
        try {
            SysFileInfoModel fileInfo = new SysFileInfoModel();
            fileInfo.setOriginalFileName(originalFileName);
            fileInfo.setRealFileName(file.getName());
            this.mapper.insertSelective(fileInfo);
            return fileInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Cacheable(value = Cache.APPLICATION, key = "'" + CacheKey.FILE_INFO + "'+#id")
    public SysFileInfoModel get(Long id){
        SysFileInfoModel infoModel = new SysFileInfoModel();
        infoModel.setId(id);
        SysFileInfoModel fileInfo = this.mapper.selectByPrimaryKey(infoModel);
        fileInfo.setAblatePath(Conf.get("system.file.upload.path") + File.separator + fileInfo.getRealFileName());
        return fileInfo;
    }

    @Override
    public SysFileInfoModel getByName(String fileName) {
        SysFileInfoModel infoModel = new SysFileInfoModel();
        infoModel.setId(Long.parseLong(fileName));
        SysFileInfoModel fileInfo = this.selectLimitOne(infoModel);
        fileInfo.setAblatePath(Conf.get("system.file.upload.path") + File.separator + fileInfo.getRealFileName());
        return fileInfo;
    }
}


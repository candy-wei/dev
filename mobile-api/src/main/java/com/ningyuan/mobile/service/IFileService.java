package com.ningyuan.mobile.service;

import com.ningyuan.base.IBaseService;
import com.ningyuan.mobile.constant.cache.Cache;
import com.ningyuan.mobile.constant.cache.CacheKey;
import com.ningyuan.mobile.dto.Base64FileDto;
import com.ningyuan.mobile.model.SysFileInfoModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IFileService extends IBaseService<SysFileInfoModel> {
    SysFileInfoModel upload(MultipartFile multipartFile);

    SysFileInfoModel upload(Base64FileDto base64File);

    SysFileInfoModel save(String originalFileName, File file);

    @Cacheable(value = Cache.APPLICATION, key = "'" + CacheKey.FILE_INFO + "'+#id")
    SysFileInfoModel get(Long id);

    SysFileInfoModel getByName(String fileName);
}

package com.liaocc.test.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface AvatarService {
    InputStream getAvatar(Long userid);
    public InputStream getAvatarbyBlog(Long blogid);
    void saveAvatar(Long userid, MultipartFile file);
}

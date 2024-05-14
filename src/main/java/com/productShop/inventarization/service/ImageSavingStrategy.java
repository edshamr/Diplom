package com.productShop.inventarization.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImageSavingStrategy {
    String saveImage(MultipartFile image);
}

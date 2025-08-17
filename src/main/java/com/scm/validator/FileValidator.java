package com.scm.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; //2mb se jyada upload nhi hoga

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if(file==null || file.isEmpty()){
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("File can not be empty!").addConstraintViolation();
            return true;
        }

        System.out.println("file size:"+ file.getSize());
        //file size
        if(file.getSize()> MAX_FILE_SIZE){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 2MB").addConstraintViolation();
            return false;
        }

        return true;
    }
}

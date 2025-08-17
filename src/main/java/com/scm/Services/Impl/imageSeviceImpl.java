package com.scm.Services.Impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.Services.imageService;
import com.scm.helper.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class imageSeviceImpl implements imageService {

    private Cloudinary cloudinary;

    public imageSeviceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String uploadImage(MultipartFile contactImage,  String filename) {
        //wo code likhna h jo image ko upload kr rha ho

//        String filename= UUID.randomUUID().toString();

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id",filename));
            return this.getUrlFromPublicId(filename);

        }catch(IOException e){
            e.printStackTrace();
            return null;
        }

        //and return kr rha ho img ur
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstants.CONTACT_IMAGE_WIDTH)
                                .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                                .crop(AppConstants.CONTACT_IMAGE_CROP))
                .generate(publicId);
    }
}

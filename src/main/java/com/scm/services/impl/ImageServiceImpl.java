package com.scm.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.services.ImageService;
@Service
public class ImageServiceImpl implements ImageService {
	
	private Cloudinary cloudinary ;
	

	public ImageServiceImpl(Cloudinary cloudinary) {
		super();
		this.cloudinary = cloudinary;
	}


	@Override
	public String uploadImage(MultipartFile picture) {
		
		 String fileName = UUID.randomUUID().toString(); // Generate a random UUID as public_id
		    
		    try {
		        // Convert the picture to byte array and upload to Cloudinary
		        byte[] data = picture.getBytes(); // Use getBytes() instead of available() method
		        
		        // Upload the image with the generated fileName
		        cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", fileName));
		  
		     // Return the URL of the uploaded image
			    return getUrlFromPublicId(fileName);
			    
		    } catch (Exception e) {
		        e.printStackTrace();
		        return null; // Return null if upload fails
		    }
		    
		    
		}

	public String getUrlFromPublicId(String publicId) {
	    return cloudinary
	            .url()
	            .transformation(new Transformation<>()
	                            .width(500)
	                            .height(500)
	                            .crop("fill")
	                            .quality("auto") // Automatically adjust quality based on image
	            )
	            .generate(publicId);
	}
}
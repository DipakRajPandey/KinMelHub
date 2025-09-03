package dipak.kinmelhub.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
@Service
public class CloudinaryService{

	@Autowired
	private Cloudinary cloudinary;
	public Map<String,Object> upload(MultipartFile image) {
		
		try {
			Map<String, Object> data=cloudinary.uploader().upload(image.getBytes(),Map.of());
		    return data;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("image upload faild");
		}
	}
}

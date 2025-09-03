package dipak.kinmelhub.config;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "deteuyxr0",
                "api_key", "621759928596191",
                "api_secret", "Jm-aMBKJkYEGWRjpjw_2qH0X_HM"
        ));
    }
}

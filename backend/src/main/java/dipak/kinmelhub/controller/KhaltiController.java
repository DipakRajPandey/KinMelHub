package dipak.kinmelhub.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dipak.kinmelhub.model.KhaltiRequest;

@RestController
@RequestMapping("/api/khalti")
// @CrossOrigin(origins = "http://localhost:5173")
public class KhaltiController {

//	    private final String khaltiSecretKey = "test_secret_key_07ef4e309c394dcbb8c2e556d3e843e8";
	    private final String khaltiSecretKey = "test_secret_key_07ef4e309c394dcbb8c2e556d3e843e8";

	    @PostMapping("/verify")
	    public ResponseEntity<?> verifyPayment(@RequestBody KhaltiRequest request) {
	     System.out.println("token  "+request.getToken());
	     System.out.println("amount  "+ request.getAmount());
	    	
	        try {
	            @SuppressWarnings("deprecation")
				URL url = new URL("https://khalti.com/api/v2/payment/verify/");
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Authorization", "Key " + khaltiSecretKey);
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setDoOutput(true);

	            String payload = String.format(
	                "{\"token\":\"%s\",\"amount\":%d}", request.getToken(), request.getAmount()
	            );

	            try (OutputStream os = conn.getOutputStream()) {
	                byte[] input = payload.getBytes("utf-8");
	                os.write(input, 0, input.length);
	            }

	            int code = conn.getResponseCode();

	            InputStream responseStream = code == 200 ? conn.getInputStream() : conn.getErrorStream();
	            String result = new BufferedReader(new InputStreamReader(responseStream))
	                .lines().collect(Collectors.joining("\n"));
	            System.out.println("Khalti Response: " + result);

	            return new ResponseEntity<>(result, HttpStatus.valueOf(code));

	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Verification failed");
	        }
	    }
	

}

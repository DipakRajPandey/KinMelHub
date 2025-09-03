package dipak.kinmelhub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dipak.kinmelhub.exception.AccessDeniedException;
import dipak.kinmelhub.model.Users;
import dipak.kinmelhub.repository.UserRepository;

@Service
public class CustomUserService  implements UserDetailsService{
@Autowired
private UserRepository userRepo;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  Users user = userRepo.findByEmail(username)
		            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	if(user.getStatus()==Users.Status.BAN) {
		throw new  AccessDeniedException("User Ban by Admin");
	}
		  return new org.springframework.security.core.userdetails.User(
				    
		            user.getEmail(),
		            user.getPassword(),
		            List.of(new SimpleGrantedAuthority(user.getRole().name()))
		        );
	}
	

}

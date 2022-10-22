package uz.pdp.appemailsend.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appemailsend.Entity.Enums.Rolenames;
import uz.pdp.appemailsend.Entity.User;
import uz.pdp.appemailsend.Payload.ApiResponse;
import uz.pdp.appemailsend.Payload.RegisterDto;
import uz.pdp.appemailsend.Repositry.RoleRepository;
import uz.pdp.appemailsend.Repositry.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository rolerepository;
    @Autowired
    JavaMailSender mailSender;

    public ApiResponse registerUser(RegisterDto registerDto) {
        if (repository.existsByEmail(registerDto.getEmail())) {
            return new ApiResponse("Email Already exist", false);
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singleton(rolerepository.findByRolename(Rolenames.ROLE_USER)));
        user.setEmailCode(UUID.randomUUID().toString());
        repository.save(user);
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("Congratulate, check email", true);
    }

    public Boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("muxammadiyev@gmail.com");
            message.setTo(sendingEmail);
            message.setSubject("Conform account");
            message.setText("<a href='http://localhots:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Confirm account</a>");
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

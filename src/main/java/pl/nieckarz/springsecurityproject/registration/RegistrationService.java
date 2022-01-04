package pl.nieckarz.springsecurityproject.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nieckarz.springsecurityproject.appuser.AppUser;
import pl.nieckarz.springsecurityproject.appuser.AppUserRole;
import pl.nieckarz.springsecurityproject.appuser.AppUserService;
import pl.nieckarz.springsecurityproject.registration.token.ConfirmationToken;
import pl.nieckarz.springsecurityproject.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }

        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER

                )
        );
    }

    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(()-> new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt() !=null){
            throw new IllegalStateException("email already confirmed");
        }

        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());

        return "confirmed";
    }
}

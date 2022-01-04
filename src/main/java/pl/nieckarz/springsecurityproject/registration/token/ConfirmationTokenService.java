package pl.nieckarz.springsecurityproject.registration.token;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nieckarz.springsecurityproject.appuser.AppUserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserRepository appUserRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }

}

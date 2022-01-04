package pl.nieckarz.springsecurityproject.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean test(String s) {

            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(s);
            boolean isValid = matcher.find();

            if(!isValid){
                throw new IllegalStateException("email is not valid");
            }


        return true;
    }
}

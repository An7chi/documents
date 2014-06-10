package ee.risthein.erko.dokumendid.services;

import ee.risthein.erko.dokumendid.entities.UserAccount;
import ee.risthein.erko.dokumendid.repositories.UserAccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

/**
 * @author Erko Risthein
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Inject
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAccount account = userAccountRepository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("No such userAccount: " + username);
        }

        return new User(
                account.getUsername(),
                account.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
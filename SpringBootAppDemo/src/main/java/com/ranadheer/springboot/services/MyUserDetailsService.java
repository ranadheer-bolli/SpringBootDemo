package com.ranadheer.springboot.services;

import com.ranadheer.springboot.entity.MyUserDetails;
import com.ranadheer.springboot.entity.User;
import com.ranadheer.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository mockRepository) {
        this.userRepository=mockRepository;
    }


    public void save(User user){
        Optional<User> user1 = userRepository.findByUserName(user.getUserName());
        if(user1.isEmpty()){
            userRepository.save(user);
        }
        else {
            throw new UsernameNotFoundException("USER ALREADY EXISTS");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(s);
       if(!user.isPresent())
           throw new UsernameNotFoundException("USER DOESN'T EXIST");
        if(user.isPresent()) {
            return user.map(MyUserDetails::new).get();
        }
        return new MyUserDetails();
    }
}

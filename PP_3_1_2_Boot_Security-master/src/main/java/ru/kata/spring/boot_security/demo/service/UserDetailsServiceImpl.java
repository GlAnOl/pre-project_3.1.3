package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.security.UsersDetails;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//       Optional<User> user= userRepository.findByUsername(username);
        Optional<User> user = Optional.ofNullable(userRepository.findByName(username));
       if(user.isEmpty()){
           throw new UsernameNotFoundException("User not found!");
       }
       return new UsersDetails(user.get());
    }


//    @Autowired
//    private UserDaoImp ud;
//
//    public UserServiceImpl() {
//    }
//
//    @Transactional
//    @Override
//    public void addUser(User user) {
//        ud.addUser(user);
//    }
//
//
//    @Transactional(readOnly = true)
//    @Override
//    public List<User> getUsersList() {
//        return ud.getUsersList();
//    }
//
//
// @Override
// @Transactional(readOnly = true)
// public User getUser(int id) {
//  return ud.getUser(id);
// }
//
//
//    @Transactional
//    @Override
//    public void dropUser(int id) {
//        ud.dropUser(id);
//    }
//
//
//    @Transactional
//    @Override
//    public void changeUser(User user) {
//       ud.changeUser(user);
//    }
}

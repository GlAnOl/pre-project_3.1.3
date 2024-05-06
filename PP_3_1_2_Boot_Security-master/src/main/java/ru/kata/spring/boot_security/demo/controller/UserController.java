package ru.kata.spring.boot_security.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public String printUser(Model model) {

        model.addAttribute("user", userService.findOne());

        return "user/user";
    }
}
//
//    @GetMapping("/new")
//    public String newUser(@ModelAttribute("user") User user) {
//        return "new";
//    }
//    @PostMapping()
//    public String createUser(@ModelAttribute("user") User user) {
//        userService.addUser(user);
//        return "redirect:/";
//    }
//
//
//    @GetMapping("/edit")
//    public String editUser(Model model, @RequestParam (value="id") int id){
//            model.addAttribute("user", userService.getUser(id));
//            return "edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String updateUser(@ModelAttribute("user") User user){
//        userService.changeUser(user);
//        return "redirect:/";
//    }
//
//
//    @DeleteMapping("/delete")
//    public String delete (@RequestParam (value="id", required = false) int id){
//        userService.dropUser(id);
//        return "redirect:/";
//    }
//
//
//










//        @GetMapping("/")
//        public String getUsersList(Model model){
//            model.addAttribute("users", userService.getUsersList());
//            return "users";
//        }
//
//        @GetMapping("/new")
//        public String createNewUser(@ModelAttribute("user") User user) {
//            return "new";
//        }
//
//        @PostMapping("/")
//        public String addUser(@ModelAttribute("user")  User user) {
//            userService.addUser(user);
//            return "redirect:/";
//        }
//
//        @GetMapping("/{id}/edit")
//        public String editUser(Model model, @PathVariable("id") int id) {
//            model.addAttribute("user", userService.getUser(id));
//            return "update";
//        }
//
//        @PatchMapping("/{id}")
//        public String updateUser(@ModelAttribute("user")  User user) {
//            userService.changeUser(user);
//            return "redirect:/";
//        }
//
//        @DeleteMapping("/{id}")
//        public String deleteUser(@PathVariable("id") int id) {
//            userService.dropUser(id);
//            return "redirect:/";
//        }


//    @RequestMapping(value = "/get", method = RequestMethod.GET)
//    @ResponseBody
//    public User getUser(){
//        return createMocUser();
//    }
//
//    private User createMocUser() {
//        return new User("Anton","Glushkov", "@mail.ru");
//    }


//}
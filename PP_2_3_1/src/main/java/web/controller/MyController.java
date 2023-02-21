package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDaoImp;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImp;

import java.util.List;

@Controller
public class MyController {
    @Autowired
    UserService userServiceImp;

   @GetMapping ("/")
    public String getAllUsers (Model model) {
       List<User> allUsers = userServiceImp.getAllUsers();
       model.addAttribute("theUsers", allUsers);
       return "all-users";
    }
    @GetMapping("/new")
    public String addNewUser (Model model) {
       User newUser = new User();
       model.addAttribute("newUser", newUser);
       return "new-user";
    }
    @PostMapping("/saveUser")
    public String saveUser (@ModelAttribute("newUser") User user,
                            @RequestParam("name")String name,
                            @RequestParam("surname")String surname) {
       user.setName(name);
       user.setSurname(surname);
       userServiceImp.add(user);
       return "redirect:/";
    }
   @GetMapping ("/{id}/updateUser")
    public String updateUser(Model model, @PathVariable("id")int id) {
       model.addAttribute("userForUpdate", userServiceImp.getUserById(id));
       return "update";
    }
    @PostMapping("/{id}")
    public String update (@ModelAttribute("userForUpdate") User user,@PathVariable("id")int id) {
       userServiceImp.update(id,user);
       return "redirect:/";
    }
    @GetMapping("/{id}/delete")
    public String delete (@PathVariable("id") int id) {
        userServiceImp.delete (id);
       return "redirect:/";
    }

}

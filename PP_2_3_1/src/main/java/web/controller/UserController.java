package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {
    @Autowired
   private UserService userServiceImp;

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

    @PostMapping ("/updateUser")
    public String updateUser2(Model model, @RequestParam("idToUpdate")int id) {
        model.addAttribute("userForUpdate", userServiceImp.getUserById(id));
        return "update";
    }

    @PostMapping("/update")
    public String update (@ModelAttribute("userForUpdate") User user
            ,@RequestParam("idToUpdate")int id) {
        userServiceImp.update(id,user);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete2 (@RequestParam("idToDelete") int id) {
        userServiceImp.delete (id);
        return "redirect:/";
    }

}

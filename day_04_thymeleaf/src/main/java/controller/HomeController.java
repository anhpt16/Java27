package controller;

import lombok.extern.slf4j.Slf4j;
import model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private List<Person> persons = new ArrayList<>(List.of(
        new Person(1, "An", "M", 20),
        new Person(2, "Bảo", "M", 22),
        new Person(3, "Hồng", "F", 18),
        new Person(4, "Khánh", "M", 25),
        new Person(5, "Ngọc", "F", 18)
    ));

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("persons", persons);
        model.addAttribute("hi", "hi");
        persons.forEach(person -> {
            System.out.println(person.toString());
        });
        System.out.println("End");
        return "home";
    }
}

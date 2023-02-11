package loginAttempts.controller;

import loginAttempts.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonController {
    private final BookService bookService;

    @Autowired
    public PersonController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getAll(Model model){
        model.addAttribute("allBooks", bookService.getAllBooks());
        return "all_books";
    }


}
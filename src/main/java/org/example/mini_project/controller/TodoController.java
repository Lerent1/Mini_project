package org.example.mini_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mini_project.model.Todo;
import org.example.mini_project.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TodoController {
    private final TodoRepository todoRepository;

    @GetMapping("/")
    public String listTodos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form";
    }

    @PostMapping("/add")
    public String addTodo(
            @Valid @ModelAttribute("todo") Todo todo,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        todo.setStatus("PENDING");
        todoRepository.save(todo);
        return "redirect:/";
    }
}

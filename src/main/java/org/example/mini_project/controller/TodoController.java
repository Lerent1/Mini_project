package org.example.mini_project.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mini_project.model.Todo;
import org.example.mini_project.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class TodoController {
    private final TodoRepository todoRepository;

    @GetMapping("/")
    public String listTodos(Model model, HttpSession session) {
        String ownerName = (String) session.getAttribute("ownerName");
        if (ownerName == null) {
            return "redirect:/welcome";
        }
        model.addAttribute("ownerName", ownerName);
        model.addAttribute("todos", todoRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form";
    }

    @PostMapping("/save")
    public String saveTodo(
            @Valid @ModelAttribute("todo") Todo todo,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        if (todo.getId() == null) {
            todo.setStatus("PENDING");
        }
        todoRepository.save(todo);

        redirectAttributes.addFlashAttribute("message", "Thao tac thanh cong");
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Todo todo = todoRepository.findTodoById(id);
        model.addAttribute("todo", todo);
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        todoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xoa thanh cong");
        return "redirect:/";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @PostMapping("/start")
    public String start(String name, HttpSession session) {
        if (name == null || name.trim().isEmpty()) {
            return "welcome";
        }
        session.setAttribute("ownerName", name);
        return "redirect:/";
    }
}
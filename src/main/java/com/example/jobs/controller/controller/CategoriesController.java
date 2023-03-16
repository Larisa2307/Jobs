package com.example.jobs.controller.controller;

import com.example.jobs.entity.Category;
import com.example.jobs.entity.Page;
import com.example.jobs.service.CategoryService;
import com.example.jobs.service.NavbarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class CategoriesController {

    final NavbarService navbarService;
    final CategoryService categoryService;

    @GetMapping("/categories")
    String getCategoriesPage(Model model, @RequestParam(required = false) final Boolean duplicate) {
        navbarService.activateNavbarTab(Page.CATEGORIES, model);
        model.addAttribute("categoryList", categoryService.getCategoryModelList());
        model.addAttribute("duplicate", duplicate);
        return "categories";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("category") Category category) {
        if (categoryService.isDuplicate(category.getName())) {
            return "redirect:/categories?duplicate=true";
        } else {
            categoryService.saveCategory(category);
        }

        return "redirect:/categories";
    }

    @PostMapping("/delete-category/{categoryId}")
    public String deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        System.out.println(categoryId);

        return "redirect:/categories";
    }

    @PostMapping("/edit-category/{id}")
    public String editCategory(@ModelAttribute("category") Category category, @PathVariable("id") final String id) {
        category.setId(id);
        if (categoryService.isDuplicateExcept(category.getName(), category.getId())) {
            return "redirect:/categories?duplicate=true";
        } else {
            categoryService.saveCategory(category);
        }

        return "redirect:/categories";
    }
}

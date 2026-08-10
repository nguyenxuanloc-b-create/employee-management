package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeRequest;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.web.EmployeeForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeeMvcController {

    private final EmployeeService employeeService;

    public EmployeeMvcController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping({"", "/list"})
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("employees", employeeService.findAll(keyword));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        return "employees/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());
        model.addAttribute("pageTitle", "Add Employee");
        model.addAttribute("formAction", "/employees/add");
        return "employees/form";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("employeeForm") EmployeeForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add Employee");
            model.addAttribute("formAction", "/employees/add");
            return "employees/form";
        }
        employeeService.create(toRequest(form));
        redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully");
        return "redirect:/employees/list";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        EmployeeForm form = new EmployeeForm();
        form.setName(employee.getName());
        form.setEmail(employee.getEmail());
        form.setDepartmentName(employee.getDepartment().getName());
        model.addAttribute("employeeForm", form);
        model.addAttribute("pageTitle", "Edit Employee");
        model.addAttribute("formAction", "/employees/" + id + "/edit");
        return "employees/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute("employeeForm") EmployeeForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Employee");
            model.addAttribute("formAction", "/employees/" + id + "/edit");
            return "employees/form";
        }
        employeeService.update(id, toRequest(form));
        redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully");
        return "redirect:/employees/list";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully");
        return "redirect:/employees/list";
    }

    private EmployeeRequest toRequest(EmployeeForm form) {
        return new EmployeeRequest(form.getName(), form.getEmail(), null, form.getDepartmentName());
    }
}

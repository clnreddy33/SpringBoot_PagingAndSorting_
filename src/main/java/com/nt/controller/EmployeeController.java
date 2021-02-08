package com.nt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nt.model.Employee;
import com.nt.service.IEmployeeService;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private IEmployeeService service;

	@GetMapping("/register")
	public String showReg(Model model) {
		//Form Backing object
		model.addAttribute("employee", new Employee());
		return "EmployeeReg";
	}

	@PostMapping("/save")
	public String saveEmp(
			@ModelAttribute Employee employee,
			Model model) 
	{
		Integer id  = service.saveEmployee(employee);
		model.addAttribute("message", "Employee '"+id+"' saved");
		
		//Form Backing object
		model.addAttribute("employee", new Employee());
		return "EmployeeReg";
	}
	
		
	@GetMapping("/all")
	public String getAllEmps(@PageableDefault(page=0,size = 3)Pageable p ,Model model) {
		Page<Employee> page =  service.getAllEmployees(p);
		model.addAttribute("list", page.getContent());
		model.addAttribute("page", page);
		return "EmployeeData";
	}

	@GetMapping("/delete")
	public String removeEmp(@RequestParam Integer eid) {
		service.deleteEmployee(eid);
		return "redirect:all";
	}
	
	@GetMapping("/edit")
	public String showEdit(@RequestParam Integer eid, Model model) {
		Employee emp = service.getOneEmployee(eid);
		model.addAttribute("employee",emp);
		return "EmployeeEdit";
	}
	
	@PostMapping("/update")
	public String updateEmployee(@ModelAttribute Employee employee) {
		service.updateEmployee(employee);
		return "redirect:all";
	}
	
	
	
	
}
package com.ygd.SSM2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ygd.SSM2.entity.Employee;
import com.ygd.SSM2.service.EmployeeService;
import com.ygd.SSM2.util.Msg;

@Controller
@RequestMapping("/Employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@InitBinder("employee")
	private void initBinder(WebDataBinder binder){
		binder.setFieldDefaultPrefix("emp.");
	}

	
	/**  
	* @Title: getAllList  
	* @Description: 在页面使用jstl从请求域中获取到pageInfo，但是不利于移动设备的解析
	* @param page
	* @param rows
	* @param employee
	* @param model
	* @return String 返回类型    
	* @throws  
	*/  
	@RequestMapping("/allList")
	public String getAllList(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows, @ModelAttribute("employee")Employee employee,Model model) {

		List<Employee> emps = employeeService.getByPage(page, rows);
		
		PageInfo<Employee> pageInfo = new PageInfo<>(emps);
		
		model.addAttribute("pageInfo", pageInfo);
		
		return "employee";
	}
	
	/**  
	* @Title: getListWithJson  
	* @Description: 根据条件分页查询员工信息
	* @param page
	* @param rows
	* @param employee
	* @return Msg 返回类型    
	* @throws  
	*/  
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getListWithJson(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows, @ModelAttribute("employee")Employee employee) {

		List<Employee> emps = employeeService.getByPage(page, rows);
		
		PageInfo<Employee> pageInfo = new PageInfo<>(emps);
		
		return Msg.success().add("pageInfo",pageInfo);
	}

	@ResponseBody
	@RequestMapping("/test")
	public Map<String, Object> test() {
		Map<String, Object> map = new HashMap<>();
		map.put("姓名", "张三");
		map.put("性别", "男");
		return map;
	}
}
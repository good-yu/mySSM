package com.ygd.SSM2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ygd.SSM2.dto.EmployeeWithDep;
import com.ygd.SSM2.entity.Employee;
import com.ygd.SSM2.service.EmployeeService;
import com.ygd.SSM2.util.Msg;

@Controller
@RequestMapping(value = "/Employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/*@InitBinder("employee")
	private void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("emp.");
	}*/

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
	@RequestMapping(value = "/allList")
	public String getAllList(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			@ModelAttribute("employee") EmployeeWithDep employee, Model model) {

		/*List<EmployeeWithDep> emps = employeeService.getByPage(page, rows);

		PageInfo<EmployeeWithDep> pageInfo = new PageInfo<>(emps);

		model.addAttribute("pageInfo", pageInfo);*/

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
	@ResponseBody
	@RequestMapping(value = "/emps", method = RequestMethod.GET)
	public Msg getListWithJson(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			@RequestParam(value = "search",required = false)String search) {

		List<EmployeeWithDep> emps = employeeService.getByPage(page, rows, search);

		PageInfo<EmployeeWithDep> pageInfo = new PageInfo<>(emps);

		return Msg.success().add("pageInfo", pageInfo);
	}

	/**
	 * @Title: addEmp 
	 * @Description: 保存员工 
	 * @param employee 
	 * @return Msg 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/addEmp", method = RequestMethod.POST)
	public Msg addEmp(Employee employee) {

		employeeService.insertEmp(employee); 

		return Msg.success();
	}

	/**
	 * @Title: getEmpById
	 * @Description: 根据id获取到员工信息 
	 * @param empId 
	 * @return Msg 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getEmp/{empId}", method = RequestMethod.GET)
	public Msg getEmpById(@PathVariable Integer empId) {

		Employee employee = employeeService.getEmployeeById(empId);

		return Msg.success().add("emp", employee);
	}

	/**
	 * @Title: updateEmp 
	 * @Description: 根据id更新用户 
	 * @param employee
	 * @return Msg 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEmp/{empId}", method = RequestMethod.PUT)
	public Msg updateEmp(Employee employee) {

		employeeService.updateEmployeeById(employee);

		return Msg.success();
	}

	/**  
	* @Title: deleteEmp  
	* @Description: 单个/批量删除
	* @param empIds
	* @return Msg 返回类型    
	* @throws  
	*/  
	@ResponseBody
	@RequestMapping(value = "/deleteEmp/{empIds}",method = RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("empIds")String empIds){
		
		if(empIds.contains("-")){
			
			List<Integer> del_ids = new ArrayList<Integer>();
			String[] ids = empIds.split("-");
			
			for(String StrIds:ids){
				del_ids.add(Integer.parseInt(StrIds));
			}
			
			employeeService.deleteByBatch(del_ids);
		}else{
			Integer empId = Integer.parseInt(empIds);
			employeeService.deleteItem(empId);
		}
		
		return Msg.success();	
	}

	/**  
	* @Title: validEmp  
	* @Description: 验证员工名重复
	* @param empName
	* @param empId
	* @return Boolean 返回类型    
	* @throws  
	*/  
	@ResponseBody
	@RequestMapping(value="/validEmpName",method=RequestMethod.GET)
	public Boolean validEmp(@RequestParam("empName")String empName,@RequestParam(value="empId",required=false)Integer empId){
		
		Integer count = employeeService.getEmpNameCount(empName, empId);
		
		if(count<1){
			return true;
		}else{
			return false;
		}
			
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

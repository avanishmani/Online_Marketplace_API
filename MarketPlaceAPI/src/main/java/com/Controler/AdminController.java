package com.Controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Entity.Admin;
import com.Entity.AdminDto;
import com.Entity.CurrentUserSession;
import com.Exception.AdminException;
import com.Exception.LoginException;
import com.Service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/adminController")
@Api(value = "My API")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@PostMapping("/adminSignUp")
	@ApiOperation(value = "Say hello", notes = "Returns a greeting")
	public ResponseEntity<Admin> signUpAdminHandler(@Valid @RequestBody Admin admin) throws AdminException {

		Admin savedUser = adminService.signUpAdmin(admin);

		return new ResponseEntity<Admin>(savedUser, HttpStatus.OK);
	}

	@PutMapping("/updateAdmin")
	public ResponseEntity<Admin> updateAdminDetailsHandler(@Valid @RequestBody Admin user,
			@RequestParam("key") String key) throws AdminException, LoginException {

		Admin updatedAdmin = adminService.updateAdminDetails(user, key);

		return new ResponseEntity<Admin>(updatedAdmin, HttpStatus.OK);

	}

	@DeleteMapping("/deleteAdmins")
	public ResponseEntity<Admin> deleteAdminHandler(@RequestParam("key") String key)
			throws AdminException, LoginException {

		Admin deletedAdmin = adminService.deleteAdminAccount(key);

		return new ResponseEntity<Admin>(deletedAdmin, HttpStatus.OK);

	}

	@GetMapping("/adminsByUserName")
	public ResponseEntity<Admin> getAdminByUserNameHandler(@RequestParam("adminUserName") String adminUserName)
			throws AdminException, LoginException {

		Admin admin = adminService.findByUserName(adminUserName);

		return new ResponseEntity<Admin>(admin, HttpStatus.OK);

	}

	@GetMapping("/adminsByAdminId")
	public ResponseEntity<Admin> getAdminByAdminIdHandler(@RequestParam("adminId") Integer adminId)
			throws AdminException, LoginException {

		Admin admin = adminService.findByAdminId(adminId);

		return new ResponseEntity<Admin>(admin, HttpStatus.OK);

	}

	@GetMapping("/admins")
	public ResponseEntity<List<Admin>> findAllAdminsHandler() throws AdminException {

		List<Admin> allAdmin = adminService.findAllUsers();

		return new ResponseEntity<List<Admin>>(allAdmin, HttpStatus.OK);

	}

	@PostMapping("/loginAdmin")
	public ResponseEntity<CurrentUserSession> loginAdminHandler(@Valid @RequestBody AdminDto admin)
			throws LoginException {

		CurrentUserSession cus = adminService.loginAdmin(admin);

		return new ResponseEntity<CurrentUserSession>(cus, HttpStatus.OK);

	}

	@PostMapping("/logoutAdmin")
	public ResponseEntity<String> logoutAdminHandler(@RequestParam("key") String key) throws LoginException {

		String res = adminService.logoutAdmin(key);

		return new ResponseEntity<String>(res, HttpStatus.OK);

	}
}

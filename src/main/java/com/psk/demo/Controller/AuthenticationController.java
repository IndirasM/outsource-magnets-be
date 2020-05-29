package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.JwtRequest;
import com.psk.demo.Controller.Model.JwtResponse;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.psk.demo.Security.SecurityConstants.TOKEN_PREFIX;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private EmployeeService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = tokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");

		UserDetails userDetails = userDetailsService.loadUserByUsername(tokenUtil.getUsernameFromToken(token));
		String newToken = tokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(newToken));
	}

//	@RequestMapping(value = "/permissions", method = RequestMethod.GET)
//	public ResponseEntity<?> getPermissions(HttpServletRequest request) throws Exception {
//		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
//
//		Employee employee = userDetailsService.findByEmail(tokenUtil.getUsernameFromToken(token));
//
//		List<String> roles = new ArrayList<>();
//		employee.getPermissions().forEach(p -> roles.add(p.getName()));
//
//		return ResponseEntity.ok(new PermissionResponse(roles));
//	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
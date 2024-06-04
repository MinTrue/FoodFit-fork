package com.sds.foodfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
	
	@GetMapping("/demo/demoResult")
	public String goDemoResult() {
		return "demo/demoResult";
	}
}
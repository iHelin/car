package com.ihelin.car.controller.admin;

import java.io.IOException;

import org.apache.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminIndexController extends AdminBaseController {
	
	@RequestMapping(value = "index")
	public String index() throws ParseException, IOException {
		return ftl("index");
	}
}

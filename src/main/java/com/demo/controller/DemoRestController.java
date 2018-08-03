package com.demo.controller;

import com.demo.domain.DemoData;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/v1")
public class DemoRestController {
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/demo")
  public ResponseEntity<List<DemoData>> getDemoData(){
    List<DemoData> data = new ArrayList<DemoData>();
    data.add(new DemoData("A","B","C","D","E"));
    data.add(new DemoData("1","2","3","4","5"));
    return ResponseEntity.ok(data);
  }
}

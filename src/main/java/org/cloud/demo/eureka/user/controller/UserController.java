package org.cloud.demo.eureka.user.controller;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloud.demo.eureka.user.remote.UserRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;

@RestController
//@Controller
public class UserController {
	@Autowired
    UserRemote userRemote;
	
	@Value("${server.port}")
	private String port;

	@GetMapping("/getuser1")
	public String getuser1(@RequestParam("name") String name) {
		return "hello world!!!  端口为：" + port + "名字为：" + name;
	}

	@RequestMapping(value = "yyy", produces = "text/script;charset=UTF-8")	
	//@GetMapping("/getuser12")
	@ResponseBody
	public String getyyy(HttpServletRequest request, HttpServletResponse response, String callback) {
		System.out.println( "Hello World!" );
	      //连接本地的 Redis 服务
	        Jedis jedis = new Jedis("redis",6379);
	        jedis.auth("LqlSuoiU6x3QQh3Y");
	        System.out.println(jedis.get("aaa"));
		Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("loginname", jedis.get("aaa"));
        jedis.close();
        JSONObject object = new JSONObject(resultMap);
        System.out.println(object.toString() );
        return callback + "(" + object.toString() + ")";
	}
	@RequestMapping(value = "xxx", produces = "text/script;charset=UTF-8")
	@ResponseBody
	public String getxxx(HttpServletRequest request, HttpServletResponse response, String callback,@RequestParam("vlaue") String vlaue) {
		Jedis jedis = new Jedis("redis",6379);
        jedis.auth("LqlSuoiU6x3QQh3Y");
        System.out.println(jedis.get("aaa"));
        jedis.set("aaa", vlaue);
        System.out.println(jedis.get("aaa"));
        
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("loginname", jedis.get("aaa"));
            JSONObject object = new JSONObject(resultMap);
            System.out.println(object.toString() );
            jedis.close();
            return  callback + "(" + object.toString() + ")";
	}
	@GetMapping("/getuser2")
	public String getuser2(@RequestParam("name") String name) {
		return userRemote.getdbuser(name);
	}
	@GetMapping("/getuser3")
	public String getuser3(@RequestParam("name") String name) {
		return userRemote.getdbuser(name)+"---hhhh";
	}
	
	
}

package org.cloud.demo.eureka.user.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="service-eureka-home",url="http://eureka-home:8080/")
public interface UserRemote {

	@RequestMapping(value = "/gethome1")
    public String gethome1(@RequestParam("name") String name);
	@RequestMapping(value = "/getdbuser")
    public String getdbuser(@RequestParam("name") String name);
}

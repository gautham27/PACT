package com.example.pacts.topic;

import java.util.*;

import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

	@Autowired
	private TopicService tps;

	@RequestMapping(method= RequestMethod.GET, value="/topics")
	public List<NewTopic> sayHi() {

		return tps.getAllTopics();
	}
	
	
	@RequestMapping("/topics/{id}")
	public NewTopic	 getTopic(@PathVariable("id") String id)
	{
		return tps.getTopic(id);
	}
	
	@RequestMapping(method= RequestMethod.POST, value="/topics",consumes ="application/JSON", produces = {"application/JSON"})
	@ResponseBody
	public String	 addTopic(@RequestBody NewTopic topic)
	{
		
	return	 tps.addTopic(topic).toString();
	}
@RequestMapping(method= RequestMethod.PUT, value="/topics/{id}" )
	public void	 updateTopic(@RequestBody NewTopic topic, @PathVariable String id)
	{
		 tps.updateTopic(topic,id);
	}
	@RequestMapping(method= RequestMethod.DELETE, value="/topics/{id}")
	public void	 deleteTopic(@PathVariable String id)
	{
		 tps.deleteTopic(id);
	}
}
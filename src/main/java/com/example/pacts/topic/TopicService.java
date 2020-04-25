package com.example.pacts.topic;

import java.util.Arrays;
import java.util.*;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

@Service
public class TopicService {
List<NewTopic> nt= new ArrayList<>(Arrays.asList(
		new NewTopic("spring", "spring boot", "Spring description"),
		new NewTopic("java", "core java", "java description"),
		new NewTopic("angular", "angular 4", "angular description")
		));

public NewTopic getTopic(String id)
{
	return nt.stream().filter(e-> e.getId().equals(id)).findFirst().get();
}

public List<NewTopic> getAllTopics()
{
	return nt;
}
public JSONObject addTopic(NewTopic topic) 
{
	 
	JSONObject jObj = new JSONObject();
	try {if(getAllTopics().stream().filter(e->e.getId().equals(topic.getId())).count()==0)
	{	nt.add(topic);
	jObj.put("Status", "Success");
			return jObj;
	}
	else
	{
		jObj.put("Status", "Failed");
		return jObj;
	}
	}catch(Exception e) {try {
		jObj.put("Status", "Failed");
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	return jObj;}
	
	}
public void updateTopic(NewTopic topic,String id)
{
for(int i=0;i<nt.size();i++)
{
	NewTopic t = nt.get(i);
	if(t.getId().equals(id))
	{
		nt.set(i,topic);
		return;
	}
}
}
public void deleteTopic(String id)
{
	nt.removeIf(e->e.getId().equals(id));
	
}
}


package app.finder.topicsource.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.finder.topicsource.model.TopicSource;
import app.finder.topicsource.service.SearchService;

@Controller
public class TopicSourceFinderController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/find")
	public String findTopic(@RequestParam(value = "topic", required = false, defaultValue = "") String topic,
			Model model) {

//		List<TopicSource> topics = new ArrayList<>();
//
//		TopicSource topicSource = new TopicSource();
//		topicSource.setFileName("Test.java");
//		topicSource.setPath("/temp/Test.java");
//		topicSource.setText("This is the source of Test.java");
//		topics.add(topicSource);
//
//		model.addAttribute("topic_list", topics);
		return "topic";
	}
	
	@RequestMapping("/topiclist")
	public String topicList(@RequestParam(value = "topic", required = false, defaultValue = "") String topic,
			Model model) {

		System.out.println("+++++ /topiclist topic: " + topic );
		
		List<TopicSource> topics;
		try {
			topics = searchService.getTopics(topic);
			model.addAttribute("topic_list", topics);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		List<TopicSource> topics = new ArrayList<>();
//
//		TopicSource topicSource = new TopicSource();
//		topicSource.setFileName("Test.java");
//		topicSource.setPath("/temp/Test.java");
//		topicSource.setText("This is the source of Test.java");
//		topics.add(topicSource);

		
		return "topiclist";
	}
}

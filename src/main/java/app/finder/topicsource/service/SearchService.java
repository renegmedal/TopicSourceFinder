package app.finder.topicsource.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import app.finder.topicsource.model.TopicSource;


@Service
public interface SearchService {

	List<TopicSource>  getTopics(String query) throws IOException;  
}

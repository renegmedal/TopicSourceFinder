package app.finder.topicsource.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Service;

import app.finder.topicsource.exception.SearchTopicException;
import app.finder.topicsource.model.TopicSource;

@Service
public class LuceneSearchService implements SearchService {

	@Override
	public List<TopicSource> getTopics(String query) throws SearchTopicException {

		String index = "/Users/rene/learn/topic-index"; //"/Users/rene/learn/learn5/lucene/finder/index";

		List<TopicSource> list = null;
		
		SearchFiles searchFiles = new SearchFiles(index);
		try {
			list = searchFiles.getTopicSources(query);
		} catch (IOException e) {
			throw new SearchTopicException("IO Exception error: " + e.getMessage());
		} catch (ParseException e) {
			throw new SearchTopicException("Parse Exception error: " + e.getMessage());
		}

//		int counter = 0;
//		for (TopicSource topicSource : list) {
//			System.out.println("" + (++counter) + ". -----------------------FileName: " + topicSource.getPath());
//			System.out.println(topicSource.getText());
//		}

		return list;
	}

}

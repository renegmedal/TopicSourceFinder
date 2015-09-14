package app.finder.topicsource.service;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import app.finder.topicsource.model.TopicSource;


/** Simple command-line based search demo. */
public class SearchFiles {

	private static final int SEARCH_MAX_SIZE = 1000;
	// private static final INDEX_DIR =
	// "/Users/rene/learn/learn5/lucene/finder/index";
	private String indexDir;

	public SearchFiles(String indexDir) {
		this.indexDir = indexDir;
	}

	public List<TopicSource> getTopicSources(String queryString) throws IOException, ParseException {
		String field = "contents";
		String queries = null;
		int repeat = 0;
		boolean raw = false;

		int hitsPerPage = SEARCH_MAX_SIZE; // 100;
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDir)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		BufferedReader in = null;
		QueryParser parser = new QueryParser(field, analyzer);

		Query query = parser.parse(queryString);

		//System.out.println("Searching for: " + query.toString(field));
		searcher.search(query, null, SEARCH_MAX_SIZE);

		List<String> list = doSearch(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);

		reader.close();

		List<TopicSource> topicSourceList = new ArrayList<TopicSource>();
		TopicSource topicSource = null;
		int counter = 0;
		for (String fileName : list) {
			topicSource = new TopicSource();
			File file = new File(fileName);
			
			topicSource.setFileName("" + (++counter) + ". " + file.getName());
			topicSource.setPath(file.getCanonicalPath());
			topicSource.setText(readFile(file));
			topicSourceList.add(topicSource);
		}

		return topicSourceList;
	}

	private String readFile(File file) throws IOException {
		
		 
		StringBuffer sb = new StringBuffer();

		BufferedReader reader = new BufferedReader(new FileReader(file));

		String sCurrentLine;
		while ((sCurrentLine = reader.readLine()) != null) {
			sb.append(sCurrentLine + "\n");
		}

		return sb.toString();
	}

//	/** Simple command-line based search demo. */
//	public static void main(String[] args) throws Exception {
//		
//
//		String index = "/Users/rene/learn/topic-index"; //"/Users/rene/learn/learn5/lucene/finder/index";
//		
//		SearchFiles searchFiles = new SearchFiles(index);
//		
//		List<TopicSource> list = searchFiles.getTopicSources("viewresolver");
//		
//		int counter = 0;
//		for (TopicSource topicSource: list) {
//			System.out.println("" + (++counter) + ". -----------------------FileName: " + topicSource.getPath());
//			System.out.println(topicSource.getText());
//		}
//		
//		 
//	}

	/**
	 * This demonstrates a typical paging search scenario, where the search
	 * engine presents pages of size n to the user. The user can then go to the
	 * next page if interested in the next hits.
	 * 
	 * When the query is executed for the first time, then only enough results
	 * are collected to fill 5 result pages. If the user wants to page beyond
	 * this limit, then the query is executed another time and all hits are
	 * collected.
	 * 
	 */
	public static List<String> doSearch(BufferedReader in, IndexSearcher searcher, Query query, int hitsPerPage,
			boolean raw, boolean interactive) throws IOException {

		List<String> list = new ArrayList<String>();

		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		//System.out.println(numTotalHits + " total matching documents");

		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);

		for (int i = start; i < end; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("path");
			if (path != null) {

				//System.out.println((i + 1) + ". " + path);

				list.add(path);

				String title = doc.get("title");
//				if (title != null) {
//					System.out.println("   Title: " + doc.get("title"));
//				}
			} else {
				System.out.println((i + 1) + ". " + "No path for this document.");
			}

		}

		return list;
	}
}

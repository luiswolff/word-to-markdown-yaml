package de.luiswolff.wordToMarkdown;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordMarkdownConverter {

	private static List<String> topics = Arrays.asList("interests", "summary", "web_development", "bussines_logic",
			"database_development", "combination", "conclusion");

	public static void main(String[] args) throws IOException {
		System.out.println("---");
		System.out.println("layout: " + args[0]);
		System.out.println("title: " + args[1]);
		System.out.println("language: " + args[2]);
		System.out.println("text:");
		try (XWPFDocument document = new XWPFDocument(new FileInputStream(args[3]))) {
			printParagraphs(document.getParagraphs());
		}
		System.out.println("---");
	}

	private static void printParagraphs(List<XWPFParagraph> paragraphs) {
		for (int index = 0; index < topics.size(); index++) {
			System.out.printf("\t%s: '", topics.get(index));
			printRuns(paragraphs.get(index).getRuns());
			System.out.println("'");
		}
	}

	private static void printRuns(List<XWPFRun> runs) {
		boolean wasLastBold = false;
		for (XWPFRun run : runs) {
			if (run.isBold()) {
				if (!wasLastBold)
					System.out.print("<b>");
				wasLastBold = true;
			}
			System.out.print(run.text());
			if (!run.isBold()) {
				if (wasLastBold)
					System.out.print("</b>");
				wasLastBold = false;
			}
		}
	}

}

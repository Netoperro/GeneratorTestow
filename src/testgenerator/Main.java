//package testgenerator;
//
////import java.io.File;
////import java.io.FileInputStream;
////import java.io.FileNotFoundException;
////import java.io.InputStream;
////
////import javax.xml.stream.XMLInputFactory;
////import javax.xml.stream.XMLStreamException;
////import javax.xml.stream.XMLStreamReader;
//
//public class Main {
//
//	public static void main(String[] args){
//		//podstawowe parsowanie
////		File xmlFile = new File("test.xml");
////		InputStream is = new FileInputStream(xmlFile);
////		XMLInputFactory factory = XMLInputFactory.newInstance();
////		XMLStreamReader reader = factory.createXMLStreamReader(is);
////		while(reader.hasNext())
////		{
////		    if(reader.hasText())
////		    {
////		        System.out.println(reader.getText());
////		    }
////		    
////		    reader.next();
////		}
////	}
//
//		//nowa instancja parsera
//		QuestionParser dpe = new QuestionParser();
//				
//		//uruchomienie przykladu
//		dpe.runExample();
//				
//
//}
//}
package testgenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class Main {

	public static void main(String[] args) throws JDOMException, IOException {
		SAXBuilder jdomBuilder = new SAXBuilder();
		Document jdomDocument = jdomBuilder.build(new File("test.xml"));
		Element root = jdomDocument.getRootElement();
		int numberOfUnits = root.getChildren().size();
		int numberOfVersions = 2;
		int[] questionsFromUnits = new int[numberOfUnits];
		questionsFromUnits[0] = 2;
		questionsFromUnits[1] = 2;
		ArrayList<ArrayList<Element>> unitsQuestions = new ArrayList<ArrayList<Element>>();
		for (int i = 0; i < numberOfUnits; ++i) {
			ArrayList<Element> list = new ArrayList<Element>();
			if (questionsFromUnits[i] > 0) {
				list = addQuestions(root.getChildren().get(i).getChildren(), questionsFromUnits[i], numberOfVersions);
			}
			unitsQuestions.add(list);
		}
		for (int i = 0; i < numberOfVersions; ++i) {
			System.out.println("Wersja #" + i);
			for (int u = 0; u < numberOfUnits; ++u) {
				if (questionsFromUnits[u] > 0) {
					System.out.println("Pytania dla dzia³u: " + root.getChildren().get(u).getAttributeValue("name"));
					ArrayList<Element> questions = new ArrayList<Element>(
						unitsQuestions.get(u).subList(questionsFromUnits[u] * i, questionsFromUnits[u] * i + questionsFromUnits[u])
					);
					for (int q = 0; q < questions.size(); ++q) {
						printQuestion(questions.get(q));
					}
				}
			}
		} 
	}
	
	private static ArrayList<Element> addQuestions(
			List<Element> unitQuestions,
			int numberOfQuestions,
			int numberOfVersions
	) {
		Random random = new Random(System.nanoTime());
		ArrayList<Element> finalQuestions = new ArrayList<Element>();
		if (unitQuestions.size() >= numberOfQuestions * numberOfVersions) {
			Collections.shuffle(unitQuestions, random);
			finalQuestions = new ArrayList<Element>(unitQuestions.subList(0, numberOfQuestions * numberOfVersions - 1));
		}
		else {
			finalQuestions = new ArrayList<Element>(unitQuestions);
			Collections.shuffle(finalQuestions, random);
			int limit = numberOfQuestions * numberOfVersions - unitQuestions.size();
			for (int i = 0; i < limit; ++i) {
				finalQuestions.add(unitQuestions.get(random.nextInt(unitQuestions.size())));
			}
		}
		return finalQuestions;
	}
	
	private static void printQuestion(Element question) {
		System.out.println("\tTresc pytania: " + question.getChildText("text"));
		if (question.getAttributeValue("type").equals("open")) {
			System.out.println("\t\tWpisz odpowiedz");
		}
		else {
			int n = 1;
			for (int i = 0; i < question.getChild("answers").getChildren().size(); ++i) {
				System.out.println("\t\t" + n + ". " + question.getChild("answers").getChildren().get(i).getText());
				n++;
			}
		}
	}

}

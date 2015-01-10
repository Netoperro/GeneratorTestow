package testgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class QuestionParser {
	
	List<Question> myQuestions;
	List<Answer> myAnswers;
	int j;

	Document dom;
	
	public QuestionParser(){
		//create a list to hold the employee objects
		myQuestions = new ArrayList<Question>();
		myAnswers = new ArrayList<Answer>();
	}	
	

	
	public void runExample() {
		
		//parse the xml file and get the dom object
		parseXmlFile();
		
		//get each employee element and create a Employee object
		parseDocument();
		
		//Iterate through the list and print the data
		printData();
		
	}	
	
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse("test.xml");
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}	
	
	private void parseDocument(){
		//get the root elememt
		Element docEle = dom.getDocumentElement();

		//get a nodelist of <employee> elements
		NodeList qnl = docEle.getElementsByTagName("question");
		if(qnl != null && qnl.getLength() > 0) {
			for(int i = 0 ; i < qnl.getLength();i++) {
				
				//get the employee element
				Element el = (Element)qnl.item(i);
				
				//get the Employee object
				Question q = getQuestion(el);
				
				//add it to list
				myQuestions.add(q);
			}
		}
		
		NodeList anl = docEle.getElementsByTagName("answers");
		if(anl != null && anl.getLength() > 0) {
			for(int j = 0 ; j < anl.getLength() ; j++) {
				
				Element el = (Element)anl.item(j);
				
				Answer a = getAnswer(el);

				myAnswers.add(a);
			}
			
		}
		
		
		

		
	}	

	
	
	
	  public static String getChildContent(Element parent, String name, String missing, String empty) {
		    Element child = getChild(parent, name);
		    if (child == null) {
		      return missing;
		    } else {
		      String content = (String) getContent(child);
		      return (content != null) ? content : empty;
		    }
		  }

		  public static Object getContent(Element element) {
		    NodeList nl = element.getChildNodes();
		    StringBuffer content = new StringBuffer();
		    for (int i = 0; i < nl.getLength(); i++) {
		      Node node = nl.item(i);
		      switch (node.getNodeType()) {
		      case Node.ELEMENT_NODE:
		        return node;
		      case Node.CDATA_SECTION_NODE:
		      case Node.TEXT_NODE:
		        content.append(node.getNodeValue());
		        break;
		      }
		    }
		    return content.toString().trim();
		  }

		  public static Element getChild(Element parent, String name) {
		    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
		      if (child instanceof Element && name.equals(child.getNodeName())) {
		        return (Element) child;
		      }
		    }
		    return null;
		  }
	
	
	
	/**
	 * I take an employee element and read the values in, create
	 * an Employee object and return it
	 * @param empEl
	 * @return
	 */
	private Question getQuestion(Element queEl) {
		
		//for each <employee> element get text or int values of 
		//name ,id, age and name
		String unitName = queEl.getAttribute("name");
		
		String questionText = getTextValue(queEl,"text");
		String answer = getTextValue(queEl,"answer");

		String questionType = queEl.getAttribute("type");
		String answerType = queEl.getAttribute("true");
		
		//Create a new Employee with the value read from the xml nodes
		Question q = new Question(unitName,questionType,questionText,answer,answerType);
		
		return q;
	}

	
	private Answer getAnswer(Element aueEl) {
		
		//for each <employee> element get text or int values of 
		//name ,id, age and name
		String answerCorrect = aueEl.getAttribute("correct");
		
//		String answer1 = getTextAnswer(aueEl,"answer");
//		String answer2 = getTextAnswer(aueEl,"answer");
//		String answer3 = getTextAnswer(aueEl,"answer");
//		String answer4 = getTextAnswer(aueEl,"answer");
		
		String answer1 = getChildContent(aueEl, "answer", "test", "test1");
		String answer2 = getChildContent(aueEl, "answer", "test", "test1");
		String answer3 = getChildContent(aueEl, "answer", "test", "test1");
		String answer4 = getChildContent(aueEl, "answer", "test", "test1");
		
		//Create a new Employee with the value read from the xml nodes
		Answer a = new Answer(answerCorrect, answer1, answer2, answer3, answer4);
		
		return a;
	}
	
	
	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content 
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is name I will return John  
	 * @param ele
	 * @param tagName
	 * @return
	 */
//	private String getTextAnswer(Element ele, String tagName) {
//		String textVal = null;
//		NodeList nl = ele.getElementsByTagName(tagName);
//		if(nl != null && nl.getLength() > 0) {
//			for (j = 0; j < nl.getLength(); j++){
//			Element el = (Element)nl.item(j);
//			textVal = el.getFirstChild().getNodeValue();
//			textVal = el.getC
//			}
//		}
//
//		return textVal;
//	}
	
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);

		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
	
	
	public void printData(){
		
		System.out.println("Welcom in our Test Gen \nWe have " + myQuestions.size() + " questions for ya.");
		
		Iterator<Question> it = myQuestions.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
		
		Iterator<Answer> it2 = myAnswers.iterator();
		while(it2.hasNext()) {
			System.out.println(it2.next().toString());
		}
	}	

	
	
	
}
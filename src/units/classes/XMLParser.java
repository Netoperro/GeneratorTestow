package units.classes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import units.classes.Question;
import units.classes.Unit;
import units.enums.Type;

public class XMLParser
{
	public XMLParser()
	{
	}
	
	public static List<Unit> ParseXMLFile(String FilePath)
	{
		List<Unit> Units = null;
		Unit NewUnit = null;
		Question NewQuestion = null;
		String TagContent = null;
		String QuestionType = null;
		Boolean CorrectAnswer = false;
		try
		{
			XMLStreamReader XMLFile = XMLInputFactory.newInstance().createXMLStreamReader(new FileReader(FilePath));
			while (XMLFile.hasNext())
			{
				int event = XMLFile.next();
				
				switch (event)
				{
					case XMLStreamConstants.START_ELEMENT:	
						if ("questions".equals(XMLFile.getLocalName()))
							Units = new ArrayList<Unit>(); 
						
						if ("unit".equals(XMLFile.getLocalName()))
							NewUnit = new Unit(XMLFile.getAttributeValue(0));

						if ("question".equals(XMLFile.getLocalName()))
							QuestionType = XMLFile.getAttributeValue(0);
						
						if ("answer".equals(XMLFile.getLocalName()))
							CorrectAnswer = Boolean.valueOf(XMLFile.getAttributeValue(0));
					break;
					case XMLStreamConstants.CHARACTERS:
						TagContent = XMLFile.getText().trim();
					break;					
					case XMLStreamConstants.END_ELEMENT:
						switch (XMLFile.getLocalName())
						{
							case "text":
								if ("close".equals(QuestionType))
									NewQuestion = new Question(Type.CLOSE, TagContent);
								else
									NewQuestion = new Question(Type.OPEN, TagContent);
							break;
							case "answer":
								NewQuestion.AddAnswer(TagContent, CorrectAnswer);
							break;
							case "answers":
								NewUnit.AddQuestion(NewQuestion);
							break;
							case "unit":
								Units.add(NewUnit);								
							break;
						}
					break;
					case XMLStreamConstants.START_DOCUMENT:
						Units = new ArrayList<Unit>();
					break;
				}		
			}
		}
		catch (XMLStreamException Ex)
		{
			Ex.printStackTrace();
		}
		catch (FileNotFoundException Ex)
		{
			Ex.printStackTrace();
		}	
		
		return Units;
	}
}

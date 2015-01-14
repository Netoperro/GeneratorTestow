package units.tests;


import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;


import org.junit.*;

import units.classes.Unit;
import units.classes.XMLParser;

public class XMLParserTest {
	
	@SuppressWarnings("static-access")
	@org.junit.Test
	public void ParseXMLFileTest() throws FileNotFoundException, XMLStreamException 
	{
		Unit testObject =  new Unit("Informatyka");
		XMLParser parser = new XMLParser();
		parser.ParseXMLFile("test.xml");
		Assert.assertEquals(testObject,testObject);
		
	}

}

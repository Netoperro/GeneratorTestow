package units.classes;

import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import units.classes.RTFExport;
import units.classes.Unit;
import units.classes.XMLParser;

public class Test
{
	//property
	private List<Unit> fListOfUnits;
	private int fQuantityVersions;
	private int fQuantityQuestions;
	
	//function
	public Test()
	{
		fListOfUnits = null;
		fQuantityVersions = 0;
		fQuantityQuestions = 0;
	}
	
	public boolean ImportXMLFile(String FilePath) throws XMLStreamException, FileNotFoundException
	{
		if (FilePath == null)
			return false;
		
		try
		{
			fListOfUnits = XMLParser.ParseXMLFile(FilePath);
		}
		catch (XMLStreamException Ex)
		{
			throw Ex;
		}
		catch (FileNotFoundException Ex)
		{
			throw Ex;
		}
		
		if (fListOfUnits != null)
			return true;
		
		return false;		
	}
	
	public boolean ExportToRTF(String FilePath) throws Exception
	{
		if (FilePath == null || fListOfUnits == null || fListOfUnits.isEmpty())
			return false;
		
		try
		{
			if (fQuantityVersions < 2)
				RTFExport.ExportToRTF(fListOfUnits, FilePath, fQuantityQuestions, 0);
			else
				for (int i=0; i<fQuantityVersions; i++)
					RTFExport.ExportToRTF(fListOfUnits, FilePath + "_ver." + (i+1), fQuantityQuestions, i+1);
		}
		catch (Exception Ex)
		{
			throw Ex;
		}
		
		return true;
	}	
	
	//setter
	public void SetQuantityVersions(int QuantityVersions)
	{
		fQuantityVersions = QuantityVersions;
	}

	public void SetQuantityQuestions(int QuantityQuestions)
	{
		fQuantityQuestions = QuantityQuestions;
	}
	
	//getter
	public int GetQuantityVersions()
	{
		return fQuantityVersions;
	}
	
	public int GetQuantityQuestions()
	{
		return fQuantityQuestions;
	}
	
	public int GetCount()
	{
		if (fListOfUnits == null)
			return 0;
		
		return fListOfUnits.size();
	}
	
	public List<Unit> GetUnits()
	{
		return fListOfUnits;
	}
}

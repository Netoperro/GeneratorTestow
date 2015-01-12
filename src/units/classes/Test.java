package units.classes;

import java.util.List;

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
	
	public void ImportXMLFile(String FilePath)
	{
		fListOfUnits = XMLParser.ParseXMLFile(FilePath);
	}
	
	public void ExportToRTF(String FilePath)
	{
		if (fListOfUnits == null)
			return;
		
		RTFExport.ExportToRTF(fListOfUnits, FilePath, fQuantityQuestions);
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

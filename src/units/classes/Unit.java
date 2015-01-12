package units.classes;

import java.util.ArrayList;
import java.util.List;

import units.classes.Question;

public class Unit
{
	//property
	private String fUnitName;
	private List<Question> fListOfQuestions;
	
	//function
	public Unit(String UnitName)
	{
		fUnitName = UnitName;
		fListOfQuestions = new ArrayList<Question>();
	}

	public void AddQuestion(Question MyQuestion)
	{
		fListOfQuestions.add(MyQuestion);
	}
	
	//setter
	public void SetUnitName(String UnitName)
	{
		fUnitName = UnitName;
	}
	
	//getter
	public String GetUnitName()
	{
		return fUnitName;
	}
	
	public int GetCount()
	{
		return fListOfQuestions.size();
	}	

	public List<Question> GetQuestions()
	{
		return fListOfQuestions;
	}
}

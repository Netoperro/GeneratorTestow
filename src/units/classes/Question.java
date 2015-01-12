package units.classes;

import java.util.ArrayList;
import java.util.List;

import units.enums.Type;

public class Question
{
	//property
	private Type fType;
	private String fText;
	private char fCorrectAnswer;
	private List<String> fListOfAnswers;

	//function
	public Question(Type Type, String TextQuestion)
	{
		fType = Type;
		fText = TextQuestion;
		fCorrectAnswer = 'x';
		fListOfAnswers = new ArrayList<String>();
	}
	
	public void AddAnswer(String Answer, Boolean Correct)
	{
		fListOfAnswers.add(Answer);
		if (Correct == true)
			switch (fListOfAnswers.size())
			{
				case 1: 
					fCorrectAnswer = 'a';
				break;
				case 2:
					fCorrectAnswer = 'b';
				break;
				case 3:
					fCorrectAnswer = 'c';
				break;
				case 4:
					fCorrectAnswer = 'd';
			}	
	}
		
	//setter
	public void SetType(Type Type)
	{
		fType = Type;
	}
	
	public void SetText(String Text)
	{
		fText = Text;
	}	

	public void SetCorrectAnswer(char CorrectAnswer)
	{
		fCorrectAnswer = CorrectAnswer;
	}
	
	//getter
	public Type GetType()
	{
		return fType;
	}
	
	public String GetText()
	{
		return fText;
	}	

	public char GetCorrectAnswer()
	{
		return fCorrectAnswer;
	}
	
	public List<String> GetAnswers()
	{
		return fListOfAnswers;
	}
	
}

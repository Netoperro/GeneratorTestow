package units.classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import units.enums.Type;
import units.classes.Unit;
import units.classes.Question;

public class RTFExport
{
	public RTFExport()
	{
	}
	
	public static void ExportToRTF(List<Unit> Units, String FilePath, int QuantityQuestions)
	{
		StringBuilder TestToExport = new StringBuilder("{\\rtf1\\ansi\\deff0\\nouicompat{\\fonttbl{\\f0\\fnil Consolas;}{\\f1\\fnil Calibri;}\n}");
		TestToExport.append("{\\colortbl ;\\red0\\green0\\blue0;\n}");
		TestToExport.append("{\\*\\generator Riched20 6.3.9600}\\viewkind4\\uc1\n\n"); 
		
		StringBuilder AnswersToExport = new StringBuilder(TestToExport.toString());
		
		for (int i=0; i<Units.size(); i++)
		{		
			if (QuantityQuestions != 0 && Units.get(i).GetCount() < QuantityQuestions)
				return;//Throw Exception
			
			TestToExport.append("\\pard\\sl240\\slmult1\\qc\\cf1\\b\\f0\\fs20\\lang21 " + Units.get(i).GetUnitName() + "\\cf0\\b0\\par\n");
			AnswersToExport.append("\\pard\\sl240\\slmult1\\qc\\cf1\\b\\f0\\fs20\\lang21 " + Units.get(i).GetUnitName() + "\\cf0\\b0\\par\n");
						
			for (int j=0; j<Units.get(i).GetCount(); j++)
		    {
				List<Question> ListOfQuestions = Units.get(i).GetQuestions();
				
				TestToExport.append("\\pard\\sl240\\slmult1\\cf1 " + (i+1) + ". " + ListOfQuestions.get(j).GetText() + "\\cf0\\par\n");
				AnswersToExport.append("\\pard\\sl240\\slmult1\\cf1 " + (i+1) + ". " + ListOfQuestions.get(j).GetText() + "\\cf0\\par\n");				
				
				if (ListOfQuestions.get(j).GetType() == Type.OPEN)
				{
					TestToExport.append("\\par\n");
					if (ListOfQuestions.get(j).GetAnswers().size() == 1)
						AnswersToExport.append("\\cf1\\tab " + ListOfQuestions.get(j).GetAnswers().get(0) + "\\cf0\\par\n");
					else
						AnswersToExport.append("\\par\n");
				}
				else
				{
					AnswersToExport.append("\\cf1\\tab " + ListOfQuestions.get(j).GetCorrectAnswer() + ").\\cf0\\par\n");
					for (int k=0; k<ListOfQuestions.get(k).GetAnswers().size(); k++)
				    {							
						if (k == 0)
							TestToExport.append("\\cf1\\tab a). ");
						else if (k == 1)
							TestToExport.append("\\cf1\\tab b). ");
						else if (k == 2)
							TestToExport.append("\\cf1\\tab c). ");
						else if (k == 3)
							TestToExport.append("\\cf1\\tab d). ");
						
						TestToExport.append(ListOfQuestions.get(k).GetAnswers().get(k) + "\\cf0\\par\n");
				    }					
				}
				
		    }	
			
			TestToExport.append("\\par\n");
			AnswersToExport.append("\\par\n");			
		}	
		
		TestToExport.append("}"); 
		AnswersToExport.append("}");
		
		try
		{ 
			File NewFile = new File(FilePath + "_Questions.rtf");
			
			if (!NewFile.exists())
				NewFile.createNewFile();
 
			BufferedWriter Buffer = new BufferedWriter(new FileWriter(NewFile.getAbsoluteFile()));
			Buffer.write(TestToExport.toString());
			Buffer.close();
				
			NewFile = new File(FilePath + "_Answers.rtf");
			 
			if (!NewFile.exists())
				NewFile.createNewFile();
 
			Buffer = new BufferedWriter(new FileWriter(NewFile.getAbsoluteFile()));
			Buffer.write(AnswersToExport.toString());
			Buffer.close();			
		}
		catch (IOException Ex)
		{
			Ex.printStackTrace();
		}
	}
}

package units.classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import units.enums.Type;
import units.classes.Unit;
import units.classes.Question;

public class RTFExport
{
	public RTFExport()
	{
	}
	
	
	public static List<Question> ExportToRTF(List<Unit> Units, String FilePath, int QuantityQuestions, int NumberOfVersion, List<Question> ExportedQuestName, int AllQuestionsCount) throws Exception
	{
		StringBuilder TestToExport = new StringBuilder("{\\rtf1\\ansi\\ansicpg1252\\deff0\\nouicompat{\\fonttbl{\\f0\\fnil\\fcharset0 Calibri;}}\n");
		TestToExport.append("{\\colortbl ;\\red0\\green0\\blue0;}\n");
		TestToExport.append("{\\*\\generator Riched20 6.3.9600}\\viewkind4\\uc1\n");
		SimpleDateFormat FormatDate = new SimpleDateFormat("dd-MM-yyyy");
		TestToExport.append("\\pard\\sl240\\slmult1\\qr\\cf1\\f0\\fs18\\lang1045 Generate date: " + FormatDate.format(new Date()) + "r.");
		
		if (NumberOfVersion != 0)
			TestToExport.append(", ver. " + NumberOfVersion + ".");
		
		TestToExport.append("\\f1\\lang21\\par\n\n");
		
		StringBuilder AnswersToExport = new StringBuilder(TestToExport.toString());
		
		for (int i=0; i<Units.size(); i++)
		{		
			if (QuantityQuestions != 0 && Units.get(i).GetCount() < QuantityQuestions)
				throw new Exception("Not enough questions in unit: " + Units.get(i).GetUnitName());
			
			if (QuantityQuestions == 0)
				QuantityQuestions = Units.get(i).GetCount();
			
			TestToExport.append("\\pard\\sl240\\slmult1\\qc\\cf1\\b\\f0\\fs22\\lang21 " + Units.get(i).GetUnitName() + "\\cf0\\b0\\par\n\n");
			AnswersToExport.append("\\pard\\sl240\\slmult1\\qc\\cf1\\b\\f0\\fs22\\lang21 " + Units.get(i).GetUnitName() + "\\cf0\\b0\\par\n\n");
				
			List<Integer> ExcpectedQuestions = new ArrayList<Integer>();
			List<Question> ListOfQuestions = Units.get(i).GetQuestions();
			Random RandomQuestion = new Random(System.nanoTime());

			for (int j=0; j<QuantityQuestions; j++)
		    {
				if(AllQuestionsCount<=ExportedQuestName.size())
					throw new Exception("Pool of questions end at "+NumberOfVersion+". Can't generate more versions of test");
				
				int Index = -1;
				do
				{
					Index = RandomQuestion.nextInt(Units.get(i).GetCount());
					for (int Find=0; Find<ExportedQuestName.size(); Find++)
						if (ExportedQuestName.get(Find) == Units.get(i).GetQuestions().get(Index))
						{
							Index = -1;
							break;
						}
					if (Index != -1)
					{
						ExportedQuestName.add(Units.get(i).GetQuestions().get(Index));
						ExcpectedQuestions.add(Index);
					}
				}
				while (Index == -1);
				
								
				TestToExport.append("\\pard\n{\\pntext\\f0 " + (j+1) + ".\\tab}{\\*\\pn\\pnlvlbody\\pnf0\\pnindent0\\pnstart1\\pndec{\\pntxta.}}\n");
				TestToExport.append("\\fi-360\\li360\\sl240\\slmult1\\cf1 " + ListOfQuestions.get(Index).GetText() + "\\cf0\\par\n\n");
				AnswersToExport.append("\\pard\n{\\pntext\\f0 " + (j+1) + ".\\tab}{\\*\\pn\\pnlvlbody\\pnf0\\pnindent0\\pnstart1\\pndec{\\pntxta.}}\n");
						
				
				if (ListOfQuestions.get(Index).GetType() == Type.OPEN)
				{
					TestToExport.append("\\pard\\sl240\\slmult1\\par\n\\pard\\sl240\\slmult1\\par\n\n");
					if (ListOfQuestions.get(Index).GetAnswers().size() == 1)
						AnswersToExport.append("\\fi-360\\li360\\sl240\\slmult1\\cf1 " + ListOfQuestions.get(Index).GetAnswers().get(0) + "\\cf0\\par\n\n");
					else
						AnswersToExport.append("\\fi-360\\li360\\sl240\\slmult1\\cf1 " + ListOfQuestions.get(Index).GetCorrectAnswer() + ".\\cf0\\par\n\n");
				}
				else
				{
					TestToExport.append("\\pard\n");
					AnswersToExport.append("\\fi-360\\li360\\sl240\\slmult1\\cf1 " + ListOfQuestions.get(Index).GetCorrectAnswer() + ".\\cf0\\par\n\n");
					for (int k=0; k<ListOfQuestions.get(Index).GetAnswers().size(); k++)
				    {							
						TestToExport.append("{\\pntext\\f0 ");
						if (k == 0)
						{
							TestToExport.append("a.\\tab}{\\*\\pn\\pnlvlbody\\pnf0\\pnindent0\\pnstart1\\pnlcltr{\\pntxta.}}\n");
							TestToExport.append("\\fi-360\\li786\\sl240\\slmult1\\cf1 ");
						}
						else if (k == 1)
							TestToExport.append("b.\\tab}\\cf1 ");
						else if (k == 2)
							TestToExport.append("c.\\tab}\\cf1 ");
						else if (k == 3)
							TestToExport.append("d.\\tab}\\cf1 ");
											
						TestToExport.append(ListOfQuestions.get(Index).GetAnswers().get(k) + "\\cf0\\par\n");
				    }
					TestToExport.append("\n\\pard\\sl240\\slmult1\\par\n\n");
				}
				
		    }	
			
			TestToExport.append("\\pard\\sl240\\slmult1\\par\n\n");
			AnswersToExport.append("\\pard\\sl240\\slmult1\\par\n\n");			
		}	
		
		TestToExport.append("}"); 
		AnswersToExport.append("}");
		
		try
		{ 
			String NewFilePath = (FilePath.toLowerCase().endsWith(".rtf")) ? FilePath.substring(0, FilePath.length() - 4) : FilePath;			
			File NewFile = new File(NewFilePath + "_Questions.rtf");
			
			if (!NewFile.exists())
				NewFile.createNewFile();
 
			BufferedWriter Buffer = new BufferedWriter(new FileWriter(NewFile.getAbsoluteFile()));
			Buffer.write(TestToExport.toString());
			Buffer.close();
					
			NewFile = new File("Answers.txt");
			 
			if (!NewFile.exists())
				NewFile.createNewFile();
 
			Buffer = new BufferedWriter(new FileWriter(NewFile.getAbsoluteFile(),true));
			Buffer.newLine();
			Buffer.write(AnswersToExport.toString());
			
			Buffer.close();
		}
		catch (IOException Ex)
		{
			throw Ex;
		}
		return ExportedQuestName;
	}
}

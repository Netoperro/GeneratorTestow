package units;

import units.classes.Test;

public class TestGenerator
{

	public static void main(String[] args)
	{
		System.out.println("test");
		Test test = new Test();
		test.ImportXMLFile("test.xml");
		int proba = test.GetCount();
		int proba2 = test.GetCount();
	}

}

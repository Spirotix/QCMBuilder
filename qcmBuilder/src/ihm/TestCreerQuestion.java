package src.ihm;

import java.util.*;

public class TestCreerQuestion 
{
	public TestCreerQuestion ()
	{
		new FrameCreerQuestion(this);
	}

	public static void main (String[] a)
	{
		new TestCreerQuestion();
	}

	public ArrayList<String> getChoixNotion(String s)
	{
		ArrayList<String> str = new ArrayList<String>();
		str.add("Test1 : "+s);
		str.add("Test2 : "+s);
		str.add("Test3 : "+s);
		str.add("Test4 : "+s);

		return str;
	}

	public ArrayList<String> getChoixRessources()
	{
		ArrayList<String> str = new ArrayList<String>();
		
		str.add("Test1");
		str.add("Test2");
		str.add("Test3");
		str.add("Test4");

		return str;
	}
}
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

	public ArrayList<String> getChoixNotion()
	{
		ArrayList<String> str = new ArrayList<String>();

		str.add("Test1");
		str.add("Test2");
		str.add("Test3");
		str.add("Test4");

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
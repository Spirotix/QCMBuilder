package src.metier;

public class Couple
{
	private Reponse premier;
	private Reponse second;

	public Couple(Reponse premier, Reponse second)
	{
		this.premier = premier;
		this.second  = second;
	}

	public Reponse getpremier()
	{
		return premier;
	}

	public Reponse getsecond()
	{
		return second;
	}
}

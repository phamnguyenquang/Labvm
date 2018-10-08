package BackEnd_Misc;

public class simpleIO {
	private String parameters;
	private boolean occupied = false;

	public simpleIO() {
		parameters = "";
	}
	
	public void setParameters(String sParameter)
	{
		parameters=sParameter;
	}
	public String returnParameter()
	{
		return parameters;
	}
	public void setOccupied(boolean b)
	{
		occupied = b;
	}
	public boolean getOccupiedState()
	{
		return occupied;
	}

}

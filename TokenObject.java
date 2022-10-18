package programming_languages_project_1;

public class TokenObject
{
	int row;
	int column;
	String kind;
	String stringValue;
	int intValue;
	boolean isNum = false;
	
	TokenObject(int row, int column, String kind, String stringValue)
	{
		this.row = row;
		this.column = column;
		this.kind = kind;
		this.stringValue = stringValue;
	}
	
	TokenObject(int row, int column, String kind, int intValue)
	{
		this.row = row;
		this.column = column;
		this.kind = kind;
		this.intValue = intValue;
		this.isNum = true;
	}
}

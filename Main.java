package programming_languages_project_1;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.*;




public class Main
{
	static int currentChar;
	static boolean isWord = false;
	static boolean isNumber = false;
	static boolean isSymbol = false;
	static boolean isComment = false;
	static boolean isWhite = false;
	static String keywordTable[] = {"program", "end", "bool", "int", "if", "then", "else", "fi",
			"while", "do", "od", "print", "or", "and", "not", "false", "true"};
	static int row = 1;
	static int column = 1;
	static String currentToken;
	static FileReader file = null;
	static TokenObject recentToken;
	
	
	public static void main(String[] args)
	{	
		try
		{
			file = new FileReader("C:\\Users\\djdel\\OneDrive - Towson University\\Desktop\\School\\COSC455_programming_languages\\assignments\\Projects\\Examples\\ab.txt");
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		
		try
		{
			currentChar = file.read();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		
		next();
		System.out.println(position() + ", " + kind() + ", " + value());
		System.out.println("first token: " + currentToken);
		while(!kind().substring(6).equals("end-of-text"))
		{
			next();
			System.out.println(position() + ", " + kind() + ", " + value());
			System.out.println("current token: " + currentToken);
		}
	}
	


	
	static void next()
	{
		
		TokenObject token = new TokenObject(0, 0, null, null);
		String stringTemp = null;
		
		readToken();

		if(isWord)
		{
			if(isKeyWord())
				token = new TokenObject(row, column - currentToken.length(), currentToken, stringTemp);
			else
				token = new TokenObject(row, column - currentToken.length(), "ID", currentToken);
		}
		else if(isNumber)
			token = new TokenObject(row, column - currentToken.length(), "NUM", Integer.parseInt(currentToken));
		else if((isSymbol && !isComment) || currentToken == "end-of-text")
		{
			if(currentToken == "end-of-text")
				token = new TokenObject(row, column, "end-of-text", null);
			else
				token = new TokenObject(row, column - currentToken.length(), currentToken, null);
		}
		else if(isComment)
			comment();
		else if(isWhite)
			token = new TokenObject(0, 0, "WHITE", null);
		else
			token = new TokenObject(row, column - currentToken.length(), "UNEXPECTED", currentToken);
		
		recentToken = token;
		
		isWhite = false;
		isComment = false;
		isWord = false;
		isNumber = false;
		isSymbol = false;
	}
	
	
	
	static void readToken()
	{
		currentToken = "";
		
		if(charIsLetter())
		{
			while(charIsLetter() || charIsNum() || currentChar == 95) //95 = '_'
			{
				currentToken += (char)currentChar;
				readChar();
			}
			isWord = true;
		}
		else if(charIsNum())
		{
			while(charIsNum())
			{
				currentToken += (char)currentChar;
				readChar();
			}
			isNumber = true;
		}
		else if(charIsSymbol())
		{
			if(currentChar == 58) //58 = ':'
			{
				currentToken += (char)currentChar;
				readChar();
				if(currentChar == 61) //61 = '='
				{
					currentToken += (char)currentChar;
					readChar();
				}
			}
			else if(currentChar == 61) //61 = '='
			{
				currentToken += (char)currentChar;
				readChar();
				if(currentChar == 60) //60 = '<'
				{
					currentToken += (char)currentChar;
					readChar();
				}
			}
			else if(currentChar == 33) //33 = '!'
			{
				currentToken += (char)currentChar;
				readChar();
				if(currentChar == 61) //61 = '='
				{
					currentToken += (char)currentChar;
					readChar();
				}
			}
			else if(currentChar == 62) //62 = '>'
			{
				currentToken += (char)currentChar;
				readChar();
				if(currentChar == 61) //61 = '='
				{
					currentToken += (char)currentChar;
					readChar();
				}
			}
			else if(currentChar == 47) //47 = '/'
			{
				currentToken += (char)currentChar;
				readChar();
				if(currentChar == 47)
				{
					currentToken += (char)currentChar;
					isComment = true;
					//comment();
					//readChar();
				}
			}
			else
			{
				currentToken += (char)currentChar;
				readChar();
			}
			
			isSymbol = true;
		}
		else if(currentChar == 10 || currentChar ==32) //checking for whitespace
		{
			if(currentChar == 10) //newline
			{
				row++;
				column = 1;
				readChar();
			}
			else //space
				readChar();
			
			isWhite = true;
		}
		else if(currentChar == -1)
			currentToken = "end-of-text";
		else //unexpected token
		{
			System.out.println((char)currentChar + " is unexpected");
			currentToken += (char)currentChar;
			readChar();
		}
	}
	
	static void readChar()
	{
		/*if(currentChar != -1)
			System.out.println(Character.toChars(currentChar));*/
		
		if(currentChar > -1)
		{
			try
			{
				currentChar = file.read();
				column++;
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
		}
		
		/*if(!charIsNotWhite())
		{
			readChar();
		}*/
	}
	
	static boolean isKeyWord()
	{
		for(String key : keywordTable)
		{
			if(currentToken.equals(key))
				return true;
		}
		
		return false;
	}
	
	static boolean charIsLetter()
	{
		if((currentChar > 64 && currentChar < 91) || (currentChar > 96 && currentChar < 123))
			return true;
		
		return false;
	}
	
	static boolean charIsNum()
	{
		if(currentChar > 47 && currentChar < 58)
			return true;
		
		return false;
	}
	
	static boolean charIsSymbol()
	{
		if(currentChar == 58 || currentChar == 59 || currentChar == 61 || currentChar == 60 ||
			currentChar == 33 || currentChar == 62 || currentChar == 43 || currentChar == 45 ||
			currentChar == 42 || currentChar == 47 || currentChar == 40 || currentChar == 41 ||
			currentChar == 95)
			return true;
		
		return false;
	}
	
	/*static boolean charIsNotWhite()
	{
		if(currentChar == 10)
		{
			row++;
			column = 1;
			return false;
		}
		else if(currentChar == 32)
		{
			//column++;
			return false;
		}
		
		return true;
	}*/
	
	static void comment()
	{
		while(currentChar != 10 && currentChar != -1)
		{
			readChar();
		}
	}
	
	static String position()
	{
		String output = "line: " + recentToken.row + ", index: " + recentToken.column;
		return output;
	}
	
	static String kind()
	{
		String output = "kind: " + recentToken.kind;
		return output;
	}
	
	static String value()
	{
		String output;
		if(recentToken.isNum)
			output = "value: " + recentToken.intValue;
		else
			output = "value: " + recentToken.stringValue;
		return output;
	}

}
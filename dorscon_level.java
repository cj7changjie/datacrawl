package webcrawler;

import java.io.*;

public class dorscon_level 
{
	public String import_File() throws IOException 
	{
		String fileName = "cna - Copy.csv";
		File file_coro = new File(fileName);
		String str_sentence = "";
		String str_dorscon = "";
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file_coro));
		String line = "";

		while ((line = br.readLine()) != null) {
			// split csv by comma without breaking sentence with comma java
			String[] count = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			
			str_sentence = count[1];
			
			if (str_sentence.contains("DORSCON"))
			{
				
				if (str_sentence.contains("red"))
				{
					return str_dorscon = "RED";
				}
				else if (str_sentence.contains("orange"))
				{
					return str_dorscon = "ORANGE";
				}
				else if (str_sentence.contains("yellow"))
				{
					return str_dorscon = "YELLOW";
				}
				else if (str_sentence.contains("green"))
				{
					return str_dorscon = "GREEN";
				}
				else
				{
					return str_dorscon = "NIL";
				}
			}
			
			//str_sentence = str_sentence + count[1] + "." + "\n";
		}
		return str_dorscon;

	}
	
	public String[] split(String file)
	{
		return file.split("\\.");
	}

	public static void main(String[] args)
	{
		dorscon_level new_file = new dorscon_level();
		try 
		{
			System.out.println("DORSCON LEVEL: " + new_file.import_File());
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
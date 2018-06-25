import java.io.*;

public class DiagnosisSystem
{
	public static void main(String[] args)
	{
		boolean stop = false;
		BufferedReader br = null;
		DiagnosisMap map = new DiagnosisMap();
		boolean[] choices = new boolean[DiagnosisMap.SYMPTOM_COUNT];
		
		try
		{
			br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Please chose from the below symptoms or 0 when you are done:"); // prompt user input
			map.printChoices();
			
			while (!stop)
			{
				String input = br.readLine();
				int choice = Integer.parseInt(input);
				
				if (choice == 0)
				{
                    stop = true;
                }
				else if (choice < 0 || choice > DiagnosisMap.SYMPTOM_COUNT) // choice out of scope
				{
					System.out.println("This is an invalid choice.");
				}
				else // remember choice
				{
					choices[choice-1] = true; // offset by one as 0 is the exit code
					System.out.println("You have selected: " + map.getSymptom(choice-1));
				}
			}
			
			map.diagnoseChoices(choices);
		}
		catch (Exception ex)
		{
			System.out.println("Program failed:");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (br != null)
				{
					br.close();
				}
			}
			catch (Exception ex)
			{
				System.out.println("Program failed:");
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
}

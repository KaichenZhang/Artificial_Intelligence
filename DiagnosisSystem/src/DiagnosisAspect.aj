import java.io.*;

public aspect DiagnosisAspect
{
	private static String FILENAME = "diagnostics.log";

	pointcut diagnostics(DiagnosisMap context, boolean[] arguments) :
		execution (* DiagnosisMap.diagnoseChoices(boolean[])) &&
		this (context) &&
		args (arguments);

	void around(DiagnosisMap context, boolean[] arguments): diagnostics(context, arguments)
	{
		String FILENAME = ".\\diagnostics.log";
		BufferedWriter bw = null;
		FileWriter fw = null;

		try
		{
			File file = new File(FILENAME);
			if (!file.exists())
			{
				file.createNewFile();
			}

			fw = new FileWriter(file.getAbsoluteFile(), true); // true = append file
			bw = new BufferedWriter(fw);

			bw.write("Diagnosis logging starting...");
			bw.newLine();
			bw.newLine();
			
			bw.write("Patient has given the following symptoms:\n");
			bw.newLine();

			for (int i = 0; i < DiagnosisMap.SYMPTOM_COUNT; i++)
			{
				if (arguments[i])
				{
					bw.write(context.getSymptom(i));
					bw.newLine();
				}
			}
			
			bw.newLine();

			proceed(context, arguments);

			for (int i = 0; i < DiagnosisMap.DISEASE_COUNT; i++)
			{
				bw.write(String.format("Logging statistics for \"%s\":", context.getDisease(i)));
				bw.newLine();
				bw.newLine();

				int totalWeight = 0;
				int totalCount = 0;
				int totalMatchWeight = 0;
				int totalMatchCount = 0;
				int totalMisfitCount = 0;
				int totalMissed = 0;
				double matchPercent = 0.0;
				double outlierPercent = 0.0;


				bw.write("This disease/condition is associated with the following symptoms:");
				bw.newLine();

				for (int j = 0; j < DiagnosisMap.SYMPTOM_COUNT; j++)
				{
					if (context.getWeight(i, j) > 0)
					{
						totalCount++;
						totalWeight += context.getWeight(i, j);
						bw.write(String.format("%s (weight = %d)", context.getSymptom(j), context.getWeight(i, j)));
						bw.newLine();
					}
				}

				bw.write(String.format("There is a total of %d symptoms worth %d points.", totalCount, totalWeight));
				bw.newLine();
				bw.newLine();

				bw.write("The following chosen symptoms match this disease:");
				bw.newLine();

				for (int j = 0; j < DiagnosisMap.SYMPTOM_COUNT; j++)
				{
					if (context.getWeight(i, j) > 0 && arguments[j])
					{
						totalMatchCount++;
						totalMatchWeight += context.getWeight(i, j);
						bw.write(String.format("%s (weight = %d)", context.getSymptom(j), context.getWeight(i, j)));
						bw.newLine();
					}
				}

				bw.newLine();
				matchPercent = totalMatchWeight * 100.0 / totalWeight;

				bw.write(String.format("There are %d matched symptoms worth %d points.", totalMatchCount, totalMatchWeight));
				bw.newLine();

				bw.write(String.format("There are %.2f%% matched symptoms (%d total matched weight / %d total disease weight).",
					matchPercent, totalMatchCount, totalWeight));
				bw.newLine();
				bw.newLine();

				bw.write("The following chosen symptoms do not match this disease:");
				bw.newLine();

				for (int j = 0; j < DiagnosisMap.SYMPTOM_COUNT; j++)
				{
					if (context.getWeight(i, j) <= 0 && arguments[j])
					{
						totalMisfitCount++;
						bw.write(context.getSymptom(j));
						bw.newLine();
					}
				}

				bw.newLine();
				outlierPercent = totalMisfitCount * 100.0 / totalCount;

				bw.write(String.format("There are %d non-matched symptoms.", totalMisfitCount));
				bw.newLine();

				bw.write(String.format("There are %.2f%% non-matched symptoms (%d total missed count / %d total disease count).",
					outlierPercent, totalMisfitCount, totalCount));
				bw.newLine();
				bw.newLine();

				if (outlierPercent <= DiagnosisMap.ALLOWED_OUTLIER_PERCENT && matchPercent > 0)
				{
					bw.write(String.format("Correlation exists when the match percentage %.2f is larger than 0 and the missed percentage %.2f is smaller or equal to the allowed threshold %d.",
						matchPercent, outlierPercent, DiagnosisMap.ALLOWED_OUTLIER_PERCENT));
					bw.newLine();

					if (matchPercent >= outlierPercent)
					{
						bw.write(String.format("Prediction is given when the match percentage %.2f is larger or equal to the missed percentage %.2f.",
							matchPercent, outlierPercent));
						bw.newLine();
					}
					else
					{
						bw.write(String.format("Uncertain association exists when the missed percentage %.2f is larger or equal to the match percentage %.2f.",
							outlierPercent, matchPercent));
						bw.newLine();
					}
				}
				else
				{
					bw.write(String.format("Correlation does not exist when the match percentage %.2f is 0 or the missed percentage %.2f is larger than the allowed threshold %d.",
						matchPercent, outlierPercent, DiagnosisMap.ALLOWED_OUTLIER_PERCENT));
					bw.newLine();
				}

				bw.newLine();
			}

			bw.write("Diagnosis logging stopped...");
			bw.newLine();
			bw.newLine();
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
				if (bw != null)
				{
					bw.close();
				}
				if (fw != null)
				{
					fw.close();
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
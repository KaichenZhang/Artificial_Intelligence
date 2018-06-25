public class DiagnosisMap
{
	private String[] _diseases;
	private String[] _symptoms;
	private int[][] _weightedMap;
	private int[] _diseaseTotal;
	private int[] _diseaseWeightedTotal;
	
	public static final int DISEASE_COUNT = 4;
	public static final int SYMPTOM_COUNT = 45;
	public static final int ALLOWED_OUTLIER_PERCENT = 50;
	
	public DiagnosisMap()
	{
		createDiseases();
		createSymptoms();
		mapDiseaseSymptom();
		calculateStatistics();
	}
	
	public String getDisease(int index)
	{
		return _diseases[index];
	}
	
	public String getSymptom(int index)
	{
		return _symptoms[index];
	}
	
	public int getWeight(int indexDisease, int indexSymptom)
	{
		return _weightedMap[indexDisease][indexSymptom];
	}
	
	public void printChoices()
	{
		int i;
		for (i = 0; i < SYMPTOM_COUNT; i++)
		{
			System.out.println(String.format("(%d) %s", i + 1, _symptoms[i])); // offset by one as 0 is the exit code
		}
	}
	
	public void diagnoseChoices(boolean[] choices)
	{
		int match = 0;
		int misfit = 0;
		int nofit = 0;

		for (int i = 0; i < DISEASE_COUNT; i++)
		{
			match = 0;
			misfit = 0;

			for (int j = 0; j < SYMPTOM_COUNT; j++)
			{
				if (_weightedMap[i][j] > 0 && choices[j]) // disease has symptoms and patient shows symptom
				{
					match += _weightedMap[i][j];
				}
				else if (_weightedMap[i][j] <= 0 && choices[j]) // disease does not have symptoms but patient shows symptom
				{
					misfit++;
				}
			}

			double matchPercent = match * 100.0 / _diseaseWeightedTotal[i];
			double outlierPercent = misfit * 100.0 / _diseaseTotal[i];
			if (outlierPercent <= ALLOWED_OUTLIER_PERCENT && matchPercent > 0)
			{
				if (matchPercent >= outlierPercent)
				{
					System.out.println(String.format("You may have disease or condition \"%s\" (%.2f%% symptoms match and %.2f%% misfit).",
							_diseases[i], matchPercent, outlierPercent));
				}
				else
				{
					System.out.println(String.format("Your symptoms correlate to disease or condition \"%s\" but not only to this (%.2f%% symptoms match and %.2f%% misfit).",
							_diseases[i], matchPercent, outlierPercent));
				}
			}
			else
			{
				System.out.println(String.format("Your symptoms do not correlate to disease or condition \"%s\" (%.2f%% symptoms match and %.2f%% misfit).",
						_diseases[i], matchPercent, outlierPercent));
				nofit++;
			}
		}

		if (nofit == DISEASE_COUNT)
		{
			System.out.println("Sorry, we could not match your symptoms with any disease or condition.");
		}
	}
	
	private void createDiseases()
	{
		int i = 0;
		_diseases = new String[DISEASE_COUNT];
		_diseases[i++] = "AIDS"; // 0
		_diseases[i++] = "Hepatitis C"; // 1
		_diseases[i++] = "Influenza"; // 2
		_diseases[i++] = "Pregnancy"; // 3
	}
	
	private void createSymptoms()
	{
		int i = 0;
		_symptoms = new String[SYMPTOM_COUNT];
		_symptoms[i++] = "Acne"; // 0
		_symptoms[i++] = "Body Pain"; // 1
		_symptoms[i++] = "Bloating"; // 2
		_symptoms[i++] = "Chills"; // 3
		_symptoms[i++] = "Constipation"; // 4
		_symptoms[i++] = "Cough"; // 5
		_symptoms[i++] = "Cramping And Spotting"; // 6
		_symptoms[i++] = "Depression"; // 7
		_symptoms[i++] = "Diarrhea"; // 8
		_symptoms[i++] = "Diziness"; // 9
		_symptoms[i++] = "Fatigue"; // 10
		_symptoms[i++] = "Fever"; // 11
		_symptoms[i++] = "Food Aversion"; // 12
		_symptoms[i++] = "Frequent Urination"; // 13
		_symptoms[i++] = "Headaches"; // 14
		_symptoms[i++] = "Heartburn"; // 15
		_symptoms[i++] = "High Blood Pressure"; // 16
		_symptoms[i++] = "Increased Heart Rate"; // 17
		_symptoms[i++] = "Joint Pain"; // 18
		_symptoms[i++] = "Memory Loss"; // 19
		_symptoms[i++] = "Missed Period"; // 20
		_symptoms[i++] = "Mood Swings"; // 21
		_symptoms[i++] = "Morning Sickness"; // 22
		_symptoms[i++] = "Mouth Ulcers"; // 23
		_symptoms[i++] = "Muscle Pain"; // 24
		_symptoms[i++] = "Nausea"; // 25
		_symptoms[i++] = "Neurologic Disorders"; // 26
		_symptoms[i++] = "Night Sweats"; // 27
		_symptoms[i++] = "Pneumonia"; // 28
		_symptoms[i++] = "Poor Appetite"; // 29
		_symptoms[i++] = "Pregnancy Glow"; // 30
		_symptoms[i++] = "Raised Temperature"; // 31
		_symptoms[i++] = "Rapid Weight Loss"; // 32
		_symptoms[i++] = "Rash"; // 33
		_symptoms[i++] = "Skin Blotches"; // 34
		_symptoms[i++] = "Smell Sensitivity"; // 35
		_symptoms[i++] = "Sore Throat"; // 36
		_symptoms[i++] = "Stomach Pain"; // 37
		_symptoms[i++] = "Stuffy Or Runny Nose"; // 38
		_symptoms[i++] = "Swollen Lymph Nodes"; // 39
		_symptoms[i++] = "Singling Or Aching Breasts"; // 40
		_symptoms[i++] = "Urine Abnormalities"; // 41
		_symptoms[i++] = "Vomiting"; // 42
		_symptoms[i++] = "Weight Gain"; // 43
		_symptoms[i++] = "Yellow Eyes And Skin"; // 44
	}
	
	private void mapDiseaseSymptom()
	{
		_weightedMap = new int[DISEASE_COUNT][SYMPTOM_COUNT];
		
		// influenza
		_weightedMap[2][11] = 1; // fever
		_weightedMap[2][3] = 1; // chills
		_weightedMap[2][5] = 4; // cough
		_weightedMap[2][36] = 3; // sore throat
		_weightedMap[2][38] = 4; // stuffyor runny nose
		_weightedMap[2][1] = 2; // body pain
		_weightedMap[2][24] = 2; // muscle pain
		_weightedMap[2][14] = 2; // headaches
		_weightedMap[2][10] = 1; // fatigue
		_weightedMap[2][42] = 1; // vomiting
		_weightedMap[2][8] = 1; // diarrhea

		// aids
		_weightedMap[0][11] = 1; // fever
		_weightedMap[0][3] = 1; // chills
		_weightedMap[0][33] = 2; // rash
		_weightedMap[0][27] = 2; // night sweats
		_weightedMap[0][24] = 2; // muscle pain
		_weightedMap[0][36] = 2; // sore throat
		_weightedMap[0][10] = 1; // fatigue
		_weightedMap[0][39] = 3; // swollen lymph nodes
		_weightedMap[0][23] = 2; // mouth ulcers
		_weightedMap[0][32] = 4; // rapid weight loss
		_weightedMap[0][8] = 1; // diarrhea
		_weightedMap[0][28] = 4; // pneumonia
		_weightedMap[0][34] = 2; // skin blotches
		_weightedMap[0][19] = 4; // memory loss
		_weightedMap[0][7] = 3; // depression
		_weightedMap[0][26] = 4; // neurologic disorders

		// hepatitis c
		_weightedMap[1][11] = 1; // fever
		_weightedMap[1][10] = 1; // fatigue
		_weightedMap[1][29] = 2; // poor appetite
		_weightedMap[1][25] = 1; // nausea
		_weightedMap[1][42] = 1; // vomiting
		_weightedMap[1][37] = 2; // stomach pain
		_weightedMap[1][24] = 2; // muscle pain
		_weightedMap[1][18] = 2; // joint pain
		_weightedMap[1][41] = 3; // urine abnormalities
		_weightedMap[1][8] = 1; // diarrhea
		_weightedMap[1][4] = 2; // constipation
		_weightedMap[1][44] = 4; // yellow eyes and skin

		// pregnancy
		_weightedMap[3][6] = 4; // cramping and spotting
		_weightedMap[3][20] = 4; // missed period
		_weightedMap[3][31] = 2; // raised temperature
		_weightedMap[3][10] = 2; // fatigue
		_weightedMap[3][17] = 3; // increased heart rate
		_weightedMap[3][40] = 4; // tingling or aching breast
		_weightedMap[3][21] = 3; // mood swings
		_weightedMap[3][13] = 3; // frequent urination
		_weightedMap[3][2] = 3; // bloating
		_weightedMap[3][4] = 2; // constipation
		_weightedMap[3][22] = 3; // morning sickness
		_weightedMap[3][25] = 1; // nausea
		_weightedMap[3][42] = 1; // vomiting
		_weightedMap[3][16] = 2; // high blood pressure
		_weightedMap[3][9] = 2; // diziness
		_weightedMap[3][35] = 2; // smell sensitivity
		_weightedMap[3][12] = 2; // food aversion
		_weightedMap[3][43] = 4; // weight gain
		_weightedMap[3][15] = 3; // heartburn
		_weightedMap[3][30] = 4; // pregnancy glow
		_weightedMap[3][0] = 1; // acne
	}
	
	private void calculateStatistics()
	{
		_diseaseTotal = new int[DISEASE_COUNT];
		_diseaseWeightedTotal = new int[DISEASE_COUNT];
		
		for (int i = 0; i < DISEASE_COUNT; i++)
		{
			int count = 0;
			int weightedCount = 0;
			
			for (int j = 0; j < SYMPTOM_COUNT; j++)
			{
				count += _weightedMap[i][j] > 0 ? 1 : 0;
				weightedCount += _weightedMap[i][j];
			}
			
			_diseaseTotal[i] = count;
			_diseaseWeightedTotal[i] = weightedCount;
		}
	}
}

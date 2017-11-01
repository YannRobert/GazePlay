package net.gazeplay.utils.stats;

import utils.games.Utils;

import java.io.PrintWriter;

public class HiddenItemsGamesStats extends Stats {

	public HiddenItemsGamesStats(String gameName) {
		super(gameName);
	}

	@Override
	public void saveStats() {

		super.saveStats();

		PrintWriter out = Utils.getInfoStatsFile(getTodayFolder());

		out.print("Date");
		out.print(',');
		out.print("Time");
		out.print(',');
		out.print("Total Time");
		out.print(',');
		out.print("Nb Goals");
		out.print(',');
		out.print("Length");
		out.print(',');
		out.print("Average Length");
		out.print(',');
		out.print("Standard Déviation");
		out.print(',');
		for (int i = 0; i < getDurationsBetweenEachGoals().size(); i++) {
			out.print("shoot ");
			out.print(i);
			out.print(",");
		}
		out.println();

		out.print(Utils.todayCSV());
		out.print(',');
		out.print(Utils.time());
		out.print(',');
		out.print(getTotalLength());
		out.print(',');
		out.print(getGoalsCount());
		out.print(',');
		out.print(getTotalActiveDuration());
		out.print(',');
		out.print(getAverageLength());
		out.print(',');
		out.print(getSD());
		out.print(',');
		printLengthBetweenGoalsToString(out);
		out.println();

		out.flush();
	}


}

package net.gazeplay.utils.stats;

import gaze.GazeEvent;
import gaze.GazeUtils;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.gazeplay.utils.HeatMapUtils;
import utils.games.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by schwab on 16/08/2017.
 */
@Slf4j
@ToString
public abstract class Stats {


	private final String gameName;

	private final long minimalDurationInMillisecondsToCountGoal;

	@Getter
	protected int goalsCount;

	@Getter
	private int discardedGoalsCount;

	@Getter
	protected long totalActiveDuration;

	@Getter
	private Long startTime;
	@Getter
	private Long stopTime;
	@Getter
	private Long duration;

	private Long lastGoalAvailableTime;

	@Getter
	private List<Long> durationsBetweenEachGoals;

	@Getter
	private final EventHandler<MouseEvent> recordMouseMovements;

	@Getter
	private final EventHandler<GazeEvent> recordGazeMovements;

	@Getter
	private HeatMapState heatMapState;

	public Stats(String gameName, long minimalDurationInMillisecondsToCountGoal) {
		this.gameName = gameName;
		this.minimalDurationInMillisecondsToCountGoal = minimalDurationInMillisecondsToCountGoal;

		recordGazeMovements = new GazeEventHandler(this);
		recordMouseMovements = new MouseEventHandler(this);
	}

	public Stats(String gameName) {
		this(gameName, 1);
	}

	public void start(ScreenDimension screenDimension) {
		if (startTime != null) {
			throw new IllegalStateException("Stats already started");
		}
		startTime = System.currentTimeMillis();

		lastGoalAvailableTime = startTime;

		goalsCount = 0;
		totalActiveDuration = 0;

		durationsBetweenEachGoals = new ArrayList<>();

		heatMapState = new HeatMapState(screenDimension);

		log.info("GazeUtils ON : " + GazeUtils.isOn());
	}

	public void stop() {
		if (stopTime != null) {
			throw new IllegalStateException("Stats already stopped");
		}
		stopTime = System.currentTimeMillis();
		duration = stopTime - startTime;
	}

	public void savePNGHeatMap(File destination) {

		Path HeatMapPath = Paths.get(HeatMapUtils.getHeatMapPath());
		Path dest = Paths.get(destination.getAbsolutePath());

		try {
			Files.copy(HeatMapPath, dest, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			log.error("Exception", e);
		}
	}

	public void saveStats() {

		File saveFile = new File(Utils.getStatsFolder());
		saveFile.mkdir();

		File gameFolder = new File(Utils.getStatsFolder() + gameName);
		gameFolder.mkdir();

		File savepath = new File(gameFolder.getAbsoluteFile() + Utils.FILESEPARATOR + Utils.today());
		savepath.mkdir();

		File heatMapCSVPath = new File(savepath.getAbsoluteFile() + Utils.FILESEPARATOR + Utils.now() + "-heatmap.csv");
		File heatMapPNGPath = new File(savepath.getAbsoluteFile() + Utils.FILESEPARATOR + Utils.now() + "-heatmap.png");

		heatMapState.saveRawHeatMap(heatMapCSVPath);
		savePNGHeatMap(heatMapPNGPath);
	}


	public long getAverageLength() {
		if (goalsCount == 0) {
			return 0;
		} else {
			return totalActiveDuration / goalsCount;
		}
	}

	public long getMedianLength() {
		if (goalsCount == 0) {
			return 0;
		} else {

			int nbElements = durationsBetweenEachGoals.size();

			List<Long> sortedList = new ArrayList<>(durationsBetweenEachGoals);
			Collections.sort(sortedList);

			int middle = (int) (nbElements / 2);

			if (nbElements % 2 == 0) {//number of elements is even, median is the average of the two central numbers

				middle -= 1;
				return (sortedList.get(middle) + sortedList.get(middle + 1)) / 2;

			} else {//number of elements is odd, median is the central number

				return sortedList.get(middle);
			}
		}
	}

	public long getTotalLength() {
		return duration;
	}

	public double getVariance() {

		double average = getAverageLength();

		double sum = 0;

		for (Long i : durationsBetweenEachGoals) {
			sum += Math.pow((i.intValue() - average), 2);
		}

		return sum / goalsCount;
	}

	public double getSD() {
		return Math.sqrt(getVariance());
	}


	/**
	 * called whenever the goal/target has been displayed or made available by the game to the player.
	 * 
	 * This does not fit all games, as some game may have multiple target available at the same time.
	 * This better fit games where only 1 goal/target is available at anytime.
	 * 
	 * This method is needed in case a new goal/target is not available immediatly when the previous goal/target was reached.
	 * Sometime the game waits for a few seconds after a target has been reached before making a new one available.
	 * 
	 * This permits to measure the actual duration it took to the player between the time the goal is available and the time the goal is reached by the player.
	 */
	public void onGoalAvailable() {
		long newGoalAvailableTime = System.currentTimeMillis();
		lastGoalAvailableTime = newGoalAvailableTime;
	}

	/**
	 * called whenever a goal/target has been reached by the player
	 */
	public void onGoalReached() {
		long newGoalReachedTime = System.currentTimeMillis();
		long durationSinceGoalAvailable = newGoalReachedTime - lastGoalAvailableTime;
		//
		if (durationSinceGoalAvailable < minimalDurationInMillisecondsToCountGoal) {
			discardedGoalsCount++;
		} else {
			// reset the lastGoalAvailableTime property, in case the game does not call onGoalAvailable()
			lastGoalAvailableTime = newGoalReachedTime;

			//
			goalsCount++;
			totalActiveDuration += durationSinceGoalAvailable;
			durationsBetweenEachGoals.add(durationSinceGoalAvailable);
		}
	}

	public List<Long> getSortedLengthBetweenGoals() {

		int nbElements = durationsBetweenEachGoals.size();

		List<Long> sortedList = new ArrayList<>(durationsBetweenEachGoals);
		Collections.sort(sortedList);

		List<Long> normalList = new ArrayList<>(durationsBetweenEachGoals);

		// What is the purpose of this ?
		// What is the expected result ?

		int j = 0;

		for (int i = 0; i < nbElements; i++) {

			if (i % 2 == 0)
				normalList.set(j, sortedList.get(i));
			else {
				normalList.set(nbElements - 1 - j, sortedList.get(i));
				j++;
			}
		}

		return normalList;
	}

	protected String getTodayFolder() {
		return Utils.getStatsFolder() + gameName + Utils.FILESEPARATOR + Utils.today() + Utils.FILESEPARATOR;
	}


	public void printLengthBetweenGoalsToString(PrintWriter out) {
		for (Long i : durationsBetweenEachGoals) {
			out.print(i);
			out.print(',');
		}
	}
}

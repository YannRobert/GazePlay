package net.gazeplay.utils.stats;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

@Slf4j
public class HeatMapState {

	private static final int HEAT_MAP_SIZE_RATIO = 5;

	private static final int trail = 10;

	@Getter
	private final ScreenDimension heatMapDimension;

	@Getter
	private final double[][] heatMap;

	public HeatMapState(ScreenDimension screenDimension) {
		heatMapDimension = screenDimension.toReducedDimension(HEAT_MAP_SIZE_RATIO).toRotatedDimension();
		heatMap = new double[heatMapDimension.getWidth()][heatMapDimension.getHeight()];
	}

	public void appendToHeatMap(int screenX, int screenY) {

		//in heatChart, x and y are opposed
		int x = screenY / HEAT_MAP_SIZE_RATIO;
		int y = screenX / HEAT_MAP_SIZE_RATIO;

		for (int i = -trail; i <= trail; i++) {
			for (int j = -trail; j <= trail; j++) {
				if (Math.sqrt(i * i + j * j) < trail) {
					inc(x + i, y + j);
				}
			}
		}
	}

	protected void saveRawHeatMap(File file) {

		PrintWriter out = null;

		try {
			out = new PrintWriter(file);

		} catch (FileNotFoundException e) {
			log.error("Exception", e);
		}

		for (int i = 0; i < heatMap.length; i++) {

			for (int j = 0; j < heatMap[0].length - 1; j++) {

				out.print((int) heatMap[i][j]);
				out.print(", ");
			}

			out.print((int) heatMap[i][heatMap[i].length - 1]);
			out.println("");
		}
		out.flush();
	}

	private void inc(int x, int y) {
		if (x >= 0 && y >= 0) {
			if (x < heatMap.length && y < heatMap[0].length) {
				heatMap[x][y]++;
			}
		}
	}

}

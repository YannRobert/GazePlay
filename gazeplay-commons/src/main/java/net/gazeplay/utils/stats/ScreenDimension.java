package net.gazeplay.utils.stats;

import lombok.Data;

@Data
public class ScreenDimension {
	private final int width;
	private final int height;

	public ScreenDimension toReducedDimension(int aspectRatio) {
		return new ScreenDimension(width / aspectRatio, height / aspectRatio);
	}

	public ScreenDimension toRotatedDimension() {
		return new ScreenDimension(height, width);
	}

}

package com.marsol0x.flappyfin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class WallBuilder {
	private int ROCK_ROWS = 3;
	private int ROCK_COLS = 3;

	private Texture rockSheet;
	private TextureRegion[][] rockTiles;
	
	private final Pool<WallPiece> wallPool = new Pool<WallPiece>() {
		@Override
		protected WallPiece newObject() {
			return new WallPiece();
		}
	};
	
	public WallBuilder() {
		rockSheet = new Texture(Gdx.files.internal("rock_sheet.png"));
		rockTiles = TextureRegion.split(rockSheet,
                                        rockSheet.getWidth() / ROCK_ROWS,
                                        rockSheet.getHeight() / ROCK_COLS);
	}
	
	public Array<WallPiece> buildWall(float x, float y, int w, int h) {
		// A wall must be at least 2 wide and 2 tall, otherwise it will look funny
		if (w < 2) return null;
		if (h < 2) return null;
		
		int remainingTiles = w * h;
		Array<WallPiece> wall = new Array<WallPiece>();
		
		// First, add corners
		// Bottom-left
		wall.add(getTile(rockTiles[2][0], x, y));
		
		// Bottom-right
		wall.add(getTile(rockTiles[2][2], x + ((w - 1) * rockTiles[0][2].getRegionWidth()), y));
		
		// Top-left
		wall.add(getTile(rockTiles[0][0], x, y + ((h - 1) * rockTiles[0][0].getRegionHeight())));
		
		// Top-right
		wall.add(getTile(rockTiles[0][2],
				x + ((w - 1) * rockTiles[0][2].getRegionWidth()),
				y + ((h - 1) * rockTiles[0][2].getRegionHeight())));
		remainingTiles -= 4;
		
		System.out.println("Corners: " + remainingTiles);
		// Check our remaining tiles, we might not need to do anything else
		if (remainingTiles <= 0) { return wall; }
		
		// Edges
		// Top and bottom edges
		for (int i = 1; i < w - 1; i++) {
			// Bottom
			wall.add(getTile(rockTiles[2][1],
					x + ((w - (i + 1)) * rockTiles[0][1].getRegionWidth()),
					y));
			// Top
			wall.add(getTile(rockTiles[0][1],
					x + ((w - (i + 1)) * rockTiles[0][1].getRegionWidth()),
					y + ((h - i) * rockTiles[0][1].getRegionHeight())));
			remainingTiles -= 2;
		}
		
		System.out.println("Top/Bottom Edges: " + remainingTiles);
		// Check our remaining tiles, we might not need to do anything else
		if (remainingTiles <= 0) { return wall; }

		// Left and right edges
		for (int i = 1; i < h - 1; i++) {
			// Left
			wall.add(getTile(rockTiles[1][0],
					x,
					y + ((h - (i + 1)) * rockTiles[1][0].getRegionHeight())));
			// Right
			wall.add(getTile(rockTiles[1][2],
					x + ((w - i) * rockTiles[1][2].getRegionWidth()),
					y + ((h - (i + 1)) * rockTiles[1][2].getRegionHeight())));
			remainingTiles -= 2;
		}

		System.out.println("Left/Right Edges: " + remainingTiles);
		// Check our remaining tiles, we might not need to do anything else
		if (remainingTiles <= 0) { return wall; }
		
		// Filler
		
		return wall;
	}
	
	private WallPiece getTile(TextureRegion img, float x, float y) {
		WallPiece wp = wallPool.obtain();
		wp.init(img, x, y);

		return wp;
	}

	public void dispose() {
		rockSheet.dispose();
	}
}

package com.Brandon.Tutorial;

import com.badlogic.gdx.Game;

public class Tutorial extends Game {

	Game game;

	@Override
	public void create() {
		game = this;
		setScreen(new MainMenu(game));
	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {
		super.render();
		// sr.begin(ShapeType.Rectangle);
		// sr.setColor(Color.BLUE);
		// sr.rect(player.getPosition().x, player.getPosition().y,
		// player.getCurrentFrame().getRegionWidth(),
		// player.getCurrentFrame().getRegionHeight());
		// sr.setColor(Color.RED);
		// sr.rect(tree.getPosition().x, tree.getPosition().y, tree.getSize().x,
		// tree.getSize().y);
		// sr.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
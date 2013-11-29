package com.Brandon.Tutorial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Tutorial implements ApplicationListener{

	SpriteBatch batch;
	Player player;
	InputProcessor inputProcessor;
	Vector2 position;
	Texture mario;
	Tree tree, tree2;
	ShapeRenderer sr;
	ArrayList<Tree> trees;
	Iterator<Tree> treeIterator;
	ArrayList<Enemy> enemies;
	Iterator<Enemy> enemyIterator;
	Enemy enemy;
	Stage stage;
	Label label;
	LabelStyle style;
	BitmapFont font;
	
	TextureAtlas buttonAtlas;
	TextButtonStyle buttonStyle;
	TextButton button;
	Skin skin;
	
	
	@Override
	public void create() {		
		batch = new SpriteBatch();
		mario = new Texture(Gdx.files.internal("mario.png"));
		sr = new ShapeRenderer();
		tree = new Tree(new Vector2(100,100), new Vector2(50, 100));
		tree2 = new Tree(new Vector2(500,100), new Vector2(50, 100));
		
		trees = new ArrayList<Tree>();
		enemies = new ArrayList<Enemy>();
		trees.add(tree);
		trees.add(tree2);
		System.out.println(trees.size());
		
		if(Gdx.files.local("player.dat").exists()){
			try {
				player = new Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() /2), "mario.png");
				player.setPosition(Player.readPlayer()) ;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Player Exists, Reading File");
		}else{
			player = new Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() /2), "mario.png");
			try {
				Player.savePlayer(player);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Player Does Not Exist, Creating Player and Saving Player");
		}
		
		enemy = new Enemy(new Vector2(50,50), player);
		enemies.add(enemy);
		
		enemies.add(new Enemy(new Vector2(100,100), player));
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		
		font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
		style = new LabelStyle(font, Color.BLACK);
		
		label = new Label("TheLazyTryhard Tutorials", style);
		label.setPosition(10, Gdx.graphics.getHeight() - 50);
		
		stage.addActor(label);
		
		skin = new Skin();
		buttonAtlas = new TextureAtlas("buttons/button.pack");
		skin.addRegions(buttonAtlas);
		
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button");
		buttonStyle.over = skin.getDrawable("buttonpressed");
		buttonStyle.down = skin.getDrawable("buttonpressed");
		buttonStyle.font = font;
		
		button = new TextButton("thelazytryhard is lame", buttonStyle);
		
		
		stage.addActor(button);
		Gdx.input.setInputProcessor(stage);
		
		button.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				player.getPosition().x = 2;
				System.out.println("clicked");
				return true;
			}
		});
	}

	@Override
	public void dispose() {
		try {
			player.savePlayer(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tree.update();
		player.update();
		stage.act();
		
		batch.begin();
		batch.draw(player.getCurrentFrame(), player.getPosition().x, player.getPosition().y);
		
		treeIterator = trees.iterator();
		while(treeIterator.hasNext()){
			Tree cur = treeIterator.next();
			cur.draw(batch);
			cur.update();
			
			if(player.getBounds().overlaps(cur.getBounds())){
				player.reAdjust();
			}
		}
		
		enemyIterator = enemies.iterator();
		while(enemyIterator.hasNext()){
			Enemy cur = enemyIterator.next();
			
			cur.update();
			batch.draw(cur.getEnemyTexture(), cur.getPosition().x, cur.getPosition().y, 25, 25);
			
			if(player.getBounds().overlaps(cur.getBounds())){
				//System.out.println("PLAYER HIT!");
			}
		}
		
		//System.out.println(enemies.size());
		
		stage.draw();
		batch.end();
		
//		sr.begin(ShapeType.Rectangle);
//		sr.setColor(Color.BLUE);
//		sr.rect(player.getPosition().x, player.getPosition().y, player.getCurrentFrame().getRegionWidth(), player.getCurrentFrame().getRegionHeight());
//		sr.setColor(Color.RED);
//		sr.rect(tree.getPosition().x, tree.getPosition().y, tree.getSize().x, tree.getSize().y);
//		sr.end();
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

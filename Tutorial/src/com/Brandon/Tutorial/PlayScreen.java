package com.Brandon.Tutorial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class PlayScreen implements Screen{

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

     ArrayList<Tile> tiles;
     Iterator<Tile> tileIterator;
     
     Enemy enemy;
     Game game;
	
     public PlayScreen(Game game){
    	 this.game = game;
     }
     
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        tree.update();
        player.update();
        
        batch.begin();
        
        tileIterator = tiles.iterator();
        while(tileIterator.hasNext()){
        	Tile cur = tileIterator.next();	
        	cur.render(batch);
        }
        
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
        
        batch.end();
        
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
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
		
        tiles = new ArrayList<Tile>();
        
        for(int i = 0; i < 10; i++){
        	for(int j = 0; j < 10; j++){
        		int R = (int) ((Math.random() * (2 - 0) + 0));
        		if(R == 0){
        			tiles.add(new Tile(new Texture("grass.png"), i * 50, j * 50, 50, 50));
        		}
        		if(R == 1){
        			tiles.add(new Tile(new Texture("dirt.png"), i * 50, j * 50, 50, 50));
        		}
        	}
        }
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		try {
			player.savePlayer(player);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

package com.Brandon.Tutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenu implements Screen{

	Stage stage;
    Label label;
    LabelStyle style;
    BitmapFont font;
    
    TextureAtlas buttonAtlas;
    TextButtonStyle buttonStyle;
    TextButton button;
    Skin skin;
    
    SpriteBatch batch;
	
    Game game;
    
    public MainMenu(Game game){
    	this.game = game;
    }
    
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
		stage.act();
		
		batch.begin();
		stage.draw();
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		  stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
          
          font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
          style = new LabelStyle(font, Color.BLACK);
          
          label = new Label("TheLazyTryhard Tutorials", style);
          label.setPosition((Gdx.graphics.getWidth() / 2) - (label.getWidth() / 2), Gdx.graphics.getHeight() - label.getHeight());
          
          stage.addActor(label);
          
          skin = new Skin();
          buttonAtlas = new TextureAtlas("buttons/button.pack");
          skin.addRegions(buttonAtlas);
          
          buttonStyle = new TextButtonStyle();
          buttonStyle.up = skin.getDrawable("button");
          buttonStyle.over = skin.getDrawable("buttonpressed");
          buttonStyle.down = skin.getDrawable("buttonpressed");
          buttonStyle.font = font;
          
          button = new TextButton("PLAY", buttonStyle);
          button.setWidth(Gdx.graphics.getWidth() / 3);
          button.setHeight(Gdx.graphics.getHeight() / 6);
          button.setPosition((Gdx.graphics.getWidth() / 2) - (button.getWidth() / 2),(Gdx.graphics.getHeight() / 2) - (button.getHeight() / 2));
          
          stage.addActor(button);
          Gdx.input.setInputProcessor(stage);
          
          button.addListener(new InputListener(){
                  @Override
                  public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                         game.setScreen(new PlayScreen(game));
                         stage.clear();
                          return true;
                  }
          });
		
          batch = new SpriteBatch();
          
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
		// TODO Auto-generated method stub
		
	}

}

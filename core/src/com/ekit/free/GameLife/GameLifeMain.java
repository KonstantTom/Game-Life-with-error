package com.ekit.free.GameLife;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameLifeMain extends ApplicationAdapter {
	SpriteBatch batch;
	Map map;
	boolean is_play = false;
	Texture stop, play, clear, random;
	Rectangle[] buttons;
	final int BUTTONS_COUNT = 4;
	OrthographicCamera camera;
	GameInputProcessor gip;
	Timer t;
	TimerTask tt = new TimerTask(){

		@Override
		public void run() {
			if(is_play)
			{
			map.process_map();
			}
		}
		
	};
	@Override
	public void create () {
		buttons = new Rectangle[BUTTONS_COUNT];
		for(int j = 0; j < BUTTONS_COUNT; j++)
		{
			buttons[j] = new Rectangle(j*(Gdx.graphics.getWidth()/8), (Gdx.graphics.getHeight()/6)*5, Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()/6);
		}
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 600);
	    stop = new Texture(Gdx.files.internal("StopButton.png"));
	    play = new Texture(Gdx.files.internal("PlayButton.png"));
	    clear = new Texture(Gdx.files.internal("ClearButton.png"));
	    random = new Texture(Gdx.files.internal("RandomButton.png"));
		batch = new SpriteBatch();
		map = new Map(Gdx.graphics.getWidth(), (Gdx.graphics.getHeight()/6)*5, 40, 25);
		gip = new GameInputProcessor(this);
		Gdx.input.setInputProcessor(gip);
		t = new Timer(true);
		t.schedule(tt, 1, 250);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		map.draw(batch);
		batch.begin();
		batch.draw(stop, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height);
		batch.draw(play, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height);
		batch.draw(clear, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height);
		batch.draw(random, buttons[3].x, buttons[3].y, buttons[3].width, buttons[3].height);
		batch.end();
	}
}

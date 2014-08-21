package com.ekit.free.GameLife;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameInputProcessor extends InputAdapter{
	GameLifeMain glm;
	Vector3 touchPos = new Vector3();
	Vector2 v = new Vector2();
	public GameInputProcessor(GameLifeMain GLM)
	{
		glm = GLM;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchPos.set(screenX, screenY, 0);
		glm.camera.unproject(touchPos);
		v.set(touchPos.x, touchPos.y);
		if(glm.buttons[0].contains(v))
		{
			glm.is_play = false;
		}else{
		if(glm.buttons[1].contains(v))
		{
			glm.is_play = true;
		}else{
		if(glm.buttons[2].contains(new Vector2(v)))
		{
			glm.map.clear();
		}else{
		if(glm.buttons[3].contains(new Vector2(v)))
		{
			glm.map.random();
		}else{
			glm.map.click((int)touchPos.x, (int)touchPos.y);
		}}}}
		return true;
	}
}

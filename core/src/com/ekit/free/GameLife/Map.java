package com.ekit.free.GameLife;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Map {
	int mh, mw;
	int ccw, cch;
	int[][] cells;
	Rectangle[][] cells_rects;
	ShapeRenderer r;
	Vector2 v;
	int cellwidth, cellheight;
	public Map(int MW, int MH, int CCW, int CCH)
	{
		mw = MW;
		mh = MH;
		ccw = CCW;
		cch = CCH;
		init();
		init_rects();
		clear();
		r = new ShapeRenderer();
		v = new Vector2();
	}
	private void init()
	{
		cells = new int[ccw][cch];
		cellwidth = mw / ccw;
		cellheight = mh / cch;
	}
	private void init_rects()
	{
		cells_rects = new Rectangle[ccw][cch];
		for(int x = 0; x < ccw; x++)
		{
			for(int y = 0; y < cch; y++)
			{
				cells_rects[x][y] = new Rectangle(x*cellwidth, y*cellheight, cellwidth, cellheight);
			}
		}
	}
	public void clear()
	{
		for(int j = 0; j < ccw; j++)
		{
			for(int k = 0; k < cch; k++)
			{
				cells[j][k] = 0;
			}
		}
	}
	public void draw(Batch b)
	{
		r.setProjectionMatrix(b.getProjectionMatrix());
		r.setTransformMatrix(b.getTransformMatrix());
		for(int j = 0; j < ccw; j++)
		{
			for(int k = 0; k < cch; k++)
			{
				draw_cell(j,k);
			}
		}
	}
	private void draw_cell(int x, int y)
	{
		switch(cells[x][y])
		{
		case 0 : r.setColor(Color.CYAN);break;
		case 1 : r.setColor(Color.YELLOW);break;
		case 2 : r.setColor(Color.ORANGE); break;
		case 3 : r.setColor(Color.RED);
		}
		r.begin(ShapeType.Filled);
		r.rect(cells_rects[x][y].getX(), cells_rects[x][y].getY(), cells_rects[x][y].getWidth(), cells_rects[x][y].getHeight());
		r.end();
		r.setColor(Color.BLACK);
		r.begin(ShapeType.Line);
		r.line(cells_rects[x][y].getX() + cellwidth, cells_rects[x][y].getY() + cellheight, cells_rects[x][y].getX() + cellwidth,cells_rects[x][y].getY());
		r.line(cells_rects[x][y].getX() + cellwidth, cells_rects[x][y].getY(), cells_rects[x][y].getX(), cells_rects[x][y].getY());
		r.line(cells_rects[x][y].getX(), cells_rects[x][y].getY(), cells_rects[x][y].getX(), cells_rects[x][y].getY() + cellheight);
		r.line(cells_rects[x][y].getX(), cells_rects[x][y].getY() + cellheight, cells_rects[x][y].getX() + cellwidth, cells_rects[x][y].getY() + cellheight);
		r.end();
	}
	private int lcac(int x, int y)
	{
		int lc = 0;
		if(cells[x][y] != 0)
		{
			lc--;
		}
		x--;
		y--;
		if(x==-1)
		{
			x = ccw - 1;
		}
		if(y==-1)
		{
			y = cch - 1;
		}
		for(int j = 0; j < 3; j++)
		{
			for(int k = 0; k < 3; k++)
			{
				if(cells[x][y] != 0)
				{
					lc++;
				}
				y++;
				if(y==cch)
				{
					y = 0;
				}
			}
			x++;
			if(x==ccw)
			{
				x = 0;
			}
		}
		return lc;
	}
	public void process_map()
	{
		int[][] lc = new int[ccw][cch];
		for(int x = 0; x < ccw; x++)
		{
			for(int y = 0; y < cch; y++)
			{
				lc[x][y] = lcac(x,y);
			}
		}
		for(int x = 0; x < ccw; x++)
		{
			for(int y = 0; y < cch; y++)
			{
				if((cells[x][y] == 0) && (lc[x][y] == 3))
				{
					cells[x][y] = 1;
				}
				if((cells[x][y] != 0) && ((lc[x][y] < 2)||(lc[x][y]>3)))
				{
					cells[x][y] += 1;
				}
				if(cells[x][y] == 4)
				{
					cells[x][y] = 0;
				}
			}
		}
	}
	public void click(int x, int y)
	{
		v.set(x,y);
		int xc = -1, yc = -1;
		for(int j = 0; j < ccw; j++)
		{
			for(int k = 0; k < cch; k++)
			{
				if(cells_rects[j][k].contains(v))
				{
					xc = j;
					yc = k;
				}
			}
		}
		if((xc != -1)&&(yc != -1))
		{
			if(cells[xc][yc] == 0)
			{
				cells[xc][yc] = 1;
			}
			else
			{
				cells[xc][yc] = 0;
			}
		}
	}
	public void random()
	{
		for(int j = 0; j < ccw; j++)
		{
			for(int k = 0; k < cch; k++)
			{
				cells[j][k] = MathUtils.random(0, 3);
			}
		}
	}
}

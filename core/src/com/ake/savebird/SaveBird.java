package com.ake.savebird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SaveBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bee1;
	Texture bee2;
	Texture bee3;
	float birdX = 0;
	float birdY = 0;
	float velocity = 2;
	float gravity = 0.2f;
	float width,height;
	Circle birdCircle;
	Random random;
	ShapeRenderer shapeRenderer;

	int score = 0;
	int scoreedEnemy = 0;
	BitmapFont font,font2;

	//düşman
	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyoffSet1 = new float[numberOfEnemies];
	float [] enemyoffSet2 = new float[numberOfEnemies];
	float [] enemyoffSet3 = new float[numberOfEnemies];
	float enemyVelecity = 15;
	float distance = 0;
	Circle[] enemyCircle1;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;
	int gameState = 0; //oyun başladımı başlamadı mı

	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		// görsel tanımlama
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		bee1 = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");

		// kuş yön
		birdX = Gdx.graphics.getWidth()/8;
		birdY = Gdx.graphics.getHeight()/2;

		birdCircle = new Circle();

		enemyCircle1 = new Circle[numberOfEnemies];
		enemyCircle2 = new Circle[numberOfEnemies];
		enemyCircle3 = new Circle[numberOfEnemies];

		distance = Gdx.graphics.getWidth()/2;
		random = new Random();
		shapeRenderer = new ShapeRenderer();

		font  =new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2  =new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);

		for (int i = 0;i<numberOfEnemies;i++){

			enemyoffSet1 [i] = (random.nextFloat() - 0.5f) * height -200;
			enemyoffSet2 [i] = (random.nextFloat() - 0.5f) * height -200;
			enemyoffSet3 [i] = (random.nextFloat() - 0.5f) * height -200;

			enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

			enemyCircle1[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();
		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, width,height);

		if(gameState == 1){

			if(enemyX[scoreedEnemy] < birdX){
				score++;

				if(scoreedEnemy < numberOfEnemies -1){
					scoreedEnemy++;
				}else{
					scoreedEnemy = 0;
				}
			}

			if (Gdx.input.justTouched()){
				velocity = -7;
			}

			for (int i = 0;i<numberOfEnemies;i++){

				if(enemyX[i] < 0){
					enemyX[i] += numberOfEnemies *distance;

					enemyoffSet1 [i] = (random.nextFloat() - 0.5f) * height -200;
					enemyoffSet2 [i] = (random.nextFloat() - 0.5f) * height -200;
					enemyoffSet3 [i] = (random.nextFloat() - 0.5f) * height -200;
				}
				else{
					enemyX[i] -= enemyVelecity;
				}
				batch.draw(bee1,enemyX[i],height/2 + enemyoffSet1[i],width/15,height/11);
				batch.draw(bee2,enemyX[i],height/2 + enemyoffSet2[i],width/15,height/11);
				batch.draw(bee3,enemyX[i],height/2 + enemyoffSet3[i],width/15,height/11);

				enemyCircle1 [i] = new Circle(enemyX[i] + width/30,height/2 + enemyoffSet1[i]+height/22,width/30);
				enemyCircle2 [i] = new Circle(enemyX[i] + width/30,height/2 + enemyoffSet2[i]+height/22,width/30);
				enemyCircle3 [i] = new Circle(enemyX[i] + width/30,height/2 + enemyoffSet3[i]+height/22,width/30);
			}

			if(birdY > 0 ){
					velocity += gravity;
					birdY -= velocity;
			}else{
				gameState = 2;
			}
			if(birdY < height ){
				velocity += gravity;
				birdY -= velocity;
			}else{
				gameState = 2;
			}

		}else if(gameState == 0){
			if (Gdx.input.justTouched()){
				gameState = 1;
				//System.out.println("deneme");
			}
		}else if(gameState == 2){

			font2.draw(batch,"Game Over! Tap to Play Again!",300,height/2);
			//font.draw(batch,String.valueOf(score),100,200);

			if (Gdx.input.justTouched()){
				gameState = 1;
				//System.out.println("deneme");
			}
			birdX = Gdx.graphics.getWidth()/8;
			//birdY = Gdx.graphics.getHeight()/2;

			for (int i = 0;i<numberOfEnemies;i++){

				enemyoffSet1 [i] = (random.nextFloat() - 0.5f) * height -200;
				enemyoffSet2 [i] = (random.nextFloat() - 0.5f) * height -200;
				enemyoffSet3 [i] = (random.nextFloat() - 0.5f) * height -200;

				enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

				enemyCircle1[i] = new Circle();
				enemyCircle2[i] = new Circle();
				enemyCircle3[i] = new Circle();
			}
			velocity = 2;
			score = 0;
			scoreedEnemy = 0;
		}

		batch.draw(bird,birdX,birdY,width/15,height/10);
		font.draw(batch,String.valueOf(score),100,200);
		batch.end();

		birdCircle.set(birdX + width/30,birdY + height/20,width/30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

		for(int i = 0;i<numberOfEnemies;i++){
			//shapeRenderer.circle(enemyX[i] + width/30,height/2 + enemyoffSet1[i]+height/22,width/30);
			//shapeRenderer.circle(enemyX[i] + width/30,height/2 + enemyoffSet2[i]+height/22,width/30);
			//shapeRenderer.circle(enemyX[i] + width/30,height/2 + enemyoffSet3[i]+height/22,width/30);

			if(Intersector.overlaps(birdCircle,enemyCircle1[i]) || Intersector.overlaps(birdCircle,enemyCircle2[i]) ||Intersector.overlaps(birdCircle,enemyCircle3[i])){
				gameState = 2;
			}
		}

		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}

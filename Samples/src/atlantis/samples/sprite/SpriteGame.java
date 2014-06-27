package atlantis.samples.sprite;

import java.awt.event.KeyEvent;

import atlantis.engine.Application;
import atlantis.engine.graphics.Sprite;
import atlantis.framework.Game;
import atlantis.framework.GameTime;
import atlantis.framework.graphics.SpriteBatch;
import atlantis.framework.input.KeyboardState;

public class SpriteGame extends Game {
	private Sprite background;
	private Sprite tree;
	private Sprite tree2;
	private Sprite femaleSprite;
	private SpriteBatch spriteBatch;
	
	public SpriteGame() {
		super(1024, 600, "Atlantis Game Engine for Java - Sprite Sample");
		this.background = new Sprite("background.png");
		this.tree = new Sprite("Tree.png");
		this.tree2 = new Sprite("Tree2.png");
		this.femaleSprite = new Sprite("BRivera-femaleelfwalk.png");
		this.femaleSprite.setViewport(0, 0, this.width, this.height);
		this.femaleSprite.forceInsideScreen(true);
		this.spriteBatch = new SpriteBatch(graphicsDevice());
		
		Application.content = this.content;
	}
	
	public void loadContent() {
		super.loadContent();
		
		this.background.initialize();
		this.background.setSize(this.width, this.height);
		
		this.tree.initialize();
		this.tree.setPosition(150, 150);
		this.tree2.initialize();
		this.tree2.setSize(128, 128);
		this.tree2.setPosition(this.width - 250, this.height - 250);
		
		this.femaleSprite.initialize();
		this.femaleSprite.prepareAnimation(64, 64);
		this.femaleSprite.addAnimation("up", new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 }, 15);
		this.femaleSprite.addAnimation("left", new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17 }, 15);
		this.femaleSprite.addAnimation("down", new int[] { 18, 19, 20, 21, 22, 23, 24, 25, 26 }, 15);
		this.femaleSprite.addAnimation("right", new int[] { 27, 28, 29, 30, 31, 32, 33, 34, 35 }, 15);
	    
	    this.femaleSprite.setSize(72, 72);
	    this.femaleSprite.setPosition(
	    		this.width / 2 - this.femaleSprite.getWidth() / 2,
	    		this.height / 2 - this.femaleSprite.getHeight() / 2);
	}
	
	public void update(GameTime gameTime) {
		super.update(gameTime);
		
		this.femaleSprite.update(gameTime);
		
		KeyboardState state = this.keyboardManager.getState();
		
		if (state.isKeyDown(KeyEvent.VK_UP)) {
			this.femaleSprite.play("up");
			this.femaleSprite.translate(0, -1);
		}
		else if (state.isKeyDown(KeyEvent.VK_DOWN)) {
			this.femaleSprite.play("down");
			this.femaleSprite.translate(0, 1);
		}
		
		if (state.isKeyDown(KeyEvent.VK_RIGHT)) {
			this.femaleSprite.play("right");
			this.femaleSprite.translate(1, 0);
		}
		else if (state.isKeyDown(KeyEvent.VK_LEFT)) {
			this.femaleSprite.play("left");
			this.femaleSprite.translate(-1, 0);
		}
		
		if (state.isKeyDown(KeyEvent.VK_ESCAPE)) {
			this.exit();
		}
	}
	
	public void draw(GameTime gameTime) {
		super.draw(gameTime);
		this.spriteBatch.begin();
		this.background.draw(gameTime, this.spriteBatch);
		this.tree.draw(gameTime, this.spriteBatch);
		this.tree2.draw(gameTime, this.spriteBatch);
		this.femaleSprite.draw(gameTime, this.spriteBatch);
		this.spriteBatch.end();
	}

	public static void main(String [] args) {
		SpriteGame game = new SpriteGame();
		game.run();
	}
}

package atlantis.samples.applet;

import atlantis.engine.Application;
import atlantis.engine.graphics.Sprite;
import atlantis.framework.DrawableGameComponent;
import atlantis.framework.Game;
import atlantis.framework.GameTime;
import atlantis.framework.graphics.SpriteBatch;

public class SpriteComponent extends DrawableGameComponent {
	private Sprite background;
	private Sprite tree;
	private Sprite tree2;
	private Sprite femaleSprite;
	private SpriteBatch spriteBatch;
	
	public SpriteComponent(Game game) {
		super(game);
		this.background = new Sprite("background.png");
		this.tree = new Sprite("Tree.png");
		this.tree2 = new Sprite("Tree2.png");
		this.femaleSprite = new Sprite("BRivera-femaleelfwalk.png");
		this.femaleSprite.setViewport(0, 0, Application.width, Application.height);
		this.femaleSprite.forceInsideScreen(true);
		this.spriteBatch = new SpriteBatch(game.graphicsDevice());
	}
		
	public void loadContent() {
		this.background.loadContent(this.game.content());
		this.background.setSize(Application.width, Application.height);
		
		this.tree.loadContent(this.game.content());
		this.tree.setPosition(150, 150);
		this.tree2.loadContent(this.game.content());
		this.tree2.setSize(128, 128);
		this.tree2.setPosition(Application.width - 250, Application.height - 250);
		
		this.femaleSprite.loadContent(this.game.content());
		this.femaleSprite.prepareAnimation(64, 64);
		this.femaleSprite.addAnimation("up", new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 }, 75);
		this.femaleSprite.addAnimation("left", new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17 }, 75);
		this.femaleSprite.addAnimation("down", new int[] { 18, 19, 20, 21, 22, 23, 24, 25, 26 }, 75);
		this.femaleSprite.addAnimation("right", new int[] { 27, 28, 29, 30, 31, 32, 33, 34, 35 }, 75);
	    
	    this.femaleSprite.setSize(72, 72);
	    this.femaleSprite.setPosition(
	    		Application.width / 2 - this.femaleSprite.getWidth() / 2,
	    		Application.height / 2 - this.femaleSprite.getHeight() / 2);
	}
	
	public void update(GameTime gameTime) {
		super.update(gameTime);
		
		this.femaleSprite.update(gameTime);
		
		if (Application.keyboard.up()) {
			this.femaleSprite.play("up");
			this.femaleSprite.setY(this.femaleSprite.getY() - 1);
		}
		else if (Application.keyboard.down()) {
			this.femaleSprite.play("down");
			this.femaleSprite.setY(this.femaleSprite.getY() + 1);
		}
		
		if (Application.keyboard.right()) {
			this.femaleSprite.play("right");
			this.femaleSprite.setX(this.femaleSprite.getX() + 1);
		}
		else if (Application.keyboard.left()) {
			this.femaleSprite.play("left");
			this.femaleSprite.setX(this.femaleSprite.getX() - 1);
		}
	}
	
	public void draw(GameTime gameTime) {
		super.draw(gameTime);
		this.spriteBatch.begin();
		this.background.draw(gameTime, spriteBatch);
		this.tree.draw(gameTime, spriteBatch);
		this.tree2.draw(gameTime, spriteBatch);
		this.femaleSprite.draw(gameTime, spriteBatch);
		this.spriteBatch.end();
	}
}

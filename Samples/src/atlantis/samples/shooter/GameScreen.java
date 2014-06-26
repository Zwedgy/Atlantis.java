package atlantis.samples.shooter;

import atlantis.engine.Application;
import atlantis.engine.Timer;
import atlantis.engine.graphics.Sprite;
import atlantis.engine.graphics.SpriteGroup;
import atlantis.engine.state.State;
import atlantis.framework.GameTime;
import atlantis.framework.content.ContentManager;

public class GameScreen extends State {
	private Starfield starfield;
	private Sprite ship;
	private SpriteGroup laserGroup;
	private SpriteGroup alienGroup;
	private Timer shootTimer;
	private Timer spawnTimer;
	private float shipSpeed;
	
	public GameScreen(String name) {
		super(name);
		
		this.starfield = new Starfield();
		this.ship = new Sprite("ShipR.png");
		this.ship.setViewport(0, 0, Application.width, Application.height);
		this.laserGroup = new SpriteGroup();
		this.alienGroup = new SpriteGroup();
		
		this.scene.add(this.starfield);
		this.scene.add(this.ship);
		this.scene.add(this.laserGroup);
		this.scene.add(this.alienGroup);
		
		this.shootTimer = new Timer(300, 0);
		// Spawn a new alien 
		this.spawnTimer = new Timer(550, 0);
		
		this.shipSpeed = 0.25f;
	}

	public void loadContent(ContentManager content) {
		super.loadContent(content);
		this.ship.prepareAnimation(48, 48);
		this.ship.addAnimation("idle", new int[] { 0 }, 85);
		this.ship.addAnimation("move", new int[] { 1, 2 }, 85);
		this.ship.play("idle");
		this.ship.setPosition(50, Application.height / 2 - this.ship.getHeight() / 2);
		this.ship.forceInsideScreen(true);
	}
	
	public void update(GameTime gameTime) {
		super.update(gameTime);

		this.shootTimer.update(gameTime);
		this.spawnTimer.update(gameTime);
		
		// Spawn a new alien
		if (!this.spawnTimer.isEnabled()) {
			Alien alien = new Alien(Application.width + 50, (int)(Math.random() * Application.height));
			alien.loadContent(Application.content);
			this.alienGroup.add(alien);
			this.spawnTimer.start();
		}
		
		if (Application.keyboard.up() || Application.keyboard.down() ||	Application.keyboard.right() || Application.keyboard.left()) {
			this.ship.play("move");
		}
		else {
			this.ship.play("idle");
		}
		
		// Move the ship
		if (Application.keyboard.left()) {
			this.ship.setX((int) (this.ship.getX() - this.shipSpeed * gameTime.getElapsedTime()));
		} 
		else if (Application.keyboard.right()) {
			this.ship.setX((int) (this.ship.getX() + this.shipSpeed * gameTime.getElapsedTime()));
		}
		
		if (Application.keyboard.up()) {
			this.ship.setY((int) (this.ship.getY() - this.shipSpeed * gameTime.getElapsedTime()));
		}
		else if (Application.keyboard.down()) {
			this.ship.setY((int) (this.ship.getY() + this.shipSpeed * gameTime.getElapsedTime()));
		}
		
		if (Application.keyboard.escape()) {
			this.stateManager.setActive("menu", true);
		}
		
		// Shoot a laser
		if (Application.keyboard.space()) {
			if (!this.shootTimer.isEnabled()) {
				Laser laser = new Laser(this.ship.getX() + this.ship.getWidth() + 1, this.ship.getY() + 25);
				laser.loadContent(Application.content);
				this.laserGroup.add(laser);
				this.shootTimer.start();
			}
		} 
		
		// Test if a laser touching an alien
		if (this.alienGroup.count() > 0) {
			for (Sprite alien : this.alienGroup.getEntities()) {
				if (!alien.isActive()) {
					continue;
				}
				
				for (Sprite laser : this.laserGroup.getEntities()) {
					// Laser with alien
					if (laser.getBounds().intersects(alien.getBounds())) {
						laser.setActive(false);
						alien.setActive(false);
					}
				}
				
				// Player with alien
				if (this.ship.getBounds().intersects(alien.getBounds())) {
					alien.setActive(false);
					this.stateManager.setActive("menu", true);
					System.out.println("\nShip: " + this.ship.getBounds().toString() + "\nAlien: " + alien.getBounds().toString());
				}
			}
		}
	}
}

/**
 * The star field
 * @author yannick
 *
 */
class Starfield extends SpriteGroup {
	Sprite [] starfields;
	
	public Starfield() {
		this.starfields = new Sprite[2];
		
		for (int i = 0; i < 2; i++) {
			this.starfields[i] = new Sprite("starfield.png");
			this.starfields[i].setPosition(i * 800, 0);
			this.add(this.starfields[i]);
		}
	}
	
	public void update(GameTime gameTime) {
		super.update(gameTime);
		
		for (Sprite Sprite : this.starfields) {
			if (Sprite.getX() <= -800) {
				Sprite.setX(800);
			}
			Sprite.setX(Sprite.getX() - 1);
		}
	}
}

/**
 * A laser 
 * @author yannick
 *
 */
class Laser extends Sprite {
	private float speed;
	
	public Laser(float x, float y) {
		super("laser.png");
		this.setPosition(x, y);
		this.speed = 0.65f;
	}
	
	public void update(GameTime gameTime) {
		super.update(gameTime);
		
		if (this.rectangle.getRight() > Application.width) {
			this.setActive(false);
		}
		else {
			this.setX((int)(this.position.x + this.speed * gameTime.getElapsedTime()));
		}
	}
}

/**
 * An alien
 * @author yannick
 *
 */
class Alien extends Sprite {
	private float speed;
	
	public Alien(int x, int y) {
		super("alien.png");
		this.setPosition(x, y);
		this.speed = 0.55f;
	}
	
	public void loadContent(ContentManager content) {
		super.loadContent(content);
		
		this.prepareAnimation(32, 32);
		this.addAnimation("normal", new int[] {0,  1, 2 }, 50);
	}
	
	public void update(GameTime gameTime) {
		super.update(gameTime);
		
		if (this.rectangle.getRight() < 0) {
			this.setActive(false);
		}
		else {
			this.play("normal");
			this.setX((int)(this.position.x - this.speed * gameTime.getElapsedTime()));
		}
	}
}
package VideoGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
public class Player extends GameObject{
	Handler handler;
	Menu menu;
	Game game;
	public Player(int x, int y, ID id, Handler handler, Menu menu, Game game) {
		super(x, y, id);
		this.handler = handler;
		this.menu = menu;
		this.game = game;
	}
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	public void tick() {
		x += velX;
		y += velY;
		x = Game.clamp(x, 0, Game.WIDTH - 38);
		y = Game.clamp(y, 0, Game.HEIGHT - 62);
		if(menu.toggleTrails == true) {
			if(HUD.HEALTH <= 0) handler.addObject(new Trail((int)x, (int)y, ID.Trail, Color.black, 32, 32, 0.08f, handler));
			else handler.addObject(new Trail((int)x, (int)y, ID.Trail, Color.white, 32, 32, 0.08f, handler));
		}
		collision();
	}
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++ ) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy || tempObject.getId() == ID.ReversePlayer || tempObject.getId() == ID.MirrorXPlayer || tempObject.getId() == ID.MirrorYPlayer) {
				if(getBounds().intersects(tempObject.getBounds())) {
					if(game.diff == 0)HUD.HEALTH--;
					else if(game.diff == 1)HUD.HEALTH -= 2;
				}
			}
			if(tempObject.getId() == ID.EnemyBoss) {
				if(getBounds().intersects(tempObject.getBounds())) {
					HUD.HEALTH -= 1000;
				}
			}
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, 32, 32);
	}
}
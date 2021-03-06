package state.workbench;

import static state.workbench.ButtonThemes.inv;
import graphics.entity.Entity;
import graphics.registry.SpriteAtlas;
import state.Mode;
import state.ModeManager;
import state.ui.Button;
import state.workbench.game.EditHistory;
import util.Grid;

public class ToolInitializer
{
	public static void init(Entity tools, SpriteAtlas sprites, Entity inventory, Entity partMounting, ModeManager manager, Mode wiring, Mode programming,EditHistory history)
	{
		Button[] toolButtons = new Button[10];
		
		new Grid(48,119,43,43,1,10).
		forEachWithIndicies((x2,y2,i,j)->{
			Button b = inv.build();
			b.setPos(x2, y2);
			toolButtons[i] = b;
			tools.addChild(b);
		});
	
		
		tools.addChild(new Entity(48,119,0,sprites.getSprite("icons.png")));
		
		
		toolButtons[0].setOnPress(()->inventory.setEnabled(true));
		toolButtons[1].setOnPress(()->partMounting.setEnabled(true));
		toolButtons[2].setOnPress(()->{
			manager.setMode(programming);
		});
		toolButtons[3].setOnPress(()->{
			manager.setMode(wiring);
		});
		toolButtons[7].setOnPress(history::undo);
		toolButtons[8].setOnPress(history::redo);
	}
}

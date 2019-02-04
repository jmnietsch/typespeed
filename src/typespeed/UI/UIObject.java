package typespeed.UI;

import typespeed.Game.GameObject;
import typespeed.Game.ObjectID;

public abstract class UIObject extends GameObject {

    @Override
    public int getZLayer() {
        return 10;
    }

    protected UIObject(ObjectID id) {
        super(id);
    }

}

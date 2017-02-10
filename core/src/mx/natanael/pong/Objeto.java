package mx.natanael.pong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Representa cualquier objeto como raqueta o pelota, dentro del juego
 * Created by Natanael on 31/01/2017.
 */

public class Objeto {
    protected Sprite sprite;

    public Objeto(Texture textura, float x, float y){
        sprite = new Sprite(textura);
        sprite.setPosition(x,y);
    }
}

package mx.natanael.pong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Natanael on 31/01/2017.
 */

public class Raqueta extends Objeto {

    public Raqueta(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public void dibujar(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void seguirPelota(Pelota pelota){
        sprite.setY(pelota.sprite.getY()-sprite.getHeight()/2+pelota.sprite.getHeight()/2);
    }
}

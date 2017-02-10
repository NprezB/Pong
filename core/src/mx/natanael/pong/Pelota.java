package mx.natanael.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Natanael on 31/01/2017.
 */

public class Pelota extends Objeto {
    private float DX = 4;
    private float DY = 4;

    private final float ALTO_MARGEN = 20;
    private final float ANCHO_RAQUETA = 20;
    private final float ALTO_RAQUETA = 100;

    public Pelota(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public void dibujar(SpriteBatch batch){
        sprite.draw(batch);
    }
    public void mover(Raqueta raqueta){
        float xp = sprite.getX();
        float yp = sprite.getY();

        if (xp >= PantallaJuego.Ancho){
            DX=-DX;
        }
        //Checar colisiones con la raqueta del jugador
        float xr = raqueta.sprite.getX();
        float yr = raqueta.sprite.getY();
        if(xp>=xr && xp <= (xr+ANCHO_RAQUETA) && yp >= yr && yp <= (yr+ALTO_RAQUETA-ALTO_MARGEN)){
            DX = -DX;
        }
        //Acelerar la pelota con un mejor algoritmo
        if(DX>=0){
            DX += Gdx.graphics.getDeltaTime()/5;
        }
        if(DY>=0){
            DY += Gdx.graphics.getDeltaTime()/5;
        }


        if (yp >= PantallaJuego.Alto ||yp<=0){
            DY=-DY;
        }

        sprite.setX(xp+DX);
        sprite.setY(yp+DY);
    }

    public void reset() {
        sprite.setPosition(PantallaJuego.Ancho/2,PantallaJuego.Alto/2);
        DX = 4;
        DY = 4;
    }
}

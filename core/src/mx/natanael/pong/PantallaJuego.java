package mx.natanael.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Natanael on 27/01/2017.
 */
public class PantallaJuego implements Screen
{
    private final Pong pong;

    public static final float Ancho = 1200;
    public static final float Alto = 800;

    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    private Texture texturaBarraHorizontal;
    private Texture texturaRaqueta;
    private Texture texturaCuadro;

    private Raqueta raquetaComputadora;
    private Raqueta raquetaJugador;
    private Pelota pelota;

    //Puntos del jugador y compu
    private int puntosJugador = 0;
    private int puntosMaquina = 0;
    private Texto texto;     // para poner msjs en la pantalla

    //Estado de juego...
    private Estado estado = Estado.Jugando;



    public PantallaJuego(Pong pong) {
        this.pong=pong;
    }

    @Override
    public void show() {
        camara = new OrthographicCamera(Ancho,Alto);
        camara.position.set(Ancho/2, Alto/2, 0);
        camara.update();

        vista = new StretchViewport(Ancho, Alto, camara);
        batch = new SpriteBatch();
        cargarTexturas();
        crearObjetos();
        // Indica quien atiende los eventos del touch
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void crearObjetos() {
        pelota = new Pelota(texturaCuadro,Ancho/2,Alto/2);
        raquetaComputadora = new Raqueta(texturaRaqueta, Ancho-texturaRaqueta.getWidth(),Alto/2);
        raquetaJugador = new Raqueta(texturaRaqueta, 0, Alto/2);
        texto = new Texto();
    }

    private void cargarTexturas() {
        texturaBarraHorizontal =  new Texture("1200x20.png");
        texturaRaqueta = new Texture("20x100.png");
        texturaCuadro = new Texture("20x20.png");
    }

    @Override
    public void render(float delta) {
        if(estado == Estado.Jugando){
            pelota.mover(raquetaJugador);
            raquetaComputadora.seguirPelota(pelota);
        }
        //Si pierde el jugador:
        if(estado==Estado.Jugando && pelota.sprite.getX()<=0){
            puntosMaquina++;
            if(puntosMaquina>=5){
                estado=Estado.Perdio;
            }
            pelota.sprite.setPosition(Ancho/2, Alto/2);
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaBarraHorizontal,0,0);
        batch.draw(texturaBarraHorizontal,0,Alto-texturaBarraHorizontal.getHeight());
        for(float y=0; y<Alto; y+=2*texturaCuadro.getHeight()){
            batch.draw(texturaCuadro,Ancho/2-texturaCuadro.getWidth()/2,y);
        }

        pelota.dibujar(batch);

        raquetaComputadora.dibujar(batch);
        raquetaJugador.dibujar(batch);

        //batch.draw(texturaRaqueta,0,Alto/2);
        //batch.draw(texturaRaqueta,Ancho-texturaRaqueta.getWidth(),Alto/2);

        // marcador
        texto.mostrarMensaje(batch, Integer.toString(puntosJugador), Ancho/2-Ancho/6, 3*Alto/4);
        texto.mostrarMensaje(batch, Integer.toString(puntosMaquina), Ancho/2+Ancho/6, 3*Alto/4);

        //Estado Perdio
        if(estado!=Estado.Jugando){
            texto.mostrarMensaje(batch, "Lo siento, perdiste",Ancho/2, Alto/2);
            texto.mostrarMensaje(batch, "Tap para continuar", 3*Ancho/4, Alto/4);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    class ProcesadorEntrada implements InputProcessor{

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            //Reiniciar el juego
            if(estado==Estado.Perdio){
                puntosMaquina=0;
                puntosJugador=0;
                pelota.reset();
                //pelota.sprite.setPosition(Ancho/2,Alto/2);
                estado = Estado.Jugando;
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            Vector3 v = new Vector3(screenX, screenY, 0);
            camara.unproject(v);
            raquetaJugador.sprite.setY(v.y);
            return true;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}

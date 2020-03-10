/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temporizador;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javax.swing.event.DocumentEvent;
import javax.xml.crypto.dsig.keyinfo.KeyValue;

/**
 *
 * @author rafae
 */
public class Temporizador extends Label {

    private int tiempo;
    private Integer i;
    private Timeline timeline;

    public Temporizador() {
        tiempo = 10;
    }

    public Temporizador(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo,boolean nuevoTiempo) {
        this.tiempo = tiempo;
        initTemporizador(nuevoTiempo);
    }

    public void initTemporizador(boolean nuevoTiempo) {
        i = tiempo;
        timeline = new Timeline();
        setText("Tiempo restante: "+i.toString()+" segundos");
        //add the keyframe to the timeline
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),new EventHandler() {
                    // KeyFrame event handler
                    @Override
                    public void handle(Event event) {
                        // update timerLabel
                        if (i == 0) {
                            timeline.stop();
                            fireEvent(new Event(EventType.ROOT));
                            i=-4;
                        } else if(i>=0){
                            i--;
                            setText("Tiempo restante: "+i.toString()+" segundos");
                        }
                        if(nuevoTiempo){
                            timeline.stop();
                        }
                    }
                }));

        timeline.play();
    }
}

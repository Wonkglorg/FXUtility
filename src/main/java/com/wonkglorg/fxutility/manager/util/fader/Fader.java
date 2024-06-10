package com.wonkglorg.fxutility.manager.util.fader;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

/**
 * Utility for fading panes in and out
 */
public class Fader {

    /**
     * Fades in a pane from a given direction, default duration of 0.5 seconds  the direction means what direction the pane will move in while fading
     *
     * @param pane      The pane to fade in
     * @param direction The direction to fade in from
     */
    public static void fadeIn(Pane pane, Direction direction) {
        fade(pane, direction, Fade.IN, 0.5);
    }

    /**
     * Fades out a pane in a given direction, default duration of 0.5 seconds  the direction means what direction the pane will move in while fading
     *
     * @param pane      The pane to fade out
     * @param direction The direction to fade out to
     */
    public static void fadeOut(Pane pane, Direction direction) {
        fade(pane, direction, Fade.OUT, 0.5);
    }

    /**
     * Fades a pane in or out from a given direction the direction means what direction the pane will move in while fading
     *
     * @param pane      The pane to fade
     * @param direction The direction to fade in from or out to
     * @param fade      The fade type (in or out)
     * @param duration  The duration of the fade
     */
    public static void fade(Pane pane, Direction direction, Fade fade, double duration) {
        new AnimationTimer() {
            private long lastTime = 0;
            private double opacity = (fade == Fade.IN) ? 0 : 1;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) / 1e9;
                lastTime = now;

                double deltaOpacity = fade.getValue() * deltaTime / duration;
                opacity = Math.max(0, Math.min(1, opacity + deltaOpacity));
                pane.setOpacity(opacity);

                switch (direction) {
                    case LEFT, RIGHT ->
                            pane.setTranslateX(fade.getValue() * direction.getTranslationX() * (pane.getWidth() / 2 * (1 - opacity)));
                    case UP, DOWN ->
                            pane.setTranslateY(fade.getValue() * direction.getTranslationY() * (pane.getHeight() / 2 * (1 - opacity)));
                }

                if ((fade == Fade.IN && opacity >= 1) || (fade == Fade.OUT && opacity <= 0)) {
                    pane.setOpacity((fade == Fade.IN) ? 1 : 0);
                    stop();
                }
            }
        }.start();
    }

}

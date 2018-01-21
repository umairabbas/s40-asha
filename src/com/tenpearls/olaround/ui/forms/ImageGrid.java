/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners. 
 * See LICENSE.TXT for license information.
 */
package com.tenpearls.olaround.ui.forms;

import com.nokia.lwuit.GestureHandler;
import com.nokia.mid.ui.gestures.GestureEvent;
import com.nokia.mid.ui.gestures.GestureInteractiveZone;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.Painter;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.geom.Rectangle;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.ui.components.ImageCell;
import com.tenpearls.olaround.ui.components.ImageGridLayout;

public class ImageGrid extends Form {

    private boolean editMode;
    private Command backCommand;
    private Command doneCommand;

    public ImageGrid() {
    	setTitle(ApplicationConstants.TITLE_SELECT_PHOTO);
        setLayout(new ImageGridLayout(2));

        ImageGridGestureHandler gestureHandler = new ImageGridGestureHandler();
        GestureHandler.setFormGestureHandler(this, gestureHandler);

        doneCommand = new Command("Done") {
            public void actionPerformed(ActionEvent e) {
                setEditMode(false);
            }
        };
        backCommand = new Command("Back") {
            public void actionPerformed(ActionEvent e) {
               OlaroundMidlet.getInstance().destoryGalleryForm();
               OlaroundMidlet.getInstance().showSignUpStep1Form(null, false);
            }
        };
        setBackCommand(backCommand);

        getContentPane().setDropTarget(true);
    }

    /**
     * Enables / disables edit mode where sub-components can be re-ordered by
     * dragging.
     *
     * @param editMode whether edit mode is enabled or disabled
     */
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;

        if (editMode) {
            setComponentsDraggable(true);
            setGlassPane(new EditModeGlassPane());
            removeCommand(backCommand);
            addCommand(doneCommand);
            setDefaultCommand(doneCommand);
            repaint();
        } else {
            setComponentsDraggable(false);
            setGlassPane(null);
            setDefaultCommand(null);
            removeCommand(doneCommand);
            setBackCommand(backCommand);
        }
    }

    /**
     * Sets all sub-components draggable or non-draggable
     *
     * @param draggable whether sub-components are set draggable or
     * non-draggable
     */
    private void setComponentsDraggable(boolean draggable) {
        for (int i = 0; i < getContentPane().getComponentCount(); i++) {
            getContentPane().getComponentAt(i).setDraggable(draggable);
        }
    }

    /**
     * Creates a fade transition to new
     * <code>ImageView</code>.
     *
     * @param image the image to be shown.
     */
    private void viewImage(Image image) {
        Transition t = CommonTransitions.createFade(500);
        setTransitionInAnimator(t);
        ImageView imageView = new ImageView(image, this);
        imageView.setTransitionInAnimator(t);
        imageView.show();
    }

    private class ImageGridGestureHandler extends GestureHandler {

        public void gestureAction(GestureEvent ge) {

            switch (ge.getType()) {
                case GestureInteractiveZone.GESTURE_TAP: {
                    Component c = getComponentAt(ge.getStartX(), ge.getStartY());
                    if (c != null && !editMode && c instanceof ImageCell) {
                        ImageCell ic = (ImageCell) c;
                        viewImage(ic.getImage());
                    }
                    break;
                }
                case GestureInteractiveZone.GESTURE_LONG_PRESS: {
                    if (!editMode) {
                        setEditMode(true);
                    }
                    break;
                }
            }
        }
    }

    private class EditModeGlassPane implements Painter {

        public void paint(Graphics g, Rectangle r) {
            // Fill transparent blue rectangle over the grid
            g.setColor(0x0000ff);
            g.setAlpha(30);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author lucas
 */
public class MainPanel extends JPanel {

    private Icon image;
    private Icon imageOld;
    private Point imageLocation;
    private Dimension imageSize;
    private Point targetLocation = new Point(35, 0);
    private Dimension targetSize = new Dimension(250, 250);

    public MainPanel() {
        setOpaque(false);
    }

    /**
     * @return the image
     */
    public Icon getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Icon image) {
        this.image = image;
    }

    /**
     * @return the imageOld
     */
    public Icon getImageOld() {
        return imageOld;
    }

    /**
     * @param imageOld the imageOld to set
     */
    public void setImageOld(Icon imageOld) {
        this.imageOld = imageOld;
    }

    /**
     * @return the imageLocation
     */
    public Point getImageLocation() {
        return imageLocation;
    }

    /**
     * @param imageLocation the imageLocation to set
     */
    public void setImageLocation(Point imageLocation) {
        this.imageLocation = imageLocation;
        repaint();
    }

    /**
     * @return the imageSize
     */
    public Dimension getImageSize() {
        return imageSize;
    }

    /**
     * @param imageSize the imageSize to set
     */
    public void setImageSize(Dimension imageSize) {
        this.imageSize = imageSize;
    }

    /**
     * @return the targetLocation
     */
    public Point getTargetLocation() {
        return targetLocation;
    }

    /**
     * @param targetLocation the targetLocation to set
     */
    public void setTargetLocation(Point targetLocation) {
        this.targetLocation = targetLocation;
    }

    /**
     * @return the targetSize
     */
    public Dimension getTargetSize() {
        return targetSize;
    }

    /**
     * @param targetSize the targetSize to set
     */
    public void setTargetSize(Dimension targetSize) {
        this.targetSize = targetSize;
    }

}

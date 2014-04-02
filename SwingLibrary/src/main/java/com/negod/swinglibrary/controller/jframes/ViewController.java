/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller.jframes;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public interface ViewController<T extends Enum<T>, F extends NegodJFrame> {

    public void start(final T jFrameKey);

    public void close(NegodJFrame frame);
}

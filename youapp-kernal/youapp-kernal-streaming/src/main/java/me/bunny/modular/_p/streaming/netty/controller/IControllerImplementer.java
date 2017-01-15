package me.bunny.modular._p.streaming.netty.controller;

/**
 * all controller interface must extend this , in the way expose its implementer.
 * @author JIAZJ
 *
 * @param <T> concrete implementer
 */
public interface IControllerImplementer<T extends IControllerImplementer<T>> extends ControllerService {

}

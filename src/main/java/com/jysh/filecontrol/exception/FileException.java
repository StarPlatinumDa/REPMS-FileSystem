package com.jysh.filecontrol.exception;

/**
 * @author flamemaster
 * @date 2020/12/9
 */
public class FileException extends RuntimeException{
    //调用父类的构造函数   给父类的参数赋值
    public FileException(String message){
        super(message);
    }
    public FileException(String message,Throwable cause){
        super(message,cause);
    }
}

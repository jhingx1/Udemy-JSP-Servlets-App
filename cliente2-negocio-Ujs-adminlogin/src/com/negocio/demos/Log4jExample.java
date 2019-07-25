package com.negocio.demos;

import org.apache.log4j.Logger;

import java.io.*;
import java.sql.SQLException;


public class Log4jExample{

   /* Get actual class name to be printed on */
   static Logger log = Logger.getLogger(Log4jExample.class.getName());
   
   public static void main(String[] args)throws IOException,SQLException{
      
	  log.debug("Hello this is a debug message");
      log.info("Hello this is an info message");
      
      log.trace("Trace Message!");
      log.debug("Debug Message!");
      log.info("Info Message!");
      log.warn("Warn Message!");
      log.error("Error Message!");
      log.fatal("Fatal Message!");
      
      
   }
}
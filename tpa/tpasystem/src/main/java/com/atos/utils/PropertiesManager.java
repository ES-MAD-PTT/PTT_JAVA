package com.atos.utils;


 /**
  This class provides a assembly of methods for the processing and load of parameters,
  that will be stored and treated in objects of type properties.
  */

final public class PropertiesManager {

 /**
  * This class contains only static methods, so it shouldn't be instanced
  */

  private PropertiesManager() {

  } // END constructor

  /**
   * Method that reads of .properties incializacion parameters
   * @param <B>theFile</B> file name .properties
   * @exception java.io.IOException it sends an exception if there is some error
   *  treating .properties file
   * @return object properties with the data of the file
   */

  public static java.util.Properties getPropertiesFromFile (String theFile)
    throws java.io.IOException {
    java.io.InputStream isProps = Thread.currentThread().getContextClassLoader().getResourceAsStream(theFile);
    // lee parametros del fichero .properties y lo cierra
    java.util.Properties props = new java.util.Properties();
    if (isProps != null) {
      props.load(isProps);
      isProps.close();
      return props;
    }
    else {
      return null;
    }

  } //END getPropertiesFromFile

  /**
   * Method that adds to the data of an object Properties in another one.
   * @param <B>theParametersOut</B> object properties destiny 
   * @param <B>theParametersIn</B> object properties origin
   * @exception java.lang.Exception if there is error treating the objects properties
   */

  public static void appendProperties (java.util.Properties thePropertiesOut,
                                       java.util.Properties thePropertiesIn)
    throws Exception {

    java.util.Enumeration params = thePropertiesIn.propertyNames();
    String param = null;

    while (params.hasMoreElements()) {
      param = (String) params.nextElement();
      thePropertiesOut.put(param,thePropertiesIn.getProperty(param));
    }
  }  // END appendProperties

} // END Properties

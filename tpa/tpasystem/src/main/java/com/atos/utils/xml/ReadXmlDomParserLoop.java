package com.atos.utils.xml;



import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReadXmlDomParserLoop {

  public static void main(String[] args) {

	  
      // Instantiate the Factory
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

      try (InputStream is = readXmlFileIntoInputStream("TPA.xml")) {

          // parse XML file
          DocumentBuilder db = dbf.newDocumentBuilder();

          // read from a project's resources folder
          Document doc = db.parse(is);

          System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
          System.out.println("------");

          FileWriter myWriter = new FileWriter("c:/temp/salida.txt");
          if (doc.hasChildNodes()) {
              printNote(doc.getChildNodes(),myWriter);
          }
          myWriter.close();

      } catch (ParserConfigurationException | SAXException | IOException e) {
          e.printStackTrace();
      }

  }

  private static void printNote(NodeList nodeList, FileWriter myWriter) throws IOException {

      for (int count = 0; count < nodeList.getLength(); count++) {

          Node tempNode = nodeList.item(count);

          // make sure it's element node.
          if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

        	 // if(tempNode.getNodeName().equals("kml") ||tempNode.getNodeName().equals("Document") ||tempNode.getNodeName().equals("Folder") ||tempNode.getNodeName().equals("Placemark") ||tempNode.getNodeName().equals("Point") ||tempNode.getNodeName().equals("LineString") ||tempNode.getNodeName().equals("name")||tempNode.getNodeName().equals("coordinates")) {

        		if(tempNode.getNodeName().equals("name")){
        			myWriter.write(tempNode.getTextContent().trim() +"\n");
        		}
        		if(tempNode.getNodeName().equals("coordinates")) {
        			//myWriter.write(tempNode.getNodeName());
        			changeCoordinates(tempNode.getTextContent(),  myWriter);
        			
        			/*System.out.print(tempNode.getNodeName() );
    	            System.out.println(tempNode.getTextContent());*/
        		}
        		  // get node name and value
	           //   System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
	            //  System.out.println("Node Value =" + tempNode.getTextContent());
	
	              if (tempNode.hasAttributes()) {
	
	                  // get attributes names and values
	/*                  NamedNodeMap nodeMap = tempNode.getAttributes();
	                  for (int i = 0; i < nodeMap.getLength(); i++) {
	                      Node node = nodeMap.item(i);
	                      System.out.println("attr name : " + node.getNodeName());
	                      System.out.println("attr value : " + node.getNodeValue());
	                  }*/
	
	              }
	
	              if (tempNode.hasChildNodes()) {
	                  // loop again if has child nodes
	                  printNote(tempNode.getChildNodes(),myWriter);
	              }
	
	             // System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
        	//  }
          }

      }

  }

  // read file from project resource's folder.
  private static InputStream readXmlFileIntoInputStream(final String fileName) {
      return ReadXmlDomParserLoop.class.getClassLoader().getResourceAsStream(fileName);
  }

  private static void changeCoordinates(String coordinates, FileWriter myWriter) throws IOException {
	  ArrayList<String> longitud = new ArrayList<String>(),latitud = new ArrayList<String>();
	  StringBuffer lon = new StringBuffer(),lat = new StringBuffer();
	  StringTokenizer st = new StringTokenizer(coordinates, " ");
	  while(st.hasMoreTokens()) {
		  String c2 = st.nextToken();
		  if(c2==null || c2.equals("") || c2.equals(" ")) {
			  continue;
		  }
		  StringTokenizer st2 = new StringTokenizer(c2, ",");
		  if(st2.countTokens()==0) {
			  continue;
		  }
		  
		  try {
			  String l2 = st2.nextToken();
			  String la2 = st2.nextToken();
			  System.out.println(l2+","+la2);
			  longitud.add(l2);
			  latitud.add(la2);
		  } catch(Exception e) {
			  System.out.println("Error: "+c2);
		  }
	  }
	  System.out.println("longitud: " + longitud.size() + ", latitud: " + latitud.size());
	  for(int i=0;i<longitud.size();i++) {
		  if(i==0) {
			  lon.append(longitud.get(i));
			  lat.append(latitud.get(i));
		  } else {
			  lon.append(",").append(longitud.get(i));
			  lat.append(",").append(latitud.get(i));
		  }
	  }
	  
	  myWriter.write("lon="+lon.toString().trim()+"\n");
	  myWriter.write("lat="+lat.toString().trim() + "\n");
  }
}
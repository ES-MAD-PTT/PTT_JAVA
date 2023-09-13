package com.atos.utils;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.atos.beans.OpTemplateBean;
import com.atos.beans.XMLMapBean;
import com.atos.beans.XMLMapBlockBean;
import com.atos.beans.XMLMapItemBean;
import com.atos.beans.XMLMapSheetBean;
import com.atos.exceptions.ValidationException;
import com.atos.mapper.utils.Xlsx2XmlMapper;


public class Xlsx2XmlConverter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7725209180694095278L;

	private String categoryCode = null; // Valores posibles: campo CATEGORY_CODE de la tabla TPA_TOPERATION_CATEGORY
										// "CONTRACT", "NOMINATION", "FORECASTING"
	private String termCode = null; 	// Valores posibles: campo TERM_CODE de la tabla TPA_TOPERATION_TERM
										// "DAILY", "WEEKLY", "SHORT", "SHORT_FIRM", "SHORT_NON_FIRM", "MEDIUM", "LONG"
	private String fileType = null; 	// Valores posibles: constrait de la tabla TPA_TOPERATION_TEMPLATE.
										// "ORIGINAL", "INTERMEDIATE", aunque los original no estan pensados para ser "parseados"
	private BigDecimal systemId = null;  // Id en BD correspondiente a onshore u offshore.
	
	// Guarda los datos de configuracion de mapeo excel -> xml.
	private TreeMap<Integer, XMLMapSheetBean> tmConfigSheets = null;
	private String xmlRootTag = null;
	private static final int xlsNoBlockId = -1;			// Indica que al recorrer las filas de la excel no se ha entrado en ningun bloque todavia.

	
	// ESTOS ARRAYS CON OFFSETS solo se pueden usar si se quieren parsear todas las sheets de la excel de la misma forma. 
	// (No hay un array para cada sheet, sino que se comparten).
	
	// Para especificar las lineas que separan la marca de start de bloque y el comienzo de los datos.
	// Segun las posiciones del array, se podrian definir en la excel hasta 30 bloques distintos. No mas.
	private int[] aiStartRowOffset = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	// Si no existiera marca de fin de bloque, se puede especificar aqui que se limite por offset desde la fila header, en lugar de buscar marca.
	// Valores:
	// -1 indica que no se tenga en cuenta, y que se busque la marca de End.
	// 1  indica que la fila de fin de bloque (no incluida en el bloque) es igual a la siguiente de inicio de bloque (si incluida en el bloque),
	// 		de manera que el bloque solo tendra una linea.
	private int[] aiExitRowOffsetFromHeaderRow = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

	// Para especificar las lineas que separan la marca de start de bloque y el comienzo de los datos de HeaderTag.
	private int[] aiHeaderTagRowOffset = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


	private static String strValueTag = "value";
	private static final String strXMLDateFormat = "dd/MM/yyyy"; 
	// Formato de fechas de la excel que se ha detectado hasta ahora.
	// ESTOS dos arrays deben tener el mismo num de elementos.
 	// Como POI define el formato de fecha de la celda excel.
	private static String[] asExcelDateFormat_ExcelPatterns = {"m/d/yy", "[$-409]mmm/yy;@", "[$-409]d/mmm/yy;@", "[$-409]mmm\\-yy;@"};
	// Como SimpleDateFormat define el formato de fecha.
	private static String[] asExcelDateFormat_JavaPatterns = {"M/d/yy", "MMM/yy", "d/MMM/yy", "MMM-yy"};

	// Para sustituir en mensajes de error.
	private static final String strBlockTagLabel = "XXX";
	private static final String strSheetLabel = "YYY";	
	
	private Xlsx2XmlMapper xMapper;



	private static final Logger log = LogManager.getLogger("com.atos.utils.Xlsx2XmlConverter");
	

	// getters/setters
	public void setAiStartRowOffset(int[] aiStartRowOffset) {
		this.aiStartRowOffset = aiStartRowOffset;
	}
	
	public void setAiExitRowOffsetFromHeaderRow(int[] aiExitRowOffsetFromHeaderRow) {
		this.aiExitRowOffsetFromHeaderRow = aiExitRowOffsetFromHeaderRow;
	}
	
	public void setAiHeaderTagRowOffset(int[] aiHeaderTagRowOffset) {
		this.aiHeaderTagRowOffset = aiHeaderTagRowOffset;
	}
	
	public void setxMapper(Xlsx2XmlMapper xMapper) {
		this.xMapper = xMapper;
	}

	public Xlsx2XmlConverter() {
		super();
	}

	
	// En principio, solo se van a parsear ficheros excel intermediate. Por si acaso fuera necesario otro valor,
	// se deja tambien el metodo init de mas abajo. 
	public void init( String _catCode, String _termCode, BigDecimal _systemId ) throws Exception {
		
		init(_catCode, _termCode, "INTERMEDIATE", _systemId);
	}
	

	// Este metodo carga en la variable tmConfigSheets la configuracion que hay en base de datos, 
	// relativa a parseo de fichero excel para generar un fichero xml.
	public void init( String _catCode, String _termCode, String _fileType, BigDecimal _systemId ) throws Exception {

		// Solo se realiza la inicializacion una vez. Si ya estan cargadas las variables miembro, no se vuelven a cargar.
		if( this.categoryCode != null )
			return;
		
		this.categoryCode = _catCode;
		this.termCode = _termCode;
		this.fileType = _fileType;
		this.systemId = _systemId;

		try {
			// Guarda el fichero de template, con el dato de XmlMapId (identifica los datos de configuracion de mapeo excel -> xml).
			OpTemplateBean otbXLSTemplate = null;

			// Se obtiene el template
			OpTemplateBean otbEntrada = new OpTemplateBean();
			otbEntrada.setOpCategoryCode(categoryCode);
			otbEntrada.setOpTermCode(termCode);
			otbEntrada.setFileType(fileType);
			otbEntrada.setSystemId(systemId);
		
			List<OpTemplateBean> lotbSalida = xMapper.getOpTemplateByCatTermFiletypeSystem(otbEntrada);
			// Solo debe haber un fichero template con las condiciones anteriores, asi que se coge el primero de la lista.
			otbXLSTemplate = lotbSalida.get(0);

			// Se obtiene el rootTag.
			XMLMapBean xmbEntrada = new XMLMapBean();
			xmbEntrada.setXmlMapId(otbXLSTemplate.getXmlMapId());
			List<XMLMapBean> lxmbSalida = xMapper.getXMLMapByXMLMapId(xmbEntrada);
			// Solo habra un xmlMap porque se busca por el identificador de la tabla.
			xmlRootTag = lxmbSalida.get(0).getRootTag();
			
			// Se obtienen los sheets.
			tmConfigSheets = new TreeMap<Integer, XMLMapSheetBean>();
			XMLMapSheetBean xmsbEntrada = new XMLMapSheetBean();
			xmsbEntrada.setXmlMapId(otbXLSTemplate.getXmlMapId());
			List<XMLMapSheetBean> lxmsbSalida = xMapper.getXMLMapSheetByXMLMapId(xmsbEntrada);
			for(XMLMapSheetBean sheetBean : lxmsbSalida) {
				if (sheetBean == null) continue;

				// Se obtienen los blocks de cada sheet.
				TreeMap<Integer, XMLMapBlockBean> tmConfigBlocks = new TreeMap<Integer, XMLMapBlockBean>();
				XMLMapBlockBean xmbbEntrada = new XMLMapBlockBean();
				xmbbEntrada.setXmlMapSheetId(sheetBean.getXmlMapSheetId());
				List<XMLMapBlockBean> lxmbbSalida = xMapper.getXMLMapBlockByXMLMapSheetId(xmbbEntrada);				
				for(XMLMapBlockBean blockBean : lxmbbSalida){
					if( blockBean == null ) continue;
					
					// Se obtienen los items por cada block.
					TreeMap<Integer, XMLMapItemBean> tmConfigItems = new TreeMap<Integer, XMLMapItemBean>();
					XMLMapItemBean xmibEntrada = new XMLMapItemBean();
					xmibEntrada.setXmlMapBlockId(blockBean.getXmlMapBlockId());
					List<XMLMapItemBean> lxmibSalida = xMapper.getXMLMapItemByXMLMapBlockId(xmibEntrada);				
					for(XMLMapItemBean itemBean : lxmibSalida){
						if( itemBean == null ) continue;
						
						// Los items se ordenan por id de columna. El orden de item no se usa por ahora.
						tmConfigItems.put(itemBean.getColumnId(), itemBean);
					}
					// Se guarda el treemap de bloques en la sheet.
					blockBean.setTmConfigItems(tmConfigItems);

					// Los bloques es ordenan por orden de bloque.
					tmConfigBlocks.put(blockBean.getBlockOrder(), blockBean);
				}
				// Se guarda el treemap de bloques en la sheet.
				sheetBean.setTmConfigBlocks(tmConfigBlocks);
				
				// Se guarda cada sheet por orden de sheet.
				tmConfigSheets.put(sheetBean.getSheetNumber(), sheetBean);
			}

        	log.debug("Configuration info from DB. xmlRootTag: " + xmlRootTag);
        	log.debug("Configuration info from DB. tmConfigSheets: " + tmConfigSheets.toString());
		}
        catch (Exception e) 
        {
        	log.error(e.getMessage(), e);
            throw e;
        }	
	}

	
	public String getXMLFromExcel( byte[] binaryData ) throws Exception {

    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		String xml = null;
		final int initialRowId = 1000000;
    	// Se definen aqui estas variables para, en caso de excepcion, indicar la celda en que se ha producido.
    	int rowId = -1;
        int colId = -1; 
        String cleanValue;
        XSSFWorkbook workbook = null;
        
        try
        {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(xmlRootTag);
			doc.appendChild(rootElement);

			Element eNivel0 = null;		// Para nodo sheet, si lo hubiera
			Element eNivel1 = null;		// Para nodo block
			Element eNivel2 = null;		// Para nodo grouptag, si lo hubiera
        	int iGroupTagCount = 0;		// Contador de items en grupos, entre iteraciones de columnas.
			Element eNivel3 = null;		// Para nodo item
			Element eNivel4_1 = null;	// Para nodo headertag, si lo hubiera
			Element eNivel4_2 = null;	// Para nodo headertag - value, si lo hubiera
			Element eNivel4_3 = null;	// Para nodo headertag - label, si lo hubiera
			
        	workbook = new XSSFWorkbook(new ByteArrayInputStream(binaryData));

        	// Se analizan todas las hojas de la excel que se indique por configuracion.
        	for (int sheetId: tmConfigSheets.keySet())
        	{
        		// Datos de configuracion
        		XMLMapSheetBean xmsbConfigSheet = tmConfigSheets.get(sheetId);
	            // Se toma la hoja excel que corresponda a partir del nombre configurado.
        		XSSFSheet sheet = workbook.getSheet(xmsbConfigSheet.getSheetName());
        		// Si no se puede encontrar alguna de los nombres de sheets que estan configurados en BD, se devuelve un error
        		// y no se genera fichero xml. Se produce este error cuando se sube una excel que corresponde a otra pantalla (long, medium, short, short non firm) 
        		if( sheet == null )
        			throw new ValidationException(msgs.getString("wrong_file_format"));
        		
	            if(xmsbConfigSheet.getSheetTag() != null){
	            	eNivel0= doc.createElement(xmsbConfigSheet.getSheetTag());
	            }
	            
	            int numBlocksInSheet = xmsbConfigSheet.getTmConfigBlocks().keySet().size();
	            
	    		// Las filas incluidas en este array estaran incluidas en el bloque. Seran la primera fila del bloque.
	            int[] blockStartRowIds = new int[numBlocksInSheet];
	    		// Las filas incluidas en este array NO estaran incluidas en el bloque. Seran la primera fila despues del bloque.	            
	            int[] blockEndRowIds = new int[numBlocksInSheet];
	            // Se establecen mas abajo, cuando se van reconociendo las marcas.
                for(int i=0; i<numBlocksInSheet; i++)
                {
                	blockStartRowIds[i] = initialRowId;
                	blockEndRowIds[i] = initialRowId;
                }	            

	            // Marca el bloque correspondiente a la linea que se esta leyendo. Comienza por cero.
	            int blockId = xlsNoBlockId;

	            // Se define un arraylist de Hashtable, un Hashtable para cada bloque.
	            // En cada hashtable se guardara el valor correspondientes al headerTag (normalmente fechas) para cada columna (con headerTag) de un bloque.
	            ArrayList<Hashtable<Integer, String>> lHeaderTagValues = new ArrayList<Hashtable<Integer, String>>();
	    		// Las filas incluidas en este array indican donde se encuentra el valor de headerTag, por cada bloque.
	            int[] headerTagRowIds = new int[numBlocksInSheet];
                for(int i=0; i<numBlocksInSheet; i++)
                {
                	Hashtable<Integer, String> ht = new Hashtable<Integer, String>();
                	lHeaderTagValues.add(i, ht);
                	headerTagRowIds[i] = initialRowId;
                }	

                
	            // BUCLE DE FILAS. Iterate through each rows one by one
	            Iterator<Row> rowIterator = sheet.iterator();
	            row_loop:
	            while (rowIterator.hasNext()) 
	            {
	            	XSSFRow row = (XSSFRow) rowIterator.next();
	                rowId = row.getRowNum();	//  row number (0 based)                
	                
	                // Comprobamos si hemos reiniciado el nivel de grouptag. 
	                // Si no esta reiniciado, es que hemos terminado antes porque
	                // faltan valores, asi que lo tenemos que guardar igualmente
	                if(eNivel2!=null) {
		                eNivel1.appendChild(eNivel2);	        
	            		// Se resetean eNivel2 y el contador, para cuando haya que definir otro grupo.
	        			eNivel2 = null;
	                }
	                // En cada fila, se inicializa el contador de grupos.
	                iGroupTagCount = 0;
              
	            	// En cada iteracion de fila, compruebo si tengo seccion asignada.
	                // Cuidado que al principio no estaran definidos los indicadores hasta que no se hayan encontrado cada marca.
	                blockId_search_loop:
	                for(int i=0; i<numBlocksInSheet; i++)
	                {
	                	if( rowId >= blockStartRowIds[i] && (rowId < blockEndRowIds[i]) ) {
	                		blockId = i;
	                		break blockId_search_loop;
	                		// Si localizo que estoy en un bloque, me quedo con ese valor y salgo.
	                		// (para no machacar el blockId con la siguiente iteracion de busqueda).
	                	}
		                else {
		                	blockId = xlsNoBlockId;
		                   	// En el caso de que no tenga seccion, sigo adelante, para ver si se puede encontrar la marca en esta fila.
		                }
	                }
	                	
	                // Datos de configuracion
	                XMLMapBlockBean xmbbConfigBlock = null;
	                if(blockId != xlsNoBlockId){
	            		// Datos de configuracion
	            		xmbbConfigBlock = xmsbConfigSheet.getTmConfigBlocks().get(blockId);	            		
	                	eNivel1= doc.createElement(xmbbConfigBlock.getBlockTag());

	                }
	                else {
    	            	eNivel1 = null;					// Para que no se pueda reutilizar de la iteracion anterior.
	                }
	                
	            	
	                // BUCLE DE COLUMNAS. For each row, iterate through all the columns
	                Iterator<Cell> cellIterator = row.cellIterator();
	                column_loop:
	                while (cellIterator.hasNext()) 
	                {
	                	XSSFCell cell = (XSSFCell) cellIterator.next();
	                    colId = cell.getColumnIndex(); // one-based column index
	                    
	            		// Datos de configuracion.
	                    XMLMapItemBean xmibConfigItem = null;
	                    if( xmbbConfigBlock != null)
	                    	xmibConfigItem = xmbbConfigBlock.getTmConfigItems().get(colId);	 
	            		
	                    String tmpTag;
	                    String value = getValueFromCell(cell);
	                    
	                    // Esta comprobacion puede que nunca se llegue a dar porque entre antes por CELL_TYPE_BLANK (default).
	                    // Si la celda no tiene valor en la primera columna, no se lee la fila.
	                    // Si no es primera columna se sigue leyendo la siguiente celda (columna) y no se generara tag en xml.
	                    // Para ser coherente, se debe seguir el mismo tratamiento que arriba CELL_TYPE_BLANK.
	                    if( (value == null) || ("".equalsIgnoreCase(value)) ) {
	                    	// Aunque el valor de la primera columna sea nulo, sigo procesando celdas de la fila, porque las marcas
	                    	// de principio o fin de bloque podria estar a mitad de fila.
	                    	//if(colId == 0)
	                    	//	continue row_loop;
	                    	// else {
	                        	// Antes de procesar la siguiente celda, hay que actualizar el contador de grupo, si se esta utilizando.
	                        	if ( (xmbbConfigBlock != null) &&
	                        			(xmibConfigItem != null) &&
	                        			(xmibConfigItem.getGroupTagCount() >0) ) {
	    	                		iGroupTagCount++;
	    	                		
	    	                		// Si fuera el ultimo elemento del grupo, hay que añadir el grupo al bloque.
	    	                		if(iGroupTagCount == xmibConfigItem.getGroupTagCount()){
	    	                			
	    	                			// Podria ocurrir que no se hubiera generado bloque o grupo.
	    	                			if( eNivel1 != null && eNivel2 != null){
	    	                				eNivel1.appendChild(eNivel2);
	    	                			}
	    	                					        
	    		                		// Se resetean eNivel2 y el contador, para cuando haya que definir otro grupo.
	    	                			eNivel2 = null;				
	    	                			iGroupTagCount = 0;
	    	                		}
	                        	}
	                    		continue column_loop;
	                    	// }
	                    }
	                   
	                    // Se quitan retornos de carro, espacios al principio y final, y se deja en mayusculas.
	                    cleanValue = value.replaceAll("(\\r|\\n)", "").trim().toUpperCase();
	                    
	
	                    /* COMPROBACION DE MARCAS */
	                    // Se buscan las marcas de bloques, solo 1 vez. Si se encuentra la marca no se procesa la fila porque no son datos.
	                    // Se itera por el numero de bloques.
	                    // Se busca en todos los bloques de la sheet, teniendo en cuenta que todavia al recorrer la hoja excel todavia no hemos
	                    // entrado en el bloque, sino que estamos identificando las filas limite. 
	                    for( int i=0; i<numBlocksInSheet ; i++){

	                    	if( blockStartRowIds[i] == initialRowId ) {
	                    		
	                    		String tmpStartLimit = xmsbConfigSheet.getTmConfigBlocks().get(i).getStartLimit();	// Por si no se definio startlimit.
	                    		if (tmpStartLimit != null && cleanValue.contains(tmpStartLimit.toUpperCase()) ) {
		                    		blockStartRowIds[i] = rowId + 1 + aiStartRowOffset[i];
		                    		
		                    		// Si encuentro la marca start de un bloque y no he encontrado la marca end del bloque anterior, 
		                    		// pongo la marca end del bloque anterior, para que se pase de seccion.
			                    	if( (i > 0) && (blockEndRowIds[i-1] == initialRowId))
			                    		blockEndRowIds[i-1] = rowId;
	
			                    	// Una vez encontrada la marca de inicio de bloque se calcula la fila con los datos de headerTag.
			                    	headerTagRowIds[i] = rowId + aiHeaderTagRowOffset[i];
			        	            
		                    		continue row_loop;
		                    	}
	                    	}	                    	
	                    	
	                    	// Solo se establece el final del bloque 1 vez. Una vez establecido no se vuelve a comprobar.
	                    	if( blockEndRowIds[i] == initialRowId ) {
	                    		
	                    		if(aiExitRowOffsetFromHeaderRow[i] == -1) {		// Si no se ha definido este offset, se busca marca de Exit.
	
	                    			String tmpEndLimit = xmsbConfigSheet.getTmConfigBlocks().get(i).getEndLimit();	// Por si no se definio endlimit.
		                    		if( tmpEndLimit != null && cleanValue.contains(tmpEndLimit.toUpperCase()) ) {
			                    		blockEndRowIds[i] = rowId;
			                    		
				                    	// Si estoy en la marca final del ultimo bloque, puedo salir del bucle de filas, y no procesar más filas.
			                    		if( i < (numBlocksInSheet - 1) )
			                    			continue row_loop;
			                    		else
			                    			break row_loop;
		                    		}	                    			
	                    		} else {										// Si se ha definido este offset, se añade al de la fila de start.
	                    														// Si no existe fila de estar, no se hace nada. Se sigue adelante, 
	                    														// hasta que se haya definido. 
	                    			
	                    			if( blockStartRowIds[i] != initialRowId )
	                    			{
	                    				blockEndRowIds[i] = blockStartRowIds[i] + aiExitRowOffsetFromHeaderRow[i];
	                    			}
	                    		}
	                    	}
	                    } /* FIN COMPROBACION DE MARCAS */
	                    
	                    // En cuanto actualizo las marcas tambien vuelvo a comprobar el bloque en que estoy.
		                blockId_search_loop2:
		                for(int i=0; i<numBlocksInSheet; i++)
		                {
		                	if( rowId >= blockStartRowIds[i] && (rowId < blockEndRowIds[i]) ) {
		                		blockId = i;
		                		break blockId_search_loop2;
		                		// Si localizo que estoy en un bloque, me quedo con ese valor y salgo.
		                		// (para no machacar el blockId con la siguiente iteracion de busqueda).
		                	}
			                else {
			                	blockId = xlsNoBlockId;
			                   	// En el caso de que no tenga seccion, sigo adelante, para ver si se puede encontrar la marca en esta fila.
			                }
		                }
	
	                    // En el caso de que no tenga seccion, se pasa a la siguiente iteracion, porque esta era para buscar una marca,
	                    // no para guardar datos en el xml.
	                    if( blockId == xlsNoBlockId )
	                    	continue row_loop;

	        			
	                    /* OBTENCION DE DATOS PARA XML */
	                    // Podria no existir xmibConfigItem, si en un bloque con datos no hay configurado una columna. 
	                    // Si no se comprueba dara error, porque hay dato en la excel, pero no esta configurado que se recoja un tag para el xml.
	                    // Si no existe el item se busca el dato de la siguiente celda (columna).
	                    tmpTag = null;
	                    if( xmibConfigItem != null)
	                    	tmpTag = xmibConfigItem.getItemTag();

	                	// Si no encuentro un tag, sigo con la siguiente celda.
	                	if ( tmpTag == null ) {
                        	// Antes de procesar la siguiente celda, hay que actualizar el contador de grupo, si se esta utilizando.
                        	if ( (xmbbConfigBlock != null) &&
                        			(xmibConfigItem != null) &&
                        			(xmibConfigItem.getGroupTagCount() >0) ) {
    	                		iGroupTagCount++;
    	                		
    	                		// Si fuera el ultimo elemento del grupo, hay que añadir el grupo al bloque.
    	                		if(iGroupTagCount == xmibConfigItem.getGroupTagCount()){

    	                			// Podria ocurrir que no se hubiera generado bloque o grupo.
    	                			if( eNivel1 != null && eNivel2 != null)
    	                				eNivel1.appendChild(eNivel2);	        
    		                		// Se resetean eNivel2 y el contador, para cuando haya que definir otro grupo.
    	                			eNivel2 = null;				
    	                			iGroupTagCount = 0;
    	                		}
                        	}
                    		continue column_loop;
                    	}

	                	// NIVELES DEL XML.
	                	//Element eNivel0 = null;		// Para nodo sheet, si lo hubiera
	                	//Element eNivel1 = null;		// Para nodo block
	                	//Element eNivel2 = null;		// Para nodo grouptag, si lo hubiera	
	                	//Element eNivel3 = null;		// Para nodo item
	        			//Element eNivel4_1 = null;		// Para nodo headertag, si lo hubiera
	        			//Element eNivel4_2 = null;		// Para nodo headertag - value, si lo hubiera
	        			//Element eNivel4_3 = null;		// Para nodo headertag - label, si lo hubiera
	        			
	                	// Se construye desde las hojas a la raiz.
	                	
	                	// 1.- Se comprueba si hay headerTag y se añade el itemTag.
	                	String strHeaderTag = null;
	                	if( xmibConfigItem != null)
	                		strHeaderTag = xmibConfigItem.getHeaderTag();
	                	String strHeaderValue = null;
	                    if( strHeaderTag != null )	
	                    {
	                    	strHeaderValue = lHeaderTagValues.get(blockId).get(colId);
	                    	if (strHeaderValue == null){
	                    		XSSFRow headerTagRow = sheet.getRow(headerTagRowIds[blockId]);
	                    		XSSFCell headerTagCell = headerTagRow.getCell(colId);
	                    		strHeaderValue = getValueFromCell(headerTagCell);
	                        	if( (strHeaderValue != null) && !("".equalsIgnoreCase(strHeaderValue))) {
	                        		lHeaderTagValues.get(blockId).put(colId,strHeaderValue);
	                        	} else {
	                        		// Si no esta definida la cabecera (strHeaderValue), no se guarda el valor
	                        		// de la celda en el XML, y se pasa a la siguiente celda.
                        		
    	                        	// Antes de procesar la siguiente celda, hay que actualizar el contador de grupo, si se esta utilizando.
    	                        	if ( (xmbbConfigBlock != null) &&
    	                        			(xmibConfigItem != null) &&
    	                        			(xmibConfigItem.getGroupTagCount() >0) ) {
    	    	                		iGroupTagCount++;
    	    	                		
    	    	                		// Si fuera el ultimo elemento del grupo, hay que añadir el grupo al bloque.
    	    	                		if(iGroupTagCount == xmibConfigItem.getGroupTagCount()){

    	    	                			// Podria ocurrir que no se hubiera generado bloque o grupo.
    	    	                			if( eNivel1 != null && eNivel2 != null)
    	    	                				eNivel1.appendChild(eNivel2);	        
    	    		                		// Se resetean eNivel2 y el contador, para cuando haya que definir otro grupo.
    	    	                			eNivel2 = null;				
    	    	                			iGroupTagCount = 0;
    	    	                		}
    	                        	}
    	                    		continue column_loop;
	                        	}	                        	
	                    	}
	                    	
	                    	eNivel4_1 = doc.createElement(strHeaderTag);
	                    	eNivel4_1.appendChild(doc.createTextNode(strHeaderValue));
	                    	
	                    	eNivel4_2 = doc.createElement(strValueTag);
	                    	eNivel4_2.appendChild(doc.createTextNode(value));
                    	
		                	eNivel3 = doc.createElement(tmpTag);
	                		eNivel3.appendChild(eNivel4_1);
	                		eNivel3.appendChild(eNivel4_2);	
	                		
	                    	// Se comprueba si hay labelTag y si lo hay, se añade al itemTag.
		                	String strLabelTag = xmibConfigItem.getLabelTag();
		                	String strLabelValue = xmibConfigItem.getLabelValue();
	                    	if( strLabelTag != null ) {
	                    		eNivel4_3 = doc.createElement(strLabelTag);
	                    		if( strLabelValue != null ) {
	    	                    	eNivel4_3.appendChild(doc.createTextNode(strLabelValue));
	                    		}
	                    		
		                		eNivel3.appendChild(eNivel4_3);	
	                    	}
                		
	                    }
	                    else {		// Si no hay headerTag, el itemTag toma directamente el valor.	
	                    	
		                	// Se prepara el item.
		                	eNivel3 = doc.createElement(tmpTag);
		                	eNivel3.appendChild(doc.createTextNode(value));
	                    }
	                	

	                	// 2.- Se prepara el group tag si lo hay.
	                	int iGroupTagMaxCount = xmibConfigItem.getGroupTagCount();		// Num. total de items que debe haber en el grupo.
	                	if( iGroupTagMaxCount > 0) {
	                		if( eNivel2 == null) {
	                			eNivel2 = doc.createElement(xmibConfigItem.getGroupTag());
	                		}
	                		
	                		eNivel2.appendChild(eNivel3);
	                		iGroupTagCount++;
	                		
	                		if(iGroupTagCount == iGroupTagMaxCount){

		                		eNivel1.appendChild(eNivel2);	        
		                		// Se resetean eNivel2 y el contador, para cuando haya que definir otro grupo.
	                			eNivel2 = null;				
	                			iGroupTagCount = 0;
	                		}
	                			
	                	} else {		// Si no hay groupTag, se añade el item directamente al bloque.
	                	
		                	// En este punto debería haber elemento de nivel1, por estar en una seccion para la que hay que guardar datos.
		                	if( eNivel1 != null)  {              	
		                		eNivel1.appendChild(eNivel3);
		                	}
	                	}
	
	                } // FIN BUCLE DE COLUMNAS. column_loop
	                
	                // Solo añado el nivel1 al root si se ha acabado de procesar la fila. 
	                // Tambien hay que tener en cuenta si hay eNivel0 (tag para sheet).
	            	if( eNivel1 != null){
	    	            if(eNivel0 != null){	            		
	    	            	eNivel0.appendChild(eNivel1);
	    	            }
	    	            else {
	    	            	rootElement.appendChild(eNivel1);
	    	            }
	            	}
	        		
	            }	// FIN BUCLE DE FILAS. row_loop

	            // Si al acabar de procesar todas las filas de una sheet, no se ha encontrado
	            // la marca de final del ultimo bloque (en caso de no utilizarse el sistema de 
	            // offset desde la marca de la primera fila), se lanza una excepcion
	            // porque significa que el usuario ha borrado la marca de final de bloque 
	            // y el xml que se vaya a generar, va a contener errores.
        		if(aiExitRowOffsetFromHeaderRow[numBlocksInSheet-1] == -1) {		// Si no se ha definido este offset, se busca marca de Exit.

        			// Si el identificador de final del ultimo bloque no se ha actualizado, sigue con el valor inicial.
        			if( blockEndRowIds[numBlocksInSheet-1] == initialRowId ) {
                		String tmpEndLimit = xmsbConfigSheet.getTmConfigBlocks().get(numBlocksInSheet-1).getEndLimit();
                		
                		if(tmpEndLimit == null){
                			// Si no se ha encontrado tag de final de bloque en la configuracion, se devuelve un error interno.
                			throw new Exception(msgs.getString("error_configuring_block") + " " + xmsbConfigSheet.getSheetName() + ".");
                		}
                		else {
                    		String errorMsg = msgs.getString("not_found_end_block_tag");
        					errorMsg = errorMsg.replace(strBlockTagLabel, tmpEndLimit);
        					errorMsg = errorMsg.replace(strSheetLabel, xmsbConfigSheet.getSheetName());
	                    		
                    		throw new ValidationException(errorMsg);
						}                		
                	}
        		}
	            
	            if(eNivel0 != null)
	            	rootElement.appendChild(eNivel0);
	            
        	} // sheet_loop
        	
        	
    		// write the content into xml file
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		DOMSource source = new DOMSource(doc);

    		// Output to console for testing
    		StringWriter strWriter = new StringWriter();
    		StreamResult result = new StreamResult(strWriter);

    		transformer.transform(source, result);
    		xml = strWriter.toString();
        } 
        catch (Exception e) 
        {
        	log.error("Error in cell rowId = " + rowId + ", colId = " + colId, e);
        	throw e;
        }
        finally {
        	if( workbook != null )
        		workbook.close();
        }
	
		return xml;
	}

	
	private String getValueFromCell( XSSFCell cell ) {
		
		XSSFWorkbook workbook = cell.getRow().getSheet().getWorkbook();
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();		
    	DataFormatter formatter = new DataFormatter();
    	String value = null;
    	String tmpValue = null;
    	String tmpCellStyle = cell.getCellStyle().getDataFormatString();
    	
        //Check the cell type and format accordingly
        switch (cell.getCellType()) 
        {
            case Cell.CELL_TYPE_NUMERIC:	// Se hace el mismo tratamiento para celdas de tipo numerico y string.
            case Cell.CELL_TYPE_STRING:
            	// Si se extrae el valor directamente, se obtienen valores numericos para fechas y cifras.
            	//value = Double.toString(cell.getNumericCellValue());
            	// En este caso, se obtienen datos formateados, con el formato definido en la excel. Se obtiene el mismo valor
            	// que se presenta en la excel.
            	tmpValue = formatter.formatCellValue(cell);
            	// Por el momento, asigno a value el valor temporal, sin haber formateado la fecha. 
            	// Si en el bucle no se encuentra el patron de fecha, queda directamente con el valor temporal, sin formatear.
            	value = tmpValue;
            	
            	// Cambio de formato de fechas
            	date_format_loop:
            	for( int i=0; i < asExcelDateFormat_ExcelPatterns.length ; i++ ) {
                	// Si encuentro un formato de fecha, cambio el formato de fecha. Si se encuentra un formato, no se busca mas. 
                	if( asExcelDateFormat_ExcelPatterns[i].equalsIgnoreCase(tmpCellStyle) ) {
                		try {
                			Date tmpDate = new SimpleDateFormat(asExcelDateFormat_JavaPatterns[i], Locale.getDefault()).parse(tmpValue);
                    		value = new SimpleDateFormat(strXMLDateFormat).format(tmpDate); 
                		} catch(java.text.ParseException pe) {
                        	log.debug("Error parsing date format: RowId = " + cell.getRowIndex() + " ColId = " + cell.getColumnIndex() +
                                			" CellType = " + cell.getCellType());
                    		value = tmpValue;                    	
                		}
                		break date_format_loop;
                	}
            	}	
            	
                break;
            case Cell.CELL_TYPE_FORMULA: 
                // Para resolver formulas y luego aplicar el formato de la celda.
            	tmpValue = formatter.formatCellValue(cell, evaluator);
            	// Por el momento, asigno a value el valor temporal, sin haber formateado la fecha. 
            	// Si en el bucle no se encuentra el patron de fecha, queda directamente con el valor temporal, sin formatear.
            	value = tmpValue;
            	
            	// Cambio de formato de fechas            	
            	date_format_loop2:
            	for( int i=0; i < asExcelDateFormat_ExcelPatterns.length ; i++ ) {
                	// Si encuentro un formato de fecha, cambio el formato de fecha. Si se encuentra un formato, no se busca mas. 
                	if( asExcelDateFormat_ExcelPatterns[i].equalsIgnoreCase(tmpCellStyle) ) {
                		try {
                			Date tmpDate = new SimpleDateFormat(asExcelDateFormat_JavaPatterns[i], Locale.getDefault()).parse(tmpValue);
                    		value = new SimpleDateFormat(strXMLDateFormat).format(tmpDate); 
                		} catch(java.text.ParseException pe) {
                        	log.debug("Error parsing date format: RowId = " + cell.getRowIndex() + " ColId = " + cell.getColumnIndex() +
                                			" CellType = " + cell.getCellType());
                    		value = tmpValue;                    	
                		}
                		break date_format_loop2;
                	}
            	}
            	
                break;
            case Cell.CELL_TYPE_BOOLEAN:
            	value = Boolean.toString(cell.getBooleanCellValue());
                break;
            default:
            	log.debug("This cell type cannot be inserted in xml: RowId = " + cell.getRowIndex() + " ColId = " + cell.getColumnIndex() +
            			" CellType = " + cell.getCellType());
            	/* Valores posibles:
            	 * 	CELL_TYPE_NUMERIC	0
					CELL_TYPE_STRING	1
					CELL_TYPE_FORMULA	2
					CELL_TYPE_BLANK		3
					CELL_TYPE_BOOLEAN	4
					CELL_TYPE_ERROR		5
            	 */
            	// Si no puedo leer la celda, dejo el valor a null y mas adelante, se seguira leyendo otra fila.
            	value = null;
        }
        
        return value;
	}
	
}

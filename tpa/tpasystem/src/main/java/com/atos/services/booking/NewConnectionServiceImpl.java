package com.atos.services.booking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import java.text.SimpleDateFormat;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.OpTemplateBean;
import com.atos.beans.booking.NewConnectionBean;
import com.atos.beans.booking.OperationFileBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.booking.NewConnectionFilter;
import com.atos.mapper.booking.NewConnectionMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;


@Service("newConnectionService")
public class NewConnectionServiceImpl implements NewConnectionService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9130821696499869216L;

	@Autowired
	private NewConnectionMapper ncMapper;

	
	private static final String sPdfText1 = "This agreement is between PTT PLC Gas Transmission Company Limited and " +
			"New Connection Applicant _____________________, hereinafter the Applicant.";
	private static final String sPdfText2Template = "By virtue of the present contract PTT Gas Transmission Company Limited " +
				"grants the Applicant the natural gas supply at the point for nomination purposes " +
				"AAAAAAAAAA included in the contract BBBBBBBBBB agreed with the shipper CCCCCCCCCC with " +
				"ERC license DDDDDDDDDD. Point is equipped with metering device  ____________ that " +
				"fulfils with standards and codes validated by the below signatory parties:";
	
	private static final String sPointListLabel = "AAAAAAAAAA";
	private static final String sContractLabel = "BBBBBBBBBB";
	private static final String sShipperLabel = "CCCCCCCCCC";
	private static final String sERCLicenseLabel = "DDDDDDDDDD";
	private static final String sNoValueString = "__________";
	private static final int iPdfCharsInLine = 85;				// Para calcular aprox. el num. de lineas y escoger una plantilla.
	private static final String sGeneratedPdfFileName = "NewConnection_"; 

	private static final String categoryCode = "BOOKING";
	private static final String fileType = "NEW_CONNECTION_PDF";		// Para buscar la template.
	protected static final String longTerm = "LONG";
	protected static final String mediumTerm = "MEDIUM";
	protected static final String shortFirmTerm = "SHORT_FIRM";
	protected static final String shortNonFirmTerm = "SHORT_NON_FIRM";

	
	public Map<BigDecimal, Object> selectShipperId(){
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
			List<ComboFilterNS> list = ncMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	
	public List<NewConnectionBean> search(NewConnectionFilter filter) {
	
		return ncMapper.selectNewConnectionCapacityRequests(filter);
	}

	
	public void getFileByOpFileId(NewConnectionBean nc) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		BigDecimal tmpIdnOperationFile = nc.getOperationFileId();
		if(tmpIdnOperationFile == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));
		
		List<OperationFileBean> lData = ncMapper.getFileByOpFileId(tmpIdnOperationFile);
		if(lData == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));
		
		// Solo se va a tener un fichero por cada capacity request.
		OperationFileBean tmpOfBean = lData.get(0);
		if(tmpOfBean == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));		

		byte[] ba = tmpOfBean.getBinaryData();
		if(ba == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));		

		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		DefaultStreamedContent tmpSCont = 
				new DefaultStreamedContent(bais, 
											"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 
											nc.getXlsFileName());
		nc.setXlsFile(tmpSCont);
	}

	
	public void generatePdfFile(NewConnectionBean nc, BigDecimal _systemId) throws Exception {
		
		// 1.- Obtener datos adicionales y la cadena a insertar en el fichero. (Segundo parrafo de la primera pagina).
		String sPdfText2 = getPdfText2(nc);

		// 2.- Obtener plantilla PDF
		byte[] srcTemplateData = getTemplateData(sPdfText2.length(), _systemId);
	
		// 3.- Generar fichero PDF.
		byte[] ba = getFilledPdfData(sPdfText2, srcTemplateData);			

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strToday = dateFormat.format(new Date());
		
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		DefaultStreamedContent tmpSCont = 
				new DefaultStreamedContent(bais, 
											"application/pdf", 
											sGeneratedPdfFileName + nc.getShipperCode() + "_" + strToday + ".pdf");
		nc.setPdfFile(tmpSCont);
	}

	
	// Devuelve el texto del segundo parrafo de la primera pagina de la plantilla.
	private String getPdfText2(NewConnectionBean nc) {

		String sResult = sPdfText2Template;
		// AAA – serán puntos de nominación indicados en el contrato (solicitud de contrato) como New Connection,
		List<String> lNewConnPointCodes = ncMapper.selectNewConnectionPointCodesByCapRequest(nc.getCapacityRequestId());
		String sNewConnPointCodes = null;
		for(int i = 0; i<lNewConnPointCodes.size(); i++) {
			
			if( sNewConnPointCodes ==  null )
				sNewConnPointCodes = lNewConnPointCodes.get(i);
			else
				sNewConnPointCodes += lNewConnPointCodes.get(i);
			
			if(i != (lNewConnPointCodes.size()-1) )		// Se añaden comas menos al ultimo.
				sNewConnPointCodes += ", ";
		}
		if( sNewConnPointCodes != null ) 
			sResult = sResult.replace(sPointListLabel, sNewConnPointCodes);
		else
			sResult = sResult.replace(sPointListLabel, sNoValueString);		// Este caso no se debe dar, porque se supone que existen puntos New Connection
		
		// BBB – código de contrato (en caso de una solicitud de capacidad todavía no aprobada, no se dispondrá de este dato)
		List<String> lContractCodes = ncMapper.selectContractCodeByCapRequest(nc.getCapacityRequestId());
		String strContractCode = null;
		if( lContractCodes.size() > 0 ) {
			strContractCode = lContractCodes.get(0);	// Cada capacity request, puede tener 1 contrato como mucho.
			sResult = sResult.replace(sContractLabel, strContractCode);
		} else {
			sResult = sResult.replace(sContractLabel, sNoValueString);		// Puede haber capacity requests sin contrato.
		}
			
		
		// CCC – código del shipper al que pertenece el contrato/solicitud de contrato
		// Por ahora no se usa la descripcion del shipper porque iText no tiene Fonts para poder escribir
		// caracteres tailandeses y en la descripcion se usaran estos caracteres. 
		// Si hubiera que usar la descripcion habria que cargar nuevas fuentes de tipo de letra. 
		sResult = sResult.replace(sShipperLabel, nc.getShipperCode());
		
		// DDD – ERC licence indicado en el módulo DAM para el shipper del contrato/solicitud de contrato (ERC License ID de la entidad Shippers)
		List<String> lERCLicenseIds = ncMapper.selectERCLicenseIdByShipperId(nc.getShipperId());
		String strERCLicenseId = null;
		if( lERCLicenseIds.size() > 0 ) {
			strERCLicenseId = lERCLicenseIds.get(0);	// Cada shipper, puede tener un ERC License como mucho.
			if( strERCLicenseId != null) 
				sResult = sResult.replace(sERCLicenseLabel, strERCLicenseId);
			else
				sResult = sResult.replace(sERCLicenseLabel, sNoValueString);				
		} else {
			sResult = sResult.replace(sERCLicenseLabel, sNoValueString);
		}
		
		return sResult;
	}	

	
	private byte[] getTemplateData( int _firstParagraphLength, BigDecimal _systemId ) throws Exception {
		
		String term = null; 
				
		int iNumLineas = _firstParagraphLength / iPdfCharsInLine;
		// Se han guardado plantillas PDF en BD dejando mas o menos hueco para el primer parrafo del documento.
		// Hay 4 plantillas guardadas de menor a mayor espacio, una por cada term.
		if( iNumLineas <= 8 )
			term = shortNonFirmTerm;
		else if ( iNumLineas <= 10 ) 
			term = shortFirmTerm;
		else if ( iNumLineas <= 12 ) 
			term = mediumTerm;
		else
			term = longTerm;

		return getTemplateFile(term, _systemId);
	}

	
	private byte[] getTemplateFile( String _term, BigDecimal _systemId ) throws Exception {
	   	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

	   	byte[] ba = null;
		OpTemplateBean otbEntrada = new OpTemplateBean();
		otbEntrada.setOpCategoryCode(categoryCode);
		otbEntrada.setOpTermCode(_term);
		otbEntrada.setFileType(fileType);
		otbEntrada.setSystemId(_systemId);
		
		List<OpTemplateBean> lotbSalida = ncMapper.getOpTemplateByCatTermFiletypeSystem(otbEntrada);
		if( lotbSalida.size() == 0) {
    		throw new Exception(msgs.getString("new_conn_template_file") + " " + msgs.getString("not_found"));
		}
		else {
			// Solo se va a tener un fichero por cada template.
			OpTemplateBean otbPDFTemplate = lotbSalida.get(0);
			ba = otbPDFTemplate.getBinaryData();
		}
		
		return ba;
	}

	
	private byte[] getFilledPdfData(String _sPdfText2, byte[] _srcTemplateData) throws Exception {
		// Lector de la plantilla para tomar formato y paginas 1 y 2.
        PdfReader reader = new PdfReader(_srcTemplateData);

        // Create output PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document();
        PdfWriter writer = PdfWriter.getInstance(doc, baos);
        doc.open();
        PdfContentByte cb = writer.getDirectContent();

        // Load existing PDF
        doc.setPageSize(reader.getPageSize(1));

        // Se copia la primera pagina de la plantilla.
        PdfImportedPage page = writer.getImportedPage(reader, 1); 
        doc.newPage();
        cb.addTemplate(page, 0, 0);

        // Add your new data / text here
        Font timesRomanFont = new Font(FontFamily.TIMES_ROMAN, 12f);
        // Se anaden un par de lineas al principio.
        String lineSeparator = System.getProperty("line.separator");
        Paragraph p0 = new Paragraph(lineSeparator + lineSeparator );
        doc.add(p0);        
        // Se anade el primer parrafo.
        Paragraph p = new Paragraph(sPdfText1, timesRomanFont);
        p.setIndentationLeft(36);
        p.setIndentationRight(36);
        doc.add(p); 
        // Se anade el segundo parrafo.
        Paragraph p2 = new Paragraph(_sPdfText2, timesRomanFont);
        p2.setIndentationLeft(36);
        p2.setIndentationRight(36);
        doc.add(p2); 
        
        // Se copia la segunda pagina de la plantilla.
        page = writer.getImportedPage(reader, 2); 
        doc.newPage();
        cb.addTemplate(page, 0, 0);
        
        doc.close();    
        reader.close();

		return baos.toByteArray();		
	}
}

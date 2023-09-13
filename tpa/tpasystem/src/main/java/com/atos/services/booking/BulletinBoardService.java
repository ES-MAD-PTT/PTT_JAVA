package com.atos.services.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.primefaces.model.StreamedContent;

import com.atos.beans.LanguageBean;
import com.atos.beans.UserBean;
import com.atos.beans.booking.BulletinBoardBean;
import com.atos.beans.booking.BulletinBoardMailBean;
import com.atos.beans.booking.CapacityRequestSubmissionBean;
import com.atos.beans.booking.OperationFileBean;
import com.atos.views.ChangeSystemView;

public interface BulletinBoardService extends Serializable {

	public List<BulletinBoardBean> search(UserBean _user, LanguageBean _lang);
	// CH011 - 05/09/16 - Se anade a esta pagina la funcionalidad de CRSubmission de descarga de template y envio ficheros excel.
    public StreamedContent selectTemplateFile( String _termCode, ChangeSystemView _system ) throws Exception;
	public CapacityRequestSubmissionBean saveFile( String _termCode, OperationFileBean _ofbfile, UserBean _user, LanguageBean _lang, ChangeSystemView _system ) throws Exception;	
	public BulletinBoardMailBean getInfoMailBulletingBoard (BigDecimal idn_contract_request);
}

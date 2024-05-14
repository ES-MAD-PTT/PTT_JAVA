package com.atos.utils;

public class Constants {

	static public String ONSHORE = "ONSHORE";
	static public String OFFSHORE = "OFFSHORE";
	
	static public String UPDATE = "UPDATE";
	static public String DELETE = "DELETE";
	static public String NEW = "NEW";
	static public String READ = "READ";

	// Message categories
	static public int ERROR = 1;
	static public int WARNING = 2;
	static public int INFO = 3;

	static public String ERROR_S = "ERROR";
	static public String WARNING_S = "WARNING";
	static public String INFO_S = "INFO";
	
	//static public String[] head_menu = {"DAM","BOOKING","NOMINATIONS","METERING","ALLOCATION","BALANCING","TARIFF","FORECAST","MAINTENANCE","ALARMS","QUALITY","GENERAL"};//
	
	static public String[] head_menu = {"DAM","BOOKING","FORECAST","MAINTENANCE","NOMINATIONS","ALARMS","QUALITY","METERING","ALLOCATION","BALANCING","TARIFF","GENERAL"};
	static public String[] notifications_head_menu = {"BALANCING","MOC","BOOKING","CONTRACT","METERING","TARIFF","ALLOCATION","QUALITY","MAINTENANCE","NOMINATION","EVENT"};
	
	static public String SHIPPER = "SHIPPER";
	static public String OPERATOR = "OPERATOR";
			
	static public String DAILY = "DAILY";
	static public String WEEKLY = "WEEKLY";
	
	static public String NOMINATION = "NOMINATION";
	static public String STANDARD_RECEPTION = "STANDARD.RECEPTION";
	static public String RENOMINATION_RECEPTION = "RENOMINATION.RECEPTION";
	
	static public String INTERNAL_GENERATION = "INTERNAL_GENERATION";
	static public String INTERMEDIATE = "INTERMEDIATE";

	
	// Nomination Areas
	static public String EAST = "EAST"; 
	static public String WEST = "WEST"; 
	static public String MIX = "EAST-WEST"; 
	static public String O_EAST = "OFSH";
	
	// Nomination Actions
	static public final String ACCEPT = "ACCEPT";
	static public final String REJECT = "REJECT";
	static public final String SAVE = "SAVE";
	// CH031 - 05/09/16 - PPM se genera una variable status en funcion de los valores de is_valid e is_responded.
	// Nomination Status
	static public String Accepted = "Accepted";
	static public String Rejected = "Rejected";
	static public String WaitingForResponse = "Waiting For Response";
	
	// Maintenance groups
	static public String ENGINEERING = "ENGINEERING";
	static public String OPERATIONS = "OPERATIONS";
	
	// Maintenance status
	static public String SUBMITTED = "SUBMITTED";
	static public String PUBLISHED = "PUBLISHED";
	static public String CLOSED = "CLOSED";
	static public String DELETED = "DELETED";
	
	// Days of week
	static public final int SUNDAY = 0;
	static public String SUNDAY_S = "SUNDAY";
	static public String SUN = "SUN";
	static public final int MONDAY = 1;
	static public String MONDAY_S = "MONDAY";
	static public String MON = "MON";
	static public final int TUESDAY = 2;
	static public String TUESDAY_S = "TUESDAY";
	static public String TUE = "TUE";
	static public final int WEDNESDAY = 3;
	static public String WEDNESDAY_S = "WEDNESDAY";
	static public String WED = "WED";
	static public final int THURSDAY = 4;
	static public String THURSDAY_S = "THURSDAY";
	static public String THU = "THU";
	static public final int FRIDAY = 5;
	static public String FRIDAY_S = "FRIDAY";
	static public String FRI = "FRI";
	static public final int SATURDAY = 6;
	static public String SATURDAY_S = "SATURDAY";
	static public String SAT = "SAT";
 
	// Operation Categories
	static public String FORECASTING = "FORECASTING";
	static public String CONTRACT = "CONTRACT";
	static public String METERING = "METERING";
	// Operation Terms
	static public String LONG = "LONG";
	static public String MEDIUM = "MEDIUM";
	static public String SHORT = "SHORT";
	static public String SHORT_FIRM = "SHORT_FIRM";
	static public String SHORT_NON_FIRM = "SHORT_NON_FIRM";
	static public String NOT_APPLY = "NOT-APPLY";
	
	//Zones
	static public String WI="WI";
	static public String CH4="CH4";
	static public String O2="O2";
	static public String CO2="CO2";
	static public String S="S";
	static public String OtherParticles="OtherParticles";
	static public String HCDewPoint="HCDewPoint";
	static public String HV="HV";
	static public String C2="C2";
	static public String N2="N2";
	static public String H2S="H2S";
	static public String Hg="Hg";
	static public String H2O="H2O";
	static public String CO2_N="CO2_N";

	// Capacity Request - Status
	// static public String SUBMITTED = "SUBMITTED";	Ya esta definido arriba.
	static public String ACCEPTED = "ACCEPTED";
	static public String COMPLETED = "COMPLETED";
	static public String PTT_REJECTED = "PTT_REJECTED";
	static public String SHIPPER_REJECTED = "SHIPPER_REJECTED";
	static public String Delete ="Delete";
	static public String Reject = "Reject";

	// Contract Attach Types
	static public String BANK_GUARANTEE = "BANK_GUARANTEE";
	static public String SIGNED_CONTRACT = "SIGNED_CONTRACT";	
	static public String OTHER = "OTHER";
	
	// Generation Codes para generar codigos de elementos, invocando al procedimiento almacenado pck_id_generation.get_id desde SystemParameterMapper.
	static public final String NOMINATION_DAILY = "NOMINATION-DAILY";
	static public final String NOMINATION_WEEKLY = "NOMINATION-WEEKLY";
	//static public final String FORECASTING = "FORECASTING";			Ya definido mas arriba.
	static public final String BOOKING_CAPACITY = "BOOKING.CAPACITY";
	static public final String ALARM = "ALARM";
	static public final String MAINTENANCE = "MAINTENANCE";
	static public final String ALLOCATION_REVIEW_POINT = "ALLOCATION.REVIEW.POINT";
	static public final String ALLOCATION_REVIEW_CONCEPT = "ALLOCATION.REVIEW.CONCEPT";
	static public final String OFFSPEC_GAS = "OFFSPEC.GAS";
	static public final String METERING_INPUT = "METERING.INPUT";
	static public final String TARIFF_CHARGE = "TARIFF.CHARGE";


	
	// Identificador de la aplicacion cuando invoca a webservice de PMIS DWH
	static public final String TPA = "TPA";
	
	// En operaciones de bloqueo en BD, codigo de proceso.
	static public final String METERING_QUERY = "METERING_QUERY";
	static public final String ACUM_INVENTORY = "ACUM_INVENTORY";
	static public final String BASE_INVENTORY = "BASE_INVENTORY";
	
	// Webservice codes
	static public final String ACUM_WEBSERVICE = "ACUM.WEBSERVICE";
	static public final String BASE_WEBSERVICE = "BASE.WEBSERVICE";
	
	
	// Status para el log de webservices en BD.
	static public final String RUNNING_S = "RUNNING";
	static public final String OK_S = "OK";
	//static public String ERROR_S = "ERROR";	 // Ya definido arriba.
	
	// Status para metering input.
	static public final String NEW_S = "NEW";
	//static public final String OK_S = "OK";	// Ya definido arriba.
	//static public String ERROR_S = "ERROR";	 // Ya definido arriba.
	
	// Status para Allocation Review.
	// Este estado NOT_REVIEWED no existe en BD. Se usa para que el usuario
	// especifique que quiere ver los repartos sin revision por el shipper.
	static public final String NOT_REVIEWED = "NOT_REVIEWED";
	static public final String INITIAL = "INITIAL";
	//static public final String ACCEPTED = "ACCEPTED";		// Ya definido previamente.
	static public final String REJECTED = "REJECTED";
	static public final String ALLOCATED = "ALLOCATED";
	
	
	static public final String CAPACITY_CHARGE= "CAPACITY.CHARGE";
	static public final String COMMODITY_CHARGE = "COMMODITY.CHARGE";
	static public final String OVERUSAGE_CHARGE = "OVERUSAGE.CHARGE";
	static public final String IMBALANCE_PENALTY_CHARGE = "IMBALANCE.PENALTY.CHARGE";
	 
	static public final String ENTRY="ENTRY";
	static public final String EXIT= "EXIT";
	static public final String Imbalance_Penalty_Charge = "Imbalance Penalty Charge";
	static public final String Bac_Adjustment_charge= "Balancing Adjustment Charge (BAC)";
	
	static public final String AES_encription_password = "238nf239fl36541sdgflk232LKJflkj23rlj2jf";
}

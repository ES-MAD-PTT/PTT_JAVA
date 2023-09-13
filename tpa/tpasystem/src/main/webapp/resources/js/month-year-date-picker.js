/**
 * 
 */

function CalendarHandler(hiddenField, pickerField) {
	//console.log('constructor for ' + hiddenField)
	var handler= {};
		if(!pickerField){
			pickerField = hiddenField + 'out';
		}
		//console.log('pickerField ' + pickerField )
		handler.activeDate=new Date();
		handler.setDate=  function (yearMonth, pickerField) {
			if(!yearMonth){
				return;
			}
			var pickerFieldSelector = "[id$='" + pickerField + "']";
			var hiddenFieldSelector = "[id$='" + hiddenField + "']";
			var ym = yearMonth.split('-');
			//console.log('seeting value for' + pickerField)
			dd = new Date(ym[0], ym[1], 1);
			$(pickerFieldSelector).datepicker('option', 'defaultDate', dd);
			$(pickerFieldSelector).datepicker('setDate', dd);
			$(pickerFieldSelector).keyup(function(e) {
				if(e.keyCode == 8 || e.keyCode == 46) {
					$(pickerFieldSelector).datepicker( "setDate", null );
					$(pickerFieldSelector).datepicker( "hide" );
					$(hiddenFieldSelector).val(null);
				}
			});

		};
	init(hiddenField, pickerField);
	return handler;
}

	function initCal(calId){
		var taca = new CalendarHandler(calId);
		//console.log("initCal " + $("[id$='" + calId +"']").val());
		taca.setDate($("[id$='" + calId +"']").val(), calId+'out');
	}

function init(hiddenField, pickerField) {
	if(!pickerField){
		pickerField = hiddenField + 'out';
	}
	var pickerFieldSelector = "[id$='" + pickerField + "']";
	var hiddenFieldSelector = "[id$='" + hiddenField + "']";
	$(pickerFieldSelector).datepicker({
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		dateFormat : 'MM yy',
		onClose : function(dateText, inst) {
			this.activeDate = new Date(inst.selectedYear, inst.selectedMonth, 1);
			$(this).datepicker('setDate', this.activeDate);
			var strDate = inst.selectedYear + '-' + inst.selectedMonth;
			$(hiddenFieldSelector).val(strDate);
			//console.log('triggering')
			//$(inst).trigger('change')
			$(pickerFieldSelector).trigger('change')
			
		},
		beforeShow : function(input, inst) {
			
			if (this.activeDate) {
				$(this).datepicker('option', 'defaultDate', this.activeDate)
			}
		}
	});
	
	

}
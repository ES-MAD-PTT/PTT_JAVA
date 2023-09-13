package com.atos.filters.booking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CapacityPublicationFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3355771927748637701L;
	
	private Date startDate;
	private Date endDate;
	private Integer selectedYear;
	private boolean selectAll=true;
	// Por defecto, se establece sistema onshore, aunque debera inicializarse al cargar la vista, porque afecta al
	// resto de areas de abjo.
	// En caso de onshore las areas de offshore estaran a false y no se podrán modificar, y no intervienen en el calculo de selectAll.
	// En caso de offshore, al revés.
	private boolean onshoreSystem=true;
	// Areas onshore
	// A1, A2, A3, B, C1, C2, C3, C4, C5, C6, D, E, F1, F2, G, H, R, S, 
	// X1, X2, X3 e Y	
	private boolean area_a1=true;
	private boolean area_a2=true;
	private boolean area_a3=true;
	private boolean area_b=true;
	private boolean area_c1=true;
	private boolean area_c2=true;
	private boolean area_c3=true;
	private boolean area_c4=true;
	private boolean area_c5=true;
	private boolean area_c6=true;
	private boolean area_d=true;
	private boolean area_e=true;
	private boolean area_f1=true;
	private boolean area_f2=true;
	private boolean area_g=true;
	private boolean area_h=true;
	private boolean area_r=true;
	private boolean area_s=true;
	private boolean area_x1=true;
	private boolean area_x2=true;
	private boolean area_x3=true;
	private boolean area_y=true;

	
//	private boolean zone_a=true;
//	private boolean zone_b=true;
//	private boolean zone_c=true;
//	private boolean zone_d=true;
//	private boolean zone_e=true;
//	private boolean zone_f=true;
//	private boolean zone_g=true;
//	private boolean zone_x=true;
//	private boolean zone_y=true;
	// Areas offshore
//	private boolean zone_ofkn=true;
//	private boolean zone_ofry=true;
//	private boolean zone_of42=true;
//	private boolean zone_of36=true;
//	private boolean zone_of34=true;
	
	private String par_chart;

	private String zones_query;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Integer getSelectedYear() {
		return selectedYear;
	}
	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
		Calendar cal_start = Calendar.getInstance();
		cal_start.set(selectedYear, 0, 1, 0, 0, 0);
		cal_start.set(Calendar.MILLISECOND, 0);
		Calendar cal_end = Calendar.getInstance();
		cal_end.set(selectedYear, 11, 31, 0, 0, 0);
		cal_end.set(Calendar.MILLISECOND, 0);
		this.setStartDate(cal_start.getTime());
		this.setEndDate(cal_end.getTime());
		
	}
	
	
	public boolean isOnshoreSystem() {
		return onshoreSystem;
	}
	public void setOnshoreSystem(boolean onshoreSystem) {
		this.onshoreSystem = onshoreSystem;
		if(this.onshoreSystem){
			setSelectAllOnshore(true);
			setSelectAllOffshore(false);
		} else {
			setSelectAllOnshore(false);
			setSelectAllOffshore(true);
		}
	}
	
	public boolean isSelectAll() {
		return selectAll;
	}
	public void setSelectAll(boolean selectAll) {
		if(this.onshoreSystem)
			setSelectAllOnshore(selectAll);
		else
			setSelectAllOffshore(selectAll);
			
	}
	private void setSelectAllOnshore(boolean selectAll) {
		this.selectAll = selectAll;
		if(selectAll){
			this.area_a1=true;
			this.area_a2=true;
			this.area_a3=true;
			this.area_b=true;
			this.area_c1=true;
			this.area_c2=true;
			this.area_c3=true;
			this.area_c4=true;
			this.area_c5=true;
			this.area_c6=true;
			this.area_d=true;
			this.area_e=true;
			this.area_f1=true;
			this.area_f2=true;
			this.area_g=true;
			this.area_h=true;
			this.area_r=true;
			this.area_s=true;
			this.area_x1=true;
			this.area_x2=true;
			this.area_x3=true;
			this.area_y=true;
		} else {
			this.area_a1=false;
			this.area_a2=false;
			this.area_a3=false;
			this.area_b=false;
			this.area_c1=false;
			this.area_c2=false;
			this.area_c3=false;
			this.area_c4=false;
			this.area_c5=false;
			this.area_c6=false;
			this.area_d=false;
			this.area_e=false;
			this.area_f1=false;
			this.area_f2=false;
			this.area_g=false;
			this.area_h=false;
			this.area_r=false;
			this.area_s=false;
			this.area_x1=false;
			this.area_x2=false;
			this.area_x3=false;
			this.area_y=false;
		}
	}
	private void setSelectAllOffshore(boolean selectAll) {
		this.selectAll = selectAll;
		if(selectAll){
			// No hay areas offshore
		} else {
			// No hay areas offshore
		}
	}
	
	// Areas onshore
	public boolean isArea_a1() {
		return area_a1;
	}
	public void setArea_a1(boolean area_a1) {
		if(this.onshoreSystem){
			this.area_a1 = area_a1;
			if(!this.area_a1){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_a2() {
		return area_a2;
	}
	public void setArea_a2(boolean area_a2) {
		if(this.onshoreSystem){
			this.area_a2 = area_a2;
			if(!this.area_a2){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_a3() {
		return area_a3;
	}
	public void setArea_a3(boolean area_a3) {
		if(this.onshoreSystem){
			this.area_a3 = area_a3;
			if(!this.area_a3){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_b() {
		return area_b;
	}
	public void setArea_b(boolean area_b) {
		if(this.onshoreSystem){
			this.area_b = area_b;
			if(!this.area_b){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_c1() {
		return area_c1;
	}
	public void setArea_c1(boolean area_c1) {
		if(this.onshoreSystem){
			this.area_c1 = area_c1;
			if(!this.area_c1){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_c2() {
		return area_c2;
	}
	public void setArea_c2(boolean area_c2) {
		if(this.onshoreSystem){
			this.area_c2 = area_c2;
			if(!this.area_c2){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_c3() {
		return area_c3;
	}
	public void setArea_c3(boolean area_c3) {
		if(this.onshoreSystem){
			this.area_c3 = area_c3;
			if(!this.area_c3){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_c4() {
		return area_c4;
	}
	public void setArea_c4(boolean area_c4) {
		if(this.onshoreSystem){
			this.area_c4 = area_c4;
			if(!this.area_c4){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_c5() {
		return area_c5;
	}
	public void setArea_c5(boolean area_c5) {
		if(this.onshoreSystem){
			this.area_c5 = area_c5;
			if(!this.area_c5){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_c6() {
		return area_c6;
	}
	public void setArea_c6(boolean area_c6) {
		if(this.onshoreSystem){
			this.area_c6 = area_c6;
			if(!this.area_c6){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_d() {
		return area_d;
	}
	public void setArea_d(boolean area_d) {
		if(this.onshoreSystem){
			this.area_d = area_d;
			if(!this.area_d){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_e() {
		return area_e;
	}
	public void setArea_e(boolean area_e) {
		if(this.onshoreSystem){
			this.area_e = area_e;
			if(!this.area_e){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_f1() {
		return area_f1;
	}
	public void setArea_f1(boolean area_f1) {
		if(this.onshoreSystem){
			this.area_f1 = area_f1;
			if(!this.area_f1){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_f2() {
		return area_f2;
	}
	public void setArea_f2(boolean area_f2) {
		if(this.onshoreSystem){
			this.area_f2 = area_f2;
			if(!this.area_f2){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_g() {
		return area_g;
	}
	public void setArea_g(boolean area_g) {
		if(this.onshoreSystem){
			this.area_g = area_g;
			if(!this.area_g){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_h() {
		return area_h;
	}
	public void setArea_h(boolean area_h) {
		if(this.onshoreSystem){
			this.area_h = area_h;
			if(!this.area_h){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_r() {
		return area_r;
	}
	public void setArea_r(boolean area_r) {
		if(this.onshoreSystem){
			this.area_r = area_r;
			if(!this.area_r){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_s() {
		return area_s;
	}
	public void setArea_s(boolean area_s) {
		if(this.onshoreSystem){
			this.area_s = area_s;
			if(!this.area_s){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_x1() {
		return area_x1;
	}
	public void setArea_x1(boolean area_x1) {
		if(this.onshoreSystem){
			this.area_x1 = area_x1;
			if(!this.area_x1){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_x2() {
		return area_x2;
	}
	public void setArea_x2(boolean area_x2) {
		if(this.onshoreSystem){
			this.area_x2 = area_x2;
			if(!this.area_x2){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_x3() {
		return area_x3;
	}
	public void setArea_x3(boolean area_x3) {
		if(this.onshoreSystem){
			this.area_x3 = area_x3;
			if(!this.area_x3){
				this.selectAll=false;
			}
		}
	}
	public boolean isArea_y() {
		return area_y;
	}
	public void setArea_y(boolean area_y) {
		if(this.onshoreSystem){
			this.area_y = area_y;
			if(!this.area_y){
				this.selectAll=false;
			}
		}
	}
	
	// Areas offshore
	// No hay.

	public ArrayList<String> getAreas(){
		ArrayList<String> zones = new ArrayList<String>();
		if(isArea_a1()){
			zones.add("A1");
		}
		if(isArea_a2()){
			zones.add("A2");
		}
		if(isArea_a3()){
			zones.add("A3");
		}
		if(isArea_b()){
			zones.add("B");
		}
		if(isArea_c1()){
			zones.add("C1");
		}
		if(isArea_c2()){
			zones.add("C2");
		}
	/*	if(isArea_c3()){
			zones.add("C3");
		}*/
		if(isArea_c4()){
			zones.add("C4");
		}
		if(isArea_c5()){
			zones.add("C5");
		}
		if(isArea_c6()){
			zones.add("C6");
		}		
		if(isArea_d()){
			zones.add("D");
		}
		if(isArea_e()){
			zones.add("E");
		}
		if(isArea_f1()){
			zones.add("F1");
		}
		if(isArea_f2()){
			zones.add("F2");
		}
		if(isArea_g()){
			zones.add("G");
		}
		if(isArea_h()){
			zones.add("H");
		}
		if(isArea_r()){
			zones.add("R");
		}
	/*	if(isArea_s()){
			zones.add("S");
		}*/
		if(isArea_x1()){
			zones.add("X1");
		}
		if(isArea_x2()){
			zones.add("X2");
		}
		if(isArea_x3()){
			zones.add("X3");
		}
		if(isArea_y()){
			zones.add("Y");
		}
		return zones;
	}
	public String getZones_query() {
		return zones_query;
	}
	
	public void setZones_query() {
		this.zones_query = "";
		ArrayList<String> zones = this.getAreas();
		for(int i=0;i<zones.size();i++){
			this.zones_query = this.zones_query + zones.get(i) + "#";
		}
	}
	public String getPar_chart() {
		return par_chart;
	}
	public void setPar_chart(String par_chart) {
		this.par_chart = par_chart;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (area_a1 ? 1231 : 1237);
		result = prime * result + (area_a2 ? 1231 : 1237);
		result = prime * result + (area_a3 ? 1231 : 1237);
		result = prime * result + (area_b ? 1231 : 1237);
		result = prime * result + (area_c1 ? 1231 : 1237);
		result = prime * result + (area_c2 ? 1231 : 1237);
		result = prime * result + (area_c3 ? 1231 : 1237);
		result = prime * result + (area_c4 ? 1231 : 1237);
		result = prime * result + (area_c5 ? 1231 : 1237);
		result = prime * result + (area_c6 ? 1231 : 1237);
		result = prime * result + (area_d ? 1231 : 1237);
		result = prime * result + (area_e ? 1231 : 1237);
		result = prime * result + (area_f1 ? 1231 : 1237);
		result = prime * result + (area_f2 ? 1231 : 1237);
		result = prime * result + (area_g ? 1231 : 1237);
		result = prime * result + (area_h ? 1231 : 1237);
		result = prime * result + (area_r ? 1231 : 1237);
		result = prime * result + (area_s ? 1231 : 1237);
		result = prime * result + (area_x1 ? 1231 : 1237);
		result = prime * result + (area_x2 ? 1231 : 1237);
		result = prime * result + (area_x3 ? 1231 : 1237);
		result = prime * result + (area_y ? 1231 : 1237);
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + (onshoreSystem ? 1231 : 1237);
		result = prime * result + ((par_chart == null) ? 0 : par_chart.hashCode());
		result = prime * result + (selectAll ? 1231 : 1237);
		result = prime * result + ((selectedYear == null) ? 0 : selectedYear.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((zones_query == null) ? 0 : zones_query.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CapacityPublicationFilter other = (CapacityPublicationFilter) obj;
		if (area_a1 != other.area_a1)
			return false;
		if (area_a2 != other.area_a2)
			return false;
		if (area_a3 != other.area_a3)
			return false;
		if (area_b != other.area_b)
			return false;
		if (area_c1 != other.area_c1)
			return false;
		if (area_c2 != other.area_c2)
			return false;
		if (area_c3 != other.area_c3)
			return false;
		if (area_c4 != other.area_c4)
			return false;
		if (area_c5 != other.area_c5)
			return false;
		if (area_c6 != other.area_c6)
			return false;
		if (area_d != other.area_d)
			return false;
		if (area_e != other.area_e)
			return false;
		if (area_f1 != other.area_f1)
			return false;
		if (area_f2 != other.area_f2)
			return false;
		if (area_g != other.area_g)
			return false;
		if (area_h != other.area_h)
			return false;
		if (area_r != other.area_r)
			return false;
		if (area_s != other.area_s)
			return false;
		if (area_x1 != other.area_x1)
			return false;
		if (area_x2 != other.area_x2)
			return false;
		if (area_x3 != other.area_x3)
			return false;
		if (area_y != other.area_y)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (onshoreSystem != other.onshoreSystem)
			return false;
		if (par_chart == null) {
			if (other.par_chart != null)
				return false;
		} else if (!par_chart.equals(other.par_chart))
			return false;
		if (selectAll != other.selectAll)
			return false;
		if (selectedYear == null) {
			if (other.selectedYear != null)
				return false;
		} else if (!selectedYear.equals(other.selectedYear))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (zones_query == null) {
			if (other.zones_query != null)
				return false;
		} else if (!zones_query.equals(other.zones_query))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CapacityPublicationFilter [startDate=" + startDate + ", endDate=" + endDate + ", selectedYear="
				+ selectedYear + ", selectAll=" + selectAll + ", onshoreSystem=" + onshoreSystem + ", area_a1="
				+ area_a1 + ", area_a2=" + area_a2 + ", area_a3=" + area_a3 + ", area_b=" + area_b + ", area_c1="
				+ area_c1 + ", area_c2=" + area_c2 + ", area_c3=" + area_c3 + ", area_c4=" + area_c4 + ", area_c5="
				+ area_c5 + ", area_c6=" + area_c6 + ", area_d=" + area_d + ", area_e=" + area_e + ", area_f1="
				+ area_f1 + ", area_f2=" + area_f2 + ", area_g=" + area_g + ", area_h=" + area_h + ", area_r=" + area_r
				+ ", area_s=" + area_s + ", area_x1=" + area_x1 + ", area_x2=" + area_x2 + ", area_x3=" + area_x3
				+ ", area_y=" + area_y + ", par_chart=" + par_chart + ", zones_query=" + zones_query + "]";
	}	
}

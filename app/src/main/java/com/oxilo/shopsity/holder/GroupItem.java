package com.oxilo.shopsity.holder;

import com.oxilo.shopsity.POJO.CampList;
import com.oxilo.shopsity.POJO.ClickDatum;
import com.oxilo.shopsity.POJO.DeviceDatum;
import com.oxilo.shopsity.POJO.HeatmapDatum;
import com.oxilo.shopsity.POJO.ImprDatum;
import com.oxilo.shopsity.POJO.InVoiceObject;
import com.oxilo.shopsity.POJO.IspDatum;

import java.util.ArrayList;
import java.util.List;

public class GroupItem {
	public String title;
	public List<InVoiceObject> items = new ArrayList<InVoiceObject>();
	public List<CampList> campLists = new ArrayList<CampList>();
	public List<ClickDatum> clickDataList = new ArrayList<>();
	public List<DeviceDatum> deviceDatumsList = new ArrayList<DeviceDatum>();
	public List<ImprDatum> imprDatumsList = new ArrayList<ImprDatum>();
	public List<IspDatum> ispDatumList = new ArrayList<IspDatum>();
	public List<HeatmapDatum>heatmapDatumList = new ArrayList<>();
}

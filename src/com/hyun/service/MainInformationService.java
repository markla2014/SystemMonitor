package com.hyun.service;


import java.util.LinkedList;

import com.hyun.vo.totalCPUpercent;
import com.hyun.vo.totalMasterOverviewInformation;
import com.hyun.vo.diagram.totalDiskSpaceDiagram;

public interface MainInformationService {

	public totalMasterOverviewInformation getMasterInfroamtion();
	public String getMasterLogin(String username, String password,String ipaddress);
	public totalDiskSpaceDiagram perpareTotalDiskSpace(totalMasterOverviewInformation informationTemp);
	public totalCPUpercent perpareTotalCPU(totalMasterOverviewInformation informationTemp);
	public LinkedList<totalDiskSpaceDiagram> perpareTableDiskSpace(totalMasterOverviewInformation informationTemp);
}

package com.hyun.service;


import com.hyun.vo.totalCPUpercent;
import com.hyun.vo.totalMasterOverviewInformation;
import com.hyun.vo.diagram.totalDiskSpaceDiagram;

public interface MainInformationService {

	public totalMasterOverviewInformation getMasterInfroamtion();
	public String getMasterLogin(String username,String password);
	public totalDiskSpaceDiagram perpareTotalDiskSpace(totalMasterOverviewInformation informationTemp);
	public totalCPUpercent perpareTotalCPU(totalMasterOverviewInformation informationTemp);
}

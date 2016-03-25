package com.hyun.service;

import com.hyun.vo.totalMasterOverviewInformation;

public interface MainInformationService {

	public totalMasterOverviewInformation getMasterInfroamtion();
	public String getMasterLogin(String username,String password);
}

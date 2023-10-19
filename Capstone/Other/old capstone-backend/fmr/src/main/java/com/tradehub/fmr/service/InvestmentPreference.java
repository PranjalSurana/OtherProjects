package com.tradehub.fmr.service;

import com.tradehub.fmr.modals.User;

import java.util.ArrayList;
import java.util.List;

public class InvestmentPreference {

    private List<User> userList = new ArrayList<User>();

    public boolean getUserDetails(String clientId) {
        for(User user: userList) {
            if(user.id == clientId) {
                return true;
            }
        }
        return false;
    }

    public void addInvestmentPref(InvestmentPrefModel investPref) {
        if(investPref == null) {
            throw new NullException("Investment Preferance cannot be null");
        }
        if(!find(investPref.getClientId())) {
            throw new IllegalArgumentException("Client Not found");
        }
        listinvestmentpref.add(investPref);
    }

}
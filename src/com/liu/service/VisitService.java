package com.liu.service;

import com.liu.bean.Visit;
import com.liu.dao.VisitDao;

import java.util.HashMap;
import java.util.Map;

public class VisitService {
    public Map addVisit(Visit visit){
        VisitDao visitDao = new VisitDao();
        int i = visitDao.addVisit(visit);
        Map codeMap = new HashMap<>();
        if(i == 1){
            codeMap.put("code",0);
            codeMap.put("msg","ok");
            return codeMap;
        } else {
            codeMap.put("code",400);
            codeMap.put("msg","no");
            return codeMap;
        }
    }
}

package com.liu.service;

import com.liu.bean.Customer;
import com.liu.dao.CustomerDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
    //全查
    public Map selectAllByParam(Map map){
        CustomerDao customerDao = new CustomerDao();
        List<Map> maps = customerDao.selectAllByParam(map);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("data",maps);
        codeMap.put("msg","ok");
        Map countMap = selectAllByParamCount(map);
        int count = (int) countMap.get("data");
        codeMap.put("count",count);
        return codeMap;
    }

    //查总条数
    public Map selectAllByParamCount(Map map){
        Map codeMap = new HashMap();
        CustomerDao customerDao = new CustomerDao();
        int i = customerDao.selectAllByParamCount(map);
        codeMap.put("code",0);
        codeMap.put("data",i);
        codeMap.put("msg","ok");
        return codeMap;
    }

    //增加
    public Map insertCustomer(Customer customer){
        CustomerDao dao = new CustomerDao();
        int i = dao.insertCustomer(customer);
        Map codeMap = new HashMap();
        if(i == 1){
            codeMap.put("code",0);
            codeMap.put("msg","yes");
        }else {
            codeMap.put("code",400);
            codeMap.put("msg","no");
        }
        return codeMap;
    }

    //删除
    public int deleteByCustomerId(Integer id){
        CustomerDao customerDao = new CustomerDao();;
        int i = customerDao.deleteByCustomerId(id);
        return i;
    }
}

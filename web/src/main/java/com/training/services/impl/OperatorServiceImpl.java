package com.training.services.impl;

import com.training.models.Operator;
import com.training.repositories.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorServiceImpl {

    @Autowired
    private OperatorRepository operatorRepository;

    public Operator createOperator(Operator operator){
        return operatorRepository.save(operator);
    }

    public List<Operator> getAllOperators(){
        return operatorRepository.findAll();
    }

}

package com.yasirakbal.ordermanagementsystem.common.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ResourceCollectionNotFoundException extends BusinessException{
    public ResourceCollectionNotFoundException(String resourceName, List<Long> idList) {
        super(
                String.format("Given %s collection not found with id list: %s", resourceName, Arrays.toString(idList.toArray())),
                HttpStatus.NOT_FOUND
        );
        addDetail("resourceName", resourceName);
        addDetail("resourceId", Arrays.toString(idList.toArray()));
    }

    public ResourceCollectionNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

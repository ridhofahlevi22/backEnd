package com.cms.resultobject;

import java.util.List;

import lombok.Data;

public @Data class ResultField {
    public List<Object> contentList;
    public int result=0;
    public List<?> list = null;
    public int totalRec = 0;
 	public int totalPage = 0;
}

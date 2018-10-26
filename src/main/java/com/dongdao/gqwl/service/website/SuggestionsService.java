package com.dongdao.gqwl.service.website;

import com.dongdao.gqwl.mapper.website.DdSuggestionsMapper;
import com.dongdao.gqwl.model.website.DdSuggestions;
import com.dongdao.gqwl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuggestionsService<T> extends BaseService<T> {

    @Autowired
    private DdSuggestionsMapper<DdSuggestions> ddSuggestionsMapper;

    public DdSuggestionsMapper<DdSuggestions> getMapper(){
        return ddSuggestionsMapper;
    }
}

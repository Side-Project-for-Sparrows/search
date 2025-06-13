package com.sparrows.search.search.adapter.in;

import com.sparrows.search.search.model.dto.SaveRequest;
import com.sparrows.search.search.model.dto.SaveResponse;
import com.sparrows.search.search.model.dto.SearchRequest;
import com.sparrows.search.search.model.dto.SearchResponse;
import com.sparrows.search.search.port.in.SearchUsecase;
import com.sparrows.search.search.strategy.SaveStrategy;
import com.sparrows.search.search.strategy.SearchStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchUsecaseImpl implements SearchUsecase {
    private final Map<String,SearchStrategy> searchstrategyMap;
    private final Map<String,SaveStrategy> saveStrategyMap;

    public SearchUsecaseImpl(List<SearchStrategy> searchStrategies,
                             List<SaveStrategy> saveStrategies) {
        this.searchstrategyMap = new HashMap<>();
        this.saveStrategyMap = new HashMap<>();

        for (SearchStrategy s : searchStrategies) {
            searchstrategyMap.put(s.getDomain(), s);
        }

        for (SaveStrategy s : saveStrategies) {
            saveStrategyMap.put(s.getDomain(), s);
        }
    }

    @Override
    public SearchResponse search(SearchRequest request) {
        SearchStrategy strategy = searchstrategyMap.get(request.getDomain());
        SearchResponse response = strategy.search(request);

        return response;
    }

    @Override
    public SaveResponse save(String domain, Object object) {
        SaveStrategy strategy = saveStrategyMap.get(domain);
        SaveRequest request = strategy.fromPayload(object);
        return strategy.save(request);
    }
}
